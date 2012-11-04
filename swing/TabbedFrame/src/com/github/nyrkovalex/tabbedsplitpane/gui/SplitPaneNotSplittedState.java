package com.github.nyrkovalex.tabbedsplitpane.gui;

/**
 * Created with IntelliJ IDEA.
 * User: alex
 * Date: 11/4/12
 * Time: 1:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class SplitPaneNotSplittedState extends SplitPaneAbstractState {
    private int splitterSize = 0;

    /**
     * Creates a new instance of a {@link SplitPaneState} implementation for the horizontal state
     * @param statedSplitPane split pane to merge
     */
    public SplitPaneNotSplittedState(StatedSplitPane statedSplitPane) {
        super(statedSplitPane);
        this.splitterSize = this.statedSplitPane.getDividerSize();
        this.statedSplitPane.setDividerSize(0);
    }

    /**
     * Restores the divider size and sets the state to {@link SplitPaneHorizontalState}
     */
    @Override
    public void splitHorizontal() {
        this.statedSplitPane.setState(new SplitPaneHorizontalState(this.statedSplitPane));
        this.statedSplitPane.setDividerSize(this.splitterSize);
    }

    /**
     * Restores the divider size and sets the state to {@link SplitPaneVerticalState}
     */
    @Override
    public void splitVertical() {
        this.statedSplitPane.setState(new SplitPaneVerticalState(this.statedSplitPane));
        this.statedSplitPane.setDividerSize(this.splitterSize);
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

    /**
     * Do not really need this as we will hide the splitter anyway
     */
    @Override
    protected void setOrientation() {
        return;
    }
}
