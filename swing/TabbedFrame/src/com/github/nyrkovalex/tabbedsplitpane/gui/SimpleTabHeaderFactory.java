package com.github.nyrkovalex.tabbedsplitpane.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Created with IntelliJ IDEA.
 * User: alex
 * Date: 11/4/12
 * Time: 7:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class SimpleTabHeaderFactory implements TabHeaderFactory {

    private ActionListener moveListener = null;
    private String buttonTitle = null;
    private ActionListener closeListener = null;

    /**
     * Creates a simple implementation of {@link TabHeaderFactory} interface which creates <b>move</b> and <b>close</b>
     * buttons with custom listeners provided.
     * @param moveButtonTitle title of a <b>move</b> button
     * @param moveListener listener for the <code>moveButton</code> activation event
     * @param closeListener listener for the <code>closeButton</code> activation event
     */
    public SimpleTabHeaderFactory(String moveButtonTitle, ActionListener moveListener, ActionListener closeListener) {
        this.buttonTitle = moveButtonTitle;
        this.moveListener = moveListener;
        this.closeListener = closeListener;
    }

    @Override
    public JPanel createHeader(Component component, String title) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.add(new JLabel(title));
        JButton moveButton = new TabHeaderButton(this.buttonTitle, component);
        moveButton.addActionListener(this.moveListener);
        panel.add(moveButton);
        JButton closeButton = new TabHeaderButton("x", component);
        closeButton.addActionListener(closeListener);
        panel.add(closeButton);
        return panel;
    }
}
