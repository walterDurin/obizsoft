package ru.lanit.dibr.utils.gui;

import javax.swing.*;

/**
 * Created by IntelliJ IDEA.
 * User: VTaran
 * Date: 16.08.2010
 * Time: 17:57:48
 */
public class LogFrame  extends JFrame {

	private Thread t;
	private Thread t2;
	private LogPanel panel;

	public LogFrame(Host host, String logPath, String name) {
		setTitle(host.getDescription()+ " : " + name);
		setSize(1500, 500);
		setVisible(true);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		final LogPanel lp = new LogPanel(host, logPath);
		panel = lp;
		add(lp);

		t = new Thread() {
			@Override
			public void run() {
				try {
					lp.connect();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			@Override
			public void interrupt() {
				lp.stop();
			}
		};
		t.start();

	}

	public void stop() {
		t2.interrupt();
		t.interrupt();
		setVisible(false);
	}

	public void setAutoScroll(boolean autoScroll) {
		panel.setAutoScroll(autoScroll);
	}
}
