package org.gmapper.yandex;

import org.mapnav.exceptions.OutOfRange;
import org.gmapper.types.IntPoint;
import org.gmapper.types.DoublePoint;
import org.gmapper.yandex.YandexUtils;
import org.gmapper.TileFactory;
import org.gmapper.BaseTile;

/**
 * User: Vova
 * Time: 23:23:32
 *
 * @author ognivo777@mail.ru
 */
public class YandexTileFactory extends TileFactory {

    public YandexTileFactory() {
    }

    public BaseTile getTile(int level, int xNum, int yNum, int type) throws OutOfRange {
        return getYndexTile(level, xNum, yNum, type);
    }

    public IntPoint tilePosFromGeoCoord(DoublePoint geoCoord, int level) {
        DoublePoint tileCoord = YandexUtils.mercatorToTiles(YandexUtils.geoToMercator(geoCoord));
        IntPoint tilePos = YandexUtils.getTile(tileCoord, level);
        return tilePos;
    }

    public IntPoint tilePixelFromGeoCoord(DoublePoint geoCoord, int level) {
        DoublePoint tileCoord = YandexUtils.mercatorToTiles(YandexUtils.geoToMercator(geoCoord));
        IntPoint tilePos = YandexUtils.getTile(tileCoord, level);
        IntPoint inTileCoord = YandexUtils.getPxCoordFromTileCoord(tileCoord, level).add(tilePos.multiply(-256));
        return inTileCoord;
    }

    public DoublePoint moveGeoCoord(DoublePoint mapGeoCenter, IntPoint mapShift, int level) {

        DoublePoint tileCoord = YandexUtils.mercatorToTiles(YandexUtils.geoToMercator(mapGeoCenter));
        IntPoint inMapCoord = YandexUtils.getPxCoordFromTileCoord(tileCoord, level);

        IntPoint p0 = YandexUtils.getTileCoordFromPixCoord(inMapCoord.add(mapShift), level);
        DoublePoint p01 = YandexUtils.tileToMercator(p0);
        p01 = YandexUtils.mercatorToGeo(p01);
        return p01;
    }

    public String getSourceName() {
        return "Yandex";
    }
}
