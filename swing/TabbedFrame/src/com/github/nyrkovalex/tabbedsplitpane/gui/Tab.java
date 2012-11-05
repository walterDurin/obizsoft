package com.github.nyrkovalex.tabbedsplitpane.gui;

import javax.swing.*;

/**
 * Created with IntelliJ IDEA.
 * User: alex
 * Date: 11/4/12
 * Time: 12:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class Tab extends JPanel {

    private JTabbedPane tabbedPane;
    private String title;

    /**
     * Creates a new <code>Tab</code> with the blank title on the provided <code>tabbedPane</code>
     * @param tabbedPane container pane for a <code>Tab</code>
     */
    public Tab(JTabbedPane tabbedPane) {
        this(tabbedPane, "");
    }

    /**
     * Creates a new <code>Tab</code> with the provided title on the provided <code>tabbedPane</code>
     * @param tabbedPane container pane for a <code>Tab</code>
     * @param title title to display on the <code>Tab</code> header
     */
    public Tab(JTabbedPane tabbedPane, String title) {
        this.tabbedPane = tabbedPane;
        this.title = title;
        this.tabbedPane.addTab(this.title, this);
    }

    /**
     * Gets the cuurrent container pane of a <code>Tab</code>
     * @return tabbed pane this tab is bond with
     */
    public JTabbedPane getTabbedPane() {
        return tabbedPane;
    }

    /**
     * Moves this <code>Tab</code> from one container pane to the other
     * @param destination pane to move this <code>Tab</code> to
     */
    public void move(JTabbedPane destination) {
        this.tabbedPane.remove(this);
        destination.addTab(this.title, this);
        this.tabbedPane = destination;
    }

    /**
     * Removes this <code>Tab</code> from the containing pane
     */
    public void close() {
        this.tabbedPane.remove(this);
        this.tabbedPane = null;
    }
}
