package im.mainclass;

import im.encryptionservice.EncryptionService;
import im.front.gui.LoginGui;
import im.front.messageservice.CenterServerInfo;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Run {
	public static void main(String[] args) throws IOException {
		LoginGui login = new LoginGui();
		login.Login();
		EncryptionService es = new EncryptionService();
		es.testEncrypt("���������������������������������������������������������������fuck������������������������");

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
			System.out.println("������server");
		} catch (IOException e) {
			System.err.println("Accept failed.");
			System.exit(1);
		}
		DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
		DataInputStream input = new DataInputStream(clientSocket.getInputStream());
		//while (true) {
			System.out.println("TEST");
			System.out.println(input.read());
			
			out.close();
			input.close();
		//}
		// clientSocket.close();
		// serverSocket.close();\
		
	}

}
