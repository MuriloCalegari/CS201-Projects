import java.util.Arrays;

/**
 * Simulate percolation thresholds for a grid-base system using
 * depth-first-search, aka 'flood-fill' techniques for determining if the top of
 * a grid is connected to the bottom of a grid.
 * <P>
 * Modified from the COS 226 Princeton code for use at Duke. The modifications
 * consist of supporting the <code>IPercolate</code> interface, renaming methods
 * and fields to be more consistent with Java/Duke standards and rewriting code
 * to reflect the DFS/flood-fill techniques used in discussion at Duke.
 * <P>
 * 
 * @author Kevin Wayne, wayne@cs.princeton.edu
 * @author Owen Astrachan, ola@cs.duke.edu
 * @author Jeff Forbes, forbes@cs.duke.edu
 */

public class PercolationDFS implements IPercolate {
	protected int[][] myGrid;
	protected int myOpenCount;

	/**
	 * Initialize a grid so that all cells are blocked.
	 * 
	 * @param n
	 *            is the size of the simulated (square) grid
	 */
	public PercolationDFS(int n) {
		myGrid = new int[n][n];
		myOpenCount = 0;
		for (int[] row : myGrid)
			Arrays.fill(row, BLOCKED);
	}

	public void open(int row, int col) {
		
		if (! inBounds(row,col)) {
			throw new IndexOutOfBoundsException(
					String.format("(%d,%d) not in bounds", row,col));
		}
		if (myGrid[row][col] != BLOCKED)
			return;
		myOpenCount += 1;
		myGrid[row][col] = OPEN;
		updateOnOpen(row,col);
	}

	public boolean isOpen(int row, int col) {
		
		if (! inBounds(row,col)) {
			throw new IndexOutOfBoundsException(
					String.format("(%d,%d) not in bounds", row,col));
		}
		return myGrid[row][col] != BLOCKED;
	}

	public boolean isFull(int row, int col) {
		
		if (! inBounds(row,col)) {
			throw new IndexOutOfBoundsException(
					String.format("(%d,%d) not in bounds", row,col));
		}
		
		return myGrid[row][col] == FULL;
	}

	private void clearFull() {
		for (int i = 0; i < myGrid.length; i++) {
			for (int j = 0; j < myGrid[0].length; j++) {
				if (myGrid[i][j] == FULL) {
					myGrid[i][j] = OPEN;
				}
			}
		}
	}

	protected void updateOnOpen(int row, int col) {
		clearFull();
		for (int k = 0; k < myGrid[0].length; k++)
			dfs(0, k);
	}

	public boolean percolates() {
		for (int col = 0; col < myGrid[myGrid.length - 1].length; col++)
			if (isFull(myGrid.length - 1, col))
				return true;
		return false;
	}

	/**
	 * Private helper method to mark all cells that are open and reachable from
	 * (row,col).
	 * 
	 * @param row
	 *            is the row coordinate of the cell being checked/marked
	 * @param col
	 *            is the col coordinate of the cell being checked/marked
	 */
	protected void dfs(int row, int col) {
		// out of bounds?
		if (! inBounds(row,col)) return;
		
		// full or NOT open, don't process
		if (isFull(row, col) || !isOpen(row, col))
			return;
		
		myGrid[row][col] = FULL;
		dfs(row - 1, col);
		dfs(row, col - 1);
		dfs(row, col + 1);
		dfs(row + 1, col);
	}

	public int numberOfOpenSites() {
		return myOpenCount;
	}
	
	/**
	 * Determine if (row,col) is valid for given grid
	 * @param row specifies row
	 * @param col specifies column
	 * @return true if (row,col) on grid, false otherwise
	 */
	protected boolean inBounds(int row, int col) {
		if (row < 0 || row >= myGrid.length) return false;
		if (col < 0 || col >= myGrid[0].length) return false;
		return true;
	}

}
