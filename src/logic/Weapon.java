package logic;

/**
 * Creates a weapon object that can attack.
 * 
 * @author malinocr
 *
 */
public class Weapon {
	public static final int WEAPON_COOLDOWN = 10;
	Entity[][] board;
	int cooldown;

	public Weapon(Entity[][] board) {
		this.board = board;
		this.cooldown = 0;
	}

	/**
	 * Sets the board of the weapon.
	 * 
	 * @param board
	 */
	public void setBoard(Entity[][] board) {
		this.board = board;
	}

	/**
	 * Attacks in a given direction from a certain point.
	 * 
	 * @param xCoord
	 *            Initial x-coordinate
	 * @param yCoord
	 *            Initial y-coordinate
	 * @param direction
	 *            Direction of attack
	 */
	public void attack(int xCoord, int yCoord, int direction) {
		try {
			if (this.cooldown == 0) {
				if (direction == Level.BOARD_RIGHT) {
					if (this.board[xCoord + 1][yCoord] instanceof Character
							&& !(this.board[xCoord + 1][yCoord] instanceof GoldBag)) {
						this.board[xCoord + 1][yCoord].die();
					} else if (this.board[xCoord + 1][yCoord] == null) {
						this.board[xCoord + 1][yCoord] = new Fireball(
								xCoord + 1, yCoord, direction, this.board);
					}
				}
				if (direction == Level.BOARD_LEFT) {
					if (this.board[xCoord - 1][yCoord] instanceof Character
							&& !(this.board[xCoord - 1][yCoord] instanceof GoldBag)) {
						this.board[xCoord - 1][yCoord].die();
					} else if (this.board[xCoord - 1][yCoord] == null) {
						this.board[xCoord - 1][yCoord] = new Fireball(
								xCoord - 1, yCoord, direction, this.board);
					}
				}
				if (direction == Level.BOARD_UP) {
					if (this.board[xCoord][yCoord - 1] instanceof Character
							&& !(this.board[xCoord][yCoord - 1] instanceof GoldBag)) {
						this.board[xCoord][yCoord - 1].die();
					} else if (this.board[xCoord][yCoord - 1] == null) {
						this.board[xCoord][yCoord - 1] = new Fireball(xCoord,
								yCoord - 1, direction, this.board);
					}
				}
				if (direction == Level.BOARD_DOWN) {
					if (this.board[xCoord][yCoord + 1] instanceof Character
							&& !(this.board[xCoord][yCoord + 1] instanceof GoldBag)) {
						this.board[xCoord][yCoord + 1].die();
					} else if (this.board[xCoord][yCoord + 1] == null) {
						this.board[xCoord][yCoord + 1] = new Fireball(xCoord,
								yCoord + 1, direction, this.board);
					}
				}
				this.cooldown = WEAPON_COOLDOWN;
			}
		} catch (IndexOutOfBoundsException e) {
			return;
		}
	}

	/**
	 * 
	 * @return current cooldown of the weapon
	 */
	public int getCooldown() {
		return this.cooldown;
	}

	/**
	 * assigns the cooldown of the weapon
	 * 
	 * @param cooldown
	 *            cooldown timer for the weapon (tick cycles)
	 */
	public void setCooldown(int cooldown) {
		this.cooldown = cooldown;
	}
}
