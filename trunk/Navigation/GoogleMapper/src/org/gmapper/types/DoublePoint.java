package org.gmapper.types;

import java.util.Formatter;

/**
 * Date: 19.09.2008
 *
 * @author Taran Vladimir <vtaran@mbtc.ru>
 */

public class DoublePoint {
    public double x = 0;
    public double y = 0;

    public DoublePoint(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public DoublePoint multiply(double k) {
        return new DoublePoint(x*k, y*k);
    }

    public DoublePoint multiply(DoublePoint k) {
        return new DoublePoint(x*k.x, y*k.y);
    }

    public DoublePoint add(double k) {
        return new DoublePoint(x+k, y+k);
    }

    public DoublePoint toInt() {
        return new DoublePoint((int)x, (int)y);
    }

    public DoublePoint round() {
        return new DoublePoint(Math.round(x), Math.round(y));
    }

    public DoublePoint add(DoublePoint k) {
        return new DoublePoint(x+k.x, y+k.y);
    }

    @Override
    public String toString() {
        return new Formatter().format("DoublePoint{" +
                "x=%+10.10f, %+10.10f}", x, y).toString();
    }

    public boolean equals(Object obj) {
        if(obj!=null && this.getClass().isAssignableFrom(obj.getClass())) {
            DoublePoint p = (DoublePoint) obj;
            return x == p.x && y == p.y;
        }
        return false;
    }


}
