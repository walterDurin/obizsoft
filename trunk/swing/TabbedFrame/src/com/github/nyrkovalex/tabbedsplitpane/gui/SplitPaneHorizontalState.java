package com.github.nyrkovalex.tabbedsplitpane.gui;

import javax.swing.*;

/**
 * Created with IntelliJ IDEA.
 * User: alex
 * Date: 11/4/12
 * Time: 1:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class SplitPaneHorizontalState extends SplitPaneAbstractState {

    /**
     * Creates a new instance of a {@link SplitPaneState} implementation for the horizontal state
     * @param statedSplitPane split pane to align horizontally
     */
    public SplitPaneHorizontalState(StatedSplitPane statedSplitPane) {
        super(statedSplitPane);
    }

    @Override
    protected void setOrientation() {
        this.statedSplitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
    }

    /**
     * Do nothing as this is already a desired state
     */
    @Override
    public void splitHorizontal() {
        return;
    }

    /**
     * Sets the current state of a splitPane controlled to the {@link SplitPaneVerticalState}
     */
    @Override
    public void splitVertical() {
        this.statedSplitPane.setState(new SplitPaneVerticalState(this.statedSplitPane));
    }

    /**
     * Sets the current state of a splitPane controlled to the {@link SplitPaneNotSplittedState}
     */
    @Override
    public void joinTabs() {
        this.statedSplitPane.setState(new SplitPaneNotSplittedState(this.statedSplitPane));
    }

    /**
     * Sets the splitter location maintaining the left / right pane ratio
     */
    @Override
    public void setSplitterLocation() {
        // TODO: Maintain the ratio
        this.statedSplitPane.setDividerLocation(0.5);
    }

}
