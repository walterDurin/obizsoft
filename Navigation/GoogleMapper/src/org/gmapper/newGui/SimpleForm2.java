package org.gmapper.newGui;

import org.gmapper.types.DoublePoint;
import org.gmapper.yandex.YandexTile;
import org.gmapper.TileFactory;

import javax.swing.*;
import java.awt.*;

/**
 * User: vtaran
 * 02.03.2009 13:41:33*
 *
 * @author vtaran@lanit.ru
 */
public class SimpleForm2 extends JFrame {

    private JPanel mapPanel;

    public SimpleForm2() throws HeadlessException {
//        setContentPane(mapPanel);
        mapPanel = new MapPanel(true, TileFactory.getYandexTileFactory(), null);
        add(mapPanel);
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);



        //======dev======== {

        MapState state = new MapState();
//        state.setMapGeoCenter(new DoublePoint(37.61714, 55.75207));
//        37.873493,55.749516
        state.setMapGeoCenter(new DoublePoint(37.873493, 55.749516));
        state.setLevel(16);
        state.setMapType(YandexTile.MAP_TYPE_MAP);
        ((MapPanel) mapPanel).setState(state);
        Toolkit.getDefaultToolkit().setDynamicLayout(true);

        //======dev========= }

    }

    public void drawMap() {
        ((MapPanel) mapPanel).drawToBuffer();
        mapPanel.revalidate();
    }

}
