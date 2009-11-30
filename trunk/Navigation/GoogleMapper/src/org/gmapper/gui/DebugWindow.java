package org.gmapper.gui;

import javax.swing.*;
import java.awt.*;

/**
 * Date: 20.08.2008
 *
 * @author Taran Vladimir <vtaran@mbtc.ru>
 */

public class DebugWindow extends JFrame {
    private JPanel mainPanel;
    public JLabel viewSize;
    public JLabel repaints;
    public JLabel mapPos;
    public JLabel mapSize;
    public JLabel tileRegion;
    public JLabel tileRegionSize;
    public JLabel centerTail;
    public JLabel centerPosOnTail;

    public DebugWindow() throws HeadlessException {
        setContentPane(mainPanel);
        setSize(200, 200);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }
}
    