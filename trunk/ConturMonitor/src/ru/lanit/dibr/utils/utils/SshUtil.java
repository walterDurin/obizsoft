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

    public static class ExecResult {
        String data;
        int statusCode;

        public String getData() {
            return data;
        }

        public int getStatusCode() {
            return statusCode;
        }
    }

    private static Session session = null;

    public static ExecResult exec(Host host, String command) {
        ExecResult result = new ExecResult();
        System.out.println("SSH exec command: "+command);
        try {
            init(host);

            Channel channel = session.openChannel("exec");
            ((ChannelExec) channel).setCommand(command);

            channel.setInputStream(null);

            ((ChannelExec) channel).setErrStream(System.err);

            InputStream in = channel.getInputStream();

            channel.connect();


            StringBuffer out = new StringBuffer();
            String nextPortion;
            byte[] tmp = new byte[1024];
            while (true) {
                while (in.available() > 0) {
                    int i = in.read(tmp, 0, 1024);
                    if (i < 0) break;
                    nextPortion = new String(tmp, 0, i);
                    out.append(nextPortion);
                    System.out.print(nextPortion);
                }
                if (channel.isClosed()) {
                    result.statusCode = channel.getExitStatus();
                    System.out.println("exit-status: " + result.statusCode );
                    break;
                }
                try {
                    Thread.sleep(100);
                } catch (Exception ee) {
                    ee.printStackTrace();
                }
            }
            result.data = out.toString();
            channel.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }


        return result;
    }

    private static void init(Host host) throws Exception {
        if(session==null) {
            JSch jsch = new JSch();
            session = host.connect();
        }
    }
}
