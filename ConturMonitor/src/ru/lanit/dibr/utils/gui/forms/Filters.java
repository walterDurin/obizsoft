package ru.lanit.dibr.utils.gui.forms;

import ru.lanit.dibr.utils.core.BlockFilter;
import ru.lanit.dibr.utils.core.GrepFilter;

import javax.swing.*;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: Vova
 * Date: 12.11.13
 * Time: 4:05
 * To change this template use File | Settings | File Templates.
 */
public class Filters extends JFrame{
    private JPanel panel1;
    private JPanel panel2;
    private JPanel panel3;
    private JPanel panel4;
    private JPanel panel5;
    private JTabbedPane tabbedPane1;

    public static void main(String[] args) {
        new Filters("123").setVisible(true);
    }

    public Filters(String title) throws HeadlessException {
        super(title);
        add(panel1);
        setSize(550,500);
        setResizable(false);
    }


    private void createUIComponents() {
        panel3 = new Filter("Show blocks", new BlockFilter("",false));
        panel4 = new Filter("Hide blocks", new BlockFilter("",true));
        panel2 = new Filter("Show lines", new GrepFilter(false));
        panel5 = new Filter("Hide lines", new GrepFilter(true));
    }
}
