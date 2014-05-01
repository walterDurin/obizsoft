package ru.lanit.dibr.utils.gui.forms;

import ru.lanit.dibr.utils.CmdLineConfiguration;
import ru.lanit.dibr.utils.Configuration;
import ru.lanit.dibr.utils.core.SshSource;
import ru.lanit.dibr.utils.gui.FunctionPanel;
import ru.lanit.dibr.utils.gui.LogFrame;
import ru.lanit.dibr.utils.gui.LogPanel;
import ru.lanit.dibr.utils.gui.MenuButton;
import ru.lanit.dibr.utils.gui.configuration.Host;
import ru.lanit.dibr.utils.gui.configuration.LogFile;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Vladimir
 * Date: 18.04.14
 * Time: 13:31
 */

public class MainWindow {
    private JFrame window;
    private JPanel rootPanel;
    private JTabbedPane tabbedPane1;
    private JPanel logList;
//    private Configuration configuration;

    public static int logsCnt = 0;
    public MainWindow(Configuration cfg) {
//        configuration = cfg;
        window = new JFrame();
        window.setTitle("Log monitor 3.7");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        window.add(rootPanel);
        window.setSize(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().getSize());

        tabbedPane1.remove(0);

        logList.setLayout(new BoxLayout(logList, BoxLayout.Y_AXIS));
        for (Map.Entry<Host, Map<String, LogFile>> entry : cfg.getServers().entrySet()) {
            JPanel hostPane = new JPanel();
            hostPane.setLayout(new BoxLayout(hostPane, BoxLayout.Y_AXIS));
            Label hostLabel = new Label(entry.getKey().getDescription(), Label.CENTER);
            hostLabel.setFont(new Font("Courier", Font.BOLD, CmdLineConfiguration.fontSize+4));
            hostPane.add(hostLabel);
            JPanel buttons = new JPanel();
            GridBagLayout mgr = new GridBagLayout();
            buttons.setLayout(mgr);
            hostPane.add(buttons);
            for (Map.Entry<String, LogFile> logEntry : entry.getValue().entrySet()) {
                addButton(buttons,  logEntry.getValue(), entry.getKey());
                logsCnt++;
            }
            logList.add(hostPane);
        }
//        logList.updateUI();
        window.setVisible(true);
    }

    private void addButton(JPanel buttons, final LogFile logFile, final Host host) {
        final JButton b = new JButton(logFile.getName());
        System.out.println(b.getFont());
        b.setFont(new Font("Courier", 0, CmdLineConfiguration.fontSize+2));
        b.setBorder(new LineBorder(Color.GRAY));
        final MenuButton menuButton = logFile.isLocal()? null : new MenuButton(host, logFile.getPath(), logFile.getName());
        b.addActionListener(new AbstractAction() {
            LogPanel lp = null;
            Component tab;
            public void actionPerformed(ActionEvent e) {
                System.out.println(e.paramString());
                if(lp ==null) {
                    if(logFile.isLocal()) {
                        //TODO: реализовать нормальный Source для локальных файлов, используюя org.apache.commons.io.input.Tailer
                        //lp = new LogFrame(b, menuButton, logFile.getName(), new TestSource(logFile.getPath()), logFile.getBlockPattern());
                    } else {
                        JPanel contentPanel  = new JPanel();
                        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
                        lp = new LogPanel(new SshSource(host, logFile), logFile.getBlockPattern());
                        contentPanel.add(lp);
                        contentPanel.add(new FunctionPanel(lp));

                        new Thread() {
                            @Override
                            public void run() {
                                try {
                                    lp.connect();
                                } catch (Exception e1) {
                                    e1.printStackTrace();
                                }
                            }
                        }.start();
                        tab = tabbedPane1.add(host.getDescription()+ " : " + logFile.getName(), contentPanel);
                    }
                }
                tabbedPane1.setSelectedComponent(tab);
                lp.getViewport().getView().requestFocusInWindow();
            }
        });

        GridBagConstraints gbc =  new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.gridx = 0;

        buttons.add(b, gbc);

        gbc.gridx++;
        if(menuButton!=null) {
            buttons.add(menuButton, gbc);
        }
    }
}
