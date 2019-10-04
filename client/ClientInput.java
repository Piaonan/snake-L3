package client;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class ClientInput {
	private BufferedReader bufferedR;

	private boolean stop;
	private Look look;

	public ClientInput(BufferedReader bR, Look l) throws IOException {
		bufferedR = bR;
		look = l;
	}

	public void doRun() throws IOException {
		int snakeNumber, snakeSize, fruitsNumber, score;

		while (!stop) {
			String line = bufferedR.readLine();
			// System.out.println("Reçu par le client : " + line);
			if (line == null) {
				ClientCore.disposeFrame();
				throw new IOException();
			}

			switch (line) {

			case "SNAKES LIST":
				TreeMap<Integer, List<Point>> snakes = new TreeMap<>();
				int key;
				List<Point> sList = new ArrayList<>();
				snakeNumber = getValue();
				for (int i = 0; i < snakeNumber; i++) {
					key = getValue();
					snakeSize = getValue();
					for (int j = 0; j < snakeSize; j++)
						sList.add(new Point(getValue(), getValue()));
					snakes.put(key, sList);
					sList = new ArrayList<>();
				}
				look.setSnakesList(snakes);
				look.repaint();
				break;
			case "FRUITS LIST":
				List<Point> fList = new ArrayList<>();
				fruitsNumber = getValue();
				for (int i = 0; i < fruitsNumber; i++)
					fList.add(new Point(getValue(), getValue()));
				look.setFruitsList(fList);
				look.repaint();
				break;
			case "SCORE":
				score = getValue();
				look.setScore(score);
				break;
			default:
				break;
			}
		}

	}

	private int getValue() throws NumberFormatException, IOException {
		return Integer.parseInt(bufferedR.readLine());
	}
}
