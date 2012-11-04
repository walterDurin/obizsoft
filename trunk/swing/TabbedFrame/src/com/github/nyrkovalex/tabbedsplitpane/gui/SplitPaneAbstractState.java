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
     * Sets the current state of a splitPane controlled to the {@link SplitPaneHorizontalState}
     */
    @Override
    public void splitHorizontal() {
        // TODO: Not sure if it is a good idea to do so because this way the class know about the child.
        this.statedSplitPane.setState(new SplitPaneHorizontalState(this.statedSplitPane));
    }

    /**
     * Sets the current state of a splitPane controlled to the {@link SplitPaneVerticalState}
     */
    @Override
    public void splitVertical() {
        // TODO: Not sure if it is a good idea to do so because this way the class know about the child.
        this.statedSplitPane.setState(new SplitPaneVerticalState(this.statedSplitPane));
    }

    /**
     * Sets the current state of a splitPane controlled to the {@link SplitPaneNotSplittedState}
     */
    @Override
    public void joinTabs() {
        // TODO: Not sure if it is a good idea to do so because this way the class know about the child.
        this.statedSplitPane.setState(new SplitPaneNotSplittedState(this.statedSplitPane));
    }

    /**
     * Sets the orientation of a splitter by the current state's rules
     */
    protected abstract void setOrientation();
}
