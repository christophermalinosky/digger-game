/**
 * 
 */
package logic;

/**
 * Alternate weapon for the character, basically the brimstone in binding of
 * issac
 * 
 * @author jim
 *
 */
public class Brimstone extends Weapon {
	/**
	 * @param board
	 */
	public Brimstone(Entity[][] board) {
		super(board);

	}

	/*
	 * Creates a new Brimstone weapon
	 * 
	 * @see logic.Weapon#attack(int, int, int)
	 */
	@Override
	public void attack(int xCoord, int yCoord, int direction) {
		switch (direction) {
		case Level.BOARD_DOWN:
			for (int i = yCoord + 1; i < this.board[yCoord].length; i++) {
				this.board[xCoord][i] = new Fireball(xCoord, i, direction,
						this.board);
			}
			break;

		case Level.BOARD_UP:
			for (int i = yCoord - 1; i > 0; i--) {
				this.board[xCoord][i] = new Fireball(xCoord, i, direction,
						this.board);
			}
			break;

		case Level.BOARD_RIGHT:
			for (int i = xCoord + 1; i < this.board.length; i++) {
				this.board[i][yCoord] = new Fireball(i, yCoord, direction,
						this.board);
			}
			break;

		case Level.BOARD_LEFT:
			for (int i = xCoord - 1; i >= 0; i--) {
				this.board[i][yCoord] = new Fireball(i, yCoord, direction,
						this.board);
			}
			break;

		}
	}
}
