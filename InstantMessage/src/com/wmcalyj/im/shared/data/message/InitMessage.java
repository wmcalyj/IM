package com.wmcalyj.im.shared.data.message;

import java.security.PublicKey;

import org.apache.commons.lang3.StringUtils;

public class InitMessage extends Message {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7202600817128178514L;
	private final PublicKey publicKey;
	private final String sourceID;

	public InitMessage(String sourceID, PublicKey publicKey) {
		this.publicKey = publicKey;
		this.sourceID = sourceID;
		super.setMessageType(MessageType.INITMESSAGE);
	}

	public PublicKey getPublicKey() {
		return publicKey;
	}

	public String getSourceID() {
		return sourceID;
	}

	@Override
	public boolean isValidMessage() {
		if (super.isValidMessage()
				&& super.getMessageType().equals(MessageType.INITMESSAGE)) {
			return StringUtils.isNotBlank(sourceID) && publicKey != null ? true
					: false;
		}
		return false;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Source ID: ").append(this.sourceID).append(". Public key: ")
				.append(this.publicKey.toString());
		return sb.toString();
	}

}
