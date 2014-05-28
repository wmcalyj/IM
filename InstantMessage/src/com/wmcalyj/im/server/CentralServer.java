package com.wmcalyj.im.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Calendar;

import org.apache.log4j.Logger;

import com.wmcalyj.im.shared.data.InstantMessageConstants;
import com.wmcalyj.im.shared.data.Message;

public class CentralServer {

	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(InstantMessageConstants.PORT);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		while (true) {

			if (needsRebootServer(serverSocket)) {
				if (!rebootServer(serverSocket)) {
					try {
						serverSocket.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return;
				}
			}
			try {
				Socket clientSocket = serverSocket.accept();
				ObjectOutputStream out = new ObjectOutputStream(
						clientSocket.getOutputStream());
				ObjectInputStream in = new ObjectInputStream(
						clientSocket.getInputStream());
				Message fromClient;
				Message fromServer;

				// Initiate conversation with client

				// CommunicationProtocol cp = new CommunicationProtocol();
				// outputLine = cp.processCommunication(message);
				// out.println(outputLine);

				while (in != null) {
					try {
						fromClient = (Message) in.readObject();
						String to = fromClient.getTo();
						if (null == to || to.length() == 0) {
							System.out.println("This is a initial connection");
							fromServer = new Message("SERVER",
									fromClient.getFrom(), "Hello "
											+ fromClient.getFrom());
							out.writeObject(fromServer);
							System.out.println("Client: "
									+ fromClient.getMessage());
						} else {
							if (fromClient.getMessage().equalsIgnoreCase("bye")) {
								fromServer = new Message("SERVER",
										fromClient.getFrom(), "Bye "
												+ fromClient.getFrom());
								out.writeObject(fromServer);
								System.out.println("Client: "
										+ fromClient.getMessage());
								break;
							}
							fromServer = new Message("SERVER",
									fromClient.getFrom(), "You said: "
											+ fromClient.getMessage());
							out.writeObject(fromServer);
							System.out.println("Client: "
									+ fromClient.getMessage());
						}
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
	}

	public static boolean needsRebootServer(ServerSocket serverSocket) {
		return Boolean.TRUE.equals(serverSocket.isClosed());
	}

	@SuppressWarnings("resource")
	public static boolean rebootServer(ServerSocket serverSocket) {
		boolean success = false;
		int tries = 0;
		while (!success) {
			try {
				serverSocket = new ServerSocket(InstantMessageConstants.PORT);
				success = true;
			} catch (IOException e) {
				tries++;
				if (tries >= InstantMessageConstants.MAX_TRIES_TO_REBOOT_SERVER) {
					return success;
				}
			}
		}
		return success;

	}
}
