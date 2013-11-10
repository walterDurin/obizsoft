package ru.lanit.dibr.utils.gui;

import sun.awt.HorizBagLayout;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Vova
 * Date: 10.11.13
 * Time: 2:20
 * To change this template use File | Settings | File Templates.
 */
public class FunctionPanel extends JPanel {
    private List<JButton> buttons = new ArrayList<JButton>();
    public FunctionPanel(final LogPanel lp) {
        addKeyListener(lp);
        JButton find = new JButton(" FIND ");
        buttons.add(find);
        find.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(e.getModifiers() == KeyEvent.SHIFT_MASK) {
                    System.out.println("Shift find");
                } else {
                    System.out.println("find");
                    lp.performFind();
                }
            }
        });
        JButton next = new JButton(" NEXT ");
        buttons.add(next);
        JButton previous = new JButton(" PREV ");
        buttons.add(previous);
        JButton lineFilter = new JButton(" LINE FILTER ");
        buttons.add(lineFilter);
        JButton blockFilter = new JButton(" BLOCK FILTER ");
        buttons.add(blockFilter);
        JButton clearFilters = new JButton(" CLR FILTERS");
        buttons.add(clearFilters);


        GridBagConstraints gbc =  new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.gridx = 0;

        for (JButton button : buttons) {
            button.setBorder(new LineBorder(Color.DARK_GRAY, 1));
            button.setFont(Font.getFont("Verdana"));
            button.addKeyListener(lp);
            add(button, gbc);
        }

        setMaximumSize(new Dimension(10000, 25));
        setMinimumSize(new Dimension(10, 25));
        setSize(100, 25);
    }
}
