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

    @Override
	public String toString() {
		return "host = " + host +
		"; user = " + user + "; password = " + password.replaceAll("\\w", "*") + "; pem = " + pem;
	}

	@Override
	public int hashCode() {
		return toString().hashCode();
	}
}
