package im.front.messageservice;

import im.webservice.messagepackage.MessagePackage;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class DefaultMessageService {
	Socket socket;
	ReceiveMessage receiveMessage;
	SendMessage sendMessage;

	public DefaultMessageService() {
		try {
			socket = new Socket(CenterServerInfo.HOST, CenterServerInfo.PORT);
			receiveMessage = new ReceiveMessage();
			sendMessage = new SendMessage();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean send(MessagePackage message) {
		try {
			sendMessage.send(message, socket);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean receive(MessagePackage message) {
		try {
			message.clone(receiveMessage.receive(socket));
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
