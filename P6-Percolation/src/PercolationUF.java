public class PercolationUF  implements IPercolate {

    private IUnionFind myFinder;
    private boolean[][] myGrid; // open, closed cells
    private final int VTOP;
    private final int VBOTTOM;
    private int myOpenCount;

    public PercolationUF(IUnionFind myFinder, int size) {
        this.myFinder = myFinder;
        myGrid = new boolean[size][size];
        myFinder.initialize(size*size + 2);
        this.myFinder = myFinder;
        VTOP = size*size;
        VBOTTOM = size*size + 1;
    }

    @Override
    public void open(int row, int col) {
        checkInBounds(row, col);

        if(myGrid[row][col]) return;

        myOpenCount++;

        myGrid[row][col] = true;
        int cellNumber = getCellNumber(row, col);
        
        if(row == 0) {
            myFinder.union(
                    cellNumber,
                    VTOP
            );
        }

        if(row == myGrid.length - 1) {
            myFinder.union(
                    cellNumber,
                    VBOTTOM
            );
        }

        if (row != 0 && isOpen(row - 1, col)) {
            myFinder.union(
                    cellNumber,
                    getCellNumber(row - 1, col)
            );
        }
        
        if(row != myGrid.length - 1 && isOpen(row + 1, col)) {
            myFinder.union(
                    cellNumber,
                    getCellNumber(row + 1, col)
            );
        }

        if(col != 0 && isOpen(row, col - 1)) {
            myFinder.union(
                    cellNumber,
                    getCellNumber(row, col - 1)
            );
        }

        if(col != myGrid.length - 1 && isOpen(row, col + 1)) {
            myFinder.union(
                    cellNumber,
                    getCellNumber(row, col + 1)
            );
        }
    }

    @Override
    public boolean isOpen(int row, int col) {
        checkInBounds(row, col);

        return myGrid[row][col];
    }

    @Override
    public boolean isFull(int row, int col) {
        checkInBounds(row, col);

        return myFinder.connected(getCellNumber(row, col), VTOP);
    }

    @Override
    public boolean percolates() {
        return myFinder.connected(VTOP, VBOTTOM);
    }

    @Override
    public int numberOfOpenSites() {
        return myOpenCount;
    }

    /**
     * Associates certain row, col pairs with a unique identifier in the grid
     * simply using row * myGrid.length + col, i.e. the i-th cell left-to-right top-to-bottom
     *
     * If row == - 1 or col == myGrid.length, then return VTOP, VBOTTOM respective.
     * @param row Cell row
     * @param col Cell column
     * @return the unique identifier to a given valid (row, col) pair
     */
    private int getCellNumber(int row, int col) {

        if(row == -1) return VTOP;
        if(row == myGrid.length) return VBOTTOM;

        return row * myGrid.length + col;
    }

    /**
     * Determine if (row,col) is valid for given grid
     * @param row specifies row
     * @param col specifies column
     * @return true if (row,col) on grid, false otherwise
     */
    private boolean inBounds(int row, int col) {
        if (row < 0 || row >= myGrid.length) return false;
        if (col < 0 || col >= myGrid[0].length) return false;
        return true;
    }

    private void checkInBounds(int row, int col) {
        if (!inBounds(row, col)) {
            throw new IndexOutOfBoundsException(
                    String.format("(%d,%d) not in bounds", row, col));
        }
    }
}
