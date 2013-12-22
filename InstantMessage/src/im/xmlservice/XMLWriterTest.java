package im.xmlservice;

import static org.junit.Assert.*;

import org.junit.Test;

public class XMLWriterTest {

	@Test
	public void testSaveConfig() {
		XMLWriter writer = new XMLWriter();
		try {
			writer.saveConfig("USER", "PWD");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
