package com.wmcalyj.im.shared.communication;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class Serialize {
	public static ArrayList<byte[]> serialize(Object obj) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ObjectOutputStream os = new ObjectOutputStream(out);
		os.writeObject(obj);
		byte[] message = out.toByteArray();
		ArrayList<byte[]> newMessage = new ArrayList<byte[]>();
		int byteSize = 117;
		int len = message.length;
		for (int i = 0; i < len - byteSize + 1; i += byteSize) {
			newMessage.add(Arrays.copyOfRange(message, i, i + byteSize));
		}
		if (len % byteSize != 0) {
			newMessage.add(Arrays.copyOfRange(message, len - len % byteSize,
					len));
		}
		return newMessage;
	}

	public static Object deserialize(byte[] data) throws IOException,
			ClassNotFoundException {
		if (data != null && data.length >= 0) {
			ByteArrayInputStream in = new ByteArrayInputStream(data);
			ObjectInputStream is = new ObjectInputStream(in);
			return is.readObject();
		} else {
			return "";
		}
	}
}
