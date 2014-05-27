package com.wmcalyj.im.shared.data;

import java.io.Serializable;

import com.wmcalyj.im.encryption.impl.LocalEncryptionHelper;

public class LoginPackage implements Serializable {
	
	private static final long serialVersionUID = 2110225219938190374L;
	private String username;
	private byte[] password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public byte[] getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = LocalEncryptionHelper.loginEncryption(password);
	}

}
