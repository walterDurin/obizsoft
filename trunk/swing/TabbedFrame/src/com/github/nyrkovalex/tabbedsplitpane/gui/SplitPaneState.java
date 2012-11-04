package com.github.nyrkovalex.tabbedsplitpane.gui;

/**
 * Created with IntelliJ IDEA.
 * User: alex
 * Date: 11/4/12
 * Time: 1:04 PM
 * To change this template use File | Settings | File Templates.
 */
public interface SplitPaneState {

    /**
     * Called when the panel is being split by vertical
     */
    public void splitHorizontal();

    /**
     * Called when the panel is being split by horizontal
     */
    public void splitVertical();

    /**
     * Sets the splitter location by the implementation defined rules
     */
    public void setSplitterLocation();

    /**
     * Called when the tabs are being merged to one
     */
    public void joinTabs();
}
