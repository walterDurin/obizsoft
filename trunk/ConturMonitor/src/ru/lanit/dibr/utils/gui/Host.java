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
	private String user;
	private String password;

	public Host(String host, String user, String password) {
		this(null, host, user, password);
	}

	public Host(String description, String host, String user, String password) {
		this.description = description;
		this.host = host;
		this.user = user;
		this.password = password;
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
