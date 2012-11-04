package com.github.nyrkovalex.tabbedsplitpane.gui;

import javax.swing.*;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: alex
 * Date: 11/5/12
 * Time: 1:07 AM
 * To change this template use File | Settings | File Templates.
 */
public class TabHeaderActionLabel extends JLabel implements TabHeaderActionItem {

    private Component tabComponent;

    public TabHeaderActionLabel(String text, Component component) {
        super();
        // TODO: Replace text with icon
        String formattedText = "<html><font color=\"#0000CF\"><u>" + text + "</u></font></html>";
        this.setText(formattedText);
        this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        this.tabComponent = component;
    }

    @Override
    public Component getComponent() {
        return this.tabComponent;
    }
}
