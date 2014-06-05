package com.wmcalyj.im.shared.data.message;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;

public class NormalMessage extends Message {
	private static final long serialVersionUID = -591804385684943848L;
	private String sourceID;
	private String destinationID;
	private ArrayList<byte[]> message;
	private String conversionID;

	public NormalMessage(String from, String to, ArrayList<byte[]> message) {
		this.sourceID = from;
		this.destinationID = to;
		this.message = message;
		super.setMessageType(MessageType.NORMALMESSAGE);
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

	public ArrayList<byte[]> getMessage() {
		return message;
	}

	public void setMessage(ArrayList<byte[]> message) {
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

	@Override
	public boolean isValidMessage() {
		if (super.isValidMessage()
				&& super.getMessageType().equals(MessageType.NORMALMESSAGE)) {
			return StringUtils.isNotBlank(this.sourceID)
					&& StringUtils.isNotBlank(this.destinationID)
					&& message.size() > 0 ? true : false;
		}
		return false;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("Message: ");
		sb.append("SourceID - ").append(this.sourceID)
				.append(". DestinationID - ").append(this.destinationID)
				.append(". ConversationID - ").append(this.conversionID)
				.append(". Message - ").append(this.message);

		return sb.toString();
	}
}
