package ru.lanit.dibr.utils.gui.forms;

import ru.lanit.dibr.utils.gui.LogPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

/**
 * User: Vova
 * Date: 12.11.13
 * Time: 1:12
 */
public class Filter extends JPanel {
    private JPanel panel1;
    private JButton delButton;
    private JButton addButton;
    private JButton clearButton;
    private CheckBoxList checkBoxList;
    private JCheckBox checkBox1;
    private JLabel label1;

    private java.util.List<JCheckBox> checkBoxesList = new ArrayList<JCheckBox>();
    private Map<String, JCheckBox> checkBoxesMap = new HashMap<String, JCheckBox>();
    private ru.lanit.dibr.utils.core.Filter filter;
    private LogPanel lp;

    public Filter(String title) throws HeadlessException {

        add(panel1);
        label1.setText(title);

        setVisible(true);

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String pattern = (String) JOptionPane.showInputDialog(panel1, "Pattern" + ":\n", "Pattern", JOptionPane.INFORMATION_MESSAGE, null, null, null);
                if(pattern!=null && !(pattern = pattern.trim()).isEmpty() && !checkBoxesMap.containsKey(pattern)) {
                    JCheckBox checkBox = new JCheckBox(pattern, true);
                    checkBoxList.addCheckbox(checkBox);
                    checkBoxesList.add(checkBox);
                    checkBoxesMap.put(pattern, checkBox);
                }
            }
        });

        delButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(checkBoxList.getSelectedIndex()>=0) {
                    checkBoxesMap.remove(checkBoxesList.remove(checkBoxList.getSelectedIndex()).getText());
                    checkBoxList.removeCheckbox(checkBoxList.getSelectedIndex());

                }
            }
        });

        clearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                checkBoxesMap.clear();
                checkBoxesList.clear();
                checkBoxList.clear();
            }
        });

        checkBox1.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                checkBoxList.markAll(checkBox1.isSelected());
            }
        });
    }

    public void applyFilter(final LogPanel logPanel, final ru.lanit.dibr.utils.core.Filter filter) {
        this.filter = filter;
        this.lp = logPanel;
        for (JCheckBox jCheckBox : checkBoxesList) {
            jCheckBox.setSelected(false);
        }
        for (final String s : filter.getStringsToSearch()) {
            if(!checkBoxesMap.containsKey(s)) {
                final JCheckBox checkBox = new JCheckBox(s, true);
                checkBoxesMap.put(s,checkBox);
                checkBoxesList.add(checkBox);
                checkBoxList.addCheckbox(checkBox);
            } else {
                checkBoxesMap.get(s).setSelected(true   );
            }
        }
    }

    public void apply() {
        filter.invalidate();
        for (JCheckBox jCheckBox : checkBoxesList) {
            if(jCheckBox.isSelected()) {
                filter.addStringToSearch(jCheckBox.getText());
            } else {
                filter.removeStringFromSearch(jCheckBox.getText());
            }
        }
        lp.resetFilters();
    }
}
