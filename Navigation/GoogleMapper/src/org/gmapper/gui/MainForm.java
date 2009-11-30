package org.gmapper.gui;

import org.gmapper.gui.listeners.MouseListener;
import org.gmapper.BaseTile;
import org.gmapper.TileFactory;
import org.mapnav.exceptions.OutOfRange;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.image.BufferedImage;
import java.awt.image.LookupOp;
import java.awt.event.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.AffineTransform;
import java.util.*;

import sun.java2d.loops.XORComposite;
import sun.java2d.NullSurfaceData;
import sun.awt.image.BufImgSurfaceData;
import sun.awt.windows.Win32BackBufferSurfaceData;

/**
 * User: Vova
 * Time: 21:13:02
 *
 * @author ognivo777@mail.ru
 */
public class MainForm extends JFrame {
    //toDo: загрузку и пока карты -  в отдельный поток
    //ToDo: Показывать счётчик загрузки
    //ToDo: выбор типа карты
    private static Log log = LogFactory.getLog(MainForm.class);
    private JPanel mainPanel;
    private JPanel toolBar;
    private JPanel map;
    private JSlider mapLevel;
    private JLabel levelLabel;
    private JComboBox mapType;
    private JComboBox mapSource;
    private BufferedImage curMapVeiw;
    private DebugWindow debugWindow;
    private JLabel loadProgressBar;

    //Количество загружаемых тайлов вне зоны показа
    private int overload = 2;

    //Координаты центра показа относительно центрального тайла
    private int dx = 128;
    private int dy = 128;
    //Гугл-координаты центрального тайла
    private int centerTailX = 0;
    private int centerTailY = 0;
    //Гугл-координаты первого тайла показываемого массива
    private int firstViewTailX = 0;
    private int firstViewTailY = 0;

    //Размер загруженного массива тайлов
    private int loadedTilesCountX= 1;
    private int loadedTilesCountY= 1;

    int mouseX;
    int mouseY;
    int prevLevel=1;
    int maxTileNumForLevel=0;

    private final String MAP_TYPE_SAT = "Спутник";
    private final String MAP_TYPE_MAP = "Карта";

    private final String MAP_SOURCE_YANDEX = "Яндекс";
    private final String MAP_SOURCE_GOOGLE = "Google";

