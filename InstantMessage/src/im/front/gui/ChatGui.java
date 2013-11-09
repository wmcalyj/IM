package im.front.gui;

import im.user.User;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ChatGui extends JFrame implements ActionListener {
	private User user;

	JFrame frame = new JFrame();
	JPanel win1 = new JPanel();
	JPanel win2 = new JPanel();
	JPanel win3 = new JPanel();
	JTextArea history = new JTextArea();
	JScrollPane scrollHistory = new JScrollPane();
	JTextArea input = new JTextArea();
	JScrollPane scrollInput = new JScrollPane(input);

	JButton submit = new JButton("发送");
	JButton close = new JButton("关闭");

	public ChatGui(String contactName, final User user) {
		setView(contactName);
		this.user = user;
	}

	private void setView(String contactName) {
		submit.addActionListener(this);
		close.addActionListener(this);

		submit.setSize(new Dimension(80, 50));
		close.setSize(new Dimension(80, 50));

		history.setLineWrap(true);
		history.setWrapStyleWord(true);
		history.setAutoscrolls(true);
		history.setEditable(false);
		scrollHistory.setPreferredSize(new Dimension(500, 300));
		scrollHistory.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		input.setLineWrap(true);
		input.setWrapStyleWord(true);
		input.setAutoscrolls(true);
		scrollInput.setPreferredSize(new Dimension(500, 100));
		scrollInput.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		// input.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		win1.add(scrollHistory);
		win2.add(scrollInput);

		win3.setPreferredSize(new Dimension(500, 50));
		// win3.setLayout(new GridLayout(1, 2));
		win3.add(submit, 0);
		win3.add(close, 1);

		frame.setSize(550, 450);
		frame.setLayout(new GridLayout(3, 1));
		frame.add(win1, 0);
		frame.add(win2, 1);
		frame.add(win3, 2);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		pack();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == submit) {
			System.out.println("submit");
		}

	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
