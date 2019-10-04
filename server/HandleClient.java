package server;

import java.awt.Color;
import java.awt.Point;
import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.Random;
import java.util.TreeMap;

import tools.Direction;
import tools.IChatLogger;

public class HandleClient implements Runnable, SnakeProtocol {

	Snake sk = new Snake();
	final Socket s;
	private int score;
	private long cooldown = System.nanoTime();
	private Random r = new Random();
	private IChatLogger logger;
	private ServerInput input;
	private ServerOutput output;
	private Color color;

	private enum ClientState {
		ST_PLAYER, ST_SPECTATOR
	};

	private boolean stop = false;
	ClientState state = ClientState.ST_SPECTATOR;

	public HandleClient(Socket s, IChatLogger logger) throws IOException {
		this.s = s;
		this.logger = logger;
		color = randomColor();
	}

	private Color randomColor() {
		Color c;
		do {
			c = new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256));
		} while (Game.existColor(c.getRGB()));
		return c;
	}

	public void run() {
		try (Socket sRun = s) {
			output = new ServerOutput(sRun.getOutputStream());
			input = new ServerInput(sRun.getInputStream(), this);
			Game.registerClient(color.getRGB(), sk, this);
			state = ClientState.ST_PLAYER;
			input.doRun();
		} catch (IOException ex) {
			if (!stop) {
				Game.unregisterClient(color.getRGB(), this);
				finish();
			}
		}
	}

	private boolean timeToMove() {
		return state == ClientState.ST_PLAYER && System.nanoTime() - cooldown > 77777777;
	}

	@Override
	public void sendSnakes(TreeMap<Integer, List<Point>> snakes) {
		output.sendSnakes(snakes);
	}

	@Override
	public void sendFruits(List<Fruit> fruits) {
		output.sendFruits(fruits);
	}

	@Override
	public void sendDirection(Direction d) {
		if (!timeToMove())
			return;
		cooldown = System.nanoTime();
		// logger.clientDirection(s.toString(), d);
		sk.setDirection(d);
		move();
	}

	public void move() {
		if (state == ClientState.ST_SPECTATOR)
			return;
		
		if (Game.willCollide(sk))
			kill();
		else if (sk.goodMove())
			output.sendScore(score += 10);
		Game.notifySnakes();
	}

	@Override
	public void sendPlay() {
		if (state == ClientState.ST_PLAYER)
			return;
		state = ClientState.ST_PLAYER;
		Game.newSnake(color.getRGB(), sk = new Snake());
		output.sendScore(score = 0);
		logger.clientPlayAgain(s.toString());
	}

	private synchronized void kill() {
		state = ClientState.ST_SPECTATOR;
		Game.killSnake(color.getRGB());
		Game.deadFruit(sk);
		logger.clientSnakeDie(s.toString(), score);
	}

	public synchronized void finish() {
		if (!stop) {
			stop = true;
			try {
				s.close();
			} catch (IOException ex) {
				logger.systemMessage("Problème lors de la fermeture du serveur: " + ex.toString());
				ex.printStackTrace();
			}
			logger.clientDisconnected(s.toString());
		}
	}
}
