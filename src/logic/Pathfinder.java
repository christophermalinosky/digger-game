package logic;

import java.util.ArrayList;

/**
 * Handles pathfinding of nobbin and hobbins
 *
 * @author gilderjw. Created Nov 5, 2014.
 */
public class Pathfinder {

	Node goal;
	Entity[][] board;

	/**
	 * Creates a new Pathfinder object
	 * 
	 * @param board
	 *            game board to be path finding on
	 */
	public Pathfinder(Entity[][] board) {
		this.board = board;
	}

	/**
	 * 
	 * @param c
	 *            character to pathfind with
	 * @param xf
	 *            final postion to get to (x)
	 * @param yf
	 *            final postion to get to (y)
	 * @param vision
	 *            1 - maximum movements to produce a valid path
	 * @return most efficient direction to move
	 */
	public int directionToMove(Character c, int xf, int yf, int vision) {
		Node firstStep = null;

		try {
			if (c instanceof Hobbin) {
				firstStep = this.getHobbinPath(c.getX(), c.getY(), xf, yf,
						vision).get(0);
			}

			if (c instanceof Nobbin)
				firstStep = this.getNobbinPath(c.getX(), c.getY(), xf, yf,
						vision).get(0);

		} catch (NullPointerException e) {
			return Level.IDGAF_WAT_DIRECTION;
		}

		if (firstStep.getX() < c.getX())
			return Level.BOARD_LEFT;

		if (firstStep.getX() > c.getX())
			return Level.BOARD_RIGHT;

		if (firstStep.getY() > c.getY())
			return Level.BOARD_DOWN;

		if (firstStep.getY() < c.getY())
			return Level.BOARD_UP;

		return Level.IDGAF_WAT_DIRECTION;
	}

	/**
	 * 
	 * returns the most efficient path from one position to another uses
	 * Manhattan distance
	 *
	 * @param x0
	 *            starting x position
	 * @param y0
	 *            starting y position
	 * @param xf
	 *            final x position
	 * @param yf
	 *            final y position
	 * @param vision
	 *            1 - maximum movements
	 * @return array of points to travel to
	 */
	ArrayList<Node> getHobbinPath(int x0, int y0, int xf, int yf, int vision) {
		ArrayList<Node> openList = new ArrayList<Node>();
		ArrayList<Node> closedList = new ArrayList<Node>();

		boolean skip = false;

		this.goal = new Node(xf, yf, null);

		openList.add(new Node(x0, y0, null));

		while (openList.size() >= 0) {

			ArrayList<Node> yolo = new ArrayList<Pathfinder.Node>(openList);// made
																			// to
																			// prevent
																			// concurrent
																			// modification
			for (Node n : yolo) {
				if (vision <= 0) {
					skip = true;
					break;
				}
				if (n.equals(this.goal)) {
					return n.getAncestry(n);
				}
				Node[] tmp = new Node[4];

				tmp[0] = (new Node(n.getX() + 1, n.getY(), n));// right
				tmp[1] = (new Node(n.getX() - 1, n.getY(), n));// left
				tmp[2] = (new Node(n.getX(), n.getY() + 1, n));// up
				tmp[3] = (new Node(n.getX(), n.getY() - 1, n));// down

				for (Node q : tmp) { // has node already been checked?
					boolean inClosedList = false;
					for (Node w : closedList) {
						if (w.equals(q)) {
							inClosedList = true;
						}
					}
					for (Node w : openList) {
						if (w.equals(q)) {
							inClosedList = true;
						}
					}
					if (!inClosedList) {
						try {
							if (this.board[q.getX()][q.getY()] instanceof GoldBag) {
							} else {
								openList.add(q);
							}

						} catch (Exception e) {

						}

					}
				}

				openList.remove(n);
				closedList.add(n);

			}
			if (skip)
				break;

			vision--;
		}

		return null;
	}

