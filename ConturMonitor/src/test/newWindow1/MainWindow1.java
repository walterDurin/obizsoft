package test.newWindow1;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * User: Vova
 * Date: 30.10.12
 * Time: 23:49
 */
public class MainWindow1 {
    private JPanel rootPanel;
    private JTabbedPane tabbedPane1;
    private JTabbedPane tabbedPane2;
    private JCheckBox splitedCheckBox;
    private JSplitPane splitPane;

    public MainWindow1() {
        splitedCheckBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(splitedCheckBox.isSelected()) {
                    splitPane.setDividerLocation(rootPanel.getWidth()/2);
                    tabbedPane1.addTab(tabbedPane2.getTitleAt(0),null);
                    tabbedPane2.removeTabAt(0);
                    tabbedPane1.addTab(tabbedPane2.getTitleAt(0),null);
                    tabbedPane2.removeTabAt(0);
//                    ((JTabbedPaneCustom)tabbedPane2).moveAll();
                } else {
                    splitPane.setDividerLocation(0);
                    ((JTabbedPaneCustom)tabbedPane1).moveAll();
//                    for (int i = 0; i <= tabbedPane1.getTabCount(); i++) {
//                        tabbedPane2.addTab(tabbedPane1.getTitleAt(0), tabbedPane1.getTabComponentAt(0));
//                        tabbedPane1.remove(0);
//                    }

                }
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("MainWindow1");
        MainWindow1 mainWindow1 = new MainWindow1();
        mainWindow1.tabbedPane1.setTitleAt(0, "first");
        mainWindow1.tabbedPane1.setTitleAt(1, "second");
        mainWindow1.tabbedPane2.setTitleAt(0, "third");
        mainWindow1.tabbedPane2.setTitleAt(1, "fourth");
        System.out.println(mainWindow1.tabbedPane1.getTabComponentAt(0));
        frame.setContentPane(mainWindow1.rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void createUIComponents() {
        JTabbedPaneCustom jTabbedPaneCustom1 = new JTabbedPaneCustom("\u25ba");
        JTabbedPaneCustom jTabbedPaneCustom2 = new JTabbedPaneCustom("\u25c4");

        jTabbedPaneCustom1.setAnother(jTabbedPaneCustom2);
        jTabbedPaneCustom2.setAnother(jTabbedPaneCustom1);

        tabbedPane1 = jTabbedPaneCustom1;
        tabbedPane2 = jTabbedPaneCustom2;

    }
}
