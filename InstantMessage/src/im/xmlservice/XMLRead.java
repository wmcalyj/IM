package im.xmlservice;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

public class XMLRead {
	public Map<String, String> readConfig(String configFile) {
		Map<String, String> items = new HashMap<String, String>();
		// List<XMLModel> items = new ArrayList<XMLModel>();
		try {
			// First, create a new XMLInputFactory
			XMLInputFactory inputFactory = XMLInputFactory.newInstance();
			// Setup a new eventReader
			InputStream in = new FileInputStream(configFile);
			XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
			// read the XML document
			XMLModel item = null;

			while (eventReader.hasNext()) {
				XMLEvent event = eventReader.nextEvent();

				if (event.isStartElement()) {
					StartElement startElement = event.asStartElement();
					// If we have an item element, we create a new item
					if (startElement.getName().getLocalPart()
							.equals(XMLConstants.ITEM)) {
						item = new XMLModel();
					}

					if (event.isStartElement()) {
						if (event.asStartElement().getName().getLocalPart()
								.equals(XMLConstants.USER)) {
							event = eventReader.nextEvent();
							item.setUsername(event.asCharacters().getData());
							continue;
						}
					}
					if (event.asStartElement().getName().getLocalPart()
							.equals(XMLConstants.PASSWORD)) {
						event = eventReader.nextEvent();
						item.setPassword(event.asCharacters().getData());
						continue;
					}
				}
				// If we reach the end of an item element, we add it to the list
				if (event.isEndElement()) {
					EndElement endElement = event.asEndElement();
					if (endElement.getName().getLocalPart()
							.equals(XMLConstants.ITEM)) {
						items.put(item.getUsername(), item.getPassword());
					}
				}

			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}
		return items;
	}
}

