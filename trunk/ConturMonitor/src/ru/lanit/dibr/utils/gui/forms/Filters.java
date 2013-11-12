package ru.lanit.dibr.utils.gui.forms;

import ru.lanit.dibr.utils.core.BlockFilter;
import ru.lanit.dibr.utils.core.GrepFilter;
import ru.lanit.dibr.utils.gui.LogPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * User: Vova
 * Date: 12.11.13
 * Time: 4:05
 */
public class Filters extends JFrame{
    private JPanel panel1;
    private JPanel panel2;
    private JPanel panel3;
    private JPanel panel4;
    private JPanel panel5;
    private JTabbedPane tabbedPane1;
    private JButton applyButton;

    private ru.lanit.dibr.utils.core.Filter directBlock;
    private ru.lanit.dibr.utils.core.Filter reverseBlock;
    private ru.lanit.dibr.utils.core.Filter directGrep;
    private ru.lanit.dibr.utils.core.Filter reverseGrep;

    private LogPanel logPanel;

    public static void main(String[] args) {
        new Filters("123", null, new BlockFilter("",false), new BlockFilter("", true), new GrepFilter(false), new GrepFilter(true)).setVisible(true);
    }

    public Filters(String title, LogPanel logPanel, ru.lanit.dibr.utils.core.Filter directBlock, ru.lanit.dibr.utils.core.Filter reverseBlock, ru.lanit.dibr.utils.core.Filter directGrep, ru.lanit.dibr.utils.core.Filter reverseGrep) throws HeadlessException {
        super(title);

        this.logPanel = logPanel;
        this.directBlock = directBlock;
        this.reverseBlock = reverseBlock;
        this.directGrep = directGrep;
        this.reverseGrep = reverseGrep;

        refresh();

        applyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ((Filter)panel3).apply();
                ((Filter)panel4).apply();
                ((Filter)panel2).apply();
                ((Filter)panel5).apply();
            }
        });

        add(panel1);
        setSize(550,500);
        setResizable(false);
    }

    public void refresh() {
        ((Filter)panel3).applyFilter(logPanel, directBlock);
        ((Filter)panel4).applyFilter(logPanel, reverseBlock);
        ((Filter)panel2).applyFilter(logPanel, directGrep);
        ((Filter)panel5).applyFilter(logPanel, reverseGrep);
    }

    private void createUIComponents() {
        panel3 = new Filter("Show blocks");
        panel4 = new Filter("Hide blocks");
        panel2 = new Filter("Show lines");
        panel5 = new Filter("Hide lines");
    }
}
