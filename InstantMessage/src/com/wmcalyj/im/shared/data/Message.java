package com.wmcalyj.im.shared.data;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

public class Message implements Serializable {

	private static final long serialVersionUID = -591804385684943848L;
	private String sourceID;
	private String destinationID;
	private byte[] message;
	private String conversionID;
	private boolean initMessage;

	public Message(String from, String to, byte[] message) {
		this.sourceID = from;
		this.destinationID = to;
		this.message = message;
		this.setInitMessage(false);
	}

	public Message(String from, byte[] message) {
		this.sourceID = from;
		this.message = message;
	}

	public Message(String from) {
		this.sourceID = from;
	}

	public Message() {
	}

	public String getSourceID() {
		return sourceID;
	}

	public void setSourceID(String sourceID) {
		this.sourceID = sourceID;
	}

	public String getDestinationID() {
		return destinationID;
	}

	public void setDestinationID(String destinationID) {
		this.destinationID = destinationID;
	}

	public byte[] getMessage() {
		return message;
	}

	public void setMessage(byte[] message) {
		this.message = message;
	}

	public String getConversionID() {
		return conversionID;
	}

	public void setConversionID(String conversionID) {
		this.conversionID = conversionID;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public boolean isValidMessage() {
		if (!this.initMessage) {
			if (StringUtils.isNotBlank(this.sourceID)
					&& StringUtils.isNotBlank(this.destinationID)
					&& this.message.length > 0) {
				return true;
			}
			return false;
		} else {
			if (StringUtils.isNotBlank(this.sourceID)) {
				return true;
			}
			return false;
		}

	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("Message: ");
		sb.append("SourceID - ").append(this.sourceID)
				.append(". DestinationID - ").append(this.destinationID)
				.append(". ConversationID - ").append(this.conversionID)
				.append(". Message - ").append(this.message)
				.append(". Is initial message: ").append(this.isInitMessage());
		return sb.toString();
	}

	public boolean isInitMessage() {
		return initMessage;
	}

	public void setInitMessage(boolean isInitMessage) {
		this.initMessage = isInitMessage;
	}
}
