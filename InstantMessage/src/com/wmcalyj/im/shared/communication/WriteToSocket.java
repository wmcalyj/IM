package com.wmcalyj.im.shared.communication;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.wmcalyj.im.shared.data.Message;

public class WriteToSocket {
	// The reason why the write to socket needs to be a singlton is because,
	// only one outputstream is supposed to write to the socket.
	// Check the following post:
	// http://stackoverflow.com/questions/2393179/streamcorruptedexception-invalid-type-code-ac
	private static WriteToSocket SINGLETON = null;
	private ObjectOutputStream out;
	private Socket socket;

	public static WriteToSocket getInstance(Socket socket) {
		if (SINGLETON == null) {
			init(socket);
		}
		return SINGLETON;
	}

	public static WriteToSocket getInstance() {
		if (SINGLETON != null) {
			return SINGLETON;
		} else {
			return null;
		}
	}

	private static synchronized void init(Socket socket) {
		if (SINGLETON == null) {
			SINGLETON = new WriteToSocket(socket);
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
