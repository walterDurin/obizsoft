package org.gmapper;

import org.mapnav.exceptions.OutOfRange;
import org.gmapper.yandex.YandexTile;
import org.gmapper.google.GoogleTile;
import org.gmapper.google.GoogleTileFactory;
import org.gmapper.types.DoublePoint;
import org.gmapper.types.IntPoint;
import org.gmapper.yandex.YandexTileFactory;

/**
 * User: Vova
 * Time: 21:06:38
 *
 * @author ognivo777@mail.ru
 */
public abstract class TileFactory {
    protected TileFactory() {
    }

    public static BaseTile getYndexTile(int level, int xNum, int yNum, int type) throws OutOfRange {
        return new YandexTile(level, xNum, yNum, type);
    }

    public static BaseTile getGoogleTile(int level, int xNum, int yNum, int type) throws OutOfRange {
        return new GoogleTile(level, xNum, yNum, type);
    }
    
    public static TileFactory getYandexTileFactory() {
        return new YandexTileFactory();
    }

    public static TileFactory getGoogleTileFactory() {
        return new GoogleTileFactory();
    }

    public abstract BaseTile getTile(int level, int xNum, int yNum, int type) throws OutOfRange;

    /**получить номер тайла из географических координат*/
    public abstract IntPoint tilePosFromGeoCoord(DoublePoint geoCoord, int level);


    /**Полчуить координаты пикселя в талйе, соответсвующего георгафич. координатам*/
    public abstract IntPoint tilePixelFromGeoCoord(DoublePoint geoCoord, int level);

    public abstract DoublePoint moveGeoCoord(DoublePoint mapGeoCenter, IntPoint mapShift, int level);
}
