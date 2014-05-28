package com.wmcalyj.im.shared.communication;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.wmcalyj.im.shared.data.Message;

public class WriteToSocket {
	private static WriteToSocket SINGLTON = null;
	private ObjectOutputStream out;
	private Socket socket;

	public static WriteToSocket getInstance(Socket socket) {
		if (SINGLTON == null) {
			init(socket);
		}
		return SINGLTON;
	}

	public static WriteToSocket getInstance() {
		if (SINGLTON != null) {
			return SINGLTON;
		} else {
			return null;
		}
	}

	private static synchronized void init(Socket socket) {
		if (SINGLTON == null) {
			SINGLTON = new WriteToSocket(socket);
		}
	}

	private WriteToSocket(Socket socket) {
		this.socket = socket;
		try {
			this.out = new ObjectOutputStream(this.socket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void sendMessage(Message message) {
		Socket _socket = this.socket;
		if (_socket != null && !_socket.isClosed()) {

			if (message != null) {
				System.out.println(message.toString());
				try {
					this.out.writeObject(message);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		} else {
			System.out.println("Socket in sendMessage is closed");
		}
	}
}
