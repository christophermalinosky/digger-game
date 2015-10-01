package logic;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public abstract class Character extends Entity{
	double moveStep;
	int facing;
	int moveStepChange;
	
	Image[] movingUp, movingDown, movingLeft, movingRight;
	
	Image[] sprites;
	
	/**
	 * @param xSlot
	 * @param ySlot
	 */
	public Character(int xSlot, int ySlot, String path, String prefix) {
		super(xSlot, ySlot);
		moveStep = 0;
		facing = 0;
		moveStepChange = 10;
		this.movingUp = new Image[4];
		this.movingDown = new Image[4];
		this.movingLeft = new Image[4];
		this.movingRight = new Image[4];
		
		this.sprites = movingDown;
		
		if (!path.equals("")) {
			this.loadSprites(4, path, prefix);
		} else {
			this.drawSprite = false;
		}
		
		
	}

	/**
	 * Sets the players coordinate on the board to 1+ the direction provided
	 * @param direction direction to move, use constants in Level. (BOARD_UP, BOARD_DOWN, BOARD_LEFT, BOARD_RIGHT)
	 * 
	 * @author caudeljn
	 */
	public void move(int direction){
		
		if (moveStep <= 0) {
			moveStep = SHAPE_SIDE_LENGTH+1;
			switch (direction) {

			case Level.BOARD_LEFT:
				if (this.xCoord > 0 && true) {
					if(board[this.xCoord-1][this.yCoord] != null){
						board[this.xCoord-1][this.yCoord].die();
					}
					board[this.xCoord-1][this.yCoord]=board[this.xCoord][this.yCoord];
					this.board[this.xCoord][this.yCoord]=null;
					this.xCoord--;
				}
				facing = direction;
				break;
			case Level.BOARD_UP:
				if (this.yCoord > 0 && true) {
					if(board[this.xCoord][this.yCoord-1] != null){
						board[this.xCoord][this.yCoord-1].die();
					}
					board[this.xCoord][this.yCoord-1]=board[this.xCoord][this.yCoord];
					this.board[this.xCoord][this.yCoord]=null;
					this.yCoord--;
				}
				facing = direction;
				break;
			case Level.BOARD_RIGHT:
				if (this.xCoord < board.length - 1 && true) {
					if(board[this.xCoord+1][this.yCoord] != null){
						board[this.xCoord+1][this.yCoord].die();
					}
					board[this.xCoord+1][this.yCoord]=board[this.xCoord][this.yCoord];
					this.board[this.xCoord][this.yCoord]=null;
					this.xCoord++;
				}
				facing = direction;
				break;
			case Level.BOARD_DOWN:
				if (this.yCoord < board[0].length - 1 && true) {
					board[this.xCoord][this.yCoord+1]=board[this.xCoord][this.yCoord];
					this.board[this.xCoord][this.yCoord]=null;
					this.yCoord++;
				}
				facing = direction;
				break;
			default:
				break;
			}
		}
	
	}
	
	/**
	 * Method to make the current entity pick up an object
	 * @param e	entity to pick up
	 */
	public void pickUp(Entity e){
	}
	
	/**
	 * Method to handle transformation between enemies, not implemented in player class
	 */
	public void transform(){
		
	}
	
	/**
	 * Handles attacking another Character
	 * @param c Character to attack
	 */
	public void attack(Character c){
		
	}
	/**
	 * Overrides Entity render, to take into account movement frames.
	 * Besides that, works the same as {@link Entity#render(Graphics)}
	 */
	@Override
	public void render(Graphics g){
		this.render(g, facing);
	}
	
	/**
	 * 
	 * @param g
	 * @param direction
	 * 
	 * @author caudeljn
	 */
	private void render(Graphics g, int direction){
		
		double xFix = 0;
		double yFix = 0;
		switch(facing){
		case Level.BOARD_LEFT:
			sprites = movingLeft;
			xFix = moveStep;
			break;
		case Level.BOARD_UP:
			sprites = movingUp;
			yFix = moveStep;
			break;
		case Level.BOARD_RIGHT:
			sprites = movingRight;
			xFix = -moveStep;
			break;
		case Level.BOARD_DOWN:
			sprites = movingDown;
			yFix = -moveStep;
			break;
		default:
			break;
		}
			
//			String data = "Character rendered at position (%f, %f), facing %d.\nmoveStep = %f\n";
//			if (this.getClass()==Player.class) {
//				System.out.printf(data, xCoord * SHAPE_SIDE_LENGTH + xFix, yCoord
//						* SHAPE_SIDE_LENGTH + yFix, facing, moveStep);
//			}

		Graphics2D g2 = (Graphics2D) g;
		g2.translate(xCoord * SHAPE_SIDE_LENGTH + xFix, yCoord
				* SHAPE_SIDE_LENGTH + yFix);
		if (!drawSprite) {
			g2.setColor(color);
			Rectangle2D.Double rect = new Rectangle2D.Double(0, 0,
					SHAPE_SIDE_LENGTH, SHAPE_SIDE_LENGTH);
			g2.fill(rect);
			g2.setColor(Color.black);
			g2.draw(rect);
		} else {
			g2.drawImage(sprites[imageIndex], 0, 0, null);
		}
		g2.translate(-xCoord * SHAPE_SIDE_LENGTH-xFix, -yCoord* SHAPE_SIDE_LENGTH-yFix);

	}
	
	/**
	 * Loads sprites from the given path with the given prefix.
	 * @param path
	 * @param prefix
	 */
	protected void loadSprites(int size, String path, String prefix){
		this.drawSprite = true;
		for(int i = 1; i <= size; i++){
			String totalPath = path+prefix;
			String suffix = Integer.toString(i) + ".png";
			BufferedImage up = null;
			BufferedImage down = null;
			BufferedImage left = null;
			BufferedImage right = null;
			try {
			    up = ImageIO.read(new File(totalPath + "Up" + suffix));
			    down = ImageIO.read(new File(totalPath + "Down" + suffix));
			    left = ImageIO.read(new File(totalPath + "Left" + suffix));
			    right = ImageIO.read(new File(totalPath + "Right" + suffix));
			} catch (IOException e) {
				System.out.println("File not found");
				throw new RuntimeException();
			}
			this.movingUp[i-1] = up;
			this.movingDown[i-1] = down;
			this.movingLeft[i-1] = left;
			this.movingRight[i-1]= right;
		}
	}
	
	@Override 
	void tick(){
		if(this.moveStep>0){
			moveStep-=moveStepChange;
			this.imageIndex = (this.imageIndex+1)%this.sprites.length;
			if(moveStep<0){
				moveStep = 0;
			}
		}
	}
}
