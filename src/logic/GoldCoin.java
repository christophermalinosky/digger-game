package logic;

import java.awt.Color;
import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;

public class GoldCoin extends Entity implements Collectable {

	/**
	 * creates a new instance of Goldcoin at the desired coordinates
	 * 
	 * @param xSlot
	 *            X position (board coords)
	 * @param ySlot
	 *            Y position (board coords)
	 */
	public GoldCoin(int xSlot, int ySlot) {
		super(xSlot, ySlot);
		Image image = null;
		try {
			image = ImageIO.read(new File("res/sprites/Collectables/coin.png"));

		} catch (Exception e) {
			throw new RuntimeException("nope on that image thing");
		}
		this.sprites = new Image[1];
		this.sprites[0] = image;

		this.drawSprite = true;
		this.color = Color.cyan;
	}

	@Override
	public void collect() {

	}

	@Override
	void tick() {

	}
}
