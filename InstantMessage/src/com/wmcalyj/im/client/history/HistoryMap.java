package com.wmcalyj.im.client.history;

import im.contacts.SingleContact;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import com.wmcalyj.im.shared.data.Message;

public class HistoryMap {
	private static Map<SingleContact, LinkedList<Message>> historyMap = null;

	private HistoryMap() {
		if (historyMap == null) {
			initHistoryMap();
		}
	}

	private static final synchronized void initHistoryMap() {
		if (historyMap == null) {
			historyMap = new HashMap<SingleContact, LinkedList<Message>>();
		}
	}

	public static void addToQueue(SingleContact contact, Message message) {
		if (historyMap == null) {
			initHistoryMap();
		}
		LinkedList<Message> queue = historyMap.get(contact);
		if (queue == null) {
			queue = new LinkedList<Message>();
		}
		queue.addFirst(message);
		historyMap.put(contact, queue);
	}

	public static LinkedList<Message> readFromQueue(SingleContact contact) {
		if (historyMap == null) {
			initHistoryMap();
		}
		LinkedList<Message> queue = historyMap.get(contact);
		if (queue == null) {
			return new LinkedList<Message>();
		}
		LinkedList<Message> returnQueue = new LinkedList<Message>();
		Message m;
		while ((m = queue.removeLast()) != null) {
			returnQueue.add(m);
		}
		return returnQueue;
	}
}
