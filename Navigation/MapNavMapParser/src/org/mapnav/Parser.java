package org.mapnav;

import org.mapnav.parts.Map;
import org.mapnav.parts.Tile;
import org.mapnav.types.GeoPoint;
import org.mapnav.exceptions.OutOfRange;

import javax.imageio.ImageIO;
import java.io.InputStream;
import java.io.IOException;
import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.ArrayList;

/**
 * Date: 18.08.2008
 *
 * @author Taran Vladimir <vtaran@mbtc.ru>
 */

public class Parser {
    public static Map readMap(InputStream mapIs, final int type) throws OutOfRange, IOException {
        Map map = new Map();
        map.setType(type);
        GeoPoint headerPoint = new GeoPoint();
        headerPoint.getLat().setDeg(mapIs.read());
        headerPoint.getLat().setMin(mapIs.read());
        headerPoint.getLon().setDeg(mapIs.read());
        headerPoint.getLon().setMin(mapIs.read());
        map.setCenter(headerPoint);
        map.setMinDemensionLevel(mapIs.read());
        map.setBlockCount(Utils.readLong(mapIs));
        List<Tile> tiles = new ArrayList<Tile>();
        if(type == Map.EXTERNAL_TYPE) {
            for(int i=0;i < map.getBlockCount(); i++ ) {
                tiles.add(readBlockHeader(mapIs, type));
            }
            for (Tile tile : tiles) {
                readImage(mapIs, tile);
            }
        } else {
            for(int i=0;i < map.getBlockCount(); i++ ) {
                tiles.add(readBlockInternal(mapIs));
            }
        }
        map.setTiles(tiles);
        return map;
    }


    private static Tile readBlockInternal(InputStream is) throws OutOfRange, IOException {
        Tile tile = readBlockHeader(is, Map.INTERNAL_TYPE);
        readImage(is, tile);
        return tile;
    }

    private static void readImage(InputStream is, Tile tile) throws OutOfRange, IOException {
        int toRead = (int) tile.getLength();
        int readed = is.read(tile.getMapImage());
        if(readed!=toRead)
            throw new IOException("Wrong map! (Incorrect block size)");
    }

    private static Tile readBlockHeader(InputStream is, final int type) throws OutOfRange, IOException {
        Tile tile = new Tile();
        tile.setXNum(Utils.readLong(is));
        tile.setYNum(Utils.readLong(is));
        tile.setDemension(Utils.readLong(is));
        tile.setType(is.read());
        tile.setLength(Utils.readLong(is));
        if(type == Map.EXTERNAL_TYPE) {
            tile.setOffset(Utils.readLong(is));
        }
        return tile;
    }

}
