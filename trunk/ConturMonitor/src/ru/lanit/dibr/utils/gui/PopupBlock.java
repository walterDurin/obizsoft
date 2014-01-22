package ru.lanit.dibr.utils.gui;

import hlam.TestStringSource;
import ru.lanit.dibr.utils.utils.XmlUtils;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 * User: Vova
 * Date: 26.11.12
 * Time: 2:31
 */
public class PopupBlock extends JFrame {

    public PopupBlock(final String title, String data) throws Exception {

        setTitle(title);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        final LogPanel logPanel = new LogPanel(new TestStringSource(XmlUtils.formatXml(data), 0, false), null);
        setContentPane(logPanel);
        Thread t = new Thread() {
            @Override
            public void run() {
                boolean retry = true;
                while(retry) {
                    try {
                        logPanel.connect();
                    } catch (Exception e) {
                        e.printStackTrace(System.out);
                        System.out.println(e);
                        //					JOptionPane.showMessageDialog(LogFrame.this, "Can't open log '" + title + "'!\n" + e.getMessage());
                        Object[] options = {"Yes, please",
                                "No, thanks"};
                        retry = JOptionPane.YES_OPTION == JOptionPane.showOptionDialog(PopupBlock.this,
                                "Can't open log '" + title + "'!\n" + e.getMessage() + "\nLet's try to reconnect?",
                                "Error",
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.ERROR_MESSAGE,
                                null,
                                options,
                                options[1]);
                    }
                }

                PopupBlock.this.setVisible(false);
            }

            @Override
            public void interrupt() {
                logPanel.stop();
            }
        };
        t.start();
        setSize(1100, 800);
        setVisible(true);
    }
}
