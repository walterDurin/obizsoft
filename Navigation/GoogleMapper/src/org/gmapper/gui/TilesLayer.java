package org.gmapper.gui;

import org.gmapper.BaseTile;
import org.gmapper.TileFactory;
import org.gmapper.TileParams;
import org.gmapper.types.IntPoint;
import org.mapnav.exceptions.OutOfRange;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * User: Vova
 * Time: 23:32:12
 *
 * @author ognivo777@mail.ru
 */
public class TilesLayer {

    private TileFactory tileFactory;
    private int size = 1;
    private BaseTile[][] tiles;
    private TileParams tileParams;
    private boolean loaded = false;
    private BufferedImage mapBuffer;

    /**
     * Загружает и рисует в буффер тайлы.
     *
     * @param tileFactory фабрика тайлов
     */
    public TilesLayer(TileFactory tileFactory) {
        this.tileFactory = tileFactory;
    }

    /**
     * Запускает процесс загрузки тайлов, начиная с центрального, и дальше по спирали
     * *
     *
     * @param tileParams параметры центрального тайла
     * @param radius     - "диаметр" загрузки. Размер стороны буффера равен radius*2 - 1 .
     */
    public void loadMap(TileParams tileParams, int radius) {
        this.size = radius * 2 - 1;
        this.tileParams = tileParams;
        mapBuffer = new BufferedImage(256 * size, 256 * size, BufferedImage.TYPE_INT_ARGB);
        try {
            BaseTile tile = tileFactory.getTile(tileParams.getLevel(), tileParams.getPos().x, tileParams.getPos().y, tileParams.getType());
            tile.load();

            mapBuffer.getGraphics().drawImage(tile.getImage(), (radius - 1) * 256, (radius - 1) * 256, null);

            IntPoint overTile;
            for (int i = 1; i <= radius; i++) {
                IntPoint p0 = new IntPoint(-i, -i);
                IntPoint d = new IntPoint(0, 1);
                overTile = tileParams.getPos().add(p0);
                BaseTile nextTile = tileFactory.getTile(tileParams.getLevel(), overTile.x, overTile.y, tileParams.getType());
                nextTile.load();
                mapBuffer.getGraphics().drawImage(nextTile.getImage(), (radius - 1 - i) * 256, (radius - 1 - i) * 256, null);
                //Отрисовка тех что уже загружены
                loadProcess(tileParams, radius, i, p0, d, true);
                //Отрисовка остальных
                loadProcess(tileParams, radius, i, p0, d, false);
            }

        } catch (OutOfRange outOfRange) {
            outOfRange.printStackTrace();
        }

        Graphics g = mapBuffer.getGraphics();

        g.setPaintMode();
        g.setColor(Color.BLUE);
//        g.drawOval(size*128-3, size*128-3, 6, 6);

        loaded = true;
    }

    private void loadProcess(TileParams tileParams, int radius, int i, IntPoint p0, IntPoint d, boolean onlyFromCache) throws OutOfRange {
        IntPoint overTile;
        BaseTile nextTile;
        for (IntPoint np = p0.add(d); !np.equals(p0); np = np.add(d)) {
            overTile = tileParams.getPos().add(np);
            nextTile = tileFactory.getTile(tileParams.getLevel(), overTile.x, overTile.y, tileParams.getType());
            if (!nextTile.checkAlreadyLoaded() || onlyFromCache) {
                continue;
            }
            nextTile.load();
            mapBuffer.getGraphics().drawImage(nextTile.getImage(), (radius - 1 + np.x) * 256, (radius - 1 + np.y) * 256, null);
            if (np.equals(new IntPoint(-i, i))) {
                d = new IntPoint(1, 0);
            } else if (np.equals(new IntPoint(i, i))) {
                d = new IntPoint(0, -1);
            } else if (np.equals(new IntPoint(i, -i))) {
                d = new IntPoint(-1, 0);
            }
        }
    }

    public boolean isLoaded() {
        return loaded;
    }

    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }

    public int getSize() {
        return size;
    }

    public BufferedImage getMapBuffer() {
        return mapBuffer;
    }

    public IntPoint getCenter() {
        return new IntPoint(128 * size);
    }

    public void resize(int scale, IntPoint shift, IntPoint inTileCoord) {
        int inPxSize = size * 256;
        inTileCoord = inTileCoord.add(-128);
        BufferedImage scaledMap = new BufferedImage(inPxSize, inPxSize, 1);
//        scaledMap.getGraphics().drawImage(mapBuffer, AffineTransform.getScaleInstance(0.7, 0.7), null)
        if (loaded && scale == 1) {
            shift = shift.add(inTileCoord);
            scaledMap.getGraphics().drawImage(mapBuffer,
//                    0,0,inPxSize, inPxSize,
                    0 - shift.x, 0 - shift.y, inPxSize - shift.x, inPxSize - shift.y,
                    inPxSize / 4, inPxSize / 4, inPxSize * 3 / 4, inPxSize * 3 / 4,
                    null);
        }
        if (loaded && scale == -1) {
            shift = shift.add(inTileCoord).multiply(0.5);
            scaledMap.getGraphics().drawImage(mapBuffer,
                    inPxSize / 4 + shift.x, inPxSize / 4 + shift.y, inPxSize * 3 / 4 + shift.x, inPxSize * 3 / 4 + shift.y,
                    0, 0, inPxSize, inPxSize,
                    null);
        }
        mapBuffer = scaledMap;
    }
}
