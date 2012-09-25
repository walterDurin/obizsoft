package org.gmapper.google;

import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.gmapper.BaseTile;
import org.mapnav.exceptions.OutOfRange;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * User: Vova
 * Time: 21:31:20
 *
 * @author ognivo777@mail.ru
 */
public class GoogleTile extends BaseTile {
    private static Log log = LogFactory.getLog(GoogleTile.class);

    protected static List<String> satUrls;
    protected static List<String> mapUrls;
    protected static List<String> overMapUrls;
    protected static List<String> lanMapUrls;
    protected static List<String> traffUrls;
    protected static List<String> photosUrls;
    //    protected static int nextSatUrl = 0;
//    protected static int nextMapUrl = 0;
//    protected static int nextOverMapUrl = 0;
//    protected static int nextLanMapUrl = 0;
//    protected static int nextTraffUrl = 0;
    protected static String cacheDir = null;

    private static final String suffix = "s=Galileo";
    private static int suffixNum = 2;

    protected static HostConfiguration proxySettings = new HostConfiguration();

    private static int nextUrlcnt = 0;

    protected static String nextSatUrl() {
        if (nextUrlcnt != 0 && nextUrlcnt == satUrls.size()) {
            nextUrlcnt = 0;
        }

        if (nextUrlcnt++ < 3)
            return satUrls.get(nextUrlcnt);
        nextUrlcnt = 0;

        return satUrls.get(nextUrlcnt++);
    }

    protected static String nextMapUrl() {
        if (nextUrlcnt != 0 && nextUrlcnt == mapUrls.size()) {
            nextUrlcnt = 0;
        }

        if (nextUrlcnt++ < 3)
            return mapUrls.get(nextUrlcnt);
        nextUrlcnt = 0;

        return mapUrls.get(nextUrlcnt++);
    }

    protected static String nextOverMapUrl() {

        if (nextUrlcnt != 0 && nextUrlcnt == overMapUrls.size()) {
            nextUrlcnt = 0;
        }

        if (nextUrlcnt++ < 3)
            return overMapUrls.get(nextUrlcnt);
        nextUrlcnt = 0;

        return overMapUrls.get(nextUrlcnt++);
    }

    protected static String nextLanMapUrl() {

        if (nextUrlcnt != 0 && nextUrlcnt == lanMapUrls.size()) {
            nextUrlcnt = 0;
        }

        if (nextUrlcnt++ < 3)
            return lanMapUrls.get(nextUrlcnt);
        nextUrlcnt = 0;

        return lanMapUrls.get(nextUrlcnt++);
    }

    protected static String nextTrafficMapUrl() {

        if (nextUrlcnt != 0 && nextUrlcnt == traffUrls.size()) {
            nextUrlcnt = 0;
        }

        if (nextUrlcnt++ < 3)
            return traffUrls.get(nextUrlcnt);
        nextUrlcnt = 0;

        return traffUrls.get(nextUrlcnt++);
    }

    protected static String nextPhotoMapUrl() {

        if (nextUrlcnt != 0 && nextUrlcnt == traffUrls.size()) {
            nextUrlcnt = 0;
        }

        if (nextUrlcnt++ < 3)
            return photosUrls.get(nextUrlcnt);
        nextUrlcnt = 0;

        return photosUrls.get(nextUrlcnt++);
    }

    private static String getNextSuffix() {
        if (suffixNum >= 10)
            suffixNum = 2;
        return suffix.substring(0, suffixNum++);
    }

    static {
        satUrls = new ArrayList<String>();
        mapUrls = new ArrayList<String>();
        overMapUrls = new ArrayList<String>();
        lanMapUrls = new ArrayList<String>();
        traffUrls = new ArrayList<String>();
        photosUrls = new ArrayList<String>();

        Properties prop = new Properties();
        try {
            prop.load(FileUtils.openInputStream(new File("settings.properties")));
        } catch (IOException e) {
            throw new RuntimeException();
        }

        for (int i = 1; i <= 4; i++) {
            String url = prop.getProperty("google.sat.url" + i);
            if (url != null)
                satUrls.add(url);
        }

        for (int i = 1; i <= 4; i++) {
            String url = prop.getProperty("google.map.url" + i);
            if (url != null)
                mapUrls.add(url);
        }

        for (int i = 1; i <= 4; i++) {
            String url = prop.getProperty("google.overmap.url" + i);
            if (url != null)
                overMapUrls.add(url);
        }

        for (int i = 1; i <= 4; i++) {
            String url = prop.getProperty("google.lan.url" + i);
            if (url != null)
                lanMapUrls.add(url);
        }

        for (int i = 1; i <= 4; i++) {
            String url = prop.getProperty("google.traffic.url" + i);
            if (url != null)
                traffUrls.add(url);
        }

        for (int i = 1; i <= 4; i++) {
            String url = prop.getProperty("google.photos.url" + i);
            if (url != null)
                photosUrls.add(url);
        }

        cacheDir = prop.getProperty("google.cahceDir");
        File cacheDirFile = new File(cacheDir);
        if (cacheDirFile == null || !cacheDirFile.isDirectory() || !cacheDirFile.canWrite()) {
            log.error("Check your google.cahceDir settings property.");
            cacheDirFile = null;
        } else {
            try {
                cacheDir = cacheDirFile.getCanonicalPath();
            } catch (IOException e) {
                log.error("" + e);
                cacheDirFile = null;
            }
        }

        String proxyAddr = prop.getProperty("network.proxy.addr");
        if (proxyAddr != null && proxyAddr.trim().length() != 0) {
            proxySettings.setProxy(proxyAddr, Integer.parseInt(prop.getProperty("network.proxy.port")));
        }

    }

