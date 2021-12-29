/**
 * This interface encapsulates what a class used in Percolation Threshold
 * simulations should support. By varying the implementations you can experiment
 * with different approaches in analyzing not only what the Percolation
 * Threshold constants are for different grids, but how efficiently these
 * constants can be determined via simulation.
 * 
 * @author Owen Astrachan
 * @author Jeff Forbes
 * @date March, 2008
 * @date September, 2008
 * @date March, 2011
 */

public interface IPercolate {

	// Possible states for a grid cell are BLOCKED, OPEN, and FULL
	public static final int BLOCKED = 1;
	public static final int OPEN = 2;
	public static final int FULL = 4;

	/**
	 * Open site (row, col) if it is not already open. By convention, (0, 0)
	 * is the upper-left site
	 * 
	 * The method modifies internal state so that determining if percolation
	 * occurs could change after taking a step in the simulation.
	 * 
	 * @param row
	 *            row index in range [0,N-1]
	 * @param col
	 *            column index in range [0,N-1]
	 */
	public abstract void open(int row, int col);

	/**
	 * Returns true if and only if site (row, col) is OPEN
	 * 
	 * @param row
	 *            row index in range [0,N-1]
	 * @param col
	 *            column index in range [0,N-1]
	 */
	public abstract boolean isOpen(int row, int col);

	/**
	 * Returns true if and only if site (row, col) is FULL
	 * 
	 * @param row
	 *            row index in range [0,N-1]
	 * @param col
	 *            column index in range [0,N-1]
	 */
	public boolean isFull(int row, int col);

	/**
	 * Returns true if the simulated percolation actually percolates. What it
	 * means to percolate could depend on the system being simulated, but
	 * returning true typically means there's a connected path from
	 * top-to-bottom.
	 * 
	 * @return true iff the simulated system percolates
	 */
	public abstract boolean percolates();
	
	/**
	 * Returns the number of distinct sites that have been opened in this
	 * simulation
	 * 
	 * @return number of open sites
	 */
	public int numberOfOpenSites();
}