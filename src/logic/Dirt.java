package logic;

import java.awt.Color;
import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;

/**
 * class that does all the exciting and perilous things that dirt does
 *
 * @author gilderjw. Created Oct 30, 2014.
 */
public class Dirt extends Entity {

	/**
	 * constructor that creates dirt at the given position
	 * 
	 * @param x
	 *            X position of the dirt (board coords)
	 * @param y
	 *            y position of the dirt (board coorde)
	 */
	public Dirt(int x, int y) {
		super(x, y);
		this.color = new Color(165, 42, 42);
		Image image = null;
		try {
			image = ImageIO.read(new File("res/sprites/Collectables/dirt.png"));

		} catch (Exception e) {
			throw new RuntimeException("nope on that image thing");
		}
		this.sprites = new Image[1];
		this.sprites[0] = image;

		this.drawSprite = true;
	}

	@Override
	void tick() {
		// do nothing, it is dirt

	}
}
