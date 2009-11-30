package tests;

import junit.framework.TestCase;
import org.mapnav.exceptions.OutOfRange;
import org.gmapper.TileFactory;
import org.gmapper.BaseTile;
import org.gmapper.google.GoogleUtils;
import org.gmapper.types.DoublePoint;
import org.gmapper.types.IntPoint;
import org.gmapper.yandex.YandexTile;
import org.gmapper.yandex.YandexUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.io.FileUtils;

import java.io.IOException;
import java.io.File;

/**
 * Date: 19.09.2008
 *
 * @author Taran Vladimir <vtaran@mbtc.ru>
 */

public class YandexTest extends TestCase {

    public void testMapCenter() {
        long mapX=4187847;
        long mapY=7474177;
        long centerX = Math.round((20037508.342789 + mapX) * 53.5865938);
        long centerY = Math.round((20037508.342789 - mapY) * 53.5865938);
        System.out.println("centerX = " + centerX);
        System.out.println("centerY = " + centerY);
        System.out.println("this.state.center=[1298154276,673226133]");
    }

    public void testCoord() {
        final int imageSize = 256;
        final int centerPicsX = 511, centerPicsY = 339;
        final int zoom = 13;
        final int mapCenterX = 1298154276, mapCenterY = 673226133;
//        long x=253, y=-177;
        long x=263, y=-170;
        long posX = x+imageSize/2, posY = y + imageSize/2;
        long pixelsOffsetX = posX - centerPicsX;
        long pixelsOffsetY = posY - centerPicsY;
        long coordinatesOffsetX = pixelsOffsetX<<zoom;
        long coordinatesOffsetY = pixelsOffsetY<<zoom;
        coordinatesOffsetX+=mapCenterX;
        coordinatesOffsetY+=mapCenterY;
        System.out.println("coordinatesOffsetX = " + coordinatesOffsetX);
        System.out.println("coordinatesOffsetY = " + coordinatesOffsetY);
        System.out.println("x=1297089316 y=670047637");
        System.out.println("Url:"+getUrl(coordinatesOffsetX, coordinatesOffsetY, zoom));
        System.out.println("http://base02.maps.yandex.net/tiles/2000?layer=1&x=1296039936&y=668991488&scale=13");
    }

    int mapID = 2000;

    public String  getUrl(long x, long y, int zoom) {
        final int eBitPerTile = 7;
        String tUrl = "http://base0%d.maps.yandex.net/tiles/%i?layer=1&%c";
        tUrl = tUrl.replace("%i", this.mapID+"");
        long scaledX = x >> zoom, scaledY = y >> zoom, tileX = scaledX >> eBitPerTile,tileY = scaledY >> eBitPerTile;
        return tUrl.replace("%d", (2 * (tileX & 1) + (tileY & 1) + 1)+"").replace("%c", "x=" + ((tileX << eBitPerTile) << zoom) + "&y=" + ((tileY << eBitPerTile) << zoom) + "&scale=" + zoom);

    }

    public DoublePoint toMercator(LongPoint p) {
	double M_PI = Math.PI,
            M_PI_2 = M_PI / 2,
            R = 6378137,
            ab = 0.00335655146887969400,
            bb = 0.00000657187271079536,
            cb = 0.00000001764564338702,
            db = 0.00000000005328478445;
	double xphi = M_PI_2 - 2 * Math.atan(1 / Math.exp(p.y / R));
	double latitude = xphi + ab * Math.sin(2 * xphi) + bb * Math.sin(4 * xphi) + cb * Math.sin(6 * xphi) + db * Math.sin(8 * xphi);
	double longitude = p.x / R;
	if (Math.abs(latitude) > M_PI_2)latitude = M_PI_2;
	if (Math.abs(longitude) > M_PI)longitude = M_PI;
	return new DoublePoint(latitude, longitude);
    }

    public void testMercator() {
        DoublePoint res = toMercator(new LongPoint(4187847, 7474177));
        System.out.println("res.x=" + res.x + " res.y=" + res.y);
    }


//====================================================================================
//====================================================================================
//====                                                                            ====
//====                   New version of coversion                                 ====
//====                                                                            ====
//====================================================================================
//====================================================================================

    public final double a = 6378137;
    public final double b = 6356752.3142;
    public final double e = 0.081819190928906;
    public final double pi = 3.14159265358979;
    public final double n = 2.71828182845904;
    public final double ko = 0.9996;
    public final double Trip = 2048; //( = z14 = 2^11)
    public final double R = 156543.0339;
    public final double Scale = 1.194329;
//    public final double TripD = 2445.984905;
    public final double KK = 4.0075031216128E7; // 0.149291125;


    public DoublePoint toGvinCoord(DoublePoint p) {
        double newX = a*p.x*pi/180;
        double newY = a * (
                Math.log(
                        Math.tan(
                                (pi/4) + (p.y*pi/180)/2
                        )*(
                                Math.pow(
                                                (
                                                        1 - e * Math.sin(p.y*pi/180)
                                                )/(
                                                        1 + e * Math.sin(p.y * pi /180)
                                                ),(e/2)
                                )
                        )
                )/Math.log(n));
        return new DoublePoint(newX, newY);
    }


