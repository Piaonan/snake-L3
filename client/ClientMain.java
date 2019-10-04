package client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientMain {
	private static Socket socket;

	public static void main(String[] args) {
		String ip = args.length == 1 ? args[0] : "localhost";
		int port = args.length == 2 ? Integer.parseInt(args[1]) : 1234;

		try {

			socket = new Socket(ip, port);
			System.out.println("Connexion établie...");
			new Thread(new ClientCore(socket)).start();
			
		} catch (UnknownHostException e) {
			System.err.println("Impossible de se connecter à l'adresse: " + ip);
		} catch (IOException e) {
			System.err.println("Aucun server écoute le port: " + port);
		} catch (Exception e) {
			System.out.println("Erreur durant l'initialisation avec le client: " + e.toString());
		}
	}
}
