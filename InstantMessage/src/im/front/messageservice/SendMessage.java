package im.front.messageservice;

import im.webservice.messagepackage.MessagePackage;

import java.io.ObjectOutputStream;
import java.net.Socket;

public class SendMessage extends Thread implements Runnable {
	public void send(MessagePackage message, Socket socket) throws Exception {

		ObjectOutputStream out = new ObjectOutputStream(
				socket.getOutputStream());
		out.writeObject(message);

	}

}