    public BaseTile getYandexTileByLL(DoublePoint p, int zoom) throws OutOfRange {

//        double TripD = Math.pow(2, zoom-4)*Scale;
//        double Scale = (Math.round((R / Math.pow(2, zoom+3))*1000000))/1000000.0;
//        double Scale = (R / Math.pow(2, zoom+3));
        System.out.println("KK = " + ((Trip * Scale) * Math.pow(2,14)));
        double TripD = KK / Math.pow(2,zoom);  
//        double TripD = Trip * Scale;


        DoublePoint urlp = YandexUtils.geoToMercator(p);
        System.out.println(urlp); //OK
        System.out.println("TripD="+TripD);
//        xUrl = Int(p.x/TripD)*TripD+1/2 * TripD;
        urlp = urlp.multiply(1L/TripD).toInt().multiply(TripD).add(0.5*TripD);
        System.out.println(urlp); //OK
//        dx = Lon2X(180);
//        dy= Lat2Y(85.0840588);
//        DoublePoint dxy = geoToMercator(new DoublePoint(180, 85.0840588));
        DoublePoint dxy = new DoublePoint(20037508.342789, 20037508.342789);
        // 20037508,3427892440
//        x = xUrl + dx;
//        y = dy - yUrl
        System.out.println("dxy=" + dxy);
        urlp = urlp.multiply(new DoublePoint(1, -1)).add(dxy);
//        x = Round(x/TripD)
//        y = Round(y/TripD)
        System.out.println("d url="+urlp);
        System.out.println("1L/TripD="+1L/TripD);        
        urlp = urlp.multiply(1L/TripD).round();
        System.out.println("Round url="+urlp);
        return TileFactory.getYndexTile(zoom, (int)urlp.x,(int) urlp.y, YandexTile.MAP_TYPE_MAP);
   }


    public void test2() throws OutOfRange, IOException {
        DoublePoint p = new DoublePoint(37.658997774124146, 55.8921177933994);
        int zoom = 14;
        BaseTile tile = getYandexTileByLL(p, zoom);
        BaseTile tile3 = GoogleUtils.latLngToPixel(p, zoom);
        DoublePoint pp = YandexUtils.geoToMercator(p);
        pp = YandexUtils.mercatorToTiles(pp);
        IntPoint tilepp = YandexUtils.getTile(pp, zoom);
        DoublePoint ppPx = YandexUtils.tileCoordinatesToPixels(pp, 23 - zoom).toInt();
        System.out.println("ppPx=" + ppPx);
        DoublePoint dpp = new DoublePoint(pp.x % 256, pp.y % 256);
        IntPoint dpp2 = YandexUtils.getPxCoordFromTileCoord(pp, zoom).add(tilepp.multiply(-256));
        System.out.println("tilepp=" + tilepp);
        System.out.println("dpp=" + dpp);
        System.out.println("dpp2=" + dpp2);
        System.out.println(tile.getLoadUrl());
        System.out.println(tile3.getLoadUrl());
    }


    public void test3() throws OutOfRange, IOException {
        DoublePoint p = new DoublePoint(37.658997774124146, 55.8921177933994);
        int zoom = 14;
        System.out.println("Geo point: "+p);
        DoublePoint pp = YandexUtils.geoToMercator(p);
        System.out.println("geoToMercator: "+pp);
        pp = YandexUtils.mercatorToTiles(pp);
        System.out.println("mercatorToTiles: "+pp);
        IntPoint tilepp = YandexUtils.getTile(pp, zoom);
        System.out.println("getTile=" + tilepp);
        IntPoint ppInPx = YandexUtils.getPxCoordFromTileCoord(pp, zoom);
        System.out.println("getPxCoordFromTileCoord : " + ppInPx);
        IntPoint dpp2 = ppInPx.add(tilepp.multiply(-256));
        System.out.println("In Tile pixel coord : " + dpp2);

        System.out.println("=======REVERSE=======");

        IntPoint p0 = YandexUtils.getTileCoordFromPixCoord(ppInPx, zoom);
        System.out.println("getTileCoordFromPixCoord: " + p0);
        DoublePoint p01 = YandexUtils.tileToMercator(p0);
        System.out.println("tileToMercator: " + p01);
        p01 = YandexUtils.mercatorToGeo(p01);
        System.out.println("mercatorToGeo: " + p01);


    }



    //работа с тайлами пробок

    public void testTrfTiles_GET_TM() throws IOException {
        //Получаем текущую метку времени
        String js_url = "http://trf.maps.yandex.net/trf/stat.js";
        HttpClient client = new HttpClient();
        GetMethod get = new GetMethod(js_url);
        client.executeMethod(get);
        String fullJs = get.getResponseBodyAsString();
        int tmPos=fullJs.indexOf("timestamp");
        String tm = fullJs.substring(tmPos + 11, tmPos + 21);
        System.out.println("Current tm="+tm);

        //загрузка тайла
        String tileUrl="http://trf.maps.yandex.net/tiles?l=trf&x=2478&y=1285&z=12&tm=";
        get = new GetMethod(tileUrl+tm);
        client.executeMethod(get);
        FileUtils.writeByteArrayToFile(new File("test_tile.png"), get.getResponseBody());



    }


}
