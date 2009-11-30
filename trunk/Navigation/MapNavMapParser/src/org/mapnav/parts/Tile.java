package org.mapnav.parts;

import org.mapnav.exceptions.OutOfRange;
import org.mapnav.Utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.io.IOException;

/**
 * Date: 18.08.2008
 *
 * @author Taran Vladimir <vtaran@mbtc.ru>
 */

public class Tile {
    private long xNum;
    private long yNum;
    private long demension;
    private int type = 2;
    private byte[] mapImage;
    private long offset;

    public Tile() {
        xNum = 1;
        yNum = 1;
        demension = 1;
        type = 2;
        offset = 0;
    }

    public long getXNum() {
        return xNum;
    }

    public void setXNum(long xNum) throws OutOfRange {
        if(xNum<0)
            throw new OutOfRange(xNum);
        this.xNum = xNum;
    }

    public long getYNum() {
        return yNum;
    }

    public void setYNum(long yNum) throws OutOfRange {
        if(yNum<0)
            throw new OutOfRange(yNum);
        this.yNum = yNum;
    }

    public long getDemension() {
        return demension;
    }

    public void setDemension(long demension) throws OutOfRange {
        if(demension<1) //ToDo : верхн€€ граница?..
            throw new OutOfRange(demension);
        this.demension = demension;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) throws OutOfRange {
        if(type!=2)
            throw new OutOfRange("Only value '2' is allow.(found: "+type+")");
        this.type = type;
    }

    public long getLength() {
        return mapImage.length;
    }

    public void setLength(long length) throws OutOfRange {
        if(length<0)
            throw new OutOfRange(length);
        mapImage = new byte[(int) length];
    }

    public byte[] getMapImage() {
        return mapImage;
    }

    public void setMapImage(byte[] mapImage) {
        this.mapImage = mapImage;
    }

    public long getOffset() {
        return offset;
    }

    public void setOffset(long offset) {
        this.offset = offset;
    }

    public String toString() {
        StringBuffer str = new StringBuffer();
        str.append("Block info:\n");
        str.append("\txNum: ").append(xNum).append("\n");
        str.append("\tyNum: ").append(yNum).append("\n");
        str.append("\tdemension: ").append(demension).append("\n");
        str.append("\ttype: ").append(type).append("\n");
        str.append("\tlength: ").append(getLength()).append("\n");
        str.append("\toffset: ").append(offset).append("\n");
        if(mapImage!=null)
            str.append("\timage: ")
//                    .append(mapImage.getWidth(null))
                    .append("x")
//                    .append(mapImage.getHeight(null))
                    .append("\n");
        return str.toString();
    }

    public void writeHeader(OutputStream out) throws IOException {
        out.write(Utils.longToBytes(xNum));
        out.write(Utils.longToBytes(yNum));
        out.write(Utils.longToBytes(demension));
        out.write((byte)type);
        out.write(Utils.longToBytes(getLength()));
    }

    public void writeImage(OutputStream out) throws IOException {
        if( mapImage!=null && mapImage.length!=0) {
            out.write(mapImage);
        }
    }
}
