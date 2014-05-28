package com.wmcalyj.im.client.gui;

import im.front.messageservice.DefaultMessageService;
import im.user.User;
import im.webservice.messagepackage.MessagePackage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.apache.commons.lang3.StringUtils;

import com.wmcalyj.im.shared.CommunicationService;
import com.wmcalyj.im.shared.data.Message;

public class ChatGui extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private User user;
	private String target;
	private Socket socket;

	JFrame frame = new JFrame();
	JPanel win1 = new JPanel();
	JPanel win2 = new JPanel();
	JPanel win3 = new JPanel();
	JPanel main = new JPanel();
	JTextArea history = new JTextArea();
	JScrollPane scrollHistory = new JScrollPane(history);
	JTextArea input = new JTextArea();
	JScrollPane scrollInput = new JScrollPane(input);

	JButton submit = new JButton("发送");
	JButton close = new JButton("关闭");

	public ChatGui(String contactName, final User user, final Socket socket) {
		setView(contactName);
		this.user = user;
		this.target = contactName;
		this.setSocket(socket);
	}

	private void setView(String contactName) {
		submit.addActionListener(this);
		close.addActionListener(this);

		submit.setPreferredSize(new Dimension(80, 50));
		close.setPreferredSize(new Dimension(80, 50));

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

		win1.setPreferredSize(new Dimension(550, 350));
		win1.add(scrollHistory);
		win2.setPreferredSize(new Dimension(550, 150));
		win2.add(scrollInput);

		win3.setPreferredSize(new Dimension(550, 50));
		// win3.setLayout(new GridLayout(1, 2));
		win3.add(submit, 0);
		win3.add(close, 1);

		main.setLayout(new BorderLayout());
		main.setPreferredSize(new Dimension(550, 550));
		main.add(win1, BorderLayout.NORTH);
		main.add(win2, BorderLayout.CENTER);
		main.add(win3, BorderLayout.SOUTH);
		frame.add(main);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		pack();
		frame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == submit) {
			if (StringUtils.isNotEmpty(input.getText())) {
				sendToService(this.user.getAccountNumber(), this.target,
						input.getText());
				history.append(receiveFromService().getSource() + "\n"
						+ receiveFromService().getText());
				history.append("\n");
			}
		}

	}

	public void sendToService(String source, String target, String text) {
		Message message = new Message(source, target, text);
		socket = getSocket();
		if (socket != null) {
			CommunicationService.sendMessage(socket, message);
		}

		System.out.println("Successfully send message");
	}

	// Testing purpose
	// TODO
	public MessagePackage receiveFromService() {
		MessagePackage message = new MessagePackage();
		if (DefaultMessageService.receive()) {
			System.out.println("Successfully receive message");
			return message;
		}
		System.out.println("Fail to receive message");
		return null;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

}
