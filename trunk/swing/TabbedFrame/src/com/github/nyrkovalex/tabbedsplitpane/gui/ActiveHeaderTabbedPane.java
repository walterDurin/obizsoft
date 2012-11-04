package com.github.nyrkovalex.tabbedsplitpane.gui;

import javax.swing.*;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: alex
 * Date: 11/4/12
 * Time: 7:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class ActiveHeaderTabbedPane extends JTabbedPane {
    private TabHeaderFactory headerFactory = null;

    /**
     * Tab pane which creates a new tab with the header provided by the {@link TabHeaderFactory}
     * @param tabPlacement Magic constant determining the tab location
     * @param headerFactory Factory that provides header pane for every new tab created
     */
    public ActiveHeaderTabbedPane(int tabPlacement, TabHeaderFactory headerFactory) {
        super(tabPlacement);
        this.headerFactory = headerFactory;
    }

    @Override
    public void insertTab(String title, Icon icon, Component component, String tip, int index) {
        super.insertTab(null, icon, component, tip, index);
        setTabComponentAt(index, this.headerFactory.createHeader(component, title));
    }
}
