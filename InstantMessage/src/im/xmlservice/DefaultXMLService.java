package im.xmlservice;

import java.util.HashMap;
import java.util.Map;

import javax.xml.stream.XMLEventWriter;

import org.apache.commons.lang3.StringUtils;

public class DefaultXMLService {
	public boolean createNewEntry(Map<String, String> userInfo) {
		XMLWriter writer = new XMLWriter();
		try {
			final XMLEventWriter eventWriter = writer.openFile();
			for (String user : userInfo.keySet()) {
				writer.saveConfig(eventWriter, user, userInfo.get(user));
			}
			writer.closeFile(eventWriter);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean allowLogin(Map<String, String> userInfo, String username,
			String password) {
		String savedPassword = userInfo.get(username);
		if (StringUtils.isEmpty(savedPassword))
			return false;
		if (savedPassword.equals(password))
			return true;
		return false;
	}

	public Map<String, String> preLoadXMLFile() {
		Map<String, String> map = new HashMap<String, String>();
		XMLRead reader = new XMLRead();
		map.putAll(reader.readConfig(XMLConstants.FILE_NAME));
		return map;
	}
}


