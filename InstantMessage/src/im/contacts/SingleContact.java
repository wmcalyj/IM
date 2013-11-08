package im.contacts;

/**
 * 
 * @author mengchaow
 * 
 *         This is a single contact which will store the information about one
 *         contact of the current user
 */
public class SingleContact {
	private String name;
	private boolean online;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isOnline() {
		return online;
	}
	public void setOnline(boolean online) {
		this.online = online;
	}

}
