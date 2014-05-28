package com.wmcalyj.im.shared.communication;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.wmcalyj.im.shared.data.Message;

public class WriteToSocket {
	public static void sendMessage(Socket socket, Message message) {
		if (socket != null && !socket.isClosed()) {
			try (ObjectOutputStream out = new ObjectOutputStream(
					socket.getOutputStream());) {
				if (message != null) {
					System.out.println(message.toString());
					out.writeObject(message);
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else {
			System.out.println("Socket in sendMessage is closed");
		}
	}
}
