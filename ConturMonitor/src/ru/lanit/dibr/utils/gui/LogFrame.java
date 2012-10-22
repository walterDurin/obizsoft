package ru.lanit.dibr.utils.gui;

import com.sun.org.apache.bcel.internal.generic.NEW;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: VTaran
 * Date: 16.08.2010
 * Time: 17:57:48
 */
public class LogFrame  extends JFrame {

	private Thread t;
	private LogPanel panel;

	public LogFrame(final JButton b, final JComponent c, final Host host, final String logPath, String name) {
		setTitle(host.getDescription()+ " : " + name);
		setSize(1500, 500);
		setVisible(true);

//        Border border = BorderFactory.createLineBorder(Color.RED);
//        ((JComponent)getRootPane()).setBorder(border);

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		final LogPanel lp = new LogPanel(host, logPath);
		panel = lp;
		add(lp);
		t = new Thread() {
			@Override
			public void run() {
				try {
					lp.connect();
				} catch (Exception e) {
                    System.out.println(e);
					JOptionPane.showMessageDialog(LogFrame.this, "Can't open log '"+logPath+" on '"+host.getHost()+"'!");
                    LogFrame.this.setVisible(false);
                    b.setBorder(new LineBorder(Color.RED));
                    b.setEnabled(false);
                    c.setEnabled(false);
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
		t.interrupt();
		setVisible(false);
	}

	public void setAutoScroll(boolean autoScroll) {
		panel.setAutoScroll(autoScroll);
	}
}
