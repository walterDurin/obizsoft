package ru.lanit.dibr.utils.gui;

import ru.lanit.dibr.utils.Configuration;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Map;
import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: VTaran
 * Date: 16.08.2010
 * Time: 18:34:16
 */
public class LogChoicer extends JFrame {
	private JPanel pane;
	private final Map<String, LogFrame> logs = new HashMap<String, LogFrame>();

	public LogChoicer(Configuration cfg) throws HeadlessException {
		setTitle("Log monitor");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setAlwaysOnTop(true);
		pane = new JPanel();
		pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS)); //Принудительно по горизонтали
		setLocationByPlatform(true);
		for (Map.Entry<Host, Map<String, String>> entry : cfg.getServers().entrySet()) {
			JPanel hostPane = new JPanel();
			hostPane.setLayout(new BoxLayout(hostPane, BoxLayout.Y_AXIS));
			Label hostLabel = new Label(entry.getKey().getDescription(), Label.CENTER);
			hostLabel.setFont(new Font("Courier", Font.BOLD, 16));
			hostPane.add(hostLabel);
			JPanel buttons = new JPanel();
			GridLayout mgr = new GridLayout(0, 2);
			buttons.setLayout(mgr);
			hostPane.add(buttons);
			int logCnt = 0;
			for (Map.Entry<String, String> logEntry : entry.getValue().entrySet()) {
				addButton(buttons,  logEntry.getKey(), entry.getKey(), logEntry.getValue());
				logCnt++;
			}
			pane.add(hostPane);
		}
		add(pane);
		pack();
//		setSize(300, getHeight());
	}

	private void addButton(JPanel buttons, final String name, final Host host, final String file) {
		final JButton b = new JButton(name+"  [off]  ");
		final String logName = host.getDescription()+":"+name;
		b.addActionListener(new AbstractAction() {
			LogFrame lf = null;
			public void actionPerformed(ActionEvent e) {
				System.out.println(e.paramString());
				if(lf==null) {
					lf = new LogFrame(host, file, name);
					b.setText(name + "  [on]     ");
					logs.put(logName, lf);
				}
				else {
					lf.setVisible(!lf.isVisible());
					b.setText(name + (lf.isVisible()?"  [on]     ":"  [hidden]"));
				}
			}
		});

		final JCheckBox c = new JCheckBox();
		c.setSelected(false);
		c.addActionListener(new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				if(logs.get(logName)!=null)
					logs.get(logName).setAutoScroll(!c.isSelected());
				else
					c.setSelected(!c.isSelected());
			}
		});
//		Box box = Box.createHorizontalBox();
//		box.add(b);
//		box.add(c);
//		buttons.add(box);
		buttons.add(b);
		buttons.add(c);
	}
}
