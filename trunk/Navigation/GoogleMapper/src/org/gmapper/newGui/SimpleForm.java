package org.gmapper.newGui;

import org.gmapper.TileFactory;
import org.gmapper.types.DoublePoint;
import org.gmapper.yandex.YandexTile;

import javax.swing.*;
import java.awt.*;

/**
 * User: Vova
 * Time: 2:23:46
 *
 * @author ognivo777@mail.ru
 */
public class SimpleForm extends JFrame {

    private JPanel mapPanel;
//    private JPanel toolBarPanel;
//    private JPanel statusBarPanel;

    public SimpleForm() throws HeadlessException {
//        setContentPane(mapPanel);
//        add(toolBarPanel);
        createUIComponents();
        add(mapPanel);
//        add(statusBarPanel);
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        //======dev======== {

        MapState state = new MapState();
//        state.setMapGeoCenter(new DoublePoint(37.61714, 55.75207));
//        37.873493,55.749516
//        state.setMapGeoCenter(new DoublePoint(37.873493, 55.749516));
        state.setMapGeoCenter(new DoublePoint(37.616081, 55.756189));
        state.setLevel(10);
        state.setMapType(YandexTile.MAP_TYPE_MAP);
        state.setOverlayMapType(0);
        ((MapPanel) mapPanel).setState(state);
        Toolkit.getDefaultToolkit().setDynamicLayout(true);

        //======dev========= }

    }

    public void setGeoCoord(DoublePoint p) {
//        statusBarPanel.add(new Label("POS: "+p.x+" "+p.y));
    }

    public void drawMap() {
        ((MapPanel) mapPanel).drawToBuffer();
        mapPanel.revalidate();
    }

    private void createUIComponents() {
        mapPanel = new MapPanel(true, TileFactory.getYandexTileFactory(), this);
//        toolBarPanel = new JPanel();
//        statusBarPanel = new JPanel();
    }
}
