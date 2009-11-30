package org.gmapper.newGui;

import org.gmapper.types.DoublePoint;

import java.util.List;
import java.util.ArrayList;

/**
 * User: Vova
 * Time: 4:39:13
 *
 * @author ognivo777@mail.ru
 */
public class MapState {

    //x - долгота (Longitude)4; y - широта (Latitude)  
    private DoublePoint mapGeoCenter;
    private int level;
    private int mapType;
    private int overlayMapType;

    public DoublePoint getMapGeoCenter() {
        return mapGeoCenter;
    }

    public void setMapGeoCenter(DoublePoint mapGeoCenter) {
        this.mapGeoCenter = mapGeoCenter;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getMapType() {
        return mapType;
    }

    public void setMapType(int mapType) {
        this.mapType = mapType;
    }

    public int getOverlayMapType() {
        return overlayMapType;
    }

    public void setOverlayMapType(int overlayMapType) {
        this.overlayMapType = overlayMapType;
    }
}
