package logic;

import java.awt.Color;

/**
 * Class that handles actions of the nobbin character (the one that does not
 * dig)
 * 
 * @author jim
 *
 */
public class Nobbin extends Character {
	
	Pathfinder finder;
	MonsterSpawner nobbinSpawner;
	Level l;

	/**
	 * @param xSlot
	 *            x position of the nobbin (Board coords)
	 * @param ySlot
	 *            y position of the nobbin (Board coords)
	 */
	public Nobbin(int xSlot, int ySlot, Level l) {
		super(xSlot, ySlot,"res/sprites/Nobbin/","Nobbin");
		this.board = l.getBoard();
		this.color = Color.CYAN;
		this.nobbinSpawner = l.nobbinSpawner;
		this.moveStepChange = 13;
		this.l = l;
	}

	int cooldown = 5;

	@Override
	void tick() {
		super.tick();
		if (this.finder == null) {
			this.finder = new Pathfinder(this.board);
		}
		int playerX = 0;
		int playerY = 0;

		if (this.cooldown <= 0) {
			for (int i = 0; i < this.board.length; i++) {
				for (int j = 0; j < this.board[0].length; j++) {
					if (this.board[i][j] instanceof Player) {
						playerX = i;
						playerY = j;
					}
				}
			}

			int directionToMove = this.finder.directionToMove(this, playerX,
					playerY, 50);

			if (directionToMove == Level.IDGAF_WAT_DIRECTION)
				directionToMove = (int) (Math.random() * 4) + 1;

			switch (directionToMove) {
			case Level.BOARD_RIGHT:
				if (this.xCoord < this.board.length - 1
						&& (this.board[this.xCoord + 1][this.yCoord] == null || this.board[this.xCoord + 1][this.yCoord] instanceof Player))
					this.move(Level.BOARD_RIGHT);
				break;

			case Level.BOARD_DOWN:
				if (this.yCoord < this.board[0].length - 1
						&& (this.board[this.xCoord][this.yCoord + 1] == null || this.board[this.xCoord][this.yCoord + 1] instanceof Player))
					this.move(Level.BOARD_DOWN);
				break;

			case Level.BOARD_LEFT:
				if (this.xCoord > 0
						&& (this.board[this.xCoord - 1][this.yCoord] == null || this.board[this.xCoord - 1][this.yCoord] instanceof Player))
					this.move(Level.BOARD_LEFT);
				break;

			case Level.BOARD_UP:
				if (this.yCoord > 0
						&& (this.board[this.xCoord][this.yCoord - 1] == null || this.board[this.xCoord][this.yCoord - 1] instanceof Player))
					this.move(Level.BOARD_UP);
				break;
			default:

			}

			this.cooldown = 5;
		} else {
			this.cooldown--;
		}
		if (Math.random() < .004)
			this.transform();
	}

	@Override
	void die() {
		super.die();

		this.nobbinSpawner.setCanSpawn(true);
	}

	@Override
	public void transform() {
		super.die();
		this.board[this.getX()][this.getY()] = new Hobbin(this.getX(),
				this.getY(), this.l);
	}

	@Override
	public void move(int direction) {
		if (direction == Level.BOARD_LEFT && this.xCoord > 0) {
			Entity e = this.board[this.xCoord - 1][this.yCoord];
			if (e instanceof GoldBag) {
				((GoldBag) e).move(direction);
			}
		}
		if (direction == Level.BOARD_RIGHT
				&& this.xCoord < this.board.length - 1) {
			Entity e = this.board[this.xCoord + 1][this.yCoord];
			if (e instanceof GoldBag) {
				((GoldBag) e).move(direction);
			}
		}
		super.move(direction);
	}
}
