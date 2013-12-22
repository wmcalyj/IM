package im.xmlservice;

import java.io.FileOutputStream;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartDocument;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

public class XMLWriter {
	public XMLEventWriter openFile() throws Exception {
		// Open the file if it's already there
		// create an XMLOutputFactory
		XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
		// create XMLEventWriter
		XMLEventWriter eventWriter = outputFactory
				.createXMLEventWriter(new FileOutputStream(
						XMLConstants.FILE_NAME));
		// create an EventFactory
		XMLEventFactory eventFactory = XMLEventFactory.newInstance();
		XMLEvent end = eventFactory.createDTD("\n");

		// create and write Start Tag
		StartDocument startDocument = eventFactory.createStartDocument();
		eventWriter.add(startDocument);
		eventWriter.add(end);

		StartElement configStartElement = eventFactory.createStartElement("",
				"", XMLConstants.CONFIG);
		eventWriter.add(configStartElement);
		eventWriter.add(end);

		return eventWriter;
	}

	public void closeFile(XMLEventWriter eventWriter) throws XMLStreamException {
		XMLEventFactory eventFactory = XMLEventFactory.newInstance();
		eventWriter.add(eventFactory
				.createEndElement("", "", XMLConstants.ITEM));
		XMLEvent end = eventFactory.createDTD("\n");
		eventWriter.add(end);
		eventWriter.add(eventFactory.createEndDocument());
		eventWriter.close();
	}

	public void saveConfig(XMLEventWriter eventWriter, String username,
			String password) throws Exception {
		XMLEventFactory eventFactory = XMLEventFactory.newInstance();
		XMLEvent end = eventFactory.createDTD("\n");
		// create config open tag
		StartElement configStartElement = eventFactory.createStartElement("",
				"", XMLConstants.ITEM);
		eventWriter.add(configStartElement);
		eventWriter.add(end);
		// Write the different nodes

		createNode(eventWriter, XMLConstants.USER, username);
		createNode(eventWriter, XMLConstants.PASSWORD, password);
		eventWriter.add(eventFactory
				.createEndElement("", "", XMLConstants.ITEM));
		eventWriter.add(end);

	}

	private void createNode(XMLEventWriter eventWriter, String name,
			String value) throws XMLStreamException {

		XMLEventFactory eventFactory = XMLEventFactory.newInstance();
		XMLEvent end = eventFactory.createDTD("\n");
		XMLEvent tab = eventFactory.createDTD("\t");
		// create Start node
		StartElement sElement = eventFactory.createStartElement("", "", name);
		eventWriter.add(tab);
		eventWriter.add(sElement);
		// create Content
		Characters characters = eventFactory.createCharacters(value);
		eventWriter.add(characters);
		// create End node
		EndElement eElement = eventFactory.createEndElement("", "", name);
		eventWriter.add(eElement);
		eventWriter.add(end);

	}
}

