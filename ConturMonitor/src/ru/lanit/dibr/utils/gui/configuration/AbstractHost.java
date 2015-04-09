package ru.lanit.dibr.utils.core;

import ru.lanit.dibr.utils.gui.configuration.Tunnel;

/**
 * Created by Vova on 19.02.2015.
 */
public class AbstractHost {
    protected String description;
    protected Tunnel tunnel;
    protected String host;
    protected int port;
    protected String user;
    protected String password;
    protected String defaultEncoding;
    protected String proxyHost;
    protected int proxyPrort;
    protected String proxyType;
    protected String proxyLogin;
    protected String proxyPasswd;

    public static final String SOCKS5="SOCKS5";
    public static final String SOCKS4="SOCKS4";
    public static final String HTTP="HTTP";


    public AbstractHost(String description, String host, int port, String user, String password, String defaultEncoding, Tunnel tunnel) {
        this.description = description;
        this.host = host;
        this.port = port;
        this.user = user;
        this.password = password;
        this.defaultEncoding = defaultEncoding;
        this.tunnel = tunnel;
    }

    public AbstractHost(String description, Tunnel tunnel, String host, int port, String user, String password, String defaultEncoding, String proxyHost, int proxyPrort, String proxyType, String proxyLogin, String proxyPasswd) {
        this.description = description;
        this.tunnel = tunnel;
        this.host = host;
        this.port = port;
        this.user = user;
        this.password = password;
        this.defaultEncoding = defaultEncoding;
        this.proxyHost = proxyHost;
        this.proxyPrort = proxyPrort;
        this.proxyType = proxyType;
        this.proxyLogin = proxyLogin;
        this.proxyPasswd = proxyPasswd;
    }

    public String getDescription() {
        return description;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDefaultEncoding() {
        return defaultEncoding;
    }

    public Tunnel getTunnel() {
        return tunnel;
    }

}
