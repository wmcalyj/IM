package zzz.zzz.zzz;

import im.front.messageservice.CenterServerInfo;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServiceWrite extends Thread implements Runnable {
	ServerSocket serverSocket;

	public WebServiceWrite(ServerSocket socket) throws IOException {
		serverSocket = socket;
	}

	public boolean write() {
		Socket clientSocket;
		try {
			clientSocket = serverSocket.accept();
			DataOutputStream out = new DataOutputStream(
					clientSocket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;

	}

}
