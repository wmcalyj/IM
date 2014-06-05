package com.wmcalyj.im.server;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import com.wmcalyj.exceptions.message.InvalidMessageException;
import com.wmcalyj.im.server.dataManagement.OnlineUsersTable;
import com.wmcalyj.im.shared.communication.SendMessageStrategy;
import com.wmcalyj.im.shared.communication.Serialize;
import com.wmcalyj.im.shared.data.InstantMessageConstants;
import com.wmcalyj.im.shared.data.RegisteredUser;
import com.wmcalyj.im.shared.data.message.InitMessage;
import com.wmcalyj.im.shared.data.message.Message;
import com.wmcalyj.im.shared.data.message.MessageType;
import com.wmcalyj.im.shared.data.message.NormalMessage;
import com.wmcalyj.im.shared.data.message.PublicKeyRequestMessage;
import com.wmcalyj.im.shared.data.message.PublicKeyResponseMessage;

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
				} catch (EOFException e) {
					e.printStackTrace();
				}

				if (fromClient != null) {
					processIncomingMessage(fromClient, out);

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

	private void processIncomingMessage(Message fromClient,
			ObjectOutputStream out) {
		if (isInitMessage(fromClient)) {
			processInitMessage((InitMessage) fromClient, out);
		} else if (isNormalMessage(fromClient)) {
			processNormalMessage((NormalMessage) fromClient, out);
		} else if (isPublicKeyRequestMessage(
				(PublicKeyRequestMessage) fromClient, out)) {
			processPublicKeyRequestMessage(
					(PublicKeyRequestMessage) fromClient, out);
		} else {
			// Do nothing
		}

	}

	private void processPublicKeyRequestMessage(
			PublicKeyRequestMessage fromClient, ObjectOutputStream out) {
		RegisteredUser user = OnlineUsersTable.getOnlineUser(fromClient
				.getKeyOwner());
		PublicKeyResponseMessage fromServer;
		if (user != null) {
			fromServer = new PublicKeyResponseMessage(user.getUid(),
					user.getPublicKey());
		} else {
			fromServer = new PublicKeyResponseMessage(fromClient.getKeyOwner(),
					null);
		}
		try {
			out.writeObject(fromServer);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private boolean isPublicKeyRequestMessage(
			PublicKeyRequestMessage fromClient, ObjectOutputStream out) {
		if (fromClient.isValidMessage()) {
			return MessageType.PUBLICKEYREQUEST.equals(fromClient
					.getMessageType()) ? true : false;
		}
		return false;
	}

	private void processNormalMessage(NormalMessage fromClient,
			ObjectOutputStream out) {
		String to = fromClient.getDestinationID();
		ObjectOutputStream newOut = getOOSForDestinationID(to);
		if (newOut == null) {
			NormalMessage fromServer = new NormalMessage("SERVER",
					fromClient.getSourceID(), destinationNotOnline(to));
			try {
				out.writeObject(fromServer);
				out.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			try {
				newOut.writeObject(fromClient);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void processInitMessage(InitMessage initMessage,
			ObjectOutputStream out) {
		registerNewOnlineUser(initMessage, socket, out);
		try {
			NormalMessage fromServer = new NormalMessage("SERVER",
					initMessage.getSourceID(), Serialize.serialize("Hello "
							+ initMessage.getSourceID()));
			SendMessageStrategy.sendMessage(fromServer, out);

		} catch (IOException | InvalidMessageException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (socket.isClosed()) {
			System.out.println("Socket closed now");
		}
		System.out.println("New user registered");

	}

	private boolean isNormalMessage(Message fromClient) {
		if (fromClient.isValidMessage()) {
			return MessageType.NORMALMESSAGE
					.equals(fromClient.getMessageType()) ? true : false;
		}
		return false;
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
		if (fromClient.isValidMessage()) {
			return MessageType.INITMESSAGE.equals(fromClient.getMessageType()) ? true
					: false;
		}
		return false;
	}

	// private void broadcastPublicKey(RegisteredUser newUser) {
	// System.out.println("Broadcast public key: "
	// + newUser.getPublicKey().toString() + "for user: "
	// + newUser.getUid());
	// PublicKeyResponseMessage publicKeyMessage = new PublicKeyMessage(
	// newUser.getUid(), newUser.getPublicKey());
	// ObjectOutputStream out = null;
	// for (RegisteredUser user : OnlineUsersTable.getOnlineUsers().values()) {
	// out = user.getOut();
	// try {
	// out.writeObject(publicKeyMessage);
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	// }
	// }

	// private byte[] getMessage(Message message) {
	// PublicKey publicKey = OnlineUsersTable.getOnlineUser(
	// message.getSourceID()).getPublicKey();
	//
	// try {
	// if (!message.isInitMessage()) {
	// System.out.println(((String) Serialize
	// .deserialize(AsymmetricEncryptionService.getService()
	// .decryptWithPublicKey(publicKey,
	// message.getMessage()))));
	// return AsymmetricEncryptionService.getService()
	// .decryptWithPublicKey(publicKey, message.getMessage());
	// }
	// } catch (InvalidKeyException | NoSuchAlgorithmException
	// | NoSuchPaddingException | IllegalBlockSizeException
	// | BadPaddingException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (ClassNotFoundException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// return null;
	// }

	private ArrayList<byte[]> destinationNotOnline(String destinationID) {
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
