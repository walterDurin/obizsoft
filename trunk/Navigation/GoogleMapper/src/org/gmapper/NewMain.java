package org.gmapper;

import org.gmapper.newGui.SimpleForm;
import org.gmapper.newGui.SimpleForm2;

import javax.swing.*;

/**
 * User: Vova
 * Time: 2:03:36
 *
 * @author ognivo777@mail.ru
 */
public class NewMain {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                SimpleForm form = new SimpleForm();
                form.setVisible(true);
                form.drawMap();
            }
        });
    }
}
