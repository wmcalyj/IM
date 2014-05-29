package com.wmcalyj.im.server;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.wmcalyj.im.shared.data.InstantMessageConstants;
import com.wmcalyj.im.shared.data.Message;

public class CentralServer {

	public static void main(String[] args) {

		int portNumber = InstantMessageConstants.PORT;

		try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
			while (true) {
				new CentralServerThread(serverSocket.accept()).start();
			}
		} catch (IOException e) {
			System.err.println("Could not listen on port " + portNumber);
			e.printStackTrace();
			System.exit(-1);
		}

		// try (ServerSocket serverSocket = new ServerSocket(portNumber);
		// Socket clientSocket = serverSocket.accept();
		// ObjectOutputStream out = new ObjectOutputStream(
		// clientSocket.getOutputStream());
		// ObjectInputStream in = new ObjectInputStream(
		// clientSocket.getInputStream());) {
		// System.out.println("Ready to start");
		// while (in != null) {
		// Message fromClient = null;
		// Message fromServer = null;
		//
		// try {
		// fromClient = (Message) in.readObject();
		// } catch (ClassNotFoundException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		//
		// if (fromClient != null) {
		// String to = fromClient.getTo();
		// if (null == to || to.length() == 0) {
		// System.out.println("This is a initial connection");
		// System.out
		// .println("Client: " + fromClient.getMessage());
		// fromServer = new Message("SERVER",
		// fromClient.getFrom(), "Hello "
		// + fromClient.getFrom());
		// if (clientSocket.isClosed()) {
		// System.out.println("Socket closed now");
		// }
		// out.writeObject(fromServer);
		// out.flush();
		//
		// } else {
		// if (fromClient.getMessage().equalsIgnoreCase("bye")) {
		// fromServer = new Message("SERVER",
		// fromClient.getFrom(), "Bye "
		// + fromClient.getFrom());
		// out.writeObject(fromServer);
		// System.out.println("Client: "
		// + fromClient.getMessage());
		// }
		// fromServer = new Message("SERVER",
		// fromClient.getFrom(), "You said: "
		// + fromClient.getMessage());
		// out.writeObject(fromServer);
		// System.out
		// .println("Client: " + fromClient.getMessage());
		// out.flush();
		// }
		// }
		// }
		// } catch (IOException e) {
		// System.out
		// .println("Exception caught when trying to listen on port "
		// + portNumber + " or listening for a connection");
		// System.out.println(e.getMessage());
		// e.printStackTrace();
		// }
	}

	public void startCentralServer() {
		ServerSocket serverSocket = null;
		Socket clientSocket = null;
		try {
			serverSocket = new ServerSocket(InstantMessageConstants.PORT);
			clientSocket = serverSocket.accept();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		while (true) {
			if (needsRebootServer(serverSocket)) {
				if ((clientSocket = rebootServer(serverSocket)) == null) {
					try {
						serverSocket.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return;
				} else {
					run(clientSocket);
				}
			}
			run(clientSocket);
		}
	}

	public boolean needsRebootServer(ServerSocket serverSocket) {
		return Boolean.TRUE.equals(serverSocket.isClosed());
	}

	@SuppressWarnings("resource")
	public Socket rebootServer(ServerSocket serverSocket) {
		boolean success = false;
		Socket clientSocket = null;
		int tries = 0;
		while (!success) {
			try {
				serverSocket = new ServerSocket(InstantMessageConstants.PORT);
				clientSocket = serverSocket.accept();
				success = true;
			} catch (IOException e) {
				tries++;
				if (tries >= InstantMessageConstants.MAX_TRIES_TO_REBOOT_SERVER) {
					return null;
				}
			}
		}
		return clientSocket;
	}

	private void run(Socket clientSocket) {
		try {
			Message fromClient = null;
			Message fromServer;

			if (clientSocket == null || clientSocket.isClosed()) {
				System.out.println("Socket closed when server starts");
				System.exit(1);
			}
			try (ObjectOutputStream out = new ObjectOutputStream(
					clientSocket.getOutputStream());
					ObjectInputStream in = new ObjectInputStream(
							clientSocket.getInputStream());) {
				while (in != null) {
					try {
						fromClient = (Message) in.readObject();
					} catch (EOFException e) {
						return;
					}
					if (fromClient == null) {
						return;
					}
					String to = fromClient.getTo();
					if (null == to || to.length() == 0) {
						System.out.println("This is a initial connection");
						fromServer = new Message("SERVER",
								fromClient.getFrom(), "Hello "
										+ fromClient.getFrom());
						out.writeObject(fromServer);
						System.out
								.println("Client: " + fromClient.getMessage());
					} else {
						if (fromClient.getMessage().equalsIgnoreCase("bye")) {
							fromServer = new Message("SERVER",
									fromClient.getFrom(), "Bye "
											+ fromClient.getFrom());
							out.writeObject(fromServer);
							System.out.println("Client: "
									+ fromClient.getMessage());
						}
						fromServer = new Message("SERVER",
								fromClient.getFrom(), "You said: "
										+ fromClient.getMessage());
						out.writeObject(fromServer);
						System.out
								.println("Client: " + fromClient.getMessage());
					}
				}
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// }
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
