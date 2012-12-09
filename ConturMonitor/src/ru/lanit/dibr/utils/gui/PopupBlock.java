package ru.lanit.dibr.utils.gui;

import ru.lanit.dibr.utils.utils.XmlUtils;

import javax.swing.*;
import java.awt.*;

/**
 * User: Vova
 * Date: 26.11.12
 * Time: 2:31
 */
public class PopupBlock extends JFrame {

    public PopupBlock(String title, String data) throws HeadlessException {
        setTitle(title);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JTextArea textArea = new JTextArea();
        textArea.setFont(new Font("Courier New", 0, 12));
//        }
        textArea.setBackground(new Color(0, 0, 0));
        textArea.setForeground(new Color(187, 187, 187));
        textArea.setSelectedTextColor(new Color(0, 0, 0));
        textArea.setSelectionColor(new Color(187, 187, 187));

//        textArea.setWrapStyleWord(true);
//        textArea.setLineWrap(true);

        textArea.setEditable(false);
        JScrollPane jScrollPane = new JScrollPane(textArea);
        add(jScrollPane);

        textArea.append(XmlUtils.formatXml(data));

        setSize(1100, 800);

        setVisible(true);
    }
}
