package com.wmcalyj.im.shared.communication;

import java.io.IOException;
import java.io.ObjectOutputStream;

import org.apache.commons.lang3.StringUtils;

import com.wmcalyj.exceptions.message.InvalidMessageException;
import com.wmcalyj.im.shared.data.message.InitMessage;
import com.wmcalyj.im.shared.data.message.Message;
import com.wmcalyj.im.shared.data.message.MessageType;
import com.wmcalyj.im.shared.data.message.NormalMessage;
import com.wmcalyj.im.shared.data.message.PublicKeyRequestMessage;
import com.wmcalyj.im.shared.data.message.PublicKeyResponseMessage;

public class SendMessageStrategy {
	public static void sendMessage(Message m, ObjectOutputStream oos)
			throws InvalidMessageException, IOException {
		if (!isValidMessage(m)) {
			throw new InvalidMessageException(getInvalidMessageInfo(m));
		}
		oos.writeObject(m);
		oos.flush();
	}

	protected static boolean isValidMessage(Message m) {
		if(!m.isValidMessage()){
			return false;
		}
		else{
			MessageType type = m.getMessageType();
			if(MessageType.INITMESSAGE.equals(type)){
				return ((InitMessage) m).isValidMessage();
			}else if(MessageType.NORMALMESSAGE.equals(type)){
				return ((NormalMessage) m).isValidMessage();
			}
			else if(MessageType.PUBLICKEYREQUEST.equals(type)){
				return ((PublicKeyRequestMessage) m).isValidMessage();
			}
			else if(MessageType.PUBLICKEYRESPONSE.equals(type)){
				return ((PublicKeyResponseMessage) m).isValidMessage();
			}
		}
		return false;
	}

	protected static String getInvalidMessageInfo(Message m) {
		StringBuilder errorInfo = new StringBuilder();
		errorInfo.append("Invalid message for message type: ").append(m.getMessageType().toString());
		return errorInfo.toString();
	}
}
