/**
 * 
 */
package logic;

/**
 * class that spawns monsters for the digger game
 * 
 * @author jim
 *
 */
public class MonsterSpawner {
	public static final int HOBBIN = 1;
	public static final int NOBBIN = 2;

	int x, y, delayInMilisconds, creatureType;
	public Entity[][] board;
	Level l;

	/**
	 * Creates a new MonsterSpawner object for a position (x,y)
	 * 
	 * @param x
	 *            X coordinate to spawn new monsters
	 * @param y
	 *            Y coordinate to spawn new monsters
	 * @param delayInMiliseconds
	 *            amount of time after monster death to spawn a new monster
	 */
	public MonsterSpawner(int x, int y, int delayInMiliseconds,
			int creatureType, Level l) {
		this.x = x;
		this.y = y;
		this.delayInMilisconds = delayInMiliseconds;
		this.creatureType = creatureType;
		this.board = l.getBoard();
		this.l = l;

		this.cooldown = this.delayInMilisconds;
	}

	/**
	 * spawns a new creature at the position of the spawner
	 * 
	 * @throws NoSuchCreatureException
	 */

	private void spawn() throws NoSuchCreatureException {

		switch (this.creatureType) {
		case HOBBIN:
			this.board[this.x][this.y] = new Hobbin(this.x, this.y, this.l);
			break;
		case NOBBIN:
			this.board[this.x][this.y] = new Nobbin(this.x, this.y, this.l);
			break;

		default:
			throw new NoSuchCreatureException();

		}

	}

	/**
	 * custom exception
	 * 
	 * @author jim
	 *
	 */
	@SuppressWarnings("serial")
	class NoSuchCreatureException extends Exception {

	}

	int cooldown;
	boolean canSpawn;

	/**
	 * Called in the main loop, actually spawns the creatures
	 */
	public void update() {
		if (this.canSpawn) {
			if (this.cooldown <= 0) {
				try {
					this.spawn();
					this.cooldown = this.delayInMilisconds;
					this.canSpawn = false;
				} catch (NoSuchCreatureException e) {

				}
			}

			this.cooldown--;
		}

	}

	/**
	 * 
	 * @param b
	 *            true if the spawner is able to spawn, false otherwise
	 *            (cooldown)
	 */
	public void setCanSpawn(boolean b) {
		this.canSpawn = b;
	}

	/**
	 * sets the current position of the monster spawner
	 * 
	 * @param x
	 *            x coord of the spawner
	 * @param y
	 *            y coord of the spawner
	 */
	public void setPostion(int x, int y) {
		this.x = x;
		this.y = y;
	}

}
