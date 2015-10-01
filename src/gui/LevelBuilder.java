package gui;

import java.io.File;

import logic.Dirt;
import logic.Emerald;
import logic.Entity;
import logic.GoldBag;
import logic.Hobbin;
import logic.Level;
import logic.Nobbin;
import logic.Player;

/**
 * A class that builds Level objects.
 * 
 * @author malinocr
 *
 */

public class LevelBuilder {
	/**
	 * Builds a Level object given the level's file and the GameComponent for
	 * the level.
	 * 
	 * @param file
	 *            The level's file
	 * @param gameComp
	 *            The level's GameComponent
	 * @return The built level object
	 */

	public static Level buildLevel(File file) {
		String[] levelText = TextReader.getText(file);
		Entity[][] board = new Entity[levelText[0].length()][levelText.length];
		Level newLevel = new Level(board, 0, 0);

		int spawnerX = 0;
		int spawnerY = 0;
		for (int j = 0; j < levelText.length; j++) {
			for (int i = 0; i < levelText[0].length(); i++) {
				switch (levelText[j].charAt(i)) {
				case 'E':
					board[i][j] = new Emerald(i, j);
					break;
				case 'G':
					board[i][j] = new GoldBag(i, j);
					break;
				case 'H':
					board[i][j] = new Hobbin(i, j, newLevel);
					break;
				case 'N':
					board[i][j] = new Nobbin(i, j, newLevel);
					break;
				case 'P':
					board[i][j] = new Player(i, j);
					break;
				case 'O':
					board[i][j] = null;
					break;
				case 'X':
					board[i][j] = new Dirt(i, j);
					break;
				case 'S':
					spawnerX = i;
					spawnerY = j;
					break;
				default:
					board[i][j] = null;
					System.out.println("Error: Letter not Understood.");
				}
			}
		}

		newLevel.setBoard(board);
		newLevel.setSpawner(spawnerX, spawnerY);

		for (int j = 0; j < levelText.length; j++) {
			for (int i = 0; i < levelText[0].length(); i++) {
				if (newLevel.getBoard()[i][j] != null) {
					newLevel.getBoard()[i][j].setBoard(board);
				}
			}
		}
		return newLevel;
	}
}
