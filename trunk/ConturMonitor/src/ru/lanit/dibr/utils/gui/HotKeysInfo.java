package ru.lanit.dibr.utils.gui;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

/**
 * User: Vova
 * Date: 13.12.12
 * Time: 0:45
 */
public class HotKeysInfo extends JFrame implements KeyListener {

    public HotKeysInfo() {
        setTitle("HotKeys");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        JTextPane jTextPane = new JTextPane();
        jTextPane.setEditable(false);
        jTextPane.addKeyListener(this);
        try {
            jTextPane.setPage(getClass().getResource("/res/Shortcuts.html"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        panel.add(jTextPane);
        add(panel);
        setSize(685, 770);
        this.setLocationRelativeTo(getRootPane());
        setResizable(false);
        setVisible(true);

    }


    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==27) {
            setVisible(false);
            dispose();
        }
    }

    public void keyReleased(KeyEvent e) {

    }
}