	/**
	 * 
	 * returns the most efficient path from one position to another uses
	 * Manhattan distance
	 *
	 * @param x0
	 *            starting x position
	 * @param y0
	 *            starting y position
	 * @param xf
	 *            final x position
	 * @param yf
	 *            final y position
	 * @param vision
	 *            1 - maximum movements
	 * @return array of points to travel to
	 */
	ArrayList<Node> getNobbinPath(int x0, int y0, int xf, int yf, int vision) {
		ArrayList<Node> openList = new ArrayList<Node>();
		ArrayList<Node> closedList = new ArrayList<Node>();

		boolean skip = false;

		this.goal = new Node(xf, yf, null);

		openList.add(new Node(x0, y0, null));

		while (openList.size() > 0) {

			ArrayList<Node> yolo = new ArrayList<Pathfinder.Node>(openList);// made
																			// to
																			// prevent
																			// concurrent
																			// modification
			for (Node n : yolo) {
				if (vision <= 0) {
					skip = true;
					break;
				}
				if (n.equals(this.goal)) {
					return n.getAncestry(n);
				}
				Node[] tmp = new Node[4];

				tmp[0] = (new Node(n.getX() + 1, n.getY(), n));// right
				tmp[1] = (new Node(n.getX() - 1, n.getY(), n));// left
				tmp[2] = (new Node(n.getX(), n.getY() + 1, n));// up
				tmp[3] = (new Node(n.getX(), n.getY() - 1, n));// down

				for (Node q : tmp) { // has node already been checked?
					if (q.getX() < 0 || q.getX() >= this.board.length
							|| q.getY() >= this.board[0].length || q.getY() < 0)
						continue;

					if (this.board[q.getX()][q.getY()] instanceof Player) {

					} else {
						if (this.board[q.getX()][q.getY()] != null) {
							closedList.add(q);
							continue;
						}
					}

					boolean inClosedList = false;
					for (Node w : closedList) {
						if (w.equals(q)) {
							inClosedList = true;
						}
					}
					for (Node w : openList) {
						if (w.equals(q)) {
							inClosedList = true;
						}
					}
					if (!inClosedList) {
						openList.add(q);
					}
				}

				openList.remove(n);
				closedList.add(n);

			}
			if (skip)
				break;

			vision--;
		}

		return null;
	}

	/**
	 * private class that is basically a ghetto linked list to keep track of
	 * possible paths
	 * 
	 * @author jim
	 *
	 */
	private class Node {
		private int x;
		private int y;
		private Node parent;

		/**
		 * creates a new node with position
		 * 
		 * @param x
		 *            x coord (board coords)
		 * @param y
		 *            y coord (board coords)
		 * @param parent
		 *            node that spawns this one
		 */
		public Node(int x, int y, Node parent) {
			this.x = x;
			this.y = y;
			this.parent = parent;
		}

		@SuppressWarnings("unused")
		public Node(Node n) {
			this.x = n.x;
			this.y = n.y;
			this.parent = n.parent;
		}

		/**
		 * 
		 * @return x value of the node
		 */
		int getX() {
			return this.x;
		}

		/**
		 * 
		 * @return y value of the node
		 */
		int getY() {
			return this.y;
		}

		/**
		 * 
		 * @return node that spawned this one
		 */
		Node getParent() {
			return this.parent;
		}

		/**
		 * Checks to see if two nodes have the same values
		 * 
		 * @param n
		 *            node to compare to
		 * @return true if the two nodes have identical x and y values, false
		 *         otherwise
		 */
		boolean equals(Node n) {
			return this.getX() == n.getX() && this.getY() == n.getY();
		}

		@Override
		public String toString() {
			return "(" + this.x + ", " + this.y + ")";
		}

		ArrayList<Node> getAncestry(Node n) {

			ArrayList<Node> tmp = new ArrayList<Node>();
			while (n.getParent() != null) {
				tmp.add(0, n);
				n = n.getParent();
			}
			return tmp;
		}

	}
}
