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
    private SplitPaneState state = null;

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
    }

    /**
     * Gets the currenly active state of a pane
     * @return Current state
     */
    public SplitPaneState getState() {
        return this.state;
    }

    /**
     * Maximizes the first (left) pane, hiding the second one
     */
    public void joinTabs() {
        this.state.joinTabs();
    }

    /**
     * Sets the splitter location by the rules of current state
     */
    public void setSplitterLocation() {
        this.state.setSplitterLocation();
    }

    /**
     * Splits the pane horizontally preserving the current tab layout
     */
    public void splitHorizontal() {
        this.state.splitHorizontal();
    }

    /**
     * Splits the pane vertically preserving the current tab layout
     */
    public void splitVertical() {
        this.state.splitVertical();
    }

    /**
     * Sets the new state for the pane
     * @param state New pane state
     */
    public void setState(SplitPaneState state) {
        this.state = state;
    }
}
