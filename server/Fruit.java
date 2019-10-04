package server;

import java.awt.Point;

abstract public class Fruit {
	protected int x;
	protected int y;

	public Fruit(int a, int b) {
		x = a;
		y = b;
	}

	public void action() {

	}

	public boolean equals(Point p) {
		return x == p.x && y == p.y;
	}
}
