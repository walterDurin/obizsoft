package com.github.nyrkovalex.tabbedsplitpane.gui;

import javax.swing.*;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: alex
 * Date: 11/4/12
 * Time: 7:23 PM
 * To change this template use File | Settings | File Templates.
 */
public interface TabHeaderFactory {

    /**
     * Creates a new header <code>JPanel</code> object for the <code>component</code> with the <code>title</code>
     * @param component Component to create header for (a tab content)
     * @param title title to set for the tab
     * @return new <code>JPanel</code> object
     */
    public JPanel createHeader(Component component, String title);
}
