package ru.lanit.dibr.utils.utils;

import javax.swing.*;

/**
 * Created by IntelliJ IDEA.
 * User: VTaran
 * Date: 20.08.2010
 * Time: 19:42:49
 */
public class buttonPane {
	private JPanel pane;

	public buttonPane() {
		createUIComponents();
	}

	public JPanel getPane() {
		return pane;
	}

	public void setPane(JPanel pane) {
		this.pane = pane;
	}

	private void createUIComponents() {
		pane = new JPanel();
		// TODO: place custom component creation code here
	}
}
