package com.github.nyrkovalex.tabbedsplitpane.gui;

import javax.swing.*;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: alex
 * Date: 11/4/12
 * Time: 7:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class TabHeaderButton extends JButton implements TabHeaderActionItem {
    private Component component = null;

    /**
     * Creates a new button to use in the tab header. This button stores a link to the <code>Component</code> the
     * header is associated with
     * @param text Button title
     * @param component The tab content <code>Component</code> object to link this button with
     */
    public TabHeaderButton(String text, Component component) {
        super(text);
        this.component = component;
    }

    /**
     * Gets the <code>Component</code> this button is associated with
     * @return tab content <code>Component</code>
     */
    @Override
    public Component getComponent() {
        return component;
    }
}
