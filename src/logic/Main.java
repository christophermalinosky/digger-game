package logic;

import gui.GameComponent;
import gui.Menu;

import javax.swing.JFrame;

/**
 * Main class of the program
 *
 */
public class Main {
	public static final String LEVEL_FILE_PATH = "Level Files/"; // used in
																	// GameComponet.java
	/**
	 * @param args
	 */
	public static int WIDTH = 800;
	public static int HEIGHT = 600;

	public static void main(String[] args) {
		GameComponent gc = new GameComponent();
		JFrame frame = new JFrame();
		frame.setSize(800, 600);

		Menu title = Menu.titleMenu(frame, gc);

		title.placeOnFrame(frame);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.addKeyListener(gc);
		frame.setVisible(true);

	}

}
