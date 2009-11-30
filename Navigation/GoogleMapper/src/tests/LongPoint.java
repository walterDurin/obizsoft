package tests;

/**
 * Date: 19.09.2008
 *
 * @author Taran Vladimir <vtaran@mbtc.ru>
 */

public class LongPoint {
    long x = 0;
    long y = 0;

    public LongPoint(long x, long y) {
        this.x = x;
        this.y = y;
    }

    public long getX() {
        return x;
    }

    public void setX(long x) {
        this.x = x;
    }

    public long getY() {
        return y;
    }

    public void setY(long y) {
        this.y = y;
    }

    public LongPoint multiply(long k) {
        return new LongPoint(x*k, y*k);
    }

    public LongPoint remainder(int s) {
        return new LongPoint(x%s, y%s);
    }

    public LongPoint division(long k) {
        return new LongPoint(x/k, y/k);
    }

    public LongPoint substract(long d) {
        return new LongPoint(x-d, y-d);
    }

    public LongPoint substract(LongPoint dCenter) {
        return new LongPoint(x-dCenter.getX(), y-dCenter.getY());
    }
}
