package zzz.zzz.zzz;

import im.webservice.messagepackage.MessagePackage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServiceRead extends Thread implements Runnable {

	ServerSocket serverSocket;

	public WebServiceRead(ServerSocket socket) throws Exception {
		serverSocket = socket;
	}

	public boolean read() {
		Socket clientSocket = null;
		try {

			while (true) {
				clientSocket = serverSocket.accept();
				System.out.println("Succeed connecting to server");
				ObjectInputStream input = new ObjectInputStream(
						clientSocket.getInputStream());
				MessagePackage mp = (MessagePackage) input.readObject();
				System.out.println(mp.toString());
				input.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
}
