package ru.lanit.dibr.utils.gui.forms;

import ru.lanit.dibr.utils.gui.FilterEntry;
import ru.lanit.dibr.utils.gui.LogPanel;
import ru.lanit.dibr.utils.gui.LogSettings;
import ru.lanit.dibr.utils.gui.SessionSettings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

/**
 * User: Vova
 * Date: 12.11.13
 * Time: 1:12
 */
public class FilterPanel extends JPanel {
    private JPanel panel1;
    private JButton delButton;
    private JButton addButton;
    private JButton clearButton;
    private CheckBoxList checkBoxList;
    private JCheckBox checkBox1;
    private JLabel label1;
    private JButton editButton;

    private java.util.List<JCheckBox> checkBoxesList = new ArrayList<JCheckBox>();
    private Map<String, JCheckBox> checkBoxesMap = new HashMap<String, JCheckBox>();
    private ru.lanit.dibr.utils.core.Filter filter;
    private LogPanel lp;
    private SessionSettings settings;
    private List<FilterEntry> filterEntries;

    public void init(final LogPanel logPanel) {
        this.lp = logPanel;

        settings = SessionSettings.getInstance();
        LogSettings logSettings = settings.getLogSettingsMap().get(getFilterSettingsKey());
        if(logSettings == null) {
            logSettings = new LogSettings();
            settings.getLogSettingsMap().put(getFilterSettingsKey(), logSettings);
        }
        filterEntries = logSettings.getFiltersList();
        for (FilterEntry filterEntry : filterEntries) {
            addFilter(filterEntry.getPattern());
        }

    }

    public FilterPanel(String title) throws HeadlessException {
        label1.setText(title);
        add(panel1);
        setVisible(true);

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String pattern = (String) JOptionPane.showInputDialog(panel1, "Pattern" + ":\n", "Pattern", JOptionPane.INFORMATION_MESSAGE, null, null, null);
                add(pattern);
            }
        });

        delButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = checkBoxList.getSelectedIndex();
                if(selectedIndex >=0) {
                    del(selectedIndex);
                }
            }
        });

        editButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = checkBoxList.getSelectedIndex();
                if(selectedIndex >=0) {
                    String curPattern =  del(selectedIndex);
                    String pattern = (String) JOptionPane.showInputDialog(panel1, "Pattern" + ":\n", "Pattern", JOptionPane.INFORMATION_MESSAGE, null, null, curPattern);
                    add(pattern);
                }
            }
        });

        clearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                checkBoxesMap.clear();
                checkBoxesList.clear();
                checkBoxList.clear();
                filterEntries.clear();
                settings.saveSettings();
            }
        });

        checkBox1.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                checkBoxList.markAll(checkBox1.isSelected());
            }
        });
    }

    private void add(String pattern) {
        if (pattern != null && !(pattern = pattern.trim()).isEmpty() && !checkBoxesMap.containsKey(pattern)) {
            addFilterAndSave(pattern);
        }
    }

    private String del(int selectedIndex) {
        checkBoxesMap.remove(checkBoxesList.remove(selectedIndex).getText());
        checkBoxList.removeCheckbox(selectedIndex);
        String removedPattern = filterEntries.remove(selectedIndex).getPattern();
        settings.saveSettings();
        return removedPattern;
    }

    private String getFilterSettingsKey() {
        return lp.getLogSourceName() + "|" + label1.getText();
    }

    private void addFilterAndSave(String pattern) {
        addFilter(pattern);
        //TODO: save check bos states so
        filterEntries.add(new FilterEntry(pattern, false));
        settings.saveSettings();
    }

    private void addFilter(String pattern) {
        JCheckBox checkBox = new JCheckBox(pattern, true);
        checkBoxList.addCheckbox(checkBox);
        checkBoxesList.add(checkBox);
        checkBoxesMap.put(pattern, checkBox);
    }

    public void applyFilter(final ru.lanit.dibr.utils.core.Filter filter) {
        this.filter = filter;
        for (JCheckBox jCheckBox : checkBoxesList) {
            jCheckBox.setSelected(false);
        }
        for (final String s : filter.getStringsToSearch()) {
            if(!checkBoxesMap.containsKey(s)) {
                addFilterAndSave(s);
            } else {
                checkBoxesMap.get(s).setSelected(true);
            }
        }
    }

    public void apply() {
        filter.disable();
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
