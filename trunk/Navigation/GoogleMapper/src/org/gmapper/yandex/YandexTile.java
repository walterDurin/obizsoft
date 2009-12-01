package org.gmapper.yandex;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HostConfiguration;
import org.mapnav.exceptions.OutOfRange;
import org.gmapper.BaseTile;

import java.util.List;
import java.util.ArrayList;
import java.util.Properties;
import java.io.IOException;
import java.io.File;

/**
 * User: Vova
 * Time: 20:16:04
 *
 * @author ognivo777@mail.ru
 */
public class YandexTile extends BaseTile {
    private static Log log = LogFactory.getLog(YandexTile.class);

    protected static List<String> satUrls;
    protected static int nextSatUrl =0;
    protected static String traffUrl;
    protected static String statJsUrl;
    protected static List<String> mapUrls;
    protected static int nextMapUrl =0;
    protected static String cacheDir = null;
    protected static HostConfiguration proxySettings = new HostConfiguration();

    protected static String nextSatUrl() {
        if(nextSatUrl !=0 && nextSatUrl == satUrls.size()) {
            nextSatUrl =0;
        }
        return satUrls.get(nextSatUrl++);
    }

    protected static String nextMapUrl() {
        if(nextMapUrl!=0 && nextMapUrl == mapUrls.size()) {
            nextMapUrl =0;
        }
        return mapUrls.get(nextMapUrl++);
    }

    static {
        satUrls = new ArrayList<String>();
        mapUrls = new ArrayList<String>();
        Properties prop = new Properties();
        try {
            prop.load(FileUtils.openInputStream(new File("settings.properties")));
        } catch (IOException e) {
            throw new RuntimeException();
        }

        traffUrl = prop.getProperty("yandex.traffic.url");
        statJsUrl = prop.getProperty("yandex.stat_js.url");

        for(int i=1; i<=4; i++) {
            String url = prop.getProperty("yandex.sat.url"+i);
            if(url!=null)
                satUrls.add(url);
        }

        for(int i=1; i<=4; i++) {
            String url = prop.getProperty("yandex.map.url"+i);
            if(url!=null)
                mapUrls.add(url);
        }

        cacheDir = prop.getProperty("yandex.cahceDir");
        File cacheDirFile = new File(cacheDir);
        if(cacheDirFile==null || !cacheDirFile.isDirectory() || !cacheDirFile.canWrite()){
            log.error("Check your yandex.cahceDir settings property.");
            cacheDirFile = null;
        } else {
            try {
                cacheDir = cacheDirFile.getCanonicalPath();
            } catch (IOException e) {
                log.error(""+e);
                cacheDirFile = null;
            }
        }

        String proxyAddr = prop.getProperty("network.proxy.addr");
        if(proxyAddr!=null && proxyAddr.trim().length()!=0) {
            proxySettings.setProxy(proxyAddr, Integer.parseInt(prop.getProperty("network.proxy.port")));
        }
    }

    public YandexTile(int level, int xNum, int yNum, int type) throws OutOfRange {
        if(level<0 || level>18)
            throw new OutOfRange("min map level is '0'; max level is '17'. Found '"+level+"'");
        int max = (int) Math.pow(2, level);
        if(xNum<0 || xNum>max)
            throw new OutOfRange(xNum);
        if(yNum<0 || yNum>max)
            throw new OutOfRange(yNum);
        if(type!=MAP_TYPE_MAP && type!=MAP_TYPE_SAT && type!=MAP_TYPE_HYB && type!=YA_MAP_TYPE_TAFFIC)
            throw new OutOfRange(type);

        //ToDo проверка номеров блоков в зависимости от уровня
        this.level = level;
        this.xNum = xNum;
        this.yNum = yNum;
        this.type = type;
        loaded = false;
    }

    protected Log getLog() {
        return log;
    }

    private String getTrafficTimestamp() {
        HttpClient client = new HttpClient();
        GetMethod get = new GetMethod(statJsUrl);
        try {
            client.executeMethod(get);
            String fullJs = get.getResponseBodyAsString();
            int tmPos=fullJs.indexOf("timestamp");
            String tm = fullJs.substring(tmPos + 11, tmPos + 21);
            log.debug("Current tm="+tm);
            return tm;
        } catch (IOException e) {
            return "1237045112";
        }
    }

    public String getLoadUrl() {
        switch(type){
            case YA_MAP_TYPE_TAFFIC:
                return traffUrl+"&tm="+getTrafficTimestamp()+"&x=" + xNum + "&y=" + yNum + "&z=" + (level);
            case MAP_TYPE_SAT:
                return nextSatUrl()+"&x=" + xNum + "&y=" + yNum + "&z=" + (level);
            case MAP_TYPE_MAP:
                return nextMapUrl()+"map"+"&x=" + xNum + "&y=" + yNum + "&z=" + (level);
            case MAP_TYPE_HYB:
                return nextMapUrl()+"skl"+"&x=" + xNum + "&y=" + yNum + "&z=" + (level);
        }
        return null;
    }

    public boolean load() {
        String url = getLoadUrl();
        if(url!=null)
            return loadMap(url);
        return loaded = false;
    }

    private boolean loadMap(String url) {
        try {
            String tileName = "[" + type + "]" + xNum + "x" + yNum + ";l=" + level;
            log.info("Load tile \"" + tileName + "\"");
            File cachedTile = null;
            if (type!= BaseTile.YA_MAP_TYPE_TAFFIC && cacheDir != null) {
                cachedTile = new File(cacheDir + File.separator + tileName + ".jpg");
            }

            if (cachedTile != null && cachedTile.exists() && cachedTile.canRead()) {
                imageData = FileUtils.readFileToByteArray(cachedTile);
                log.debug("from cache loaded");
            } else {
                log.info("url: " + url);
                GetMethod get = new GetMethod(url);
                HttpClient client = new HttpClient();
                int code = client.executeMethod(proxySettings, get);
                if (code != 200) {
                    log.error("Loading error: http-code=" + get.getStatusCode() + "; http-status" + get.getStatusText());
                    imageData = IOUtils.toByteArray(YandexTile.class.getResourceAsStream("/org/gmapper/yandex/404t.png"));
                    return loaded = true;
//                    return loaded = false;
                }
                imageData = get.getResponseBody();
                log.debug("from http loaded");
            }
            if (cachedTile != null && !cachedTile.exists())
                FileUtils.writeByteArrayToFile(cachedTile, imageData);
            return loaded = true;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("", e);
            //ToDo
            return loaded = false;
        }
    }
}
