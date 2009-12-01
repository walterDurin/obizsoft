package org.gmapper.newGui;

import org.gmapper.types.IntPoint;
import org.gmapper.TileFactory;
import org.gmapper.TileParams;
import org.gmapper.google.GoogleTileFactory;
import org.gmapper.google.GoogleTile;
import org.gmapper.yandex.YandexTileFactory;
import org.gmapper.yandex.YandexTile;
import org.gmapper.gui.TilesLayer;
import org.mapnav.types.GeoPoint;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * User: Vova
 * Time: 2:31:20
 *
 * @author ognivo777@mail.ru
 */
public class MapPanel extends JPanel {
    private static Log log = LogFactory.getLog(MapPanel.class);

    private static ArrayList<MapState> yaMapStates = new ArrayList<MapState>();
    private int yaMapStateNextNum = 4;
    static {
        MapState mapState = new MapState();
        mapState.setMapType(YandexTile.MAP_TYPE_SAT);
        mapState.setOverlayMapType(0);
        yaMapStates.add(mapState);

        mapState = new MapState();
        mapState.setMapType(YandexTile.MAP_TYPE_SAT);
        mapState.setOverlayMapType(YandexTile.MAP_TYPE_HYB);
        yaMapStates.add(mapState);

        mapState = new MapState();
        mapState.setMapType(YandexTile.MAP_TYPE_SAT);
        mapState.setOverlayMapType(YandexTile.YA_MAP_TYPE_TAFFIC);
        yaMapStates.add(mapState);

        mapState = new MapState();
        mapState.setMapType(YandexTile.MAP_TYPE_MAP);
        mapState.setOverlayMapType(0);
        yaMapStates.add(mapState);

        mapState = new MapState();
        mapState.setMapType(YandexTile.MAP_TYPE_MAP);
        mapState.setOverlayMapType(YandexTile.MAP_TYPE_HYB);
        yaMapStates.add(mapState);

        mapState = new MapState();
        mapState.setMapType(YandexTile.MAP_TYPE_MAP);
        mapState.setOverlayMapType(YandexTile.YA_MAP_TYPE_TAFFIC);
        yaMapStates.add(mapState);

    }

    private static ArrayList<MapState> googleMapStates = new ArrayList<MapState>();
    private int googleMapStateNextNum = 0;
    static {
        MapState mapState = new MapState();
        mapState.setMapType(GoogleTile.MAP_TYPE_SAT);
        mapState.setOverlayMapType(0);
        googleMapStates.add(mapState);

        mapState = new MapState();
        mapState.setMapType(GoogleTile.MAP_TYPE_SAT);
        mapState.setOverlayMapType(GoogleTile.MAP_TYPE_HYB);
        googleMapStates.add(mapState);

        mapState = new MapState();
        mapState.setMapType(GoogleTile.MAP_TYPE_MAP);
        mapState.setOverlayMapType(0);
        googleMapStates.add(mapState);

        mapState = new MapState();
        mapState.setMapType(GoogleTile.GOOGLE_LANDSCAPE);
        mapState.setOverlayMapType(0);
        googleMapStates.add(mapState);
    }

    private MapState state = new MapState();
    private TilesLayer tilesLayer;
    private TilesLayer overlayLayer;
    private TileFactory tileFactory;
//    private SimpleForm mainForm;

    //MapDragging variables
    private IntPoint startDragPos;
    private IntPoint inDragShift = new IntPoint(0,0);


