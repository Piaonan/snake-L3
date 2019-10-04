package client;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.PrintWriter;


public class TheKeyboard extends KeyAdapter {
	PrintWriter printW;


	public TheKeyboard(PrintWriter os) {
		super();
		printW = os;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			printW.println("UP");
			printW.flush();
			break;
		case KeyEvent.VK_DOWN:
			printW.println("DOWN");
			printW.flush();
			break;
		case KeyEvent.VK_LEFT:
			printW.println("LEFT");
			printW.flush();
			break;
		case KeyEvent.VK_RIGHT:
			printW.println("RIGHT");
			printW.flush();
			break;
		case KeyEvent.VK_ENTER:
			printW.println("ENTER");
			printW.flush();
			break;
		}
		
	}	
}
