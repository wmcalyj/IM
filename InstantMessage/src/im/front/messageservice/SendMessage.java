package im.front.messageservice;

import im.webservice.messagepackage.MessagePackage;

import java.io.DataOutputStream;
import java.net.Socket;

public class SendMessage extends Thread implements Runnable {
	public void send(MessagePackage message, Socket socket) throws Exception {

		System.out.println("TEST2");
		DataOutputStream out = new DataOutputStream(socket.getOutputStream());
		System.out.println("TEST3");
		out.writeUTF("asdlkj");
	}

}
