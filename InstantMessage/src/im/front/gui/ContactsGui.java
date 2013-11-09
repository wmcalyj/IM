package im.front.gui;

import im.contacts.Contacts;
import im.user.User;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import zzz.zzz.zzz.FakeData;

public class ContactsGui extends JFrame implements ActionListener {

	private JFrame frame = new JFrame("Contacts");
	private JScrollPane scrollPane = new JScrollPane();
	private User user;

	public ContactsGui(final User user) {
		this.user = user;
		setView();
		
	}

	public void setView()
	{
		FakeData data = new FakeData();
		Contacts contacts = data.getContacts();
		int size = contacts.getContacts().size(); 
		scrollPane.setSize(new Dimension(150, 80));

		JPanel panel = new JPanel();
		scrollPane.setViewportView(panel);
		panel.setLayout(new GridLayout(0, 1));
		// TODO
		// Sort according to Online or Not
		for (int i = 0; i < size; i++) {
			JLabel label = new JLabel(contacts.getContacts().get(i).getName());
			label.setPreferredSize(new Dimension(150, 90));
			label.setBorder(BorderFactory.createLineBorder(Color.YELLOW));
			label.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					if (e.getClickCount() == 2) {
						// TODO
						// Pop up the chat window
						ChatGui chat = new ChatGui(((JLabel)e.getSource()).getText(), user);
						System.out.println(((JLabel)e.getSource()).getText());
					}
				}
			});
			panel.add(label);
		}
		frame.add(scrollPane);
		frame.setSize(new Dimension(500, 300));
		frame.setVisible(true);
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println(e.getSource());
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
