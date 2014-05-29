package com.wmcalyj.im.client.history;

import java.awt.ComponentOrientation;
import java.util.LinkedList;

import javax.swing.JTextArea;

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
			if (contact != null) {
				LinkedList<Message> historyQueue = HistoryMap
						.readFromQueue(contact);
				if (historyQueue == null) {
					System.out.println("history queue is null");
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

	protected void processMessage(JTextArea history, Message m) {
		history.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		history.append(m.getFrom());
		history.append(":");
		history.append(m.getMessage());
		history.append("\n");
	}
}
