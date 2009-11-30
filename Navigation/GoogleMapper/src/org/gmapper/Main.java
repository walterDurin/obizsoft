package org.gmapper;

import org.gmapper.gui.MainForm;
import org.gmapper.gui.DebugWindow;
import org.apache.commons.io.FileUtils;

import java.util.Properties;
import java.io.IOException;
import java.io.File;

/**
 * Date: 19.08.2008
 *
 * @author Taran Vladimir <vtaran@mbtc.ru>
 */

public class Main {
    public static Properties settings = new Properties();
    static {
        try {
            settings.load(FileUtils.openInputStream(new File("settings.properties")));
        } catch (IOException e) {
            settings=null;
        }
    }
    public static void main(String[] args) {
        MainForm mainForm = new MainForm();
        mainForm.setVisible(true);
        mainForm.redrawMap();
        if(settings!=null && settings.getProperty("general.showDebugWindow")!=null) {
            DebugWindow debug = new DebugWindow();
            debug.setLocationRelativeTo(null);
            debug.setLocation(mainForm.getX()+mainForm.getWidth(), mainForm.getY());
            debug.setVisible(true);
            mainForm.setDebugWindow(debug);
        }
    }
}
