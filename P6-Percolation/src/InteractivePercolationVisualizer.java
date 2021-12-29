/******************************************************************************
 *  Compilation:  javac InteractivePercolationVisualizer.java
 *  Execution:    java InteractivePercolationVisualizer N
 *  Dependencies: Percolation.java
 *
 * @author Josh Hug
 * @author Owen Astrachan
 ******************************************************************************/

import java.awt.Font;

public class InteractivePercolationVisualizer {

	public static void main(String[] args) {

		// N-by-N percolation system
		int N = 10;
		if (args.length > 0) {
			N = Integer.parseInt(args[0]);
		}

		//IPercolate perc = new PercolationDFS(N);
		IPercolate perc = new PercolationUF(new QuickUWPC(),N);
		//IPercolate perc = new PercolationBFS(N);
		//IPercolate perc = new PercolationDFSFast(N);
		System.out.printf("visualizing %dx%d grid\n",N,N);

		// number of sites opened
		int opened = 0;

		// set title
		StdDraw.setTitle("Interactive Percolation Visualizer");
		// turn on animation mode
		StdDraw.enableDoubleBuffering();

		// set background, leave margin for writing
		StdDraw.setXscale(-0.5, N+0.5);
		StdDraw.setYscale(-0.5, N+0.5);
		StdDraw.clear();
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.filledSquare(N/2.0, N/2.0, N/2.0);
		StdDraw.setFont(new Font("SansSerif", Font.PLAIN, 12));

		int[] lastOpened = {-1,-1};
		// repeatedly open site specified my mouse click and draw resulting system
		while (true) {

			// detected mouse click
			if (StdDraw.isMousePressed()) {

				// screen coordinates
				double x = StdDraw.mouseX();
				double y = StdDraw.mouseY();

				// convert to row i, column j
				int i = (int) (N - Math.floor(y));
				int j = (int) (1 + Math.floor(x));
				int vi = i-1;
				int vj = j-1;
				if (vi >= 0 && vi < N && vj >= 0 && vj < N) {
					if (!perc.isOpen(vi, vj)) { 
						opened++;
						perc.open(vi, vj);
						lastOpened[0] = vi;
						lastOpened[1] = vj;
					}
				}

				// draw percolation system
				StdDraw.clear();
				StdDraw.setPenColor(StdDraw.BLACK);
				StdDraw.filledSquare(N/2.0, N/2.0, N/2.0);
				for (int row = 1; row <= N; row++) {
					for (int col = 1; col <= N; col++) {
						int r = row-1;
						int c = col-1;
						if (perc.isFull(r, c)) {
							StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
							//System.out.printf("%d %d is full\n",r,c);
						}
						else if (perc.isOpen(r, c)) {
							StdDraw.setPenColor(StdDraw.WHITE);
							//System.out.printf("%d %d is open\n",r,c);
						}
						else {
							StdDraw.setPenColor(StdDraw.BLACK);
							//System.out.printf("%d %d is blocked\n",r,c);
						}
						StdDraw.filledSquare(col - 0.5, N - row + 0.5, 0.45);
					}
				}
				StdDraw.setPenColor(StdDraw.BLACK);
				StdDraw.text(.25*N, -N*.025, "sites opened = " + opened);
				if (perc.percolates()) StdDraw.text(.75*N, -N*.025, "percolates");
				else                   StdDraw.text(.75*N, -N*.025, "does not percolate");

				if (lastOpened[0] != -1) {
					StdDraw.text(.25*N, N*1.025, "last opened = ["+vi+","+vj+"]");
				}
			}
			StdDraw.show();
			StdDraw.pause(20);
		}
	}
}
