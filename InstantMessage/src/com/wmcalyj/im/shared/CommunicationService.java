package com.wmcalyj.im.shared;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import com.wmcalyj.im.shared.data.Message;

public class CommunicationService {

	public static void sendMessage(Socket socket, Message message) {
		try (ObjectOutputStream out = new ObjectOutputStream(
				socket.getOutputStream());) {
			if (message != null) {
				out.writeObject(message);
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public static String communicate(Socket socket, Message message) {

		try (ObjectOutputStream out = new ObjectOutputStream(
				socket.getOutputStream());
				ObjectInputStream in = new ObjectInputStream(
						socket.getInputStream());) {
			Message fromServer;
			Message fromUser;
			while (in != null) {
				try {
					fromServer = (Message) in.readObject();

					System.out.println("Server message: "
							+ fromServer.getMessage());
					if (fromServer.equals("Bye "))
						break;
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				if (message != null) {
					out.writeObject(message);
				}
			}

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return null;

	}

	public static void establishConnection(String clientID, Socket socket)
			throws IOException {
		String hostName = "localhost";
		int portNumber = 4433;

		System.out.println("Client starts");
		try {
			socket = new Socket(hostName, portNumber);
			ObjectOutputStream out = new ObjectOutputStream(
					socket.getOutputStream());
			// Message firstMessage = new Message(clientID,
			// "establish connection");
			// out.writeObject(firstMessage);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void closeConnection(Socket socket) throws IOException {
		socket.close();
	}
}
