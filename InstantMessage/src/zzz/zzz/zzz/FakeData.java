package zzz.zzz.zzz;

import im.contacts.Contacts;
import im.contacts.SingleContact;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author mengchaow
 * 
 *         For testing purpose. Fake all the data we need for now
 */
public class FakeData {
	// Fake Contacts
	public Contacts getContacts() {
		List<SingleContact> listOfContacts = new ArrayList<SingleContact>();
		for (int i = 0; i < 10; i++) {
			SingleContact contact = new SingleContact();
			contact.setName("contact" + i);
			contact.setOnline(true);
			listOfContacts.add(contact);
		}
		Contacts contacts = new Contacts();
		contacts.setContacts(listOfContacts);
		return contacts;
	}
}
