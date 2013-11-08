package im.front.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginGui extends JFrame implements ActionListener {
	private JFrame frame = new JFrame("IM");
	private JPanel panel = new JPanel();
	private JLabel username = new JLabel("用户名");
	private JLabel password = new JLabel("密");
	private JTextField usernameInput = new JTextField();
	private JPasswordField passwordInput = new JPasswordField();
	private JButton submit = new JButton("确认");
	private JButton cancel = new JButton("取消");

	private void setView() {
		// Add listener to buttons
		submit.addActionListener(this);
		cancel.addActionListener(this);
		// Set label/textbox size
		usernameInput.setPreferredSize(new Dimension(200, 80));
		passwordInput.setPreferredSize(new Dimension(200, 80));
		username.setPreferredSize(new Dimension(120, 80));
		password.setPreferredSize(new Dimension(120, 80));
		submit.setPreferredSize(new Dimension(150, 80));
		cancel.setPreferredSize(new Dimension(150, 80));
		// Set label
		username.setHorizontalAlignment(JLabel.CENTER);
		password.setHorizontalAlignment(JLabel.CENTER);
		username.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		password.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		// Set GridLayout
		panel.setLayout(new GridLayout(3, 2));
		// Add component to panel
		panel.add(username, 0);
		panel.add(usernameInput, 1);
		panel.add(password, 2);
		panel.add(passwordInput, 3);
		panel.add(submit, 4);
		panel.add(cancel, 5);

		frame.setBounds(new Rectangle(500, 300));
		frame.add(panel);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == submit) {
			System.out.println("Submit");
			// TODO
			// Check username & password
			frame.dispose();
			new ContactsGui();
			System.out.println("New Window");
		} else if (e.getSource() == cancel) {
			frame.dispose();
		}

	}

	public void Login() {
		setView();
	}
}
