package com.wmcalyj.im.shared.communication;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import com.wmcalyj.im.client.data.FriendsTable;
import com.wmcalyj.im.client.history.HistoryMap;
import com.wmcalyj.im.shared.data.message.Message;
import com.wmcalyj.im.shared.data.message.MessageType;
import com.wmcalyj.im.shared.data.message.NormalMessage;
import com.wmcalyj.im.shared.data.message.PublicKeyResponseMessage;

public class ListenToSocket extends Thread {
	private Socket socket;
	private boolean stop;

	public ListenToSocket(Socket socket) {
		this.socket = socket;
		this.stop = false;
	}

	public void turnOff() {
		this.stop = true;
	}

	public void turnOn() {
		this.stop = false;
	}

	@Override
	public void run() {
		int i = 0;
		if (this.socket != null && !socket.isClosed()) {
			while (!stop) {
				try (ObjectInputStream in = new ObjectInputStream(
						socket.getInputStream());) {
					Message fromServer = null;
					while (in != null) {
						try {
							try {
								fromServer = (Message) in.readObject();
							} catch (EOFException e) {
								continue;
							}
							if (fromServer == null) {
								continue;
							}

							if (fromServer.getMessageType().equals(
									MessageType.PUBLICKEYRESPONSE)) {
								FriendsTable.getTable().addToFriendsTable(
										(PublicKeyResponseMessage) fromServer);
							} else {
								if (fromServer.getMessageType().equals(
										MessageType.NORMALMESSAGE)) {
									NormalMessage message = (NormalMessage) fromServer;
									HistoryMap.addToQueue(
											message.getSourceID(), message);
								}
							}
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					i++;
					if (i >= 20) {
						break;
					}
				}
			}
		}
	}
}
