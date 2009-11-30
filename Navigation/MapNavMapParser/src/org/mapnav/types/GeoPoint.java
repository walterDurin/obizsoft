package org.mapnav.types;

import org.gmapper.types.DoublePoint;

/**
 * Date: 18.08.2008
 *
 * @author Taran Vladimir <vtaran@mbtc.ru>
 */

public class GeoPoint {
    private Degrees lat = null; //широта (+N/-S)
    private Degrees lon = null; //долгота (+E/-W)

    public GeoPoint() {
        lat = new Degrees();
        lon = new Degrees();
    }

    public GeoPoint(DoublePoint dp) {
        this();
        lat.setFloatDeg(dp.y);
        lon.setFloatDeg(dp.x);
    }

    public Degrees getLat() {
        return lat;
    }

    public void setLat(Degrees lat) {
        this.lat = lat;
    }

    public Degrees getLon() {
        return lon;
    }

    public void setLon(Degrees lon) {
        this.lon = lon;
    }

    public String toString() {
        return lat.getDeg()+"°"+lat.getMin()+"'"+lat.getSec()+"\""+(lat.isPositive()?"N":"S") + "  " + lon.getDeg()+"°"+lon.getMin()+"'"+lon.getSec()+"\""+(lon.isPositive()?"E":"W");
    }
}
