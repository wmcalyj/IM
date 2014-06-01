package com.wmcalyj.im.shared.data.message;

import java.io.Serializable;

public class Message implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4393366595133780890L;
	private MessageType messageType;

	public MessageType getMessageType() {
		return messageType;
	}

	public void setMessageType(MessageType messageType) {
		this.messageType = messageType;
	}

	public boolean isValidMessage() {
		return this.messageType != null ? true : false;
	}

	@Override
	public String toString() {
		return this.messageType.toString();
	}
}
