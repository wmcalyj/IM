package im.front.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class ContactsGui extends JFrame implements ActionListener {
	
	public ContactsGui()
	{
		super("Another GUI");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        add(new JLabel("Empty JFrame"));
        pack();
        setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent paramActionEvent) {
		// TODO Auto-generated method stub

	}

}
