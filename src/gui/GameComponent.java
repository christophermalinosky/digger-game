package gui;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

import logic.Brimstone;
import logic.Level;
import logic.Main;
import logic.Player;
import logic.Weapon;

/**
 * GameComponent is the core of the game, it contains all of the levels as well
 * as 'the loop'
 * 
 * @author jim
 *
 */
@SuppressWarnings("serial")
public class GameComponent extends JComponent implements KeyListener {
	public static final int RENDER_DELAY = 100; // maybe later we can use this
												// to modify fps
	public static final int BOARDTILESX = 15;
	public static final int BOARDTILESY = 10;
	private static final int PLAYER_STARTING_LIVES = 3;

	private ArrayList<File> levelFiles;
	private ArrayList<Level> levels;
	private int currentLevel;
	private int score;

	private boolean[] keysDown = new boolean[65535];
	private Player player;
	private int lives;

	boolean levelIsPaused;

	/**
	 * Thread object to run the render function of the game
	 * 
	 * @author jim
	 *
	 */
	public class gameThread extends Thread {
		GameComponent gc;

		public gameThread(GameComponent gc) {
			this.gc = gc;
		}

		@Override
		public void run() {
			for (;;) {
				GameComponent.this.checkPlayer();
				GameComponent.this.checkGems();
				this.gc.repaint();
				try {
					Thread.sleep(RENDER_DELAY); // prevent dat lag doe
				} catch (InterruptedException e) {
					// lol what even is this ^
				}
			}

		};

	}

