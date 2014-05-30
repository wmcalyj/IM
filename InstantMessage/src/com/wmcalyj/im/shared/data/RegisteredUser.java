package com.wmcalyj.im.shared.data;

import java.security.PublicKey;
import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;

public class RegisteredUser {
	private String uid;
	private PublicKey publicKey;

	public RegisteredUser(String uid, PublicKey publicKey) {
		this.uid = uid;
		this.publicKey = publicKey;
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
		return StringUtils.isNotBlank(this.uid) && this.publicKey != null ? true
				: false;
	}
}
