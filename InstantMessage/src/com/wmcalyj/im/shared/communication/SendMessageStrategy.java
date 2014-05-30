package com.wmcalyj.im.shared.communication;

import java.io.IOException;
import java.io.ObjectOutputStream;

import org.apache.commons.lang3.StringUtils;

import com.wmcalyj.exceptions.message.InvalidMessageException;
import com.wmcalyj.im.shared.data.Message;

public class SendMessageStrategy {
	public static void sendMessage(Message m, ObjectOutputStream oos)
			throws InvalidMessageException, IOException {
		if (!m.isValidMessage()) {
			throw new InvalidMessageException(getInvalidMessageInfo(m));
		}
		oos.writeObject(m);
		oos.flush();
	}

	protected static String getInvalidMessageInfo(Message m) {
		StringBuilder errorInfo = new StringBuilder();
		if (StringUtils.isBlank(m.getSourceID())) {
			errorInfo.append("Source ID is empty. ");
		}
		if (StringUtils.isBlank(m.getDestinationID())) {
			errorInfo.append("Destination ID is empty. ");
		}
		if (m.getMessage().length <= 0) {
			errorInfo.append("Message is empty. ");
		}
		return errorInfo.toString();
	}
}
