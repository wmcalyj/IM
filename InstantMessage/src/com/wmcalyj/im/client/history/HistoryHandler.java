package com.wmcalyj.im.client.history;

import java.awt.ComponentOrientation;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.util.LinkedList;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.JTextArea;

import com.wmcalyj.im.client.data.FriendsTable;
import com.wmcalyj.im.encryption.AsymmetricEncryptionService;
import com.wmcalyj.im.shared.communication.Serialize;
import com.wmcalyj.im.shared.data.message.NormalMessage;

public class HistoryHandler extends Thread {
	private JTextArea history = null;
	private String contact = null;

	public HistoryHandler(JTextArea history, String contact) {
		super("HistoryHandler");
		this.history = history;
		this.contact = contact;
	}

	@Override
	public void run() {
		while (true) {
			if (contact != null) {
				LinkedList<NormalMessage> historyQueue = HistoryMap
						.readFromQueue(contact);
				if (historyQueue == null || historyQueue.size() == 0) {
					continue;
				}

				for (int i = 0, j = historyQueue.size(); i < j; i++) {
					processMessage(history, historyQueue.get(i));
				}

				try {
					sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				System.out.println("Contact is null");
			}
		}

	}

	protected void processMessage(JTextArea history, NormalMessage m) {
		history.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		history.append(m.getSourceID());
		history.append(":");
		try {
			if (m.getMessage() != null && m.getMessage().size() > 0) {
				byte[] decryptedmessage = decryptMessage(m.getSourceID(), m);
				String message = (String) Serialize
						.deserialize(decryptedmessage);
				history.append(message);
			}
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		history.append("\n");
	}

	private static byte[] decryptMessage(String name, NormalMessage message) {
		PublicKey key = FriendsTable.getTable().getPublicKeyForContact(name);
		if (key != null) {
			try {
				byte[] decryptedMessage = AsymmetricEncryptionService
						.getService().decrypt(message.getMessage(), name);
				return decryptedMessage;
			} catch (InvalidKeyException | NoSuchAlgorithmException
					| NoSuchPaddingException | IllegalBlockSizeException
					| BadPaddingException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;

	}
}
