package im.webservice;

import im.xmlservice.DefaultXMLService;

public class Server extends WebServerVariable {
	public Server() {
		if (getUserInfo() == null || getUserInfo().size() == 0) {
			DefaultXMLService xmlService = new DefaultXMLService();
			setUserInfo(xmlService.preLoadXMLFile());
		}
	}

}
