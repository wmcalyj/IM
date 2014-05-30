package com.wmcalyj.im.client.history;

import java.awt.ComponentOrientation;
import java.io.IOException;
import java.util.LinkedList;

import javax.swing.JTextArea;

import com.wmcalyj.im.shared.communication.Serialize;
import com.wmcalyj.im.shared.data.Message;

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
			System.out.println("Contact: " + contact);
			if (contact != null) {
				System.out.println("Trying to read from queue");
				LinkedList<Message> historyQueue = HistoryMap
						.readFromQueue(contact);
				if (historyQueue == null) {
					System.out.println("history queue is null");
					continue;
				}
				if (historyQueue.size() == 0) {
					System.out.println("This is an empty queue");
				}
				for (int i = 0, j = historyQueue.size(); i < j; i++) {
					processMessage(history, historyQueue.get(i));
					System.out.println("HIT");
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

	protected void processMessage(JTextArea history, Message m) {
		history.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		history.append(m.getSourceID());
		history.append(":");
		System.out.println("HIT PROCESS MESSAGE");
		try {
			if (m.getMessage() != null && m.getMessage().length > 0) {
				String message = (String) Serialize.deserialize(m.getMessage());
				history.append(message);
			}
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		history.append("\n");
	}
}
