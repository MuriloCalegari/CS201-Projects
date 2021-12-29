import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

public class TestPercolation {
	
	public IPercolate getPercolator(int size) {
		//return new PercolationDFS(size);
		//return new PercolationBFS(size);
		//return new PercolationDFSFast(size);
		IUnionFind finder = new QuickUWPC();
		IPercolate perc = new PercolationUF(finder,size);
		return perc;
	}
	
	/**
	 * test checks if IPercolate's isOpen method works correctly
	 */
	@Test
	public void testIsOpen() {
		IPercolate perc = getPercolator(10);
		for (int i = 1; i < 10; i++)
			for (int j = 0; j < 10; j++) {
				perc.open(i, j);
				assertTrue( perc.isOpen(i, j),"This test checks if IPercolate's isOpen method " + "works correctly");
			}
	}

	
	/**
	 * This test checks if IPercolate's isFull method works correctly
	 */
	@Test
	public void testIsFull() {
		IPercolate perc = getPercolator(10);
		for (int i = 0; i < 10; i++)
			for (int j = 0; j < 10; j++) {
				perc.open(i, j);
				assertTrue(perc.isFull(i, j),"This test checks if IPercolate's isFull method " + "works correctly");
			}
	}


	private void doTestPercolate(IPercolate perc) {
		for (int i = 0; i < 9; i++)
			for (int j = 0; j < 10; j++) {
				perc.open(i, j);
				assertFalse(perc.percolates(),
						"This test checks if " + perc.getClass().getName() + " percolates method works correctly");
			}
		perc.open(9, 0);
		assertTrue(perc.percolates(),
				"This test checks if " + perc.getClass().getName() + "percolates method works correctly");
	}

	/**
	 * This test checks if IPercolate's percolates method works correctly
	 */
	@Test
	public void testPercolates() {
		IPercolate perc = getPercolator(10);
		doTestPercolate(perc);
	}

	

	/**
	 * Check if Exception is thrown unless (0 <= i < N) and (0 <= j < N)
	 */
	private static void bounds(IPercolate perc, int N, int i, int j) {
		boolean passed1 = false;
		boolean passed2 = false;
		boolean passed3 = false;
		System.out.println("  *  N = " + N + ", (i, j) = (" + i + ", " + j + ")");
		try {
			perc.open(i, j);
		} catch (Exception e) {
			passed1 = true;
		}
		assertTrue(passed1,"This test checks if Exception thrown for open() for " + perc.getClass().getName());

		try {
			boolean b = perc.isOpen(i, j);
		} catch (Exception e) {
			passed2 = true;
		}
		assertTrue(passed2,"This test checks if Exception thrown for isOpen() for " + perc.getClass().getName());

		try {
			boolean b = perc.isFull(i, j);
		} catch (Exception e) {
			passed3 = true;
		}
		assertTrue(passed3,"This test checks if Exception thrown for isFull() for " + perc.getClass().getName());
	}

	private void testBounds(IPercolate perc) {
        bounds(perc, 10, -1,  5);
        bounds(perc, 10, 11,  5);
        bounds(perc, 10, 10,  5);
        bounds(perc, 10,  5, -1);
        bounds(perc, 10,  5, 11);
        bounds(perc, 10,  5, 10);
	}
	/**
	 * Check if Exception is thrown when (i, j) are out of bounds
	 */
	@Test
	public void testBounds() {
		IPercolate perc = getPercolator(10);
		testBounds(perc);
	}

}
