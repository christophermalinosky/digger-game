package logic;

import java.awt.Color;

/**
 * class the handles the actions of the hobbin (the one that can dig)
 * 
 * @author jim
 *
 */
public class Hobbin extends Character {
	protected static String PATH = "res/sprites/Hobbin/";
	protected static String PREFIX = "Hobbin";
	
	Pathfinder pathFinder;
	MonsterSpawner hobbinSpawner;
	Level l;

	/**
	 * creates the enemy that can dig
	 * 
	 * @param xSlot
	 *            x position on the gameboard
	 * @param ySlot
	 *            y position on the gameboard
	 */
	public Hobbin(int xSlot, int ySlot, Level l) {
		super(xSlot, ySlot,"res/sprites/Hobbin/","Hobbin");
		this.board = l.getBoard();
		this.pathFinder = new Pathfinder(this.board);
		this.hobbinSpawner = l.hobbinSpawner;
		this.color = Color.DARK_GRAY;
		this.l = l;
		this.moveStepChange = 12;
	}

	int cooldown = 2;

	@Override
	void tick() {
		super.tick();

		if (this.cooldown <= 0) {
			int playerX = 0;
			int playerY = 0;
			for (int i = 0; i < this.board.length; i++) {
				for (int j = 0; j < this.board[0].length; j++) {
					if (this.board[i][j] instanceof Player) {
						playerX = i;
						playerY = j;
					}
				}
			}
			int moveDirection = this.pathFinder.directionToMove(this, playerX,
					playerY, 20);
			boolean canMove = true;
			try {
				switch (moveDirection) {

				case Level.BOARD_DOWN:
					if (this.board[this.xCoord][this.yCoord + 1] instanceof Hobbin
							|| this.board[this.xCoord][this.yCoord + 1] instanceof Nobbin) {
						canMove = false;
					}
				case Level.BOARD_LEFT:
					if (this.board[this.xCoord - 1][this.yCoord] instanceof Hobbin
							|| this.board[this.xCoord][this.yCoord + 1] instanceof Nobbin) {
						canMove = false;
					}

				case Level.BOARD_RIGHT:
					if (this.board[this.xCoord + 1][this.yCoord] instanceof Hobbin
							|| this.board[this.xCoord][this.yCoord + 1] instanceof Nobbin) {
						canMove = false;
					}

				case Level.BOARD_UP:
					if (this.board[this.xCoord][this.yCoord - 1] instanceof Hobbin
							|| this.board[this.xCoord][this.yCoord + 1] instanceof Nobbin) {
						canMove = false;
					}
				}
			} catch (ArrayIndexOutOfBoundsException e) {

			}
			if (canMove)
				this.move(moveDirection);

			if (Math.random() < .004)
				this.transform();

			this.cooldown = 2;
		}

		this.cooldown--;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see logic.Entity#die()
	 */
	@Override
	void die() {
		super.die();

		this.hobbinSpawner.setCanSpawn(true);
	}

	@Override
	public void transform() {
		super.die();
		this.board[this.getX()][this.getY()] = new Nobbin(this.getX(),
				this.getY(), this.l);
	}

	@Override
	public void move(int direction) {
		if (direction == Level.BOARD_LEFT && this.xCoord > 0) {
			Entity e = this.board[this.xCoord - 1][this.yCoord];
			if (e instanceof GoldBag) {
				int prevX = e.xCoord;
				((GoldBag) e).move(direction);
				if (e.xCoord == prevX) {
					return;
				}
			}
		}
		if (direction == Level.BOARD_RIGHT
				&& this.xCoord < this.board.length - 1) {
			Entity e = this.board[this.xCoord + 1][this.yCoord];
			if (e instanceof GoldBag) {
				int prevX = e.xCoord;
				((GoldBag) e).move(direction);
				if (e.xCoord == prevX) {
					return;
				}
			}
		}
		super.move(direction);
	}
}
