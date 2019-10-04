package server;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import tools.Direction;

public class Snake {
	private final static int INITSIZE = 3;
	private Random r = new Random();
	List<Point> body = new ArrayList<>();
	Direction direction;

	public Snake() {
		switch (r.nextInt(4)) {
		case 0:
			direction = Direction.Up;
			randomPop(r.nextInt(50)*10,500,0,10);
			break;
		case 1:
			direction = Direction.Down;
			randomPop(r.nextInt(50)*10,-10,0,-10);
			break;
		case 2:
			direction = Direction.Left;
			randomPop(500,r.nextInt(50)*10,10,0);
			break;
		case 3:
			direction = Direction.Right;
			randomPop(-10,r.nextInt(50)*10,-10,0);
			break;
		}
	}

	private void randomPop(int startX, int startY, int var1, int var2) {
		for (int i = 0; i < INITSIZE; i++)
			body.add(new Point(startX + var1 * i, startY + var2 * i));
	}

	public void setDirection(Direction direction) {
		if (this.direction == direction.reverseDirection())
			return;
		this.direction = direction;
	}

	public boolean goodMove() {
		Point head = body.get(0);
		Point next = new Point(head.x + direction.getX(), head.y + direction.getY());
		body.add(0, next);
		boolean b;
		if (!(b = Game.willCollideFruit(next)))
			body.remove(body.size() - 1);
		return b;
	}
}
