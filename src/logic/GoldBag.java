package logic;

import java.awt.Color;
import java.awt.Image;

/**
 * class to handle functions of the gold back in digger
 *
 */
public class GoldBag extends Character {
	boolean isFalling;
	int wiggleStep;
	int blocksFallen;

	/**
	 * creates a new GoldBag object that the desired position
	 * 
	 * @param xSlot
	 *            x position (board coords)
	 * @param ySlot
	 *            y position (board coords)
	 */
	public GoldBag(int xSlot, int ySlot) {
		super(xSlot, ySlot, "res/sprites/GoldBag/", "GoldBag");
		this.color = Color.yellow;

		this.isFalling = false;
		this.wiggleStep = 0;
		this.blocksFallen = 0;

		this.moveStepChange = 20;
	}

	@Override
	protected void loadSprites(int index, String path, String prefix) {
		this.movingUp = new Image[1];
		this.movingDown = new Image[1];
		this.movingLeft = new Image[1];
		this.movingRight = new Image[1];
		super.loadSprites(index - 3, path, prefix);
	}

	/**
	 * 
	 * @return true if the gold block is on top of an entity, false otherwise
	 */
	private boolean checkStable() {
		if (this.yCoord < this.board[0].length - 1) {
			Entity toCheck = this.board[this.xCoord][this.yCoord + 1];
			return !(toCheck == null || (toCheck instanceof Character));
		} else {
			return true;
		}
	}

	@Override
	public void move(int direction) {
		switch (direction) {
		case Level.BOARD_LEFT:
			if (this.xCoord > 0
					&& this.board[this.xCoord - 1][this.yCoord] == null) {
				super.move(direction);
			}
			break;
		case Level.BOARD_RIGHT:
			if (this.xCoord < this.board.length - 1
					&& this.board[this.xCoord + 1][this.yCoord] == null) {
				super.move(direction);
			}
			break;
		case Level.BOARD_DOWN:

			super.move(direction);
			break;
		}
	}

	/**
	 * moves the ball down when nothing is under it
	 * 
	 * @return true of the bag was successful at falling, false otherwise
	 */
	private boolean fall() {
		if (this.board[this.xCoord][this.yCoord + 1] != null) {
			this.board[this.xCoord][this.yCoord + 1].die();
		}

		int prevPos = this.yCoord;
		super.move(Level.BOARD_DOWN);
		return this.yCoord != prevPos;
	}

	@Override
	void tick() {
		if (this.wiggleStep == 0) {
			super.tick();
		}

		if (!this.isFalling) {
			if (!this.checkStable()) {
				this.isFalling = true;
				this.wiggleStep++;
				this.facing = Level.BOARD_LEFT;
			}

		} else {
			if (this.blocksFallen < 1) {
				this.wiggleStep++;
				if (this.wiggleStep >= 12) {
					if (!this.checkStable()) {
						if (this.fall()) {
							this.wiggleStep = 0;
							this.blocksFallen++;
							this.moveStep = 0;
						}
					} else {
						this.wiggleStep = 0;
						this.isFalling = false;
					}
				} else {
					this.moveStep++;
					if (this.moveStep >= 3) {
						this.facing = this.facing == Level.BOARD_LEFT ? Level.BOARD_RIGHT
								: Level.BOARD_LEFT;
						this.moveStep = -3;
					}
				}
			} else {
				// Has fallen before.
				if (!this.checkStable()) {
					if (this.fall()) {
						this.blocksFallen++;
					}
				} else {
					if (this.blocksFallen > 1) {
						// this.die();
						GoldCoin replace = new GoldCoin(this.xCoord,
								this.yCoord);
						replace.setBoard(this.board);
						this.board[this.xCoord][this.yCoord] = replace;
					} else {
						this.isFalling = false;
						this.wiggleStep = 0;
						this.blocksFallen = 0;
					}
				}
			}

		}
	}

}
