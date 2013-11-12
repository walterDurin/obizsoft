package ru.lanit.dibr.utils;

import com.jcraft.jsch.*;
import ru.lanit.dibr.utils.gui.LogChoicer;

import javax.swing.text.BadLocationException;
import java.io.*;

/**
 * User: VTaran
 * Date: 16.08.2010
 * Time: 15:30:23
 */
public class Main {
	public static void main(String[] args) throws JSchException, IOException, BadLocationException, InterruptedException {


		Configuration cfg = new Configuration(args.length>0?args[0]:"settings.xml");

		LogChoicer logs = new LogChoicer(cfg);
		logs.setVisible(true);

	}
}
