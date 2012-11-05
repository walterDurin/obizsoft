package com.github.nyrkovalex.tabbedsplitpane.gui;

import javax.swing.*;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: alex
 * Date: 11/4/12
 * Time: 12:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class StatedSplitPane extends JSplitPane {
    private SplitPaneState notSplitted = null;
    private SplitPaneState horizontal = null;
    private SplitPaneState vertical = null;
    private SplitPaneState currentState = null;

    /**
     * Split pane having three states as {@link SplitPaneState}:
     * <ul>
     *     <li>{@link SplitPaneNotSplittedState}</li>
     *     <li>{@link SplitPaneHorizontalState}</li>
     *     <li>{@link SplitPaneVerticalState}</li>
     * </ul>
     * @param first Left split pane component
     * @param second Right split pane component
     */
    public StatedSplitPane(Component first, Component second) {
        super(JSplitPane.HORIZONTAL_SPLIT, first, second);
        this.notSplitted = new SplitPaneNotSplittedState(this);
        this.horizontal = new SplitPaneHorizontalState(this);
        this.vertical = new SplitPaneVerticalState(this);
        this.currentState = this.getNotSplitted();
    }

    /**
     * Maximizes the first (left) pane, hiding the second one
     */
    public void joinTabs() {
        this.getCurrentState().joinTabs();
    }

    /**
     * Sets the splitter location by the rules of current state
     */
    public void setSplitterLocation() {
        this.getCurrentState().setSplitterLocation();
    }

    /**
     * Splits the pane horizontally preserving the current tab layout
     */
    public void splitHorizontal() {
        this.getCurrentState().splitHorizontal();
    }

    /**
     * Splits the pane vertically preserving the current tab layout
     */
    public void splitVertical() {
        this.getCurrentState().splitVertical();
    }

    /**
     * Sets the new state for the pane
     * @param state New pane state
     */
    void setState(SplitPaneState state) {
        this.currentState = state;
    }

    public SplitPaneState getHorizontal() {
        return horizontal;
    }

    public SplitPaneState getVertical() {
        return vertical;
    }

    public SplitPaneState getNotSplitted() {
        return notSplitted;
    }

    public SplitPaneState getCurrentState() {
        return currentState;
    }
}
