package im.mainclass;

import im.encryptionservice.EncryptionService;
import im.front.gui.LoginGui;
import im.front.messageservice.CenterServerInfo;
import im.front.messageservice.DefaultMessageService;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import zzz.zzz.zzz.FakeWebservice;

public class Run {
	public static void main(String[] args) throws IOException {
		LoginGui login = new LoginGui();
		login.Login();
		EncryptionService es = new EncryptionService();
		es.testEncrypt("���������������������������������������������������������������fuck������������������������");
		FakeWebservice fws = new FakeWebservice();
//		DefaultMessageService ws = new DefaultMessageService();

		// Test client side server

	}

}
