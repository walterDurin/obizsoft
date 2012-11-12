package ru.lanit.dibr.utils.core;

import com.jcraft.jsch.*;
import ru.lanit.dibr.utils.gui.configuration.Host;
import ru.lanit.dibr.utils.gui.configuration.LogFile;
import ru.lanit.dibr.utils.utils.MyUserInfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * User: Vova
 * Date: 13.11.12
 * Time: 2:12
 */
public class SshSource implements LogSource {
    private Host host;
    private LogFile logFile;
    BufferedReader reader = null;
    ChannelExec channel = null;
    Session session = null;

    public SshSource(Host host, LogFile logFile) {
        this.host = host;
        this.logFile = logFile;
    }

    public void startRead() throws Exception {
        JSch jsch = new JSch();
        session = jsch.getSession(host.getUser(), host.getHost(), host.getPort());
        if (host.getProxyHost() != null) {
            Proxy proxy = null;
            if (host.getProxyType().equals(Host.HTTP)) {
                proxy = new ProxyHTTP(host.getProxyHost(), host.getProxyPrort());
            } else if (host.getProxyType().equals(Host.SOCKS4)) {
                proxy = new ProxySOCKS4(host.getProxyHost(), host.getProxyPrort());
            } else if (host.getProxyType().equals(Host.SOCKS5)) {
                proxy = new ProxySOCKS4(host.getProxyHost(), host.getProxyPrort());
            } else {
                throw new Exception("Unknown proxy type! Please use one of following: '" + Host.HTTP + "'; '" + Host.SOCKS4 + "'; " + Host.SOCKS5 + "'; ");
            }
            //proxy.
//            Proxy proxy = new ProxySOCKS4(host.getHost(), host.getPort());
            session.setProxy(proxy);
        }
        session.setConfig("StrictHostKeyChecking", "no"); //принимать неизвестные ключи от серверов
        //сжатие потока
        session.setConfig("compression.s2c", "zlib@openssh.com,zlib,none");
        session.setConfig("compression.c2s", "zlib@openssh.com,zlib,none");
        session.setConfig("compression_level", "9");

        if (host.getPem() != null) {
            jsch.addIdentity(host.getPem());
        } else {
            UserInfo ui = new MyUserInfo(host.getPassword());
            session.setUserInfo(ui);
        }

        session.connect(30000);   // making a connection with timeout.
        channel = (ChannelExec) session.openChannel("exec");
        channel.setCommand("tail -500f " + logFile.getPath());
        reader = new BufferedReader(new InputStreamReader(channel.getInputStream(), host.getDefaultEncoding()));
        channel.connect(3 * 1000);


    }

    public String readLine() throws IOException {
        return reader.readLine();
    }

    public void reset() throws IOException {
        reader.reset();
    }

    public void reloadFull() throws Exception {
        if (channel != null) {
            channel.sendSignal("KILL");
            channel.setCommand("cat " + logFile.getPath());
        }
    }

    public void close() throws Exception {
        if (reader != null)
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        if (channel != null && channel.isConnected()) {
            channel.disconnect();
        }
        if (session != null && session.isConnected()) {
            session.disconnect();
        }

    }
}
