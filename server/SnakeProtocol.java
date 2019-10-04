package server;

import java.awt.Point;
import java.util.List;
import java.util.TreeMap;

import tools.Direction;

public interface SnakeProtocol {
	
	//INTPUT SERVER
	default void sendDirection(Direction d) {}
	default void sendPlay() {}
	
	
	//OUTPUT
	default void sendSizeFrame() {}
	default void sendSnakes(TreeMap<Integer, List<Point>>  snakes) {}
	default void sendFruits(List<Fruit> fruits) {}
	default void sendScore(int score) {};
}
