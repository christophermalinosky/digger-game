/**
 * 
 */
package logic;

import java.awt.Color;
import java.awt.Image;

/**
 * Creates a fireball that kills enemies.
 * 
 * @author malinocr
 *
 */
public class Fireball extends Character {
	int cooldown = 1;

	public Fireball(int xSlot, int ySlot, int direction, Entity[][] board) {
		super(xSlot, ySlot, "res/sprites/Fireball/","Fireball");

		if (board[xSlot][ySlot] != null) {
			board[xSlot][ySlot].die();
		}

		this.color = Color.pink;
		this.facing = direction;
		this.board = board;

		this.moveStepChange = 13;
	}

	/**
	 * moves the fireball along its merry way
	 */
	public void move() {
		if (this.facing == Level.BOARD_RIGHT) {
			if (this.xCoord == this.board.length - 1) {
				this.die();
			} else if ((this.board[this.xCoord + 1][this.yCoord] instanceof Character)
					&& !(this.board[this.xCoord + 1][this.yCoord] instanceof GoldBag)) {
				this.attack((Character) this.board[this.xCoord + 1][this.yCoord]);
			} else if (this.board[this.xCoord + 1][this.yCoord] == null) {
				super.move(Level.BOARD_RIGHT);
			} else {
				this.die();
			}
		}
		if (this.facing == Level.BOARD_LEFT) {
			if (this.xCoord == 0) {
				this.die();
			} else if ((this.board[this.xCoord - 1][this.yCoord] instanceof Character)
					&& !(this.board[this.xCoord - 1][this.yCoord] instanceof GoldBag)) {
				this.attack((Character) this.board[this.xCoord - 1][this.yCoord]);
			} else if (this.board[this.xCoord - 1][this.yCoord] == null) {
				super.move(Level.BOARD_LEFT);
			} else {
				this.die();
			}
		}
		if (this.facing == Level.BOARD_UP) {
			if (this.yCoord == 0) {
				this.die();
			} else if ((this.board[this.xCoord][this.yCoord - 1] instanceof Character)
					&& !(this.board[this.xCoord][this.yCoord - 1] instanceof GoldBag)) {
				this.attack((Character) this.board[this.xCoord][this.yCoord - 1]);
			} else if (this.board[this.xCoord][this.yCoord - 1] == null) {
				super.move(Level.BOARD_UP);
			} else {
				this.die();
			}
		}
		if (this.facing == Level.BOARD_DOWN) {
			if (this.yCoord == this.board[0].length - 1) {
				this.die();
			} else if ((this.board[this.xCoord][this.yCoord + 1] instanceof Character)
					&& !(this.board[this.xCoord][this.yCoord + 1] instanceof GoldBag)) {
				this.attack((Character) this.board[this.xCoord][this.yCoord + 1]);
			} else if (this.board[this.xCoord][this.yCoord + 1] == null) {
				super.move(Level.BOARD_DOWN);
			} else {
				this.die();
			}
		}
	}

	@Override
	public void tick() {
		super.tick();
		this.checkAttack();
		// if(cooldown == 0){
		if (this.moveStep <= 0) {
			this.move();
			// cooldown = 1;
			// this.moveStep = SHAPE_SIDE_LENGTH + 1;
		} else {
			// cooldown--;
			// this.moveStep -= this.moveStepChange;
		}
	}

	@Override
	public void attack(Character c) {
		c.die();
		this.die();
	}
	
	@Override
	protected void loadSprites(int index, String path, String prefix){
		this.movingUp = new Image[3];
		this.movingDown = new Image[3];
		this.movingLeft = new Image[3];
		this.movingRight = new Image[3];
		super.loadSprites(index-1, path, prefix);
	}

	/**
	 * will attack something if in range
	 */
	public void checkAttack() {
		if (!(this.yCoord >= this.board[0].length - 2 || this.yCoord <= 1
				|| this.xCoord <= 1 || this.xCoord >= this.board.length - 2)) {
			if (this.facing == Level.BOARD_RIGHT) {
				if ((this.board[this.xCoord + 1][this.yCoord] instanceof Character)
						&& !(this.board[this.xCoord + 1][this.yCoord] instanceof GoldBag)) {
					this.attack((Character) this.board[this.xCoord + 1][this.yCoord]);
				}
			}
			if (this.facing == Level.BOARD_LEFT) {
				if ((this.board[this.xCoord - 1][this.yCoord] instanceof Character)
						&& !(this.board[this.xCoord - 1][this.yCoord] instanceof GoldBag)) {
					this.attack((Character) this.board[this.xCoord - 1][this.yCoord]);
				}
			}
			if (this.facing == Level.BOARD_UP) {
				if ((this.board[this.xCoord][this.yCoord - 1] instanceof Character)
						&& !(this.board[this.xCoord][this.yCoord - 1] instanceof GoldBag)) {
					this.attack((Character) this.board[this.xCoord][this.yCoord - 1]);
				}
			}
			if (this.facing == Level.BOARD_DOWN) {
				if ((this.board[this.xCoord][this.yCoord + 1] instanceof Character)
						&& !(this.board[this.xCoord][this.yCoord + 1] instanceof GoldBag)) {
					this.attack((Character) this.board[this.xCoord][this.yCoord + 1]);
				}
			}
		}
	}
}
