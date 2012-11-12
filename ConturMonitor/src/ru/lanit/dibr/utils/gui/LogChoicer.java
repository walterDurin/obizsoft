package ru.lanit.dibr.utils.gui;

import ru.lanit.dibr.utils.Configuration;
import ru.lanit.dibr.utils.gui.configuration.Host;
import ru.lanit.dibr.utils.gui.configuration.LogFile;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: VTaran
 * Date: 16.08.2010
 * Time: 18:34:16
 */
public class LogChoicer extends JFrame {
    public static int size;
    public static int countShownLogWindow = 0;
    public static int logsCnt = 0;
	private JPanel pane;
	private final Map<String, LogFrame> logs = new HashMap<String, LogFrame>();

	public LogChoicer(Configuration cfg) throws HeadlessException {
		setTitle("Log monitor");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setAlwaysOnTop(true);
		pane = new JPanel();
		pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS)); //������������� �� ���������
		//setLocationByPlatform(true);
        setLocation(0, 250);
		for (Map.Entry<Host, Map<String, LogFile>> entry : cfg.getServers().entrySet()) {
			JPanel hostPane = new JPanel();
			hostPane.setLayout(new BoxLayout(hostPane, BoxLayout.Y_AXIS));
			Label hostLabel = new Label(entry.getKey().getDescription(), Label.CENTER);
			hostLabel.setFont(new Font("Courier", Font.BOLD, 16));
			hostPane.add(hostLabel);
			JPanel buttons = new JPanel();
			GridBagLayout mgr = new GridBagLayout();
			buttons.setLayout(mgr);
			hostPane.add(buttons);
			for (Map.Entry<String, LogFile> logEntry : entry.getValue().entrySet()) {
				addButton(buttons,  logEntry.getValue(), entry.getKey());
                logsCnt++;
			}
			pane.add(hostPane);
		}
		setContentPane(pane);
		pack();
        setResizable(false);
        size = getWidth();
        

//		setSize(300, getHeight());
	}

	private void addButton(JPanel buttons, final LogFile logFile, final Host host) {
        final JButton b = new JButton(logFile.getName());
        b.setBorder(new LineBorder(Color.GRAY));
		final String logName = host.getDescription()+":"+logFile;
        final MenuButton menuButton = new MenuButton(host, logFile.getPath(), logFile.getName(), logs, logName);
		b.addActionListener(new AbstractAction() {
			LogFrame lf = null;
			public void actionPerformed(ActionEvent e) {
				System.out.println(e.paramString());
				if(lf==null) {
					lf = new LogFrame(b, menuButton, host, logFile);
                    lf.setVisible(true);
					b.setForeground(new Color(48, 129, 97));
                    b.setBorder(new LineBorder(new Color(48, 129, 97)));
                    b.setText(logFile.getName());
					logs.put(logName, lf);
                    lf.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosing(WindowEvent e) {
                            lf.setVisible(false);
                                b.setBorder(new LineBorder(Color.GRAY));
                                b.setForeground(Color.GRAY);
                            b.setText(logFile.getName());
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
                    b.setText(logFile.getName());
				}

                //auto arrange visible windows
                ArrayList<LogFrame>  visibleWindows = new ArrayList<LogFrame>();
                for (LogFrame logWindow : logs.values()) {
                    if(logWindow.isVisible()) {
                        visibleWindows.add(logWindow);
                    }
                }
                int height = (int) (GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().getHeight()/visibleWindows.size());
                int i = 0;
                for (LogFrame visibleWindow : visibleWindows) {
                    visibleWindow.setLocation(LogChoicer.size,(int)((i++)*height));
                    visibleWindow.setSize((int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth() - LogChoicer.size), height);
                }

			}
		});

        GridBagConstraints gbc =  new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.gridx = 0;

		buttons.add(b, gbc);


/*
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
        gbc.gridx++;
		buttons.add(c, gbc);
*/

        gbc.gridx++;
        buttons.add(menuButton, gbc);
	}
}
