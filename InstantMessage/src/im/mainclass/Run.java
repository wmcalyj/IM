package im.mainclass;

import im.encryptionservice.EncryptionService;
import im.front.gui.LoginGui;
import im.front.messageservice.CenterServerInfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Run {
	public static void main(String[] args) throws IOException {
		LoginGui login = new LoginGui();
		login.Login();
		EncryptionService es = new EncryptionService();
		es.testEncrypt("测试下中文看看，诶哟卧槽，还真能打中文？！fuck！！！牛逼！！！");

		// Test client side server
		ServerSocket serverSocket = null;

		try {
			serverSocket = new ServerSocket(CenterServerInfo.PORT);
		} catch (IOException e) {
			System.err.println("Could not listen on port: 4444.");
			e.printStackTrace();
			System.exit(1);
		}

		Socket clientSocket = null;
		try {
			clientSocket = serverSocket.accept();
			System.out.println("连上server");
		} catch (IOException e) {
			System.err.println("Accept failed.");
			System.exit(1);
		}
		// while (true) {
		PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
		BufferedReader in = new BufferedReader(new InputStreamReader(
				clientSocket.getInputStream()));
		StringBuilder sb = new StringBuilder();
		String inputLine, outputLine;
		System.out.println("TEST");
		if ((inputLine = in.readLine()) != null)
			sb.append(inputLine);
		System.out.println("Server receives: " + sb.toString());
		out.close();
		in.close();
		// }
		// clientSocket.close();
		// serverSocket.close();
	}

}
