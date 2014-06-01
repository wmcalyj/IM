package im.contacts;

import java.security.PublicKey;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @author mengchaow
 * 
 *         This is a single contact which will store the information about one
 *         contact of the current user
 */
public class SingleContact {
	private String name;
	private String accountNumber;
	private boolean online;
	private PublicKey publicKey;

	public SingleContact(String name, String accountNumber, boolean online,
			PublicKey publicKey) {
		this.name = name;
		this.accountNumber = accountNumber;
		this.online = online;
		this.publicKey = publicKey;
	}

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

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public PublicKey getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(PublicKey publicKey) {
		this.publicKey = publicKey;
	}

	public boolean isValidContact() {
		return StringUtils.isNotBlank(name)
				&& StringUtils.isNotBlank(accountNumber) && publicKey != null ? true
				: false;
	}

}
