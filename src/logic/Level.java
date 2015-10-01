package logic;

import gui.GameComponent;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

/**
 * Class that handles level aspects of the game
 * 
 *
 */
public class Level {
	public static final int BOARD_RIGHT = 0;
	public static final int BOARD_UP = 1;
	public static final int BOARD_LEFT = 2;
	public static final int BOARD_DOWN = 3;
	public static final int IDGAF_WAT_DIRECTION = 69;

	int emeraldsLeft;
	Entity[][] board;
	Entity[][] initialBoard;
	GameComponent gameComp;
	public MonsterSpawner nobbinSpawner, hobbinSpawner;

	private boolean isPaused;

	/**
	 * creates a new level object
	 * 
	 * @param board
	 *            game board for that level
	 * @param spawnerX
	 *            x position of the creature spawn object (board coords)
	 * @param spawnerY
	 *            y position of the creature spawn object (board coords)
	 */
	public Level(Entity[][] board, int spawnerX, int spawnerY) {

		this.isPaused = false;

		this.setBoard(board);
		int delay = 200 * (1000 / GameComponent.RENDER_DELAY);
		this.nobbinSpawner = new MonsterSpawner(spawnerX, spawnerY, delay,
				MonsterSpawner.NOBBIN, this);
		this.hobbinSpawner = new MonsterSpawner(spawnerX, spawnerY, delay,
				MonsterSpawner.HOBBIN, this);

	}

	/**
	 * plays the level
	 * 
	 * @param g
	 *            graphics componet for drawing
	 */
	public void play(Graphics g) {
		Rectangle2D.Double background = new Rectangle2D.Double(0, 0, 
				GameComponent.BOARDTILESX*Entity.SHAPE_SIDE_LENGTH,
				GameComponent.BOARDTILESY*Entity.SHAPE_SIDE_LENGTH);
		g.setColor(Color.black);
		((Graphics2D) g).fill(background);
		// System.out.println("loading level " + this.toString());
		for (Entity[] i : this.board) {
			for (Entity e : i) {
				if (e != null) {
					if (!this.isPaused()) {
						e.tick();
						this.nobbinSpawner.update();
						this.hobbinSpawner.update();
					}
					e.render(g);

				}
			}
			// System.out.println(this.board);
		}
		// System.out.println("rendering level ");
	}

	/**
	 * toggles whether or not the game is paused
	 */
	public void togglePaused() {
		this.isPaused = !this.isPaused;
	}

	/**
	 * 
	 * @return the player object in the level
	 */
	public Player getPlayer() {
		for (int i = 0; i < this.board.length; i++) {
			for (int j = 0; j < this.board[0].length; j++) {
				if (this.board[i][j] instanceof Player) {
					return (Player) this.board[i][j];
				}
			}
		}
		return null;
	}

	/**
	 * 
	 * @return number of gems remaining on the level
	 */
	public int countGems() {
		int count = 0;
		for (int i = 0; i < this.board.length; i++) {
			for (int j = 0; j < this.board[0].length; j++) {
				if (this.board[i][j] instanceof Emerald) {
					count++;
				}
			}
		}
		return count;
	}

	/**
	 * Resets the positions of all the characters (except GoldBag) back to their
	 * original positions. Used every time the character dies
	 * 
	 * @author malinocr
	 */
	public void resetBoard() {
		for (int i = 0; i < this.board.length; i++) {
			for (int j = 0; j < this.board[0].length; j++) {
				if (this.board[i][j] instanceof Character
						&& !(this.board[i][j] instanceof GoldBag)) {
					this.board[i][j].die();
				}
			}
		}
		for (int i = 0; i < this.initialBoard.length; i++) {
			for (int j = 0; j < this.initialBoard[0].length; j++) {
				if (this.initialBoard[i][j] instanceof Character
						&& !(this.initialBoard[i][j] instanceof GoldBag)) {
					this.initialBoard[i][j].reset();
					this.board[i][j] = this.initialBoard[i][j];
				}
			}
		}
		this.hobbinSpawner.setCanSpawn(false);
		this.nobbinSpawner.setCanSpawn(false);
	}

	public void setBoard(Entity[][] board) {
		this.board = board;
		this.initialBoard = new Entity[board.length][board[0].length];
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				this.initialBoard[i][j] = board[i][j];
			}
		}
	}

	/**
	 * sets the position of the spawner to given location
	 * 
	 * @param x
	 *            x coord for the spawners
	 * @param y
	 *            y coord for the spawners
	 */
	public void setSpawner(int x, int y) {
		this.hobbinSpawner.setPostion(x, y);
		this.nobbinSpawner.setPostion(x, y);
	}

	/**
	 * 
	 * @return width of the game board
	 */
	public int getWidth() {
		return this.board[0].length;
	}

	/**
	 * 
	 * @return the height of the game board
	 */
	public int getHeight() {
		return this.board.length;
	}

	/**
	 * 
	 * @return board of the level
	 */
	public Entity[][] getBoard() {
		return this.board;
	}

	/**
	 * 
	 * @return true if the game is paused, false otherwise
	 */
	public boolean isPaused() {
		return this.isPaused;
	}
}
