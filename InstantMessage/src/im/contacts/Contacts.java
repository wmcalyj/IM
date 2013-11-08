package im.contacts;

import java.util.List;

/**
 * 
 * @author mengchaow
 * 
 *         Contacts will be used to store all the cotacts of the current user.
 *         The information will not be stored locally, instead, all the contacts
 *         will be stored in the server and each time the user logs in, client
 *         server will pull the contacts from the server and show them to the
 *         clients (To Be Discussed!!!)
 */
public class Contacts {
	private List<SingleContact> contacts;

	public List<SingleContact> getContacts() {
		return contacts;
	}

	public void setContacts(List<SingleContact> contacts) {
		this.contacts = contacts;
	}
}
