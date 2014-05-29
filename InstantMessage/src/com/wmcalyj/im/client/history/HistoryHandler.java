package com.wmcalyj.im.client.history;

import im.contacts.SingleContact;

import java.util.LinkedList;

import javax.swing.JTextArea;

import com.wmcalyj.im.shared.data.Message;

public class HistoryHandler extends Thread {
	private JTextArea history = null;
	private SingleContact contact = null;

	public HistoryHandler(JTextArea history, SingleContact contact) {
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
				StringBuilder newHistory = new StringBuilder();
				for (int i = 0, j = historyQueue.size(); i < j; i++) {
					newHistory.append(this.history.getText());
					processMessage(newHistory, historyQueue.get(i));
				}
				this.history.setText(newHistory.toString());
				try {
					sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("History: " + newHistory.toString());
			} else {
				System.out.println("Contact is null");
			}
		}

	}

	protected void processMessage(StringBuilder sb, Message m) {
		sb.append(m.getFrom()).append(": ").append("\n");
		sb.append("\t").append(m.getMessage()).append("\n");
	}
}
