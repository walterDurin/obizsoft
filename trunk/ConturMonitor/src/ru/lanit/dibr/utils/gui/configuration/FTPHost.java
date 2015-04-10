package ru.lanit.dibr.utils.gui.configuration;

import ru.lanit.dibr.utils.core.AbstractHost;

/**
 * Created by Vova on 10.04.2015.
 */
public class FTPHost extends AbstractHost {
    public FTPHost(String description, String host, int port, String user, String password, String defaultEncoding, Tunnel tunnel) {
        super(description, host, port, user, password, defaultEncoding, tunnel);
    }

    public FTPHost(String description, Tunnel tunnel, String host, int port, String user, String password, String defaultEncoding, String proxyHost, int proxyPrort, String proxyType, String proxyLogin, String proxyPasswd) {
        super(description, tunnel, host, port, user, password, defaultEncoding, proxyHost, proxyPrort, proxyType, proxyLogin, proxyPasswd);
    }


}
