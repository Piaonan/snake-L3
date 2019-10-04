package client;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import javax.swing.JComponent;

public class Look extends JComponent {
	private int width = 100;
	private int height = 100;
	private TheKeyboard press;
	private static final long serialVersionUID = 1L;
	private TreeMap<Integer, List<Point>> snakes = new TreeMap<>();
	private List<Point> fruits = new ArrayList<>();

	public Look(int w, int h, TheKeyboard key) {
		super();
		press = key;
		setSizeGround(w, h);
		setWindow();
	}

	private void setWindow() {
		setOpaque(true);
		setSize(width, height);
		addKeyListener(press);
		setFocusable(true);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.gray);
		g.fillRect(0, 0, width, height);

		snakes.entrySet().forEach(entry -> {
			g.setColor(new Color(entry.getKey()));
			entry.getValue().forEach(p -> g.fillOval(p.x, p.y, 10, 10));
			Point head = entry.getValue().get(0);
			g.setColor(Color.BLACK);
			g.fillOval(head.x, head.y, 5, 5);
		});

		g.setColor(Color.PINK);
		fruits.forEach(fruit -> g.fillRect(fruit.x+2, fruit.y+2, 7, 7));
	}

	void setSizeGround(int w, int h) {
		width = w;
		height = h;
	}

	void setSnakesList(TreeMap<Integer, List<Point>> l) {
		snakes = l;
	}

	void setFruitsList(List<Point> l) {
		fruits = l;
	}

	void setScore(int score) {
		ClientCore.setScore(score);
	}

}
