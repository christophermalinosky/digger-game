package logic;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;

public abstract class Entity {
	public static final int SHAPE_SIDE_LENGTH = 40;
	Entity[][] board;
	int xCoord, yCoord;// Coordinates
	Image[] sprites;
	int startingX;
	int startingY;

	// Testing only: delete later
	boolean drawSprite;
	int imageIndex;

	// Color is for use before implementing sprites
	// Delete once sprites are in use.
	Color color;

	public Entity(int xSlot, int ySlot) {
		this.xCoord = xSlot;
		this.yCoord = ySlot;
		this.startingX = xSlot;
		this.startingY = ySlot;

		this.drawSprite = false;
		this.imageIndex = 0;
		this.sprites = new Image[1];
	}

	/**
	 * Kills the entity
	 * 
	 * @author malinocr
	 */
	void die() {
		this.board[this.xCoord][this.yCoord] = null;
	}

	/**
	 * allows us to loop though array and tick everything
	 */
	abstract void tick();

	/**
	 * Sets the board the entity is on.
	 * 
	 * @param board
	 */
	public void setBoard(Entity[][] board) {
		this.board = board;
	}

	/**
	 * Render the images for all entities
	 * 
	 * @param g
	 *            The graphics to draw on.
	 * @author malinocr
	 */
	public void render(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.translate(this.xCoord * SHAPE_SIDE_LENGTH, this.yCoord
				* SHAPE_SIDE_LENGTH);
		if (!this.drawSprite) {
			g2.setColor(this.color);
			Rectangle2D.Double rect = new Rectangle2D.Double(0, 0,
					SHAPE_SIDE_LENGTH, SHAPE_SIDE_LENGTH);
			g2.fill(rect);
			g2.setColor(Color.black);
			//g2.draw(rect);
		} else {
			g2.drawImage(this.sprites[this.imageIndex], 0, 0, null);
		}
		g2.translate(-this.xCoord * SHAPE_SIDE_LENGTH, -this.yCoord
				* SHAPE_SIDE_LENGTH);

	}

	/**
	 * Sets the current Entity's x-coordinate on the game board
	 * 
	 * @param x
	 *            X-Coordinate on the board
	 */
	public void setX(int x) {
		this.xCoord = x;
	}

	/**
	 * Sets the current Entity's y-coordinate on the game board y Y-Coordinate
	 * on the board
	 * 
	 * @param y
	 */
	public void setY(int y) {
		this.yCoord = y;
	}

	/**
	 * 
	 * @return X-Coordinate of the entity on the board
	 */
	public int getX() {
		return this.xCoord;
	}

	/**
	 * 
	 * @return Y-Coordinate of the entity on the board
	 */
	public int getY() {
		return this.yCoord;
	}

	/**
	 * Returns the entity back to its starting position.
	 * 
	 * @author malinocr
	 */
	public void reset() {
		this.xCoord = this.startingX;
		this.yCoord = this.startingY;
	}
}
