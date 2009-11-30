package org.gmapper.types;

import java.util.Formatter;

/**
 * Date: 19.09.2008
 *
 * @author Taran Vladimir <vtaran@mbtc.ru>
 */

public class IntPoint {
    public int x = 0;
    public int y = 0;

    public IntPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public IntPoint(long x, long y) {
        this.x = (int) x;
        this.y = (int) y;
    }

    public IntPoint(int i) {
        this(i,i);
    }

    public IntPoint multiply(double k) {
        return new IntPoint((int) (x*k), (int) (y*k));
    }

    public IntPoint multiply(IntPoint k) {
        return new IntPoint(x*k.x, y*k.y);
    }

    public IntPoint add(int k) {
        return new IntPoint(x+k, y+k);
    }

    public IntPoint round() {
        return new IntPoint(Math.round(x), Math.round(y));
    }

    public IntPoint add(IntPoint k) {
        return new IntPoint(x+k.x, y+k.y);
    }

    @Override
    public String toString() {
        return new Formatter().format("IntPoint{" +
                "x=%d, %d}", x, y).toString();
    }

    public boolean equals(Object obj) {
        if(obj!=null && this.getClass().isAssignableFrom(obj.getClass())) {
            IntPoint p = (IntPoint) obj;
            return x == p.x && y == p.y;
        }
        return false;
    }
}