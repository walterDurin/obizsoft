package ru.lanit.dibr.utils;

import com.jcraft.jsch.*;
import ru.lanit.dibr.utils.gui.Host;
import ru.lanit.dibr.utils.gui.LogChoicer;

import javax.swing.text.BadLocationException;
import java.io.*;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
import java.beans.XMLEncoder;

/**
 * Created by IntelliJ IDEA.
 * User: VTaran
 * Date: 16.08.2010
 * Time: 15:30:23
 */
public class Main {
	public static void main(String[] args) throws JSchException, IOException, BadLocationException, InterruptedException {


		Configuration cfg = new Configuration();

		LogChoicer logs = new LogChoicer(cfg);
		logs.setVisible(true);


//		LogFrame lf = new LogFrame(hostData, "/bea/domains/integroDomain/servers/AdminServer/logs/AdminServer.log");
//		LogFrame lf = new LogFrame(hostData, "/bea/domains/integroDomain/logs/wli_applications.wls1.log");

	}
}
