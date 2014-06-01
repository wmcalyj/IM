package com.wmcalyj.im.shared.communication;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.wmcalyj.im.encryption.AsymmetricEncryptionService;
import com.wmcalyj.im.shared.data.InstantMessageConstants;
import com.wmcalyj.im.shared.data.message.InitMessage;

public class EstablishCommunication {
	private EstablishCommunication() {
	};

	public static Socket establishConnection() throws IOException {
		String hostName = InstantMessageConstants.HOST;
		int portNumber = InstantMessageConstants.PORT;
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
		try {
			PublicKey publicKey = AsymmetricEncryptionService.getService()
					.getPublicKey();
			InitMessage initMessage = new InitMessage(clientID, publicKey);
			if (initMessage.isValidMessage()) {
				WriteToSocket.getInstance(socket).sendMessage(initMessage);
			} else {
				System.out.println("Invalid init message");
			}
		} catch (InvalidKeyException | NoSuchAlgorithmException
				| NoSuchPaddingException | IllegalBlockSizeException
				| BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
