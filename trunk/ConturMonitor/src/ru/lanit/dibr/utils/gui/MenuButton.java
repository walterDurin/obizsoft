package ru.lanit.dibr.utils.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Created by IntelliJ IDEA.
 * User: Vova
 * Date: 07.09.2010
 * Time: 23:47:43
 * To change this template use File | Settings | File Templates.
 */
public class MenuButton extends JButton {
    public MenuButton() {
        setPreferredSize(new Dimension(15,15));
        addActionListener(new AbstractAction() {
			LogFrame lf = null;
			public void actionPerformed(ActionEvent e) {
                JPopupMenu opts = new JPopupMenu();
                opts.setInvoker(MenuButton.this);
                opts.add(new JMenuItem("1111"));
                opts.show(MenuButton.this, 10, 10);
//                opts.setAlwaysOnTop(true);
            }
        });
    }
}

