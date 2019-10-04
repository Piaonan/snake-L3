package server;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.TreeMap;

import javax.swing.Timer;

public class Game {
	private static int counter;
	private static List<HandleClient> handleList = new ArrayList<>();
	private static TreeMap<Integer, Snake> snakeList = new TreeMap<>();
	private static List<Fruit> fruits = new ArrayList<>();
	private static Random r = new Random();
	static final int WIDTHMAP = 500, HEIGHTMAP = 500, 
					WIDTHFRAME = 507, HEIGHTFRAME = 530;

	private static Timer inLoop = new Timer(600, new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			handleList.forEach(handler -> handler.move());
			if (counter++ == 10) {
				counter = 0;
				newFruit();
			}
		}
	});

	public static synchronized void newFruit() {
		int x, y;
		do {
			x = r.nextInt(WIDTHMAP/10)*10;
			y = r.nextInt(HEIGHTMAP/10)*10;
		} while (collide(new Point(x, y)));
		fruits.add(new FruitNormal(x, y));
		notifyFruits();
	}

	public static synchronized void deadFruit(Snake s) {
		s.body.forEach(p -> {
			if(fruits.stream().allMatch(fruit -> !fruit.equals(p)))
				fruits.add(new FruitNormal(p.x, p.y));
		});
		notifyFruits();
	}

	private static synchronized boolean collide(Point p) {
		return fruits.stream().anyMatch(fruit -> fruit.equals(p))
				|| snakeList.values().stream().anyMatch(snake -> snake.body.contains(p));
	}

	/* ----------------- */
	public static synchronized void registerClient(int color, Snake s, HandleClient client) {
		handleList.add(client);
		newSnake(color, s);
		client.sendFruits(fruits);
	}

	public static synchronized void newSnake(int color, Snake s) {
		if (snakeList.isEmpty())
			inLoop.start();
		snakeList.put(color, s);
		notifySnakes();
	}

	public static synchronized void unregisterClient(int color, HandleClient client) {
		handleList.remove(client);
		killSnake(color);
	}

	public static synchronized void killSnake(int color) {
		snakeList.remove(color);
		if (snakeList.isEmpty())
			inLoop.stop();
		notifySnakes();
	}
	/* ----------------- */

	public static synchronized void notifySnakes() {
		TreeMap<Integer, List<Point>> tree = new TreeMap<>();
		snakeList.entrySet().forEach(entry -> {
			tree.put(entry.getKey(), entry.getValue().body);
		});
		handleList.forEach(handler -> handler.sendSnakes(tree));
	}

	public static synchronized void notifyFruits() {
		handleList.forEach(handler -> handler.sendFruits(fruits));
	}

	public static synchronized boolean existColor(int color) {
		return snakeList.containsKey(color);
	}

	private static synchronized boolean isOutOfMap(Point p) {
		return p.x < 0 || p.y < 0 || p.x >= WIDTHMAP|| p.y >= HEIGHTMAP;
	}

	public static synchronized boolean willCollide(Snake sk) {
		Point head = sk.body.get(0);
		Point next = new Point(head.x + sk.direction.getX(), head.y + sk.direction.getY());
		return snakeList.values().stream().anyMatch(snake -> snake.body.contains(next)) || isOutOfMap(next);
	}

	public static synchronized boolean willCollideFruit(Point next) {
		for (int i = 0; i < fruits.size(); i++) {
			if (fruits.get(i).equals(next)) {
				fruits.get(i).action();
				fruits.remove(i);
				notifyFruits();
				return true;
			}
		}
		return false;
	}

	public static synchronized void clearAll() {
		inLoop.stop();
		snakeList.clear();
		handleList.forEach(handler -> handler.finish());
		handleList.clear();
		fruits.clear();
	}
}
