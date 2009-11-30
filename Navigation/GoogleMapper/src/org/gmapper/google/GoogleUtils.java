package org.gmapper.google;

import org.mapnav.exceptions.OutOfRange;
import org.gmapper.BaseTile;
import org.gmapper.TileFactory;
import org.gmapper.types.DoublePoint;
import org.gmapper.types.IntPoint;

/**
 * User: Vova
 * Time: 21:44:13
 *
 * @author ognivo777@mail.ru
 */
public final class GoogleUtils {

    public static String tileNameFromPos(int level, int numX, int numY) throws OutOfRange {
        if(level < 1 || level > 20)
            throw new OutOfRange(level+"");
        int d = (int) Math.pow(2,level-1);
        if(numX > d || numX < 0)
            throw new OutOfRange(numX+" for level "+level);
        if(numY > d || numY < 0)
            throw new OutOfRange(numY+" for level "+level);        
        String result="t";
        for(int i=2; i<=level; i++) {
            d = d / 2;
            if (numY < d) {
                if (numX < d) {
                    result += "q";
                } else {
                    result += "r";
                    numX -= d;
                }
            } else {
                if (numX < d) {
                    result += "t";
                } else {
                    result += "s";
                    numX -= d;
                }
                numY -=d;
            }
        }
        return result;
    }


    /**ll.x - LON ; ll.y - LAT*/
    public static BaseTile latLngToPixel(DoublePoint ll, int zoom) throws OutOfRange {
        IntPoint tilenum = tilePosFromGeoCoord(ll, zoom);
        return TileFactory.getGoogleTile(zoom, tilenum.x, tilenum.y, GoogleTile.MAP_TYPE_MAP);
    }

    public static IntPoint tilePosFromGeoCoord(DoublePoint ll, int zoom) {
        IntPoint mapCoord = mapPosFromGeo(ll, zoom);
//        System.out.println("google latLngToPixel " + mapCoord);
//        System.out.println("google in tile pixel " + new IntPoint(mapCoord.x%256, mapCoord.y%256));
        IntPoint tilenum = new IntPoint(mapCoord.x>>8, mapCoord.y>>8);
//        System.out.println("google tile pos" + tilenum);
        return tilenum;
    }

    public static IntPoint mapPosFromGeo(DoublePoint ll, int zoom) {
        double centerPoint = Math.pow(2, zoom+7);
        double totalPixels = centerPoint * 2;
        double pixelsPerLngDegree = totalPixels / 360;
        double pixelsPerLngRadian = totalPixels / (2 * Math.PI);
        double siny = Math.min(Math.max(Math.sin(ll.y * (Math.PI / 180)), -0.9999), 0.9999);
        long x = Math.round(centerPoint + ll.x * pixelsPerLngDegree);
        long y = Math.round(centerPoint - 0.5 * Math.log((1 + siny) / (1 - siny)) * pixelsPerLngRadian);
        IntPoint mapCoord = new IntPoint(x, y);
        return mapCoord;
    }

    public static DoublePoint geoFromMapPos(IntPoint pos, int zoom) {
        //ToDo See :  http://mapbuilder.narod.ru/gm.htm

        double centerPoint = Math.pow(2, zoom+7);
        double totalPixels = centerPoint * 2;
        double pixelsPerLngDegree = totalPixels / 360;
        double pixelsPerLngRadian = totalPixels / (2 * Math.PI);

        double z = (pos.y - centerPoint) / -pixelsPerLngRadian;
        double lon = (pos.x - centerPoint) / pixelsPerLngDegree;
        double lat = (2 * Math.atan(Math.exp(z)) - Math.PI/2) * 180/Math.PI;

        return new DoublePoint(lon, lat);
    }
}
