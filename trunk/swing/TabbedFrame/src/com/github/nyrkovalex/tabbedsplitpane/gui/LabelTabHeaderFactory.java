package com.github.nyrkovalex.tabbedsplitpane.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;

/**
 * Created with IntelliJ IDEA.
 * User: alex
 * Date: 11/5/12
 * Time: 1:06 AM
 * To change this template use File | Settings | File Templates.
 */
public class LabelTabHeaderFactory implements TabHeaderFactory {

    private final MouseAdapter moveAdapter;
    private final MouseAdapter closeAdapter;
    // TODO: replace text with icons
    private final String MOVE_LABEL = "&lt;&gt;";
    private final String CLOSE_LABEL = "x";

    public LabelTabHeaderFactory(MouseAdapter moveAdapter, MouseAdapter closeAdapter) {
        this.moveAdapter = moveAdapter;
        this.closeAdapter = closeAdapter;
    }

    @Override
    public JPanel createHeader(Component component, String title) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        JLabel moveLabel = new TabHeaderActionLabel(this.MOVE_LABEL, component);
        moveLabel.addMouseListener(this.moveAdapter);
        panel.add(moveLabel);
        panel.add(new JLabel(" " + title + " "));
        JLabel closeLabel = new TabHeaderActionLabel(this.CLOSE_LABEL, component);
        closeLabel.addMouseListener(this.closeAdapter);
        panel.add(closeLabel);
        return panel;
    }
}
