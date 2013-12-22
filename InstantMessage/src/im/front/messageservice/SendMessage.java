package im.front.messageservice;

import im.webservice.messagepackage.MessagePackage;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class SendMessage extends Thread implements Runnable {
	public void send(MessagePackage message, Socket socket) {
		try {
			ObjectOutputStream out = new ObjectOutputStream(
					socket.getOutputStream());
			out.writeObject(message);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
