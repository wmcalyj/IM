package im.front.messageservice;

import im.webservice.messagepackage.MessagePackage;

import java.io.DataInputStream;
import java.net.Socket;

public class ReceiveMessage extends Thread implements Runnable {
	public MessagePackage receive(Socket socket) throws Exception {

		DataInputStream in = new DataInputStream(socket.getInputStream());
		System.out.println("I have received: " + in.readUTF());
		return null;
		// // MessagePackage messageObject = (MessagePackage) in.read();
		// String messageText = messageObject.getText();
		// System.out.println("I have received: " + messageText);
		// return messageObject;

	}
}
