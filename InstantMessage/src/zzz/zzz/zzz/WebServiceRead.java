package zzz.zzz.zzz;

import im.front.messageservice.CenterServerInfo;

import java.io.DataInputStream;
import java.io.IOException;
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
			clientSocket = serverSocket.accept();
			System.out.println("Succeed connecting to server");
			DataInputStream input = new DataInputStream(
					clientSocket.getInputStream());
			while (true) {
				input.read();
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
}
