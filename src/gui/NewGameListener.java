package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

public class NewGameListener implements ActionListener {
	JFrame frame;
	boolean gameMade;
	GameComponent game;

	/**
	 * constructor that creates a NewGameListener
	 * 
	 * @param frame
	 *            frame to attach listener to
	 * @param game
	 *            component to use
	 */
	NewGameListener(JFrame frame, GameComponent game) {
		super();

		this.game = game;
		this.frame = frame;
		this.gameMade = false;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (this.gameMade == false) {
			this.frame.add(this.game);
			// frame.addKeyListener(game);
			this.frame.setVisible(true);
			this.frame.requestFocus();
			this.gameMade = true;
			this.game.showControls();
		} else {
			this.game.reloadGame();
			this.frame.requestFocus();
		}
		// ready gameComponent
		// game.reloadGame();
		// place GC on frame (Possibly with game.start() instead?):
		// frame.add(game);

	}

}
