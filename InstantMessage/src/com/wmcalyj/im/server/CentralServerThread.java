package com.wmcalyj.im.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.wmcalyj.im.shared.data.InstantMessageConstants;
import com.wmcalyj.im.shared.data.Message;

public class CentralServerThread extends Thread {
	private Socket socket = null;

	public CentralServerThread(Socket socket) {
		super("CentralServerThread");
		this.socket = socket;
	}

	@Override
	public void run() {

		try (ObjectOutputStream out = new ObjectOutputStream(
				this.socket.getOutputStream());
				ObjectInputStream in = new ObjectInputStream(
						this.socket.getInputStream());) {
			System.out.println("Ready to start");
			while (in != null) {
				Message fromClient = null;
				Message fromServer = null;

				try {
					fromClient = (Message) in.readObject();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				if (fromClient != null) {
					String to = fromClient.getTo();
					if (null == to || to.length() == 0) {
						System.out.println("This is a initial connection");
						System.out
								.println("Client: " + fromClient.getMessage());
						fromServer = new Message("SERVER",
								fromClient.getFrom(), "Hello "
										+ fromClient.getFrom());
						if (socket.isClosed()) {
							System.out.println("Socket closed now");
						}
						out.writeObject(fromServer);
						out.flush();

					} else {

						fromServer = new Message("SERVER",
								fromClient.getFrom(), "You said: "
										+ fromClient.getMessage());
						out.writeObject(fromServer);
						System.out
								.println("Client: " + fromClient.getMessage());
						out.flush();
					}
				}
			}
		} catch (IOException e) {
			System.out
					.println("Exception caught when trying to listen on port "
							+ InstantMessageConstants.PORT
							+ " or listening for a connection");
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
}
