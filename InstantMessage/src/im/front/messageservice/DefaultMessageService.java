package im.front.messageservice;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class DefaultMessageService {
	Socket socket;

	public DefaultMessageService() throws UnknownHostException, IOException {
		socket = new Socket(CenterServerInfo.HOST, CenterServerInfo.PORT);
	}
}