    public MapPanel(boolean isDoubleBuffered, TileFactory tileFactory, SimpleForm simpleForm) {
        super(isDoubleBuffered);
        this.tileFactory = tileFactory;
//        this.mainForm = simpleForm;
        Toolkit.getDefaultToolkit().setDynamicLayout(true);



        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if(e.getButton()==MouseEvent.BUTTON1)
                    startDragPos = new IntPoint(e.getX(), e.getY());
                if(e.getButton()==MouseEvent.BUTTON3) {
                    rollMapSource();
                }
                if(e.getButton()==MouseEvent.BUTTON2) {
                    rollMaptype();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if(e.getButton()==MouseEvent.BUTTON1) {
                    moveMap();
                    startDragPos=null;
                }
            }
        });

        addMouseWheelListener(new MouseWheelListener() {
            public void mouseWheelMoved(MouseWheelEvent e) {
                if(e.getScrollType()==MouseWheelEvent.WHEEL_UNIT_SCROLL) {
                    scaleMap(-e.getWheelRotation(), new IntPoint((int)( e.getX() - getSize().getWidth() / 2), (int) ( e.getY() - getSize().getHeight() / 2)));
                }
            }
        });

        addMouseMotionListener(new MouseMotionListener() {
            public void mouseDragged(MouseEvent e) {
                if(startDragPos!=null)
                dragMap(new IntPoint(e.getX(), e.getY()).add(startDragPos.multiply(-1)));
            }
            public void mouseMoved(MouseEvent e) {
            }
        });

        addComponentListener(new ComponentListener() {
            public void componentResized(ComponentEvent e) {
                int radius = (int) (getSize().getWidth() > getSize().getHeight() ? getSize().getWidth() : getSize().getHeight());
                radius = radius /256;
                radius = radius / 2 + 2;
                if (radius != (tilesLayer.getSize()+1)/2) {
                    drawToBuffer();
                    repaint();
                }
            }

            public void componentMoved(ComponentEvent e) {
            }

            public void componentShown(ComponentEvent e) {
            }

            public void componentHidden(ComponentEvent e) {
            }
        });


    }

    private void rollMaptype() {
        if(tileFactory instanceof YandexTileFactory) {
            state.setMapType(yaMapStates.get(yaMapStateNextNum).getMapType());
            state.setOverlayMapType(yaMapStates.get(yaMapStateNextNum).getOverlayMapType());
            yaMapStateNextNum++;
            if(yaMapStateNextNum==yaMapStates.size())
                yaMapStateNextNum=0;
        }
        if(tileFactory instanceof GoogleTileFactory) {
            state.setMapType(googleMapStates.get(googleMapStateNextNum).getMapType());
            state.setOverlayMapType(googleMapStates.get(googleMapStateNextNum).getOverlayMapType());
            googleMapStateNextNum++;
            if(googleMapStateNextNum==googleMapStates.size())
                googleMapStateNextNum=0;
        }
        log.debug("Cur SOURCE: " +tileFactory.getSourceName()+ "\nCur MAP: "+state.getMapType() + "\nCur OVER: " + state.getOverlayMapType());
        drawToBuffer();
        repaint();
    }

    private void rollMapSource() {
        if(tileFactory instanceof YandexTileFactory) {
            tileFactory = new GoogleTileFactory();
        } else {
            tileFactory = new YandexTileFactory();
        }
        log.debug("Cur SOURCE: " +tileFactory.getSourceName()+ "\nCur MAP: "+state.getMapType() + "\nCur OVER: " + state.getOverlayMapType());
        drawToBuffer();
        repaint();
    }

    private void scaleMap(final int k, final IntPoint shift) {
        tilesLayer.resize(k, shift, tileFactory.tilePixelFromGeoCoord(state.getMapGeoCenter(), state.getLevel()));
        repaint();
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                state.setMapGeoCenter(tileFactory.moveGeoCoord(state.getMapGeoCenter(), shift, state.getLevel()));
                state.setLevel(state.getLevel() + k);
                state.setMapGeoCenter(tileFactory.moveGeoCoord(state.getMapGeoCenter(), shift.multiply(-1), state.getLevel()));
                drawToBuffer();
                repaint();
            }
        });
    }

    private void moveMap() {
        startDragPos = null;
        state.setMapGeoCenter(tileFactory.moveGeoCoord(state.getMapGeoCenter(), inDragShift.multiply(-1), state.getLevel()));
        inDragShift = new IntPoint(0,0);
        drawToBuffer();
        repaint();
    }

    private void dragMap(IntPoint mapShift) {
        inDragShift = mapShift;
//        draw(getGraphics());
        repaint();
    }

    public MapState getState() {
        return state;
    }

    public void setState(MapState state) {
        this.state = state;
        drawToBuffer();
    }

    public TileFactory getTileFactory() {
        return tileFactory;
    }

    public void setTileFactory(TileFactory tileFactory) {
        this.tileFactory = tileFactory;
        drawToBuffer();
    }

    public void drawToBuffer() {
        IntPoint tilePos = tileFactory.tilePosFromGeoCoord(state.getMapGeoCenter(), state.getLevel());
        TileParams centerTileParams = new TileParams(tilePos, state.getLevel(), state.getMapType());

        tilesLayer = new TilesLayer(tileFactory);

        int raduis = (int) (getSize().getWidth() > getSize().getHeight() ? getSize().getWidth() : getSize().getHeight());
        raduis = raduis /256;
        raduis = raduis / 2 + 2;

        tilesLayer.loadMap(centerTileParams, raduis);
        if(state.getOverlayMapType()!=0) {
            TileParams overlayCenterTileParams = new TileParams(tilePos, state.getLevel(), state.getOverlayMapType());
            overlayLayer = new TilesLayer(tileFactory);
            overlayLayer.loadMap(overlayCenterTileParams, raduis);
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        IntPoint inTileCoord = tileFactory.tilePixelFromGeoCoord(state.getMapGeoCenter(), state.getLevel());
        IntPoint panelCenter = new IntPoint((int)getSize().getWidth()/2, (int)getSize().getHeight()/2);
        //panelCenter - tilesLayer.getCenter() - inTileCoord +128
        IntPoint drawStart = panelCenter.add(tilesLayer.getCenter().add(inTileCoord).multiply(-1)).add(128);
        drawStart = drawStart.add(inDragShift);
        g.setPaintMode();
        g.drawImage(tilesLayer.getMapBuffer(), drawStart.x,  drawStart.y, null);
        if(state.getOverlayMapType()!=0)
            g.drawImage(overlayLayer.getMapBuffer(), drawStart.x,  drawStart.y, null);
        if(tilesLayer.isLoaded())
            g.setColor(Color.BLACK);
        else
            g.setColor(Color.PINK);
//        g.setXORMode(Color.BLACK);
        g.drawOval(panelCenter.x-15, panelCenter.y-15, 30, 30);
        g.drawLine(panelCenter.x-20, panelCenter.y,   panelCenter.x-5, panelCenter.y);
        g.drawLine(panelCenter.x+20, panelCenter.y,   panelCenter.x+5, panelCenter.y);
        g.drawLine(panelCenter.x,   panelCenter.y-20, panelCenter.x,   panelCenter.y-5);
        g.drawLine(panelCenter.x,   panelCenter.y+20, panelCenter.x,   panelCenter.y+5);

        GeoPoint gp = new GeoPoint(state.getMapGeoCenter());

        g.drawString("POS: "+gp, 10, (int)getSize().getHeight()-10);
    }

}
