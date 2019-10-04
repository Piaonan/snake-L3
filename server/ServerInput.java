package server;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import tools.Direction;

public class ServerInput {
	SnakeProtocol handler;
	InputStream in;
	boolean stop = false;
	
	public ServerInput(InputStream in, SnakeProtocol handler) throws IOException {
		this.in = in;
		this.handler = handler;
	}
	
	public void doRun() throws IOException {
		try (BufferedReader is = new BufferedReader(new InputStreamReader(in))) {
			while (!stop) {
				String line = is.readLine();
				if(line==null)throw new IOException();
				switch (line) {
					case "LEFT":
						handler.sendDirection(Direction.Left);
						break;
					case "RIGHT":
						handler.sendDirection(Direction.Right);
						break;
					case "UP":
						handler.sendDirection(Direction.Up);
						break;
					case "DOWN":
						handler.sendDirection(Direction.Down);
						break;
					case "ENTER":
						handler.sendPlay();
						break;
					default:
						throw new IOException("Demande incorrecte du client.");
				}
			}
		}
	}
	
}
