package im.front.messageservice;

import im.webservice.messagepackage.MessagePackage;

import java.io.DataOutputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SendMessage extends Thread implements Runnable {
	public void send(byte[] message, Socket socket) throws Exception {
		DataOutputStream out = new DataOutputStream(socket.getOutputStream());
		out.write(message);
	}

	public void send(MessagePackage message, Socket socket) throws Exception {
		ObjectOutputStream out = new ObjectOutputStream(
				socket.getOutputStream());
		out.writeObject(message);
	}

}
