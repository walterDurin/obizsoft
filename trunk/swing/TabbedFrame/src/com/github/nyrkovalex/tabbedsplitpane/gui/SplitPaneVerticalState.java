package com.github.nyrkovalex.tabbedsplitpane.gui;

import javax.swing.*;

/**
 * Created with IntelliJ IDEA.
 * User: alex
 * Date: 11/4/12
 * Time: 1:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class SplitPaneVerticalState extends SplitPaneAbstractState {

    /**
     * Creates a new instance of a {@link SplitPaneState} implementation for the vertical state
     * @param statedSplitPane split pane to split
     */
    public SplitPaneVerticalState(StatedSplitPane statedSplitPane) {
        super(statedSplitPane);
    }

    @Override
    protected void setOrientation() {
        this.statedSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
    }

    /**
     * Sets the current state of a splitPane controlled to the {@link SplitPaneHorizontalState}
     */
    @Override
    public void splitHorizontal() {
        this.statedSplitPane.setState(new SplitPaneHorizontalState(this.statedSplitPane));
    }

    /**
     * Sets the current state of a splitPane controlled to the {@link SplitPaneNotSplittedState}
     */
    @Override
    public void joinTabs() {
        this.statedSplitPane.setState(new SplitPaneNotSplittedState(this.statedSplitPane));
    }

    /**
     * Do nothing as we are already in the desired state
     */
    @Override
    public void splitVertical() {
        return;
    }

    /**
     * Sets the splitter location maintaining the top / bottom pane ratio
     */
    @Override
    public void setSplitterLocation() {
        // TODO: Maintain ratio
        this.statedSplitPane.setDividerLocation(0.5);
    }
}
