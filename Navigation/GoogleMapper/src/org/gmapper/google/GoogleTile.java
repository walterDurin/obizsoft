package org.gmapper.google;

import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.io.FileUtils;
import org.mapnav.exceptions.OutOfRange;
import org.gmapper.BaseTile;

import java.util.List;
import java.util.ArrayList;
import java.util.Properties;
import java.io.IOException;
import java.io.File;

/**
 * User: Vova
 * Time: 21:31:20
 *
 * @author ognivo777@mail.ru
 */
public class GoogleTile extends BaseTile {
    private static Log log = LogFactory.getLog(GoogleTile.class);

    protected static List<String> satUrls;
    protected static int nextSatUrl =0;
    protected static List<String> mapUrls;
    protected static int nextMapUrl =0;
    protected static List<String> overMapUrls;
    protected static int nextOverMapUrl =0;
    protected static List<String> lanMapUrls;
    protected static int nextLanMapUrl =0;
    protected static String cacheDir = null;

    private static final String suffix = "s=Galileo";
    private static int suffixNum = 2 ;

    protected static HostConfiguration proxySettings = new HostConfiguration();

    private static int nextUrlcnt=0;
    protected static String nextSatUrl() {

        if(nextSatUrl !=0 && nextSatUrl == satUrls.size()) {
            nextSatUrl =0;
        }

        if(nextUrlcnt++ < 2)
            return satUrls.get(nextSatUrl);
        nextUrlcnt = 0;

        return satUrls.get(nextSatUrl++);
    }

    protected static String nextMapUrl() {
        if(nextMapUrl!=0 && nextMapUrl == mapUrls.size()) {
            nextMapUrl =0;
        }

        if(nextUrlcnt++ < 2)
            return  mapUrls.get(nextMapUrl);
        nextUrlcnt = 0;

        return mapUrls.get(nextMapUrl++);
    }

    protected static String nextOverMapUrl() {

        if(nextOverMapUrl!=0 && nextOverMapUrl == overMapUrls.size()) {
            nextOverMapUrl =0;
        }

        if(nextUrlcnt++ < 2)
            return overMapUrls.get(nextOverMapUrl);
        nextUrlcnt = 0;

        return overMapUrls.get(nextOverMapUrl++);
    }

    protected static String nextLanMapUrl() {

        if(nextLanMapUrl!=0 && nextLanMapUrl == lanMapUrls.size()) {
            nextLanMapUrl =0;
        }

        if(nextUrlcnt++ < 2)
            return lanMapUrls.get(nextLanMapUrl);
        nextUrlcnt = 0;

        return lanMapUrls.get(nextLanMapUrl++);
    }

    private static String getNextSuffix() {
        if(suffixNum>=10)
            suffixNum=2;
        return suffix.substring(0, suffixNum++);
    }

    static{
        satUrls = new ArrayList<String>();
        mapUrls = new ArrayList<String>();
        overMapUrls = new ArrayList<String>();
        lanMapUrls = new ArrayList<String>();

        Properties prop = new Properties();
        try {
            prop.load(FileUtils.openInputStream(new File("settings.properties")));
        } catch (IOException e) {
            throw new RuntimeException();
        }

        for(int i=1; i<=4; i++) {
            String url = prop.getProperty("google.sat.url"+i);
            if(url!=null)
                satUrls.add(url);
        }

        for(int i=1; i<=4; i++) {
            String url = prop.getProperty("google.map.url"+i);
            if(url!=null)
                mapUrls.add(url);
        }

        for(int i=1; i<=4; i++) {
            String url = prop.getProperty("google.overmap.url"+i);
            if(url!=null)
                overMapUrls.add(url);
        }

        for(int i=1; i<=4; i++) {
            String url = prop.getProperty("google.lan.url"+i);
            if(url!=null)
                lanMapUrls.add(url);
        }

        cacheDir = prop.getProperty("google.cahceDir");
        File cacheDirFile = new File(cacheDir);
        if(cacheDirFile==null || !cacheDirFile.isDirectory() || !cacheDirFile.canWrite()){
            log.error("Check your google.cahceDir settings property.");
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

    public GoogleTile(int level, int xNum, int yNum, int type) throws OutOfRange {
        if(level<0)
            throw new OutOfRange(level);
        int max = (int) Math.pow(2, level);
        if(xNum<0 || xNum>max)
            throw new OutOfRange(xNum);
        if(yNum<0 || yNum>max)
            throw new OutOfRange(yNum);
        if(type!=MAP_TYPE_MAP && type!=MAP_TYPE_SAT && type!=MAP_TYPE_HYB && type!=GOOGLE_LANDSCAPE)
            throw new OutOfRange(yNum);
        if((type==MAP_TYPE_MAP||type==GOOGLE_LANDSCAPE) && level>18)
            throw new OutOfRange("For MAP type map max level is '18'. Found '"+level+"'");
        if((type==MAP_TYPE_SAT || type==MAP_TYPE_HYB) && level>20)
            throw new OutOfRange("For SAT type map max level is '20'. Found '"+level+"'");

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

    public String getLoadUrl() {
        switch(type){
            case MAP_TYPE_SAT:
                return nextSatUrl() + "&x=" + xNum + "&y=" + yNum + "&z=" + (level) + "&" + getNextSuffix();
            case MAP_TYPE_MAP:
                return nextMapUrl() + "&x=" + xNum + "&y=" + yNum + "&z=" + (level) + "&" + getNextSuffix();
            case MAP_TYPE_HYB:
                return nextOverMapUrl() + "&x=" + xNum + "&y=" + yNum + "&z=" + (level) + "&" + getNextSuffix();
            case GOOGLE_LANDSCAPE:
                return nextLanMapUrl() + "&x=" + xNum + "&y=" + yNum + "&z=" + (level) + "&" + getNextSuffix();
        }
        log.info(""+type);
        return null;
    }

    public boolean load() {
        String url = getLoadUrl();
        if(url!=null)
            return loadMap(url);
        return loaded = false;
    }

    private String getStringType() {
        switch(getType()) {
            case MAP_TYPE_SAT:
                return "sat";
            case MAP_TYPE_MAP:
                return "map";
            case MAP_TYPE_HYB:
                return "hyb";
            case GOOGLE_LANDSCAPE:
                return "lan";
        }
        return "unknow";
    }

    private boolean loadMap(String url) {
        try {

                String tileName = getStringType()+xNum+"x"+yNum+"l"+level;
                log.info("Load tile \""+tileName+"\"");
                File cachedTile = null;
                if(cacheDir!=null) {
                 cachedTile = new File(cacheDir + File.separator + tileName+".jpg");
            }

            if(cachedTile!=null && cachedTile.exists() && cachedTile.canRead()) {
                imageData = FileUtils.readFileToByteArray(cachedTile);
                log.debug("from cache loaded");
            } else {
                log.info("url: "+url);
                GetMethod get = new GetMethod(url);
                HttpClient client = new HttpClient();
                int code = client.executeMethod(proxySettings, get);
                if(code!=200) {
                    log.error("Loading error: http-code="+get.getStatusCode()+"; http-status"+get.getStatusText());
                    return loaded = false;
                }
                imageData = get.getResponseBody();
                log.debug("from http loaded");
            }
            if(cachedTile!=null && !cachedTile.exists())
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
