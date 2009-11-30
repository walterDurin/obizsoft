package org.mapnav.types;

import org.mapnav.exceptions.OutOfRange;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * Date: 18.08.2008
 *
 * @author Taran Vladimir <vtaran@mbtc.ru>
 */

public class Degrees {
    private int deg;
    private int min;
    private int sec;
    private boolean isPositive;

    public Degrees() {
        deg = 0;
        min = 0;
        sec = 0;
        isPositive = true;
    }

    public int getDeg() {
        return deg;
    }

    public void setDeg(int deg) throws OutOfRange {
        if(deg<0) {
            isPositive=false;
            deg*=-1;
        } else {
            isPositive = true;
        }
        if(deg>180)
            throw new OutOfRange(deg);
        this.deg = deg;
    }

    public double getFloatDeg() {
        return deg+min/60.+sec/3600.;
    }

    public void setFloatDeg(double floatDeg) {
        if(floatDeg<0) {
            isPositive=false;
            floatDeg=-floatDeg;
        } else {
            isPositive = true;
        }
        deg = (int) floatDeg;
        min =  (int)((floatDeg - deg)*60);
        BigDecimal sec = new BigDecimal(((floatDeg-deg)-min/60.)*60*60).round(new MathContext(2, RoundingMode.HALF_UP));
        this.sec = sec.intValue();
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) throws OutOfRange {
        if(deg<0 || deg>60)
            throw new OutOfRange(min);
        this.min = min;
    }

    public int getSec() {
        return sec;
    }

    public void setSec(int sec) throws OutOfRange {
        if(deg<0 || deg>60)
            throw new OutOfRange(sec);
        this.sec = sec;
    }

    public boolean isPositive() {
        return isPositive;
    }

    public void setPositive(boolean positive) {
        isPositive = positive;
    }
}
