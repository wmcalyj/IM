package im.front.messageservice;

import im.webservice.messagepackage.MessagePackage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ReceiveMessage extends Thread implements Runnable {
	public MessagePackage receive(Socket socket) throws Exception {

		ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
		MessagePackage messageObject = (MessagePackage) in.readObject();
		String messageText = messageObject.getText();
		System.out.println("I have received: " + messageText);
		return messageObject;

	}
}