    public MainForm() throws HeadlessException {
        setContentPane(mainPanel);
        setSize(600, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mapType.addItem(MAP_TYPE_MAP);
        mapType.addItem(MAP_TYPE_SAT);
        mapType.setSelectedItem(MAP_TYPE_MAP);
        mapType.addItemListener(new ItemListener() {
            private int prevSelectedIndex = mapType.getSelectedIndex();
            public void itemStateChanged(ItemEvent e) {
                if(mapType.getSelectedIndex()!= prevSelectedIndex){
                    prevSelectedIndex = mapType.getSelectedIndex();
                    taskRedrawMap();
                }
            }
        });


        mapSource.addItem(MAP_SOURCE_YANDEX);
        mapSource.addItem(MAP_SOURCE_GOOGLE);
        mapSource.setSelectedItem(MAP_SOURCE_GOOGLE);
        mapSource.addItemListener(new ItemListener() {
            private int prevSelectedIndex = mapSource.getSelectedIndex();
            public void itemStateChanged(ItemEvent e) {
                if(mapSource.getSelectedIndex()!= prevSelectedIndex) {
                    prevSelectedIndex = mapSource.getSelectedIndex();
                    taskRedrawMap();
                }
            }
        });


        mapLevel.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                levelLabel.setText(mapLevel.getValue()+"");
                maxTileNumForLevel = (int) Math.pow(2, mapLevel.getValue()-1) - 1;
            }
        });
        mapLevel.addMouseListener(new org.gmapper.gui.listeners.MouseListener(){
            public void mouseReleased(MouseEvent e) {
                if(prevLevel > mapLevel.getValue()) {
                    int k = (prevLevel-mapLevel.getValue());
                    int localX = centerTailX % (2*k);
                    if(dx!=0) {
                        dx = dx/(2*k) + localX*(256/(2*k));
                    } else {
                        dx = localX*(256/(2*k)); 
                    }
                    centerTailX /= 2*k;
                    int localY = centerTailY % (2*k);
                    if(dy!=0) {
                        dy = dy/(2*k) + localY*(256/(2*k));
                    } else {
                        dy = localY*(256/(2*k));
                    }
                    centerTailY /= 2*k;
                } else if(prevLevel < mapLevel.getValue()) {
                    int k = (mapLevel.getValue()-prevLevel);
                    centerTailX=centerTailX*(2*k);
                    dx=dx*(2*k);
                    if(dx!=0) {
                        centerTailX+=dx / 256;
                        dx = dx % 256;
                    }

                    centerTailY=centerTailY*(2*k);
                    dy=dy*(2*k);
                    if(dy!=0) {
                        centerTailY+=dy / 256;
                        dy = dy % 256;
                    }
                }
                taskRedrawMap();
            }
        });

        map.addMouseListener(new MouseListener() {
            public void mousePressed(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
            }
        });

        map.addMouseMotionListener(new MouseMotionListener() {
            public void mouseDragged(MouseEvent event) {
                int dragX = mouseX - event.getX();
                int dragY = mouseY - event.getY();
                mouseX = event.getX();
                mouseY = event.getY();
                dx += dragX;
                dy += dragY;
                boolean redraw = false;
                if(dx > 256) {
                    redraw = true;
                    centerTailX += dx / 256;
                    dx = dx % 256;
                }
                if(dx < 0) {
                    redraw = true;
                    centerTailX -= dx / 256 + 1;
                    dx = 256 - ((-dx)%256);
                }

                if(dy > 256) {
                    redraw = true;
                    centerTailY += dy / 256;
                    dy = dy % 256;
                }
                if(dy < 0) {
                    redraw = true;
                    centerTailY -= dy / 256 + 1;
                    dy = 256 - ((-dy)%256);
                }

                if(redraw){
                    redrawMap();
                } else {
                    repaintMap();
                }
            }

            public void mouseMoved(MouseEvent event) {
            }
        });
    }

    public DebugWindow getDebugWindow() {
        return debugWindow;
    }

    public void setDebugWindow(DebugWindow debugWindow) {
        this.debugWindow = debugWindow;
    }

    public void update(Graphics g) {
        super.update(g);
        paint(g);
    }

    public void paint(Graphics g) {
        super.paint(g);
        repaintMap();
    }

    public void repaintMap() {
        BufferedImage paintBuffer = new BufferedImage(map.getWidth(), map.getHeight(), 1);
        if(curMapVeiw!=null) {
            int veiwCenterX = map.getWidth()/2;
            int veiwCenterY = map.getHeight()/2;

            int mapCenterX = (centerTailX - firstViewTailX)*256 + dx;
//            int mapCenterX = (firstViewTailX - centerTailX)*256 + dx;
            int mapCenterY = (centerTailY - firstViewTailY)*256 + dy;
//            int mapCenterY = (firstViewTailY - centerTailY)*256 + dy;

            int drawX = veiwCenterX - mapCenterX;
            int drawY = veiwCenterY - mapCenterY;

            
            paintBuffer.getGraphics().drawImage(curMapVeiw, drawX, drawY, null);
            map.getGraphics().setXORMode(Color.red);
            map.getGraphics().drawImage(paintBuffer, 0, 0, null);
            map.getGraphics().drawLine(veiwCenterX-7, veiwCenterY, veiwCenterX-2, veiwCenterY);
            map.getGraphics().drawLine(veiwCenterX+7, veiwCenterY, veiwCenterX+2, veiwCenterY);
            map.getGraphics().drawLine(veiwCenterX, veiwCenterY-7, veiwCenterX, veiwCenterY-2);
            map.getGraphics().drawLine(veiwCenterX, veiwCenterY+7, veiwCenterX, veiwCenterY+2);

            /*
            Graphics2D g2d = (Graphics2D) map.getGraphics();
            Shape invLine = new Line2D.Float(veiwCenterX-7, veiwCenterY-7, veiwCenterX+7, veiwCenterY+7);
            g2d.setPaint(Color.red);
            //g2d.setXORMode(Color.black);
            g2d.setPaintMode();
//            g2d.setStroke(new BasicStroke(1.0f);
            g2d.draw(invLine);     */


            if(debugWindow!=null) {
                debugWindow.mapSize.setText(curMapVeiw.getWidth()+"x"+curMapVeiw.getHeight());
                debugWindow.mapPos.setText(drawX+"x"+drawY);
            }
        } else {
            if(debugWindow!=null) {
                debugWindow.mapSize.setText("NULLxNULL");
                debugWindow.mapPos.setText("NULLxNULL");
            }
        }
        if(debugWindow!=null) {
            debugWindow.repaints.setText((Integer.parseInt(debugWindow.repaints.getText())+1)+"");
            debugWindow.viewSize.setText(map.getWidth()+"x"+map.getHeight());
            debugWindow.centerTail.setText(centerTailX+"x"+centerTailY);
            debugWindow.centerPosOnTail.setText(dx+"x"+dy);
        }
    }

    private int getCurMapType(){
        if(mapType.getSelectedItem().equals(MAP_TYPE_MAP))
            return BaseTile.MAP_TYPE_MAP;
        if(mapType.getSelectedItem().equals(MAP_TYPE_SAT))
            return BaseTile.MAP_TYPE_SAT;
        return BaseTile.MAP_TYPE_SAT;
    }

    private void taskRedrawMap() {
        Thread redraw = new Thread("Redrawer") {
            public void run() {
                redrawMap();
            }
        };
        redraw.start();
    }

    public void redrawMap() {
        prevLevel = this.mapLevel.getValue();
        int mapViewTilesCountByX = map.getWidth()/256;
        mapViewTilesCountByX += overload + mapViewTilesCountByX % 2;
        int mapViewTilesCountByY = map.getHeight()/256;
        mapViewTilesCountByY += overload + mapViewTilesCountByX % 2;

        firstViewTailX = centerTailX - mapViewTilesCountByX/2;

        firstViewTailY = centerTailY - mapViewTilesCountByY/2;

        try {
            java.util.List<java.util.List<BaseTile>> tiles = new ArrayList<java.util.List<BaseTile>>();
            loadProgressBar.setText(0+"");
            for(int i=firstViewTailX; i<=firstViewTailX + mapViewTilesCountByX && i <=maxTileNumForLevel; i++) {
                if(i<0){
                    firstViewTailX=i+1;
                    continue;
                }
                java.util.List<BaseTile> curCol = new ArrayList<BaseTile>();
                for(int j=firstViewTailY; j<=firstViewTailY + mapViewTilesCountByY && j <=maxTileNumForLevel; j++) {
                    if(j < 0) {
                        firstViewTailY = j+1;
                        continue;
                    }
                    log.info("Load tile: " + i + "x" + j + " ["+mapLevel.getValue()+"]");
                    BaseTile nextTile = null;
                    if(mapSource.getSelectedItem()==MAP_SOURCE_GOOGLE) {
                        nextTile = TileFactory.getGoogleTile(mapLevel.getValue(), i, j, getCurMapType());
                    } else if(mapSource.getSelectedItem()==MAP_SOURCE_YANDEX) {
                        nextTile = TileFactory.getYndexTile(mapLevel.getValue(), i, j, getCurMapType());
                    }
                    nextTile.load();
                    curCol.add(nextTile);
                    loadProgressBar.setText((Integer.parseInt(loadProgressBar.getText())+1)+"");
                }
                tiles.add(curCol);
            }
            if(tiles.size()==0) {
                curMapVeiw = null;
                return;
            }

            loadedTilesCountX = tiles.size();
            loadedTilesCountY = tiles.get(0).size();
            String regionSize = (loadedTilesCountX)+"x"+(loadedTilesCountY);
            log.info("Load "+ regionSize +" tiles..");
            String region = firstViewTailX+"x"+firstViewTailY
                    + " - " +
                     (firstViewTailX+loadedTilesCountX -1) + "x" + (firstViewTailY+loadedTilesCountY-1);
            log.info("Load "+ region +"region..");
            if(debugWindow!=null) {
                debugWindow.tileRegion.setText(region);
                debugWindow.tileRegionSize.setText(regionSize);
            }
            int mapVeiwSizeX = (loadedTilesCountX) * 256;
            int mapVeiwSizeY = (loadedTilesCountY) * 256;
            log.info("Create mapView, size: "+mapVeiwSizeX+"x"+mapVeiwSizeY);
            curMapVeiw = new BufferedImage(mapVeiwSizeX, mapVeiwSizeY, 1);
            //for
            for(int i = 0; i < loadedTilesCountX; i++ ) {
                for(int j = 0; j < loadedTilesCountY; j++) {
                    int drawX = i * 256;
                    int drawY = j * 256;
                    curMapVeiw.getGraphics().drawImage(tiles.get(i).get(j).getImage(), drawX, drawY, null);
                    log.info("drawedTo: " + drawX + "x" + drawY);
                }
            }
            repaintMap();
        } catch (OutOfRange outOfRange) {
            log.error("", outOfRange);
            //ToDo: Show Exception
         }

    }

}
