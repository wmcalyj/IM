package com.wmcalyj.im.shared.data.message;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;

import com.wmcalyj.im.shared.communication.Serialize;

public class NormalMessage extends Message {
	private static final long serialVersionUID = -591804385684943848L;
	private String sourceID;
	private String destinationID;
	private byte[] message;
	private String conversionID;

	public NormalMessage(String from, String to, byte[] message) {
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

	@Override
	public boolean isValidMessage() {
		if (super.isValidMessage()
				&& super.getMessageType().equals(MessageType.NORMALMESSAGE)) {
			return StringUtils.isNotBlank(this.sourceID)
					&& StringUtils.isNotBlank(this.destinationID)
					&& this.message.length > 0 ? true : false;
		}
		return false;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("Message: ");
		try {
			sb.append("SourceID - ").append(this.sourceID)
					.append(". DestinationID - ").append(this.destinationID)
					.append(". ConversationID - ").append(this.conversionID)
					.append(". Message - ")
					.append((String) Serialize.deserialize(this.message));
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sb.toString();
	}
}
