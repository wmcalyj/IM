package com.wmcalyj.im.client.history;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import com.wmcalyj.im.shared.data.Message;

public class HistoryMap {
	private static Map<String, LinkedList<Message>> historyMap = null;

	private HistoryMap() {
		if (historyMap == null) {
			initHistoryMap();
		}
	}

	private static final synchronized void initHistoryMap() {
		if (historyMap == null) {
			historyMap = new HashMap<String, LinkedList<Message>>();
		}
	}

	public static synchronized void addToQueue(String name, Message message) {
		if (historyMap == null) {
			initHistoryMap();
			System.out.println("Init history map");
		}
		LinkedList<Message> queue = historyMap.get(name);
		if (queue == null) {
			queue = new LinkedList<Message>();
		}
		queue.addFirst(message);
		System.out.println("Message to add: " + message.toString());
		historyMap.put(name, queue);
	}

	public static LinkedList<Message> readFromQueue(String name) {
		if (historyMap == null) {
			System.out.println("History map is null");
			return new LinkedList<Message>();
		}
		LinkedList<Message> queue = historyMap.get(name);
		if (queue == null) {
			System.out.println("Queue to read is null");
			return new LinkedList<Message>();
		}
		LinkedList<Message> returnQueue = new LinkedList<Message>();
		Message m;
		if (queue.isEmpty()) {
			System.out.println("Queue is empty");
		}
		while (!queue.isEmpty()) {
			m = queue.removeLast();
			System.out.println("Message to read: " + m.toString());
			returnQueue.add(m);
		}
		System.out.println("HIT READ FROM QUEUE");
		return returnQueue;
	}
}
