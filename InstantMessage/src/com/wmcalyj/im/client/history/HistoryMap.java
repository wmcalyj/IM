package com.wmcalyj.im.client.history;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.wmcalyj.im.client.data.FriendsTable;
import com.wmcalyj.im.encryption.AsymmetricEncryptionService;
import com.wmcalyj.im.shared.data.message.Message;
import com.wmcalyj.im.shared.data.message.NormalMessage;

public class HistoryMap {
	private static Map<String, LinkedList<NormalMessage>> historyMap = null;

	private HistoryMap() {
		if (historyMap == null) {
			initHistoryMap();
		}
	}

	private static final synchronized void initHistoryMap() {
		if (historyMap == null) {
			historyMap = new HashMap<String, LinkedList<NormalMessage>>();
		}
	}

	public static synchronized void addToQueue(String name,
			NormalMessage message) {
		if (historyMap == null) {
			initHistoryMap();
			System.out.println("Init history map");
		}
		LinkedList<NormalMessage> queue = historyMap.get(name);
		if (queue == null) {
			queue = new LinkedList<NormalMessage>();
		}
		decryptMessage(name, message);
		queue.addFirst(message);
		System.out.println("Message to add: " + message.toString());
		historyMap.put(name, queue);
	}

	private static void decryptMessage(String name, NormalMessage message) {
		PublicKey key = FriendsTable.getTable().getPublicKeyForContact(name);
		if (key != null) {
			try {
				byte[] decryptedMessage = AsymmetricEncryptionService
						.getService().decryptWithPublicKey(key,
								message.getMessage());
				message.setMessage(decryptedMessage);
			} catch (InvalidKeyException | NoSuchAlgorithmException
					| NoSuchPaddingException | IllegalBlockSizeException
					| BadPaddingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public static LinkedList<NormalMessage> readFromQueue(String name) {
		if (historyMap == null) {
			System.out.println("History map is null");
			return new LinkedList<NormalMessage>();
		}
		LinkedList<NormalMessage> queue = historyMap.get(name);
		if (queue == null) {
			return new LinkedList<NormalMessage>();
		}
		LinkedList<NormalMessage> returnQueue = new LinkedList<NormalMessage>();
		NormalMessage m;
		while (!queue.isEmpty()) {
			m = queue.removeLast();
			System.out.println("Message to read: " + m.toString());
			returnQueue.add(m);
		}
		return returnQueue;
	}
}
