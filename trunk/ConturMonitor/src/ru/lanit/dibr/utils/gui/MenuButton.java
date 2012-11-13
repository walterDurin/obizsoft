package ru.lanit.dibr.utils.gui;

import ru.lanit.dibr.utils.gui.configuration.Host;
import ru.lanit.dibr.utils.utils.ScpUtils;
import ru.lanit.dibr.utils.utils.SshUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.Map;

import com.jcraft.jsch.JSchException;

/**
 * Created by IntelliJ IDEA.
 * User: Vova
 * Date: 07.09.2010
 * Time: 23:47:43
 * To change this template use File | Settings | File Templates.
 */
public class MenuButton extends JButton {
    public MenuButton(final Host host, final String file, final String fileDescription, final Map<String, LogFrame> logs, final String logName) {
        setText("...");
        setPreferredSize(new Dimension(15,15));

        final JPopupMenu opts = new JPopupMenu();
        opts.setInvoker(MenuButton.this);

        // Меню ======== СОХРАНИТЬ ============
        JMenuItem menuItem = new JMenuItem("Сохранить");
        menuItem.addActionListener(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                System.out.println(e);
                try {
                    String savedFileName;
                    // `basename /smartpaas-app/was/node1/WAShome/profiles/AppSrv01/logs/server1/stopServer.log`.` date +%Y%m%d%H%M%S -r /smartpaas-app/was/node1/WAShome/profiles/AppSrv01/logs/server1/stopServer.log`.zip
                    String zipFileName = SshUtil.exec(host, "echo ~/`basename "+file+"`.`date +%Y%m%d%H%M%S -r " + file +"`.zip ").trim();
                    System.out.println(zipFileName);
                    if(!SshUtil.exec(host, "zip -j -9 "+zipFileName+" "+file).contains("error")) {
                        savedFileName = ScpUtils.getFile(host, zipFileName, host.getDescription()+"_"+fileDescription);
                        SshUtil.exec(host, "rm " + zipFileName);
                        JOptionPane.showMessageDialog(MenuButton.this, "Файл \n'"+file+"'\nс хоста\n'"+host.getHost()+"'\nзаархивирован и сохранён в катлоге программы как\n'"+savedFileName+"'.");
                    } else {
                        savedFileName = ScpUtils.getFile(host, file, host.getDescription()+"_"+fileDescription);
                        JOptionPane.showMessageDialog(MenuButton.this, "Файл \n'"+file+"'\nс хоста\n'"+host.getHost()+"'\nсохранён в катлоге программы как\n'"+savedFileName+"'.");
                    }
                } catch (JSchException e1) {
                    e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                } catch (IOException e1) {
                    e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }

            }
        });
        opts.add(menuItem);

        // Меню ======== СТОП СКРОЛЛ ============
//        final JCheckBoxMenuItem scroolChBoxItm = new JCheckBoxMenuItem("Остановить скролл", false);
//        scroolChBoxItm.addActionListener(new AbstractAction() {
//			public void actionPerformed(ActionEvent e) {
//                System.out.println(e);
//                if(logs.get(logName)!=null)
//                    logs.get(logName).setAutoScroll(!scroolChBoxItm.isSelected());
//                else
//                    scroolChBoxItm.setSelected(!scroolChBoxItm.isSelected());
//			}
//		});
//        opts.add(scroolChBoxItm);

        //  Показ меню
        addActionListener(new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
                if(opts.isVisible()) {
                    opts.setVisible(false);
                    //opts.updateUI();
                } else {
                    opts.show(MenuButton.this, 10, 10);
                }
            }
        });
    }
}

