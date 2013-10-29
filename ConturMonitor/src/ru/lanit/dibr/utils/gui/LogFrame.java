package ru.lanit.dibr.utils.gui;

import ru.lanit.dibr.utils.core.LogSource;
import ru.lanit.dibr.utils.core.SshSource;
import ru.lanit.dibr.utils.gui.configuration.Host;
import ru.lanit.dibr.utils.gui.configuration.LogFile;

import javax.swing.*;
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

	public LogFrame(final JButton b, final JComponent c, final String title, final LogSource logSource, final String blockPattern) {
		setTitle(title);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        JPanel contentPanel  = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        final LogPanel lp = new LogPanel(logSource, blockPattern);
		panel = lp;
        contentPanel.add(lp);
//
//        JPanel bottomPanel = new JPanel();
//        JButton scrollState = new JButton("123");
//        bottomPanel.add(scrollState);
//        bottomPanel.setMaximumSize(new Dimension(100, 150));
//        bottomPanel.setMinimumSize(new Dimension(10, 150));
//        bottomPanel.setSize(100, 150);
//        contentPanel.add(bottomPanel);
//
        setContentPane(contentPanel);
		t = new Thread() {
			@Override
			public void run() {
				try {
					lp.connect();
				} catch (Exception e) {
                    e.printStackTrace();
                    System.out.println(e);
					JOptionPane.showMessageDialog(LogFrame.this, "Can't open log '" + title + "'!\n" + e.getMessage());
                    LogFrame.this.setVisible(false);

                    //ToDO: ������ ������ ��� �����������!
                    if(b!=null) {
                        b.setBorder(new LineBorder(Color.RED));
                        b.setEnabled(false);
                    }
                    if(c!=null) {
                        c.setEnabled(false);
                    }
				}
			}

			@Override
			public void interrupt() {
				lp.stop();
			}
		};
		t.start();

	}
}
