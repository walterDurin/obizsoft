package com.github.nyrkovalex.tabbedsplitpane.gui;

/**
 * Created with IntelliJ IDEA.
 * User: alex
 * Date: 11/4/12
 * Time: 3:41 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class SplitPaneAbstractState implements SplitPaneState {
    protected StatedSplitPane statedSplitPane = null;

    protected SplitPaneAbstractState(StatedSplitPane statedSplitPane) {
        this.statedSplitPane = statedSplitPane;
        this.setOrientation();
        this.setSplitterLocation();
    }

    /**
     * Sets the orientation of a splitter by the current state's rules
     */
    protected abstract void setOrientation();
}
