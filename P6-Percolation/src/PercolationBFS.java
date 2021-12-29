import java.util.LinkedList;
import java.util.Queue;

public class PercolationBFS extends PercolationDFSFast{

    /**
     * Initialize a grid so that all cells are blocked.
     *
     * @param n is the size of the simulated (square) grid
     */
    public PercolationBFS(int n) {
        super(n);
    }

    @Override
    protected void dfs(int row, int col) {
        if(!inBounds(row, col)) return;

        if(isFull(row, col) || !isOpen(row, col)) return;

        myGrid[row][col] = FULL;

        int[] rowDelta = {-1, 1, 0, 0};
        int[] colDelta = {0, 0, -1, 1};

        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{row, col});

        while(queue.size() > 0) {
            int[] cell = queue.remove();
            int currentRow = cell[0];
            int currentCol = cell[1];

            for(int k = 0; k < rowDelta.length; k++) {
                int nextRow = currentRow + rowDelta[k];
                int nextCol = currentCol + colDelta[k];

                if(inBounds(nextRow, nextCol) &&
                        isOpen(nextRow, nextCol) &&
                        !isFull(nextRow, nextCol)) {
                    myGrid[nextRow][nextCol] = FULL;
                    queue.add(new int[]{nextRow, nextCol});
                }
            }

        }

    }
}
