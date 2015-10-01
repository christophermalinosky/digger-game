package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * 
 * Class that holds the buttons at the bottom of the screen
 *
 */
public class Menu {
	private JPanel panel;
	private ArrayList<JButton> buttons;

	String position;

	/**
	 * Default constructor that makes a new Menu object
	 */
	public Menu() {
		this.panel = new JPanel();
		this.buttons = new ArrayList<JButton>();

		this.position = BorderLayout.SOUTH;
	}

	/**
	 * Creates a Menu object at a certain position on the frame
	 * 
	 * @param position
	 *            Frame position to put the menu
	 */
	public Menu(String position) {
		this.panel = new JPanel();
		this.buttons = new ArrayList<JButton>();

		this.position = position;
	}

	/**
	 * adds a button to the menu at the end
	 * 
	 * @param button
	 *            button to add
	 */
	public void addButton(JButton button) {
		this.buttons.add(button);
		this.panel.add(button, this.position);
	}

	/**
	 * Adds a button to the menu at a desired position
	 * 
	 * @param button
	 *            button to add
	 * @param buttonPosition
	 *            position to put button
	 */
	public void addButton(JButton button, String buttonPosition) {
		this.buttons.add(button);
		this.panel.add(button, buttonPosition);
	}

	/**
	 * 
	 * @return the panel that the Menu uses
	 */
	public JPanel getPanel() {
		return this.panel;
	}

	/**
	 * 
	 * @return list of buttons in the menu
	 */
	public ArrayList<JButton> getButtons() {
		return this.buttons;
	}

	/**
	 * 
	 * @return position of the menu
	 */
	public String getPosition() {
		return this.position;
	}

	/**
	 * \ sets the position of the menu
	 * 
	 * @param position
	 *            where to put the menu
	 */
	public void setPosition(String position) {
		this.position = position;
	}

	/**
	 * adds the menu to the desired frame
	 * 
	 * @param frame
	 *            frame to add menu to
	 */
	public void placeOnFrame(JFrame frame) {
		frame.add(this.panel, this.position);

	}

	/**
	 * Static method that creates and returns a new titleMenu
	 * 
	 * @param frame
	 *            frame to add the title menu to
	 * @param game
	 *            Main game component
	 * @return the title menu object
	 */
	public static Menu titleMenu(JFrame frame, GameComponent game) {
		Menu title = new Menu();
		// title.panel.setLayout(new GridLayout(3, 1));
		JButton newGame = new JButton("New Game");
		JButton quit = new JButton("Quit");

		quit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}

		});

		newGame.addActionListener(new NewGameListener(frame, game));

		title.addButton(newGame);
		title.addButton(quit);

		return title;
	}
}
