package im.front.messageservice;

import java.io.ObjectInputStream;
import java.net.Socket;

public class ReceiveMessage extends Thread implements Runnable {
	public void receive(Socket socket, byte[] bytes) throws Exception {

		ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
		while (in.read() != -1) {
			in.read(bytes);
			System.out.println("Receive message: " + new String(bytes));
		}

		// // MessagePackage messageObject = (MessagePackage) in.read();
		// String messageText = messageObject.getText();
		// System.out.println("I have received: " + messageText);
		// return messageObject;

	}
}
