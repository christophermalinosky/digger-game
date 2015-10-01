package logic;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;

/**
 * Class to handle all actions of the emeralds
 * 
 * @author jim
 *
 */
public class Emerald extends Entity implements Collectable {

	/**
	 * @see{Entity(int, int)}
	 */
	public Emerald(int x, int y) {
		super(x, y);
		this.color = Color.green;

		Image image = null;
		try {
			image = ImageIO.read(new File(
					"res/sprites/Collectables/emerald.png"));

		} catch (Exception e) {
			throw new RuntimeException("nope on that image thing");
		}
		this.sprites = new Image[1];
		this.sprites[0] = image;

		this.drawSprite = true;
	}

	@Override
	public void collect() {

	}

	@Override
	void tick() {
		// stay still

	}

	@Override
	public void render(Graphics g) {
		super.render(g);

	}
}
