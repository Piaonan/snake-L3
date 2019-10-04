package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JFrame;

public class ClientCore implements Runnable {

	private final Socket s;
	private InputStream inputStream;
	private PrintWriter printW;
	private BufferedReader bufferedR;
	private static int width, height;
	private static JFrame frame = new JFrame("Jeu du Snake - SCORE : 0");

	public ClientCore(Socket sMain) {
		s = sMain;
	}
	
	@Override
	public void run() {
		try {
			printW = new PrintWriter(s.getOutputStream());
			inputStream = s.getInputStream();
			bufferedR = new BufferedReader(new InputStreamReader(inputStream));
			
			String line = bufferedR.readLine();
			System.out.println("mot reçu : " + line);

			width = Integer.parseInt(bufferedR.readLine());
			height = Integer.parseInt(bufferedR.readLine());
			initialize();
		} catch (IOException e) {
			System.err.println("Aucune réponse de la part du serveur: " + e.toString());
		}
	}	

	private void initialize() throws IOException {
		
		Look look = new Look(width, height, new TheKeyboard(printW));
		
		frame.setContentPane(look);
		frame.setSize(look.getSize());
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		ClientInput input = new ClientInput(bufferedR, look);
		input.doRun();
	}
	
	public static void disposeFrame() {
		frame.dispose();
	}
	
	public static void setScore(int score) {
		frame.setTitle("Jeu du Snake - SCORE : "+score);
	}

}
