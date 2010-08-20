package test;

import com.jcraft.jsch.*;
import ru.lanit.dibr.utils.utils.MyUserInfo;
import ru.lanit.dibr.utils.gui.Host;
import org.junit.Test;

import java.io.*;

/**
 * Created by IntelliJ IDEA.
 * User: VTaran
 * Date: 17.08.2010
 * Time: 10:28:19
 */
public class TestReadLog {
	private static String user = "bea";
	private static String host = "integro1-test-c1";
	private static String passwd = "bea123";

	public static void main(String[] args) throws JSchException, IOException, InterruptedException {
		testconnect();
	}

	public static void testconnect() throws JSchException, IOException, InterruptedException {
		Host hostData = new Host(host, user, passwd);

		JSch jsch=new JSch();
		Session session=jsch.getSession(hostData.getUser(), hostData.getHost(), 22);
		UserInfo ui=new MyUserInfo(hostData.getPassword());
		session.setUserInfo(ui);
		session.connect(3000);   // making a connection with timeout.
		Channel channel= session.openChannel("shell");
//		channel.setCommand("tail -100f " + "/bea/domains/integroDomain/servers/AdminServer/logs/AdminServer.log");
		channel.setOutputStream(System.out);
		PipedOutputStream out = new PipedOutputStream();
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
		PipedInputStream in = new PipedInputStream(out);
		channel.setInputStream(System.in);
		channel.connect(3000);
		Thread.sleep(1000);
//		writer.write("ls\n");
		writer.write("tail -f /bea/domains/integroDomain/servers/AdminServer/logs/AdminServer.log\n");
//		writer.newLine();
		writer.flush();
		Thread.sleep(10000);


	}
}
