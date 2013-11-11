package ru.lanit.dibr.utils.gui.configuration;

import com.jcraft.jsch.*;
import ru.lanit.dibr.utils.utils.MyUserInfo;

public class Host {
	private String description;
	private String host;
	private int port;
	private String user;
	private String password;
	private String pem;
    private String defaultEncoding;
    private String proxyHost;
    private int proxyPrort;
    private String proxyType;
    private Tunnel tunnel;
    public static final String SOCKS4="SOCKS4";
    public static final String SOCKS5="SOCKS5";
    public static final String HTTP="HTTP";
    public static final int DEFAULT_HTTP_PORT = 3128;

    public Host(String host, int port, String user, String password) {
		this(null, host, port, user, password, null, null, null);
	}

	public Host(String description, String host, int port, String user, String password, String pem, String defaultEncoding, Tunnel tunnel) {
		this.description = description;
		this.host = host;
        this.port = port;
		this.user = user;
		this.password = password;
		this.pem = pem;
        this.defaultEncoding = defaultEncoding;
        this.tunnel = tunnel;
	}

    public Host(String description, String host, int port, String user, String password, String pem, String defaultEncoding, String proxyAddress, int proxyPrort, String proxyType, Tunnel tunnel) {
        this.description = description;
        this.host = host;
        this.port = port;
        this.user = user;
        this.password = password;
        this.pem = pem;
        this.defaultEncoding = defaultEncoding;
        this.tunnel = tunnel;
        this.proxyHost = proxyAddress;
        this.proxyPrort = proxyPrort;
        this.proxyType = proxyType;
    }

    public Session createSession() throws Exception {

        if(tunnel!=null) {
            tunnel.connect();
        }

        JSch jsch = new JSch();
        Session session = jsch.getSession(user, host, port);
        if (proxyHost != null) {
            Proxy proxy = null;
            if (proxyType.equals(HTTP)) {
                proxy = new ProxyHTTP(proxyHost, proxyPrort);
            } else if (proxyType.equals(SOCKS4)) {
                proxy = new ProxySOCKS4(proxyHost, proxyPrort);
            } else if (proxyType.equals(SOCKS5)) {
                proxy = new ProxySOCKS4(proxyHost, proxyPrort);
            } else {
                throw new Exception("Unknown proxy type! Please use one of following: '" + HTTP + "'; '" + SOCKS4 + "'; " + SOCKS5 + "'; ");
            }
            session.setProxy(proxy);
        }
        session.setConfig("StrictHostKeyChecking", "no"); //принимать неизвестные ключи от серверов
        //сжатие потока
        session.setConfig("compression.s2c", "zlib@openssh.com,zlib,none");
        session.setConfig("compression.c2s", "zlib@openssh.com,zlib,none");
        session.setConfig("compression_level", "9");

        if (pem != null) {
            jsch.addIdentity(pem);
        } else {
            UserInfo ui = new MyUserInfo(password);
            session.setUserInfo(ui);
        }
        return session;
    }

    public Session connect() throws Exception {
        Session session = createSession();
        session.connect(30000);   // making a connection with timeout.
        return session;
    }

    public String getDescription() {
		return description;
	}

	public String getHost() {
		return host;
	}

    public String getDefaultEncoding() {
        return defaultEncoding;
    }

    @Override
	public String toString() {
		return "host = " + host +
		        "; user = " + user + "; password = " + (password!=null?password.replaceAll(".", "*"):"") + "; pem = " + pem +
                "; proxyHost=" + proxyHost + "; proxyPort=" + proxyPrort + "; proxyType=" + proxyType + "; tunnel = [" + tunnel + "]";
	}

	@Override
	public int hashCode() {
		return toString().hashCode();
	}
}
