package ru.lanit.dibr.utils.gui;

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
    private String defaultEncoding;

	public Host(String host, int port, String user, String password) {
		this(null, host, port, user, password, null);
	}

	public Host(String description, String host, int port, String user, String password, String defaultEncoding) {
		this.description = description;
		this.host = host;
        this.port = port;
		this.user = user;
		this.password = password;
        this.defaultEncoding = defaultEncoding;
	}

    public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
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

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

    public String getDefaultEncoding() {
        return defaultEncoding;
    }

    public void setDefaultEncoding(String defaultEncoding) {
        this.defaultEncoding = defaultEncoding;
    }

    @Override
	public String toString() {
		return "host = " + host +
		"; user = " + user + "; password = " + password;
	}

	@Override
	public int hashCode() {
		return toString().hashCode();
	}
}
