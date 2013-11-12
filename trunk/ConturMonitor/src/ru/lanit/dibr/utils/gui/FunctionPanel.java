package ru.lanit.dibr.utils.gui;

import sun.awt.HorizBagLayout;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

        JButton button = new JButton(" Filters ");
        button.setToolTipText("Show filters");
        buttons.add(button);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lp.filtersWindow.refresh();
                lp.filtersWindow.setVisible(true);
            }
        });

        button = new JButton(" << ");
        button.setToolTipText("Search previous <Shift + F3>");
        buttons.add(button);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lp.findWord(true);
            }
        });

        button = new JButton(" FIND ");
        buttons.add(button);
        button.setToolTipText("Search <Ctrl + F>");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lp.performFind();
            }
        });

        button = new JButton(" >> ");
        button.setToolTipText("Search next <F3>");
        buttons.add(button);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lp.findWord(false);
            }
        });

        button = new JButton(" LINE FILTER ");
        buttons.add(button);
        button.setToolTipText("Show the only lines with text <Ctrl + G>");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lp.addGrepFilter(false);
            }
        });

        button = new JButton(" BLOCK FILTER ");
        buttons.add(button);
        button.setToolTipText("Show the only blocks with text <Ctrl + B>");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                    lp.addBlockFilter(false);
            }
        });

        button = new JButton(" CLR FILTERS ");
        buttons.add(button);
        button.setToolTipText("Clear Filter <Shift + F5>");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lp.clearFilters();
            }
        });


        GridBagConstraints gbc =  new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.gridx = 0;

        for (JButton nextBtn : buttons) {
            nextBtn.setBorder(new LineBorder(Color.DARK_GRAY, 1));
            nextBtn.setFont(Font.getFont("Verdana"));
            nextBtn.addKeyListener(lp);
            add(nextBtn, gbc);
        }

        setMaximumSize(new Dimension(10000, 25));
        setMinimumSize(new Dimension(10, 25));
        setSize(100, 25);
    }
}
