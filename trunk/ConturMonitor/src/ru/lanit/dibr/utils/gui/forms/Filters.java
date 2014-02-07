package ru.lanit.dibr.utils.gui.forms;

import ru.lanit.dibr.utils.core.BlockFilter;
import ru.lanit.dibr.utils.core.Filter;
import ru.lanit.dibr.utils.core.GrepFilter;
import ru.lanit.dibr.utils.gui.LogPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

/**
 * User: Vova
 * Date: 12.11.13
 * Time: 4:05
 */
public class Filters extends JFrame{
    private JPanel panel1;
    private JPanel directGrepPanel;
    private JPanel directBlockPanel;
    private JPanel reverseBlockPanel;
    private JPanel reverseGrepPanel;
    private JTabbedPane tabbedPane1;
    private JButton applyButton;

    private Filter directBlock;
    private Filter reverseBlock;
    private Filter directGrep;
    private Filter reverseGrep;

    private Map<Filter, JPanel> filtersMap = new HashMap<Filter, JPanel>();

    public static void main(String[] args) {
        new Filters("123", null, new BlockFilter("",false), new BlockFilter("", true), new GrepFilter(false), new GrepFilter(true)).setVisible(true);
    }

    public Filters(String title, LogPanel logPanel, ru.lanit.dibr.utils.core.Filter directBlock, ru.lanit.dibr.utils.core.Filter reverseBlock, ru.lanit.dibr.utils.core.Filter directGrep, ru.lanit.dibr.utils.core.Filter reverseGrep) throws HeadlessException {
        super(title);

        this.directBlock = directBlock;
        this.reverseBlock = reverseBlock;
        this.directGrep = directGrep;
        this.reverseGrep = reverseGrep;

        ((FilterPanel)directBlockPanel).init(logPanel);
        ((FilterPanel)reverseBlockPanel).init(logPanel);
        ((FilterPanel)directGrepPanel).init(logPanel);
        ((FilterPanel)reverseGrepPanel).init(logPanel);

        refresh();

        applyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ((FilterPanel)directBlockPanel).apply();
                ((FilterPanel)reverseBlockPanel).apply();
                ((FilterPanel)directGrepPanel).apply();
                ((FilterPanel)reverseGrepPanel).apply();
            }
        });

        add(panel1);
        pack();
//        setSize(550,500);
        setResizable(false);
    }

    public void addTo(Filter filter) {
        ((FilterPanel)filtersMap.get(filter)).applyFilter(filter);
    }

    public void refresh() {
        ((FilterPanel)directBlockPanel).applyFilter(directBlock);
        filtersMap.put(directBlock, directBlockPanel);
        ((FilterPanel)reverseBlockPanel).applyFilter(reverseBlock);
        filtersMap.put(reverseBlock, reverseBlockPanel);
        ((FilterPanel)directGrepPanel).applyFilter(directGrep);
        filtersMap.put(directGrep, directGrepPanel);
        ((FilterPanel)reverseGrepPanel).applyFilter(reverseGrep);
        filtersMap.put(reverseGrep, reverseGrepPanel);
    }

    private void createUIComponents() {
        directBlockPanel = new FilterPanel("Show blocks");
        reverseBlockPanel = new FilterPanel("Hide blocks");
        directGrepPanel = new FilterPanel("Show lines");
        reverseGrepPanel = new FilterPanel("Hide lines");
    }
}
