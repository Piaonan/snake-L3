package server;

import javax.swing.JButton;
import javax.swing.JFrame;

public class MyFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	JButton button = new JButton("SERVER ON");

	public MyFrame() {
		this.setTitle("SNAKE SERVER");
		this.setSize(300, 150);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.getContentPane().add(button);
		this.setVisible(true);
	}

}