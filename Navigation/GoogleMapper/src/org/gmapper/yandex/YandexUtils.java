package org.gmapper.yandex;

import org.gmapper.types.DoublePoint;
import org.gmapper.types.IntPoint;

/**
 * User: Vova
 * Time: 4:24:18
 *
 * @author ognivo777@mail.ru
 */
public class YandexUtils {

    public static DoublePoint geoToMercator(DoublePoint g) {
        double d = g.x * Math.PI / 180, m = g.y * Math.PI / 180, l = 6378137, k = 0.0818191908426, f = k * Math.sin(m);
        double h = Math.tan(Math.PI / 4 + m / 2), j = Math.pow(Math.tan(Math.PI / 4 + Math.asin(f) / 2), k), i = h / j;
//        return new DoublePoint(Math.round(l * d), Math.round(l * Math.log(i)));
        return new DoublePoint(l * d, l * Math.log(i));
    }


    public static DoublePoint mercatorToGeo(DoublePoint e) {
        double j = Math.PI, f = j / 2, i = 6378137, n = 0.003356551468879694, k = 0.00000657187271079536, h = 1.764564338702e-8, m = 5.328478445e-11;
        double g = f - 2 * Math.atan(1 / Math.exp(e.y / i));
        double l = g + n * Math.sin(2 * g) + k * Math.sin(4 * g) + h * Math.sin(6 * g) + m * Math.sin(8 * g);
        double d = e.x / i;
        return new DoublePoint(d * 180 / Math.PI, l * 180 / Math.PI);
    }

    public static DoublePoint mercatorToTiles(DoublePoint e) {
        double d = Math.round((20037508.342789 + e.x) * 53.5865938), f = Math.round((20037508.342789 - e.y) * 53.5865938);
        d = boundaryRestrict(d, 0, 2147483647);
        f = boundaryRestrict(f, 0, 2147483647);
        return new DoublePoint(d, f);
    }


    public static DoublePoint tileToMercator(IntPoint d) {
        return new DoublePoint(Math.round(d.x / 53.5865938 - 20037508.342789), Math.round(20037508.342789 - d.y / 53.5865938));
    }


    public static DoublePoint tileCoordinatesToPixels(DoublePoint i, int h) {
        double g = Math.pow(2, toScale(h));
        return new DoublePoint((int) i.x / g, (int) i.y / g);
    }

    public static double boundaryRestrict(double f, double e, double d) {
        return Math.max(Math.min(f, d), e);
    }

    public static int toScale(int i) {
        return 23 - i;
    }

    public static IntPoint getTile(DoublePoint h, int i) {
        long e = 8;
        long j = toScale(i), g = (long) h.x >> j, f = (long) h.y >> j;
        return new IntPoint(g >> e, f >> e);
    }

    public static IntPoint getPxCoordFromTileCoord(DoublePoint h, int i) {
        long j = toScale(i), g = (long) h.x >> j, f = (long) h.y >> j;
        return new IntPoint(g, f);
    }

    public static IntPoint getTileCoordFromPixCoord(IntPoint h, int i) {
        long j = toScale(i), g = h.x << j, f = h.y << j;
        return new IntPoint(g, f);
    }


}
