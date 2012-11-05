package com.github.nyrkovalex.tabbedsplitpane.gui;

import javax.swing.*;

/**
 * Created with IntelliJ IDEA.
 * User: alex
 * Date: 11/4/12
 * Time: 1:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class SplitPaneNotSplittedState implements SplitPaneState {
    private StatedSplitPane statedSplitPane = null;

    /**
     * Creates a new instance of a {@link SplitPaneState} implementation for the horizontal state
     * @param statedSplitPane split pane to merge
     */
    public SplitPaneNotSplittedState(StatedSplitPane statedSplitPane) {
        this.statedSplitPane = statedSplitPane;
    }

    /**
     * Restores the divider size and sets the state to {@link SplitPaneHorizontalState}
     */
    @Override
    public void splitHorizontal() {
        this.statedSplitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        this.statedSplitPane.setState(this.statedSplitPane.getHorizontal());
        this.statedSplitPane.setSplitterLocation();
    }

    /**
     * Restores the divider size and sets the state to {@link SplitPaneVerticalState}
     */
    @Override
    public void splitVertical() {
        this.statedSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        this.statedSplitPane.setState(this.statedSplitPane.getVertical());
        this.statedSplitPane.setSplitterLocation();
    }

    /**
     * Sets the splitter location to the extreme end of a pane to hide it
     */
    @Override
    public void setSplitterLocation() {
        int position = this.statedSplitPane.getWidth();
        this.statedSplitPane.setDividerLocation(position);
    }

    /**
     * Do nothing as it is already the desired state
     */
    @Override
    public void joinTabs() {
        return;
    }
}
