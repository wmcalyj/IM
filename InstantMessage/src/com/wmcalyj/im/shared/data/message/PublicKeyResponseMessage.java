package com.wmcalyj.im.shared.data.message;

import java.security.PublicKey;

import org.apache.commons.lang3.StringUtils;

public class PublicKeyResponseMessage extends Message {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6004407132823458867L;

	private PublicKey publicKey;
	private String keyOwner;

	public PublicKeyResponseMessage(String keyOwner, PublicKey publicKey) {
		this.keyOwner = keyOwner;
		this.publicKey = publicKey;
		super.setMessageType(MessageType.PUBLICKEYRESPONSE);
	}

	public PublicKey getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(PublicKey publicKey) {
		this.publicKey = publicKey;
	}

	public String getKeyOwner() {
		return keyOwner;
	}

	public void setKeyOwner(String keyOwner) {
		this.keyOwner = keyOwner;
	}

	@Override
	public boolean isValidMessage() {
		return super.isValidMessage() && StringUtils.isNotBlank(this.keyOwner) ? true
				: false;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Key owner: ").append(this.keyOwner).append(". Public key: ")
				.append(this.publicKey.toString());
		return sb.toString();
	}
}
