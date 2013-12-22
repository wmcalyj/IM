package zzz.zzz.zzz;

import im.front.messageservice.CenterServerInfo;

import java.net.ServerSocket;

/**
 * @author mengchaow
 * 
 */
public class FakeWebservice extends Thread implements Runnable {
	public FakeWebservice() {
		try {
			ServerSocket serverSocket = new ServerSocket(CenterServerInfo.PORT);
			WebServiceRead wsr = new WebServiceRead(serverSocket);
			Thread t1 = new Thread(wsr);
			WebServiceWrite wsw = new WebServiceWrite(serverSocket);
			Thread t2 = new Thread(wsw);
			t1.run();
			t2.run();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}