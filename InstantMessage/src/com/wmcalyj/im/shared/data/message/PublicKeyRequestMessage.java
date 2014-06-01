package com.wmcalyj.im.shared.data.message;

import org.apache.commons.lang3.StringUtils;

public class PublicKeyRequestMessage extends Message {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1183415456388685668L;
	private String keyOwner;

	public PublicKeyRequestMessage(String keyOwner) {
		this.keyOwner = keyOwner;
		super.setMessageType(MessageType.PUBLICKEYREQUEST);
	}

	public String getKeyOwner() {
		return keyOwner;
	}

	public void setKeyOwner(String keyOwner) {
		this.keyOwner = keyOwner;
	}

	@Override
	public boolean isValidMessage() {
		if (super.isValidMessage()
				&& super.getMessageType().equals(MessageType.PUBLICKEYREQUEST)) {
			return StringUtils.isNotBlank(this.keyOwner) ? true : false;
		}
		return false;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("The user of the key to request: ").append(this.keyOwner);
		return sb.toString();
	}
}
