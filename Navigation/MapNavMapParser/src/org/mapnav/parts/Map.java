package org.mapnav.parts;

import org.mapnav.types.GeoPoint;
import org.mapnav.exceptions.OutOfRange;
import org.mapnav.Utils;

import java.util.List;
import java.util.ArrayList;
import java.io.OutputStream;
import java.io.IOException;

/**
 * Date: 18.08.2008
 *
 * @author Taran Vladimir <vtaran@mbtc.ru>
 */

public class Map {
    public static final int INTERNAL_TYPE = 1; //mnm
    public static final int EXTERNAL_TYPE = 2; //mno
    private GeoPoint center;
    private int minDemensionLevel;
    private long blockCount;
    private int type;
    private List<Tile> tiles;

    public Map() {
        center = new GeoPoint();
        minDemensionLevel = 1;
        blockCount = 0;
        type=1;
        tiles = new ArrayList<Tile>();
    }

    public GeoPoint getCenter() {
        return center;
    }

    public void setCenter(GeoPoint center) {
        this.center = center;
    }

    public int getMinDemensionLevel() {
        return minDemensionLevel;
    }

    public void setMinDemensionLevel(int minDemensionLevel) throws OutOfRange {
        if(minDemensionLevel<1 || minDemensionLevel>20)
            throw new OutOfRange(minDemensionLevel);
        this.minDemensionLevel = minDemensionLevel;
    }

    public long getBlockCount() {
        return blockCount;
    }

    public void setBlockCount(long blockCount) throws OutOfRange {
        if(blockCount<1)
            throw new OutOfRange(blockCount);
        this.blockCount = blockCount;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<Tile> getTiles() {
        return tiles;
    }

    public void setTiles(List<Tile> tiles) {
        this.tiles = tiles;
    }

    public void writeToStream(OutputStream out) throws IOException {
        out.write(center.getLat().getDeg());
        out.write(center.getLat().getMin());
        out.write(center.getLon().getDeg());
        out.write(center.getLon().getMin());
        out.write(minDemensionLevel);
        out.write(Utils.longToBytes(blockCount));
        if(type==INTERNAL_TYPE) {
            for (Tile tile : tiles) {
                tile.writeHeader(out);
                tile.writeImage(out);
            }
        } else {
            long offset=0;
            for (Tile tile : tiles) {
                tile.writeHeader(out);
                out.write(Utils.longToBytes(offset));
                offset+=tile.getLength();
            }
            for (Tile tile : tiles) {
                tile.writeImage(out);
            }
        }
    }

    public String toString() {
        StringBuffer str = new StringBuffer();
        str.append("Map info:\n");
        str.append("\tType: ").append(type == INTERNAL_TYPE?"internal":"external").append("\n");
        str.append("\tCenter: ").append(center).append("\n");
        str.append("\tMinDemension: ").append(minDemensionLevel).append("\n");
        str.append("\tBlockCount: ").append(blockCount).append("\n");
        if(blockCount!=0 && tiles!=null && tiles.size()!=0) {
            for (Tile tile : tiles) {
                str.append(tile);
            }
        }
        return str.toString();
    }
}