    public GoogleTile(int level, int xNum, int yNum, int type) throws OutOfRange {
        if (level < 0)
            throw new OutOfRange(level);
        int max = (int) Math.pow(2, level);
        if (xNum < 0 || xNum > max)
            throw new OutOfRange(xNum);
        if (yNum < 0 || yNum > max)
            throw new OutOfRange(yNum);
        if (type != MAP_TYPE_MAP && type != MAP_TYPE_SAT && type != MAP_TYPE_HYB && type != GOOGLE_LANDSCAPE
                && type != GOOGLE_TRANSPORT && type != GOOGLE_PHOTOS && type != GOOGLE_TRAFFIC)
            throw new OutOfRange(yNum);
        if ((type == MAP_TYPE_MAP || type == GOOGLE_LANDSCAPE) && level > 18)
            throw new OutOfRange("For MAP type map max level is '18'. Found '" + level + "'");
        if ((type == MAP_TYPE_SAT || type == MAP_TYPE_HYB) && level > 20)
            throw new OutOfRange("For SAT type map max level is '20'. Found '" + level + "'");

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
        switch (type) {
            case MAP_TYPE_SAT:
                return nextSatUrl() + "&x=" + xNum + "&y=" + yNum + "&z=" + (level) + "&" + getNextSuffix();
            case MAP_TYPE_MAP:
                return nextMapUrl() + "&x=" + xNum + "&y=" + yNum + "&z=" + (level) + "&" + getNextSuffix();
            case MAP_TYPE_HYB:
                return nextOverMapUrl() + "&x=" + xNum + "&y=" + yNum + "&z=" + (level) + "&" + getNextSuffix();
            case GOOGLE_LANDSCAPE:
                return nextLanMapUrl() + "&x=" + xNum + "&y=" + yNum + "&z=" + (level) + "&" + getNextSuffix();
            case GOOGLE_TRANSPORT:
                return nextMapUrl() + ",transit:comp%7Cvm:1&x=" + xNum + "&y=" + yNum + "&z=" + (level) + "&" + getNextSuffix() + "&style=15";
            case GOOGLE_PHOTOS:
                return nextPhotoMapUrl() + "&x=" + xNum + "&y=" + yNum + "&z=" + (level) + "&" + getNextSuffix();
            case GOOGLE_TRAFFIC:
                return nextTrafficMapUrl() + "&x=" + xNum + "&y=" + yNum + "&z=" + (level);

        }
        log.info("" + type);
        return null;
    }

    public boolean load() {
        String url = getLoadUrl();
        if (url != null)
            return loadMap(url);
        return loaded = false;
    }

    private String getStringType() {
        switch (getType()) {
            case MAP_TYPE_SAT:
                return "sat";
            case MAP_TYPE_MAP:
                return "map";
            case MAP_TYPE_HYB:
                return "hyb";
            case GOOGLE_LANDSCAPE:
                return "lan";
            case GOOGLE_TRANSPORT:
                return "map_tran";
        }
        return "unknow";
    }

    public boolean checkAlreadyLoaded() {
        File cachedTile = getTileFile();
        return cachedTile != null && cachedTile.exists() && cachedTile.canRead();
    }

    public File getTileFile() {
        String tileName = getStringType() + xNum + "x" + yNum + "l" + level;
        log.info("Load tile \"" + tileName + "\"");
        File cachedTile = null;
        if (type != GoogleTile.GOOGLE_TRAFFIC && type != GoogleTile.GOOGLE_PHOTOS && cacheDir != null) {
            cachedTile = new File(cacheDir + File.separator + tileName + ".jpg");
        }
        return cachedTile;
    }

    private boolean loadMap(String url) {
        try {
            File cachedTile = getTileFile();

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
                    return loaded = false;
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
