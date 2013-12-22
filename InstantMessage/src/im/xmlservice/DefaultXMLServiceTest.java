package im.xmlservice;

import java.util.HashMap;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;

public class DefaultXMLServiceTest {

	@Test
	public void testCreateNewEntry() {
		Map<String, String> map = new HashMap<String, String>();
		for (int i = 0; i < 15; i++) {
			map.put("USER" + i, "PASSWORD" + i);
		}
		DefaultXMLService service = new DefaultXMLService();

		service.createNewEntry(map);

	}

	@Ignore
	public void testAllowLogin() {

	}

	@Test
	public void testPreLoadXMLFile() {
		System.out.println("Reading Test");
		DefaultXMLService service = new DefaultXMLService();
		Map<String, String> map = service.preLoadXMLFile();
		for (String user : map.keySet()) {
			System.out
					.println("user: " + user + "\tpassword: " + map.get(user));
		}
		System.out.println("Reading Test End");
	}

}
