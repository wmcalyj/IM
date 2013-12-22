package im.front.messageservice;

import im.webservice.messagepackage.MessagePackage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ReceiveMessage extends Thread implements Runnable {
	public MessagePackage receive(Socket socket) {

		try {
			ObjectInputStream in = new ObjectInputStream(
					socket.getInputStream());
			MessagePackage messageObject = (MessagePackage) in.readObject();
			String messageText = messageObject.getText();
			System.out.println("I have received: " + messageText);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}
}
