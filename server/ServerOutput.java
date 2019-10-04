package server;

import java.awt.Point;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.TreeMap;

public class ServerOutput implements SnakeProtocol {
	PrintWriter printW;

	public ServerOutput(OutputStream out) throws IOException {
		printW = new PrintWriter(out, true);
		sendSizeFrame();
	}

	@Override
	public synchronized void sendSnakes(TreeMap<Integer, List<Point>> snakes) {
		printW.println("SNAKES LIST");
		printW.println(snakes.size());
		snakes.entrySet().forEach(entry -> {
			printW.println(entry.getKey().intValue());
			printW.println(entry.getValue().size());
			entry.getValue().forEach(point -> {
				printW.println(point.x);
				printW.println(point.y);
			});
		});
		printW.flush();
	}

	@Override
	public synchronized void sendSizeFrame() {
		printW.println("SIZE FRAME");
		printW.println(Game.WIDTHFRAME);
		printW.println(Game.HEIGHTFRAME);
		printW.flush();
	}
	
	@Override
	public synchronized void sendFruits(List<Fruit> fruits) {
		printW.println("FRUITS LIST");
		printW.println(fruits.size());
		fruits.forEach(fruit -> {
			printW.println(fruit.x);
			printW.println(fruit.y);
		});
		printW.flush();
	}
	
	@Override
	public synchronized void sendScore(int score) {
		printW.println("SCORE");
		printW.println(score);
		printW.flush();
	}

}
