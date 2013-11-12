package ru.lanit.dibr.utils.gui.forms;

import ru.lanit.dibr.utils.core.AbstractFilter;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * Created by IntelliJ IDEA.
 * User: Vova
 * Date: 12.11.13
 * Time: 1:12
 * To change this template use File | Settings | File Templates.
 */
public class Filter extends JPanel {
    private JPanel panel1;
    private JButton delButton;
    private JButton addButton;
    private JButton clearButton;
    private CheckBoxList checkBoxList;
    private JCheckBox checkBox1;
    private JLabel label1;

    private AbstractFilter filter;

    public Filter(String title, final AbstractFilter filter) throws HeadlessException {
        this.filter = filter;

        for (final String s : filter.getStringsToSearch()) {
            final JCheckBox checkBox = new JCheckBox(s);
            checkBox.addItemListener(new ItemListener() {
                public void itemStateChanged(ItemEvent e) {
                    if(checkBox.isSelected()) {
                        filter.addStringToSearch(s);
                    } else {
                        filter.removeStringFromSearch(s);
                    }
                }
            });
            checkBoxList.addCheckbox(checkBox);
        }

        add(panel1);
        label1.setText(title);

        setVisible(true);

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String pattern = (String) JOptionPane.showInputDialog(panel1, "Pattern" + ":\n", "Pattern", JOptionPane.INFORMATION_MESSAGE, null, null, null);
                checkBoxList.addCheckbox(new JCheckBox(pattern));
            }
        });

        delButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(checkBoxList.getSelectedIndex()>=0) {
                    //((JCheckBox)checkBoxList.getComponent(checkBoxList.getSelectedIndex())).setSelected(false);
                    checkBoxList.removeCheckbox(checkBoxList.getSelectedIndex());
                }
            }
        });

        clearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                checkBoxList.clear();
            }
        });

        checkBox1.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                checkBoxList.markAll(checkBox1.isSelected());
                System.out.println(checkBox1.isSelected());
            }
        });
    }

    private void createUIComponents() {
    }

    public void actionPerformed(ActionEvent e) {

    }
}
