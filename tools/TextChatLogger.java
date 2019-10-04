package tools;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TextChatLogger implements IChatLogger {
	public File f = null;
	public FileWriter fw = null;

	public TextChatLogger() throws IOException {
		f = new File("logs.txt");
		if (!f.exists())
			f.createNewFile();
		fw = new FileWriter(f, true);
	}

	@Override
	public void clientConnected(String ip) {
		sendBuffer(ip + " vient de se connecter.");
	}

	@Override
	public void clientDisconnected(String ip) {
		sendBuffer(ip + " s'est déconnecté.");
	}

	@Override
	public void systemMessage(String msg) {
		sendBuffer(msg + " : message système.");
	}

	@Override
	public void clientSnakeDie(String ip, int score) {
		sendBuffer(ip + " est mort. il a fait un total de " + score + (score > 0 ? " points." : " point."));
	}

	@Override
	public void clientPlayAgain(String ip) {
		sendBuffer(ip + " rejoue une nouvelle fois.");
	}

	@Override
	public void clientDirection(String ip, Direction d) {
		sendBuffer(ip + " a envoyé la direction : " + d.toString());
	}

	private synchronized void sendBuffer(String s) {
		try {
			fw.write(s + "\r\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void close() {
		try {
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
