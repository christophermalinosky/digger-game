package logic;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * class to handle player entity
 * 
 *
 */
public class Player extends Character {
	/**
	 * Creates a new player object
	 * 
	 * @param xSlot
	 *            x-Coordinate for player to start at
	 * @param ySlot
	 *            y-coordinate for player to start at
	 */
	Weapon weapon;

	Image[] movingUp, movingDown, movingLeft, movingRight, rest;

	/**
	 * constructs a player at the given position
	 * 
	 * @param xSlot
	 *            x position of the player (board coords)
	 * @param ySlot
	 *            y position of the player (board coords)
	 */
	public Player(int xSlot, int ySlot) {
		super(xSlot, ySlot, "res/sprites/Player/","Tank");
		this.drawSprite = true;
		this.weapon = new Weapon(this.board);
		this.color = Color.blue;
		this.moveStepChange = 10;

		this.movingUp = new Image[4];
		this.movingDown = new Image[4];
		this.movingLeft = new Image[4];
		this.movingRight = new Image[4];

		this.sprites = this.movingDown;

	}

	int score;

	@Override
	public void pickUp(Entity e) {
		if (e instanceof Emerald) {
			e.die();
			this.score += 200;
			System.out.println("Level score: " + this.score);
		} else if (e instanceof GoldCoin) {
			e.die();
			this.score += 100;
			System.out.println("Level score: " + this.score);
		} else if (e instanceof Dirt) {
			e.die();
		} else if (e instanceof GoldBag) {
			((GoldBag) e).move(this.facing);
		}
	}

	/**
	 * @author malinocr
	 */
	@Override
	public void move(int direction) {
		if (this.moveStep <= 0) {
			this.facing = direction;
			switch (this.facing) {
			case Level.BOARD_UP:
				this.sprites = this.movingUp;
				break;
			case Level.BOARD_DOWN:
				this.sprites = this.movingDown;
				break;
			case Level.BOARD_LEFT:
				this.sprites = this.movingLeft;
				break;
			case Level.BOARD_RIGHT:
				this.sprites = this.movingRight;
				break;
			}
			if (direction == Level.BOARD_RIGHT) {
				try {
					Entity toCheck = this.board[this.xCoord + 1][this.yCoord];
					this.pickUp(toCheck);
					Entity toCheck2 = this.board[this.xCoord + 1][this.yCoord];
					if (toCheck2 == null) {
						super.move(Level.BOARD_RIGHT);
					}
				} catch (IndexOutOfBoundsException e) {
					return;
				}
			}
			if (direction == Level.BOARD_LEFT) {
				try {
					Entity toCheck = this.board[this.xCoord - 1][this.yCoord];
					this.pickUp(toCheck);
					Entity toCheck2 = this.board[this.xCoord - 1][this.yCoord];
					if (toCheck2 == null) {
						super.move(Level.BOARD_LEFT);
					}
				} catch (IndexOutOfBoundsException e) {
					return;
				}
			}
			if (direction == Level.BOARD_UP) {
				try {
					Entity toCheck = this.board[this.xCoord][this.yCoord - 1];
					this.pickUp(toCheck);
					Entity toCheck2 = this.board[this.xCoord][this.yCoord - 1];
					if (toCheck2 == null) {
						super.move(Level.BOARD_UP);
					}
				} catch (IndexOutOfBoundsException e) {
					return;
				}
			}
			if (direction == Level.BOARD_DOWN) {
				try {
					Entity toCheck = this.board[this.xCoord][this.yCoord + 1];
					this.pickUp(toCheck);
					Entity toCheck2 = this.board[this.xCoord][this.yCoord + 1];
					if (toCheck2 == null) {
						super.move(Level.BOARD_DOWN);
					}
				} catch (IndexOutOfBoundsException e) {
					return;
				}
			}
		}
	}

	/**
	 * assigns the current weapon of the player
	 * 
	 * @param w
	 *            weapon for the player to use
	 */
	public void setWeapon(Weapon w) {
		this.weapon = w;
	}

	/**
	 * tells the weapon to do its job
	 */
	public void useWeapon() {
		this.weapon.setBoard(this.board);
		this.weapon.attack(this.xCoord, this.yCoord, this.facing);
	}

	@Override
	public void tick() {
		super.tick();
		if (this.moveStep == 0) {
			this.sprites = this.rest;
		}
		if (this.weapon.getCooldown() > 0) {
			this.weapon.setCooldown(this.weapon.getCooldown() - 1);
		}
	}

	/**
	 * sets the score of the player
	 * 
	 * @param score
	 *            new score for the player
	 */
	public void setScore(int score) {
		this.score = score;
	}

	/**
	 * 
	 * @return current score of the player
	 */
	public int getScore() {
		return this.score;
	}
}
