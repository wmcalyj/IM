package com.wmcalyj.im.shared.communication;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import com.wmcalyj.im.shared.data.InstantMessageConstants;
import com.wmcalyj.im.shared.data.Message;

public class EstablishCommunication {
	private EstablishCommunication() {
	};

	public static Socket establishConnection() throws IOException {
		String hostName = InstantMessageConstants.HOST;
		int portNumber = InstantMessageConstants.PORT;

		System.out.println("Client starts");
		try {
			Socket socket = new Socket(hostName, portNumber);

			return socket;
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static void sendInitMessage(Socket socket, String clientID) {
		if (socket != null && !socket.isClosed()) {
			Message firstMessage = new Message(clientID, "establish connection");
			WriteToSocket.getInstance(socket).sendMessage(firstMessage);
		}
	}
}