	/**
	 * Creates a new instance of GameComponet, populating the level list with
	 * levels from the filepath
	 * 
	 * @param filePath
	 *            string of directory of level files
	 * @author jim
	 */
	public GameComponent() {
		this.levelFiles = new ArrayList<File>();
		this.levels = new ArrayList<Level>();
		this.lives = PLAYER_STARTING_LIVES;
		this.score = 0;

		File f = new File(Main.LEVEL_FILE_PATH);

		for (File tmp : f.listFiles()) {
			if (tmp.isFile())
				this.levelFiles.add(tmp);
		}

		for (File l : this.levelFiles) {
			this.levels.add(LevelBuilder.buildLevel(l));
		}

		// start render thread
		new gameThread(this).start();
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		try {
			this.levelIsPaused = this.levels.get(this.currentLevel).isPaused();

			this.checkPlayer();
			int code = arg0.getKeyCode();
			if (code > 0 && code < this.keysDown.length) {
				this.keysDown[code] = true;
			}
			switch (code) {
			case KeyEvent.VK_O:
				if (this.currentLevel != 0) {
					this.score += this.player.getScore();
					System.out.println("Total Score: " + this.score);
					this.reloadLevel(this.currentLevel);
					this.currentLevel--;
				}
				break;
			case KeyEvent.VK_L:
				if (this.currentLevel < this.levels.size() - 1) {
					this.score += this.player.getScore();
					System.out.println("Total Score: " + this.score);
					this.reloadLevel(this.currentLevel);
					this.currentLevel++;
				}
				break;
			case KeyEvent.VK_D:
				if (!this.levelIsPaused)
					this.player.move(Level.BOARD_RIGHT);
				break;
			case KeyEvent.VK_W:
				if (!this.levelIsPaused)
					this.player.move(Level.BOARD_UP);
				break;
			case KeyEvent.VK_S:
				if (!this.levelIsPaused)
					this.player.move(Level.BOARD_DOWN);
				break;
			case KeyEvent.VK_A:
				if (!this.levelIsPaused)
					this.player.move(Level.BOARD_LEFT);
				break;
			case KeyEvent.VK_SPACE:
				if (!this.levelIsPaused)
					this.player.useWeapon();
				break;
			case KeyEvent.VK_P:
				this.levels.get(this.currentLevel).togglePaused();
				break;
			case KeyEvent.VK_1:
				this.player.setWeapon(new Weapon(this.levels.get(
						this.currentLevel).getBoard()));
				break;
			case KeyEvent.VK_2:
				this.player.setWeapon(new Brimstone(this.levels.get(
						this.currentLevel).getBoard()));
				break;
			}

			// if(code==KeyEvent.VK_O && this.currentLevel != 0){
			// score += player.getScore();
			// System.out.println("Total Score: " + score);
			// reloadLevel(currentLevel);
			// this.currentLevel--;
			// }
			// if(code==KeyEvent.VK_L && this.currentLevel < levels.size()-1){
			// score += player.getScore();
			// System.out.println("Total Score: " + score);
			// reloadLevel(currentLevel);
			// this.currentLevel++;
			// }
			//
			//
			// if(code==KeyEvent.VK_D){
			// this.player.move(Level.BOARD_RIGHT);
			// }
			// if(code==KeyEvent.VK_W){
			// this.player.move(Level.BOARD_UP);
			// }
			// if(code==KeyEvent.VK_S){
			// this.player.move(Level.BOARD_DOWN);
			// }
			// if(code==KeyEvent.VK_A){
			// this.player.move(Level.BOARD_LEFT);
			// }
			// if(code==KeyEvent.VK_SPACE){
			// this.player.useWeapon();
			// }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		int code = arg0.getKeyCode();
		if (code > 0 && code < this.keysDown.length) {
			this.keysDown[code] = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// Ignore this for now; it's for text boxes or similar things.
	}

	public void playGame(Level l, Graphics g) {
		l.play(g);
	}

	public ArrayList<File> getLevels() {
		return this.levelFiles;
	}

	@Override
	public void paintComponent(Graphics g) {
		// System.out.println("lol");
		
	if(keysDown[KeyEvent.VK_D]){
		if (!this.levelIsPaused)
			this.player.move(Level.BOARD_RIGHT);
	}
	if(keysDown[KeyEvent.VK_W]){
		if (!this.levelIsPaused)
			this.player.move(Level.BOARD_UP);
	}
	if(keysDown[KeyEvent.VK_S]){
		if (!this.levelIsPaused)
			this.player.move(Level.BOARD_DOWN);
	}
	if(keysDown[KeyEvent.VK_A]){
		if (!this.levelIsPaused)
			this.player.move(Level.BOARD_LEFT);
	}

		this.levels.get(this.currentLevel).play(g);
		// Potentially draw a background.

		// Draw each entity.
	}

	/**
	 * Checks to see if there is still a player on the map and resets the board
	 * if there is no player. If there is a player, it sets player to the player
	 * on the board.
	 * 
	 * @author malinocr
	 */
	@SuppressWarnings("deprecation")
	public void checkPlayer() {
		if (this.levels.get(this.currentLevel).getPlayer() == null) {
			this.lives--;
			if (this.lives == 0) {
				this.score += this.player.getScore();
				this.levels.get(this.currentLevel).togglePaused();
				StringBuilder message = new StringBuilder();
				message.append("You lose.\n");
				message.append("Your final score was " + this.score + ".\n");
				JOptionPane pane = new JOptionPane(message,
						JOptionPane.INFORMATION_MESSAGE);
				JDialog dialog = pane.createDialog("You lose.");

				dialog.show();
				this.levels.get(this.currentLevel).togglePaused();
				this.reloadGame();
			} else {
				this.levels.get(this.currentLevel).resetBoard();
			}
		} else {
			this.player = this.levels.get(this.currentLevel).getPlayer();
		}
	}

	/**
	 * Checks to see if there are any emeralds on the map and moves to the next
	 * level if there are no more gems.
	 * 
	 * @author malinocr
	 */
	@SuppressWarnings("deprecation")
	public void checkGems() {
		if (this.levels.get(this.currentLevel).countGems() == 0) {
			if (this.currentLevel < this.levels.size() - 1) {
				this.score += this.player.getScore();
				this.levels.get(this.currentLevel).togglePaused();
				StringBuilder message = new StringBuilder();
				message.append("You collected all the emeralds.\n");
				message.append("You move on to the next level!\n");
				message.append("Your total score is " + this.score + ".\n");
				JOptionPane pane = new JOptionPane(message,
						JOptionPane.INFORMATION_MESSAGE);
				JDialog dialog = pane.createDialog("Next Level");
				dialog.show();
				this.levels.get(this.currentLevel).togglePaused();
				this.reloadLevel(this.currentLevel);
				this.currentLevel++;
			} else {
				this.score += this.player.getScore();
				StringBuilder message = new StringBuilder();
				message.append("You win!\n");
				message.append("Your total score is " + this.score + ".\n");
				JOptionPane pane = new JOptionPane(message,
						JOptionPane.INFORMATION_MESSAGE);
				JDialog dialog = pane.createDialog("Winner!");
				dialog.show();
				this.reloadGame();
			}
		}
	}

	/**
	 * Reloads the entire level including characters
	 * 
	 * @param levelNum
	 *            the level index in levelFiles
	 */
	public void reloadLevel(int levelNum) {
		this.levels.set(levelNum,
				LevelBuilder.buildLevel(this.levelFiles.get(levelNum)));
		this.player.setScore(0);
	}

	/**
	 * Releads the entire game including reseting lives and remaking all levels.
	 */
	public void reloadGame() {
		for (int i = 0; i < this.levels.size(); i++) {
			this.reloadLevel(i);
		}
		this.currentLevel = 0;
		this.lives = PLAYER_STARTING_LIVES;
		this.score = 0;
	}

	/**
	 * Shows the controls of the game in a pop-up window.
	 */
	@SuppressWarnings("deprecation")
	public void showControls() {
		this.levels.get(this.currentLevel).togglePaused();
		StringBuilder message = new StringBuilder();
		message.append("Move by using the WASD keys.\n");
		message.append("Go up and down levels with O and L.\n");
		message.append("Pause with P.\n");
		message.append("Use spacebar to fire.\n");
		message.append("Collect all the emeralds to move on to the next level.\n");
		message.append("Complete all 8 levels to win the game!");
		JOptionPane pane = new JOptionPane(message,
				JOptionPane.INFORMATION_MESSAGE);
		JDialog dialog = pane.createDialog("Controls");
		dialog.show();
		this.levels.get(this.currentLevel).togglePaused();
	}
}
