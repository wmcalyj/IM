package im.user;

import im.contacts.Contacts;

import java.util.List;

/**
 * @author mengchaow
 * 
 */
public class User {

	private String userName;
	private String accountNumber;
	private Contacts contacts;

	public User(String userName, String accountNumber) {
		this.userName = userName;
		this.accountNumber = accountNumber;
	}

	public User() {

	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Contacts getContacts() {
		return contacts;
	}

	public void setContacts(Contacts contacts) {
		this.contacts = contacts;
	}

}
