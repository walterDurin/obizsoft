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
                    SshUtil.exec(host, "zip -j -9 "+file+".zip "+file);
                    String savedFileName = ScpUtils.getFile(host, file+".zip", host.getDescription()+"_"+fileDescription);
                    SshUtil.exec(host, "rm "+file+".zip");
                    JOptionPane.showMessageDialog(MenuButton.this, "Файл \n'"+file+"'\nс хоста\n'"+host.getHost()+"'\nзаархивирован сохранён в катлоге программы как\n'"+savedFileName+"'.");
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

