package com.wmcalyj.im.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.lang3.BooleanUtils;

import com.wmcalyj.exceptions.message.InvalidMessageException;
import com.wmcalyj.im.encryption.AsymmetricEncryptionService;
import com.wmcalyj.im.server.dataManagement.OnlineUsersTable;
import com.wmcalyj.im.shared.communication.SendMessageStrategy;
import com.wmcalyj.im.shared.communication.Serialize;
import com.wmcalyj.im.shared.data.InitMessage;
import com.wmcalyj.im.shared.data.InstantMessageConstants;
import com.wmcalyj.im.shared.data.Message;
import com.wmcalyj.im.shared.data.RegisteredUser;

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
			while (in != null) {
				Message fromServer = null;
				Message fromClient = null;

				try {
					fromClient = (Message) in.readObject();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				if (fromClient != null) {
					if (isInitMessage(fromClient)) {

						registerNewOnlineUser((InitMessage) fromClient, socket,
								out);
						fromServer = new Message("SERVER",
								fromClient.getSourceID(),
								Serialize.serialize("Hello "
										+ fromClient.getSourceID()));
						if (socket.isClosed()) {
							System.out.println("Socket closed now");
						}
						try {
							SendMessageStrategy.sendMessage(fromServer, out);
						} catch (InvalidMessageException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						System.out.println("New user registered");
					}
					String to = fromClient.getDestinationID();
					ObjectOutputStream newOut = getOOSForDestinationID(to);
					if (newOut == null) {
						fromServer = new Message("SERVER",
								fromClient.getSourceID(),
								destinationNotOnline(to));
						out.writeObject(fromServer);
						out.flush();
					} else {
						newOut.writeObject(fromClient);
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

	private ObjectOutputStream getOOSForDestinationID(String to) {
		RegisteredUser user = OnlineUsersTable.getOnlineUser(to);
		if (user != null) {
			return user.getOut();
		} else {
			System.out.println(to + "is not online yet");
		}
		return null;
	}

	private void registerNewOnlineUser(InitMessage fromClient, Socket socket,
			ObjectOutputStream out) {
		RegisteredUser newUser = new RegisteredUser(fromClient.getSourceID(),
				fromClient.getPublicKey(), socket, out);
		System.out.println("New user: " + fromClient.getSourceID());
		OnlineUsersTable.addOnlineUser(fromClient.getSourceID(), newUser);
		System.out.println("Public key: "
				+ fromClient.getPublicKey().toString());
	}

	protected boolean isInitMessage(Message fromClient) {
		return BooleanUtils.isTrue(fromClient.isInitMessage()) ? true : false;
	}

	private byte[] getMessage(Message message) {
		PublicKey publicKey = OnlineUsersTable.getOnlineUser(
				message.getSourceID()).getPublicKey();

		try {
			if (!message.isInitMessage()) {
				System.out.println(((String) Serialize
						.deserialize(AsymmetricEncryptionService.getService()
								.decryptWithPublicKey(publicKey,
										message.getMessage()))));
				return AsymmetricEncryptionService.getService()
						.decryptWithPublicKey(publicKey, message.getMessage());
			}
		} catch (InvalidKeyException | NoSuchAlgorithmException
				| NoSuchPaddingException | IllegalBlockSizeException
				| BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private byte[] destinationNotOnline(String destinationID) {
		try {
			return Serialize.serialize("Destination: " + destinationID
					+ "is not online right now");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
