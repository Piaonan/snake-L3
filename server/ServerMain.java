package server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JFrame;

public class ServerMain {
	static ServerCore server;

	public static void main(String[] args) {
		MyFrame f = new MyFrame();
		start(args);
		f.button.addActionListener(new ActionListener() {
			public synchronized void actionPerformed(ActionEvent e) {
				f.button.setEnabled(false);
				if (!server.stop) {
					server.finish();
					f.button.setText("SERVER OFF");
					f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				} else {
					f.button.setText("SERVER ON");
					f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
					start(args);
				}
				f.button.setEnabled(true);
			}
		});

	}

	private static void start(String[] args) {
		int port = args.length == 1 ? Integer.parseInt(args[0]) : 1234;
		try {
			server = new ServerCore(port);
		} catch (IOException e) {
			System.err.println("Erreur durant l'initialisation avec le serveur: " + e.toString());
		}
	}

}
