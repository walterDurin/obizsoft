package ru.lanit.dibr.utils;

import com.jcraft.jsch.*;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import ru.lanit.dibr.utils.gui.LogChoicer;

import javax.swing.text.BadLocationException;
import java.io.*;

/**
 * User: VTaran
 * Date: 16.08.2010
 * Time: 15:30:23
 */
public class Main {
	public static void main(String[] args) {

        CmdLineParser parser = new CmdLineParser(new CmdLineConfiguration());
        try {
            parser.parseArgument(args);
            Configuration cfg = new Configuration(CmdLineConfiguration.settingsFileName);

            LogChoicer logs = new LogChoicer(cfg);
            logs.setVisible(true);

        } catch (CmdLineException e) {
            e.printStackTrace();
        }
	}
}
