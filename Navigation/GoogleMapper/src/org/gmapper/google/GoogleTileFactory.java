package org.gmapper.google;

import org.mapnav.exceptions.OutOfRange;
import org.gmapper.types.IntPoint;
import org.gmapper.types.DoublePoint;
import org.gmapper.TileFactory;
import org.gmapper.BaseTile;

/**
 * User: Vova
 * Time: 23:23:32
 *
 * @author ognivo777@mail.ru
 */
public class GoogleTileFactory extends TileFactory {

    public GoogleTileFactory() {
    }

    public BaseTile getTile(int level, int xNum, int yNum, int type) throws OutOfRange {
        return getGoogleTile(level, xNum, yNum, type);
    }

    public IntPoint tilePosFromGeoCoord(DoublePoint geoCoord, int level) {
        return GoogleUtils.tilePosFromGeoCoord(geoCoord, level);
    }

    public IntPoint tilePixelFromGeoCoord(DoublePoint geoCoord, int level) {
        IntPoint mapCoord = GoogleUtils.mapPosFromGeo(geoCoord, level);
        return new IntPoint(mapCoord.x%256, mapCoord.y%256);
    }

    public DoublePoint moveGeoCoord(DoublePoint mapGeoCenter, IntPoint mapShift, int level) {
        IntPoint newMapPos = GoogleUtils.mapPosFromGeo(mapGeoCenter, level).add(mapShift);
        return GoogleUtils.geoFromMapPos(newMapPos, level);
    }
}