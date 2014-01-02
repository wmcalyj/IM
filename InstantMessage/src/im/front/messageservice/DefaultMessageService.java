package im.front.messageservice;

import im.webservice.messagepackage.MessagePackage;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class DefaultMessageService {
	static Socket socket;
	static ReceiveMessage receiveMessage;
	static SendMessage sendMessage;
	static int size = -1;

	public DefaultMessageService() {
		try {
			InetAddress host = InetAddress.getLocalHost();
			socket = new Socket(host, CenterServerInfo.PORT);
			receiveMessage = new ReceiveMessage();
			sendMessage = new SendMessage();
			Thread receive = new Thread(receiveMessage);
			Thread send = new Thread(sendMessage);
			receive.start();
			send.start();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static boolean send(MessagePackage message) {
		try {
			InetAddress host = InetAddress.getLocalHost();
			Socket sendSocket = new Socket(host, CenterServerInfo.PORT);
			sendMessage.send(message, sendSocket);
			// byte[] bytes = serialize(message);
			// size = bytes.length;
			// sendMessage.send(bytes, socket);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static boolean receive() {
		try {
			byte[] bytes;
			if (size != -1) {
				bytes = new byte[size];
				receiveMessage.receive(socket, bytes);
				MessagePackage message = deserialize(bytes);
				System.out.println("Source: " + message.getSource());
				System.out.println("Target: " + message.getTarget());
				System.out.println("Text: " + message.getText());
				size = -1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static byte[] serialize(MessagePackage obj) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ObjectOutputStream os = new ObjectOutputStream(out);
		os.writeObject(obj);
		return out.toByteArray();
	}

	public static MessagePackage deserialize(byte[] data) throws IOException,
			ClassNotFoundException {
		ByteArrayInputStream in = new ByteArrayInputStream(data);
		ObjectInputStream is = new ObjectInputStream(in);
		return (MessagePackage) is.readObject();
	}
}
