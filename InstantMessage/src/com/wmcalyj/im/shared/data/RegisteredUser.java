package com.wmcalyj.im.shared.data;

import java.io.ObjectOutputStream;
import java.net.Socket;
import java.security.PublicKey;
import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;

public class RegisteredUser {
	private String uid;
	private PublicKey publicKey;
	private Socket socket;
	private ObjectOutputStream out;

	public RegisteredUser(String uid, PublicKey publicKey, Socket socket,
			ObjectOutputStream out) {
		this.uid = uid;
		this.publicKey = publicKey;
		this.socket = socket;
		this.out = out;
	}

	public String getUid() {
		return this.uid;
	}

	public PublicKey getPublicKey() {
		return this.publicKey;
	}

	public boolean equals(RegisteredUser user) {
		if (user == null) {
			return false;
		}
		if (StringUtils.equals(this.uid, user.getUid())
				&& Arrays.equals(publicKey.getEncoded(), user.getPublicKey()
						.getEncoded())) {

			return true;
		}
		return false;
	}

	public boolean isValidRegisteredUser() {
		return StringUtils.isNotBlank(this.uid) && this.publicKey != null
				&& this.socket != null ? true : false;
	}

	public Socket getSocket() {
		return socket;
	}

	public ObjectOutputStream getOut() {
		return out;
	}

}
