package com.wmcalyj.im.client.data;

import im.contacts.SingleContact;

import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;

import com.wmcalyj.im.shared.data.message.PublicKeyResponseMessage;

public class FriendsTable {
	private static Map<String, SingleContact> friendTable = null;
	private static FriendsTable SINGLETON = null;

	private FriendsTable() {
		// empty
	}

	public static final FriendsTable getTable() {
		if (SINGLETON == null || friendTable == null) {
			initFriendsTable();
		}
		return SINGLETON;
	}

	private static synchronized void initFriendsTable() {
		if (SINGLETON == null || friendTable == null) {
			friendTable = new HashMap<String, SingleContact>();
			SINGLETON = new FriendsTable();
		}
	}

	public void addToFriendsTable(SingleContact contact) {
		if (contact.isValidContact()) {
			friendTable.put(contact.getName(), contact);
		}
	}

	public void addToFriendsTable(PublicKeyResponseMessage publicKeyMessage) {
		SingleContact contact = new SingleContact(
				publicKeyMessage.getKeyOwner(), publicKeyMessage.getKeyOwner(),
				true, publicKeyMessage.getPublicKey());
		if (contact.isValidContact()) {
			addToFriendsTable(contact);
		}
	}

	public PublicKey getPublicKeyForContact(String contact) {
		SingleContact friend = friendTable.get(contact);
		if (friend != null && friend.isValidContact()) {
			return friend.getPublicKey();
		}
		return null;
	}
}
