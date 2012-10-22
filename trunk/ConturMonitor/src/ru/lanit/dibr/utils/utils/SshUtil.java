package ru.lanit.dibr.utils.utils;

import com.jcraft.jsch.*;

import java.io.InputStream;

import ru.lanit.dibr.utils.gui.configuration.Host;

/**
 * Created by IntelliJ IDEA.
 * User: Vova
 * Date: 13.09.2010
 * Time: 0:24:30
 * To change this template use File | Settings | File Templates.
 */
public class SshUtil {

    public static void exec(Host host, String command) {
        try {
            JSch jsch = new JSch();

            Session session = jsch.getSession(host.getUser(), host.getHost(), host.getPort());
            session.setConfig("StrictHostKeyChecking", "no");

            if(host.getPem()!=null) {
                jsch.addIdentity(host.getPem());
            } else {
                UserInfo ui=new MyUserInfo(host.getPassword());
                session.setUserInfo(ui);
            }

            session.connect(30000);

            Channel channel = session.openChannel("exec");
            ((ChannelExec) channel).setCommand(command);

            channel.setInputStream(null);

            ((ChannelExec) channel).setErrStream(System.err);

            InputStream in = channel.getInputStream();

            channel.connect();

            byte[] tmp = new byte[1024];
            while (true) {
                while (in.available() > 0) {
                    int i = in.read(tmp, 0, 1024);
                    if (i < 0) break;
                    System.out.print(new String(tmp, 0, i));
                }
                if (channel.isClosed()) {
                    System.out.println("exit-status: " + channel.getExitStatus());
                    break;
                }
                try {
                    Thread.sleep(1000);
                } catch (Exception ee) {
                }
            }
            channel.disconnect();
            session.disconnect();
        } catch (Exception e) {
            System.out.println(e);
        }

    }
}
