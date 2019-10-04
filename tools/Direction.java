package tools;


public enum Direction {
	Up(0, -10), Down(0, 10), Left(-10, 0), Right(10, 0);

	private int x;
	private int y;
	
	Direction(int x, int y) {
		this.x = x;
		this.y = y;
	}	
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public Direction reverseDirection() {
		switch(this) {
			case Right: return Direction.Left;
			case Left: return Direction.Right;
			case Down: return Direction.Up;
			case Up: return Direction.Down;
		}
		return null;
	}
}
