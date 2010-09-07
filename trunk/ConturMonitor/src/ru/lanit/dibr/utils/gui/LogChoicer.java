package ru.lanit.dibr.utils.gui;

import ru.lanit.dibr.utils.Configuration;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
		pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS)); //Принудительно по вертикали
		setLocationByPlatform(true);
		for (Map.Entry<Host, Map<String, String>> entry : cfg.getServers().entrySet()) {
			JPanel hostPane = new JPanel();
			hostPane.setLayout(new BoxLayout(hostPane, BoxLayout.Y_AXIS));
			Label hostLabel = new Label(entry.getKey().getDescription(), Label.CENTER);
			hostLabel.setFont(new Font("Courier", Font.BOLD, 16));
			hostPane.add(hostLabel);
			JPanel buttons = new JPanel();
			GridBagLayout mgr = new GridBagLayout();
			buttons.setLayout(mgr);
			hostPane.add(buttons);
			for (Map.Entry<String, String> logEntry : entry.getValue().entrySet()) {
				addButton(buttons,  logEntry.getKey(), entry.getKey(), logEntry.getValue());
			}
			pane.add(hostPane);
		}
		setContentPane(pane);
		pack();
        setResizable(false);
        

//		setSize(300, getHeight());
	}

	private void addButton(JPanel buttons, final String name, final Host host, final String file) {
        final JButton b = new JButton(name);
        final JCheckBox c = new JCheckBox();
        b.setBorder(new LineBorder(Color.GRAY));
		final String logName = host.getDescription()+":"+name;
		b.addActionListener(new AbstractAction() {
			LogFrame lf = null;
			public void actionPerformed(ActionEvent e) {
				System.out.println(e.paramString());
				if(lf==null) {
					lf = new LogFrame(b, c, host, file, name);
					b.setForeground(new Color(48, 129, 97));
                    b.setBorder(new LineBorder(new Color(48, 129, 97)));
                    b.setText(name);
					logs.put(logName, lf);
                    lf.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosing(WindowEvent e) {
                            lf.setVisible(false);
                                b.setBorder(new LineBorder(Color.GRAY));
                                b.setForeground(Color.GRAY);
                            b.setText(name);
                        }
                    });
				}
				else {
                    lf.setVisible(!lf.isVisible());
                    if(lf.isVisible()) {
                        b.setBorder(new LineBorder(new Color(48, 129, 97)));
                        b.setForeground(new Color(48, 129, 97));
                    } else {
                        b.setBorder(new LineBorder(Color.GRAY));
                        b.setForeground(Color.GRAY);
                    }
                    b.setText(name);
				}
			}
		});

		c.setSelected(false);
		c.addActionListener(new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				if(logs.get(logName)!=null)
					logs.get(logName).setAutoScroll(!c.isSelected());
				else
					c.setSelected(!c.isSelected());
			}
		});

        GridBagConstraints gbc =  new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.gridx = 0;

		buttons.add(b, gbc);

        gbc.gridx++;
		buttons.add(c, gbc);

        gbc.gridx++;
        buttons.add(new MenuButton(), gbc);
	}
}
