package ru.lanit.dibr.utils.gui.configuration;

/**
 * Created by IntelliJ IDEA.
 * User: VTaran
 * Date: 16.08.2010
 * Time: 17:21:10
 */
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
    public static final String SOCKS4="SOCKS4";
    public static final String SOCKS5="SOCKS5";
    public static final String HTTP="HTTP";
    public static final int DEFAULT_HTTP_PORT = 3128;

    public Host(String host, int port, String user, String password) {
		this(null, host, port, user, password, null, null);
	}

	public Host(String description, String host, int port, String user, String password, String pem, String defaultEncoding) {
		this.description = description;
		this.host = host;
        this.port = port;
		this.user = user;
		this.password = password;
		this.pem = pem;
        this.defaultEncoding = defaultEncoding;
	}

    public Host(String description, String host, int port, String user, String password, String pem, String defaultEncoding, String proxyAddress, int proxyPrort, String proxyType) {
        this.description = description;
        this.host = host;
        this.port = port;
        this.user = user;
        this.password = password;
        this.pem = pem;
        this.defaultEncoding = defaultEncoding;
        this.proxyHost = proxyAddress;
        this.proxyPrort = proxyPrort;
        this.proxyType = proxyType;
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

    public String getUser() {
		return user;
	}

	public String getPassword() {
		return password;
	}

    public String getPem() {
        return pem;
    }

    public String getDefaultEncoding() {
        return defaultEncoding;
    }

    public String getProxyHost() {
        return proxyHost;
    }

    public int getProxyPrort() {
        return proxyPrort;
    }

    public String getProxyType() {
        return proxyType;
    }

    @Override
	public String toString() {
		return "host = " + host +
		        "; user = " + user + "; password = " + (password!=null?password.replaceAll("\\w", "*"):"") + "; pem = " + pem + 
                "; proxyHost=" + proxyHost + "; proxyPort=" + proxyPrort + "; proxyType=" + proxyType;
	}

	@Override
	public int hashCode() {
		return toString().hashCode();
	}
}
