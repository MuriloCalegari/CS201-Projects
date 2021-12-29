/******************************************************************************
 *  Based on code in https://algs4.cs.princeton.edu/41graph/Maze.java
 *  But here added both DFS and BFS with depth counting
 *  and Union-Find maze generation in addition to the princeton maze generation
 * @author Princeton staff
 * @author Owen Astrachan
 * @date October 20, 2020
 *
 *
 * Note Stdraw uses (x,y) coordinates and *NOT* (row,col) coordinates
 * so this code uses (x,y) with lower-right corner at 0,0 which is off-grid
 * so maze always starts at (1,1) lower right in maze
 ******************************************************************************/
import java.util.*;
import java.awt.Color;

public class MazeDemo {
    private int mySize;             // dimension of maze
    private boolean[][] north;      // is there a wall to north of cell i, j
    private boolean[][] east;
    private boolean[][] south;
    private boolean[][] west;
    private boolean[][] visited;
    Random myRandom;

    public MazeDemo(int n) {
        this.mySize = n;
        StdDraw.setXscale(0, n+2);
        StdDraw.setYscale(0, n+2);
        myRandom = new Random();
        init();
    }

    private void init() {
        // initialize border cells as already visited
        visited = new boolean[mySize +2][mySize +2];
        for (int x = 0; x < mySize +2; x++) {
            visited[x][0] = true;
            visited[x][mySize +1] = true;
        }
        for (int y = 0; y < mySize +2; y++) {
            visited[0][y] = true;
            visited[mySize +1][y] = true;
        }

        // initialize all walls as present
        north = new boolean[mySize +2][mySize +2];
        east  = new boolean[mySize +2][mySize +2];
        south = new boolean[mySize +2][mySize +2];
        west  = new boolean[mySize +2][mySize +2];
        for (int x = 0; x < mySize +2; x++) {
            for (int y = 0; y < mySize +2; y++) {
                north[x][y] = true;
                east[x][y]  = true;
                south[x][y] = true;
                west[x][y]  = true;
            }
        }
    }

    // generate maze with union find
    private void generateUF(){
        int cellCount = mySize*mySize;

        IUnionFind finder = new QuickUWPC(cellCount);
        List<int[]> edgeList = new LinkedList<>();
        // add edges to list, identified by adjoining cells
        for(int c=0; c < cellCount; c++) {
            int y = c / mySize;
            int x = c % mySize;
            // this program has hidden cells on border x/y = 0, mySize, so add to x
            // also starts with #0 in lower left corner, so y increasing up
            y = mySize - y;
            x += 1;
            if (x < mySize) {
                edgeList.add(new int[]{c, c + 1});
            }
            if (1 < y) {
                edgeList.add(new int[]{c, c + mySize});
            }
        }

        Collections.shuffle(edgeList);

        while (finder.components() > 1) {
            int[] edge = edgeList.remove(0);
            int c1 = edge[0];
            int c2 = edge[1];
            int y = c1/mySize;   // corresponds to "row"
            int x = c1%mySize;   // corresponds to "col"
            y = mySize - y;      // y increasing going up
            x += 1;              // +1 to avoid buffer columns
            if (! finder.connected(c1,c2)) {
                finder.union(c1,c2);
                if (c2-c1 == 1) {
                    // vertical wall
                    east[x][y] = false;
                    west[x+1][y] = false;
                    //System.out.printf("east %d %d %d %d\n",x,y,c1,c2);
                }
                else {
                    north[x][y-1] = false;     // for cell numbering
                    south[x][y] = false;
                    //System.out.printf("north %d %d %d %d\n",x,y,c1,c2);
                }
            }
        }
    }

    // generate the maze using perfect maze generating algorithm/Princeton
    private void generate(int x, int y) {
        visited[x][y] = true;

        // while there is an unvisited neighbor
        while (!visited[x][y+1] || !visited[x+1][y] || !visited[x][y-1] || !visited[x-1][y]) {

            // pick random neighbor (could use Knuth's trick instead)
            while (true) {
                int r = myRandom.nextInt(4);
                if (r == 0 && !visited[x][y+1]) {
                    north[x][y] = false;
                    south[x][y+1] = false;
                    generate(x, y + 1);
                    break;
                }
                else if (r == 1 && !visited[x+1][y]) {
                    east[x][y] = false;
                    west[x+1][y] = false;
                    generate(x+1, y);
                    break;
                }
                else if (r == 2 && !visited[x][y-1]) {
                    south[x][y] = false;
                    north[x][y-1] = false;
                    generate(x, y-1);
                    break;
                }
                else if (r == 3 && !visited[x-1][y]) {
                    west[x][y] = false;
                    east[x-1][y] = false;
                    generate(x-1, y);
                    break;
                }
            }
        }
    }

    // generate the maze starting from lower left
    private void generate() {
        generate(1, 1);
    }

    /**
     * Solving maze using depth-first search
     * @param x is starting x (col)
     * @param y is starting y (row)
     * @param depth is current depth, #calls away from start
     * @return depth of solution when maze found
     */
    private int solveDFS(int x, int y, int depth) {
        if (x == 0 || y == 0 || x == mySize +1 || y == mySize +1) return 0;
        if (visited[x][y]) return 0;

        visited[x][y] = true;

        StdDraw.setPenColor(Color.BLUE);
        StdDraw.filledCircle(x + 0.5, y + 0.5, 0.25);
        StdDraw.show();
        StdDraw.pause(30);

        // reached middle which is goal of maze
        if (x == mySize /2 && y == mySize /2) {
            return depth;
        }

        if (!north[x][y]) {
            int d = solveDFS(x, y + 1,depth+1);
            if (d > 0) return d;
        }
        if (!east[x][y])  {
            int d = solveDFS(x + 1, y,depth+1);
            if (d > 0) return d;
        }
        if (!south[x][y]) {
            int d = solveDFS(x, y - 1,depth+1);
            if (d > 0) return d;
        }
        if (!west[x][y])  {
            int d = solveDFS(x - 1, y,depth+1);
            if (d > 0) return d;
        }

        StdDraw.setPenColor(StdDraw.GRAY);
        StdDraw.filledCircle(x + 0.5, y + 0.5, 0.25);
        StdDraw.show();
        StdDraw.pause(30);
        return 0;
    }

    /**
     * Solve maze using BFS starting at (x,y)
     * @param x starting x (col)
     * @param y starting y (row)
     * @param ignore to be parallel to DFS parameters, not used
     * @return
     */
    private int solveBFS(int x, int y, int ignore) {

        Queue<int[]> q = new LinkedList<>();
        q.add(new int[]{x,y,0});
        int maxq = 1;

        // invariant: all [x,y] pairs on queue are NOT visited

        while (q.size() > 0) {
            maxq = Math.max(maxq,q.size());
            int[] cell = q.remove();
            x = cell[0];
            y = cell[1];
            int depth = cell[2];

            visited[x][y] = true;
            StdDraw.setPenColor(Color.BLUE);
            StdDraw.filledCircle(x + 0.5, y + 0.5, 0.25);
            StdDraw.show();
            StdDraw.pause(30);

            // reached middle
            if (x == mySize / 2 && y == mySize / 2) {
                System.out.printf("max = %d, depth = %d\n",maxq,depth);
                return depth;
            }

            if (!north[x][y] && !visited[x][y+1]) q.add(new int[]{x,y+1,depth+1});
            if (!east[x][y]  && !visited[x+1][y]) q.add(new int[]{x+1,y,depth+1});
            if (!south[x][y] && !visited[x][y-1]) q.add(new int[]{x,y-1,depth+1});
            if (!west[x][y]  && !visited[x-1][y]) q.add(new int[]{x-1,y,depth+1});

        }
        return 0; // not reached if maze solved
    }

    /**
     * solve maze from [1,1], using DFS or BFS
     * @param dfs true? then dfs used, else bfs used
     */
    public void solve(boolean dfs) {
        for (int x = 1; x <= mySize; x++)
            for (int y = 1; y <= mySize; y++)
                visited[x][y] = false;

        int depth = 0;
        if (dfs) {
            depth = solveDFS(1,1,0);
        }
        else {
            depth = solveBFS(1,1,0);
        }
        System.out.printf("depth to solve = %d\n",depth);
    }

    // draw the maze
    public void draw() {
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.filledCircle(mySize /2.0 + 0.5, mySize /2.0 + 0.5, 0.375);
        StdDraw.filledCircle(1.5, 1.5, 0.375);

        StdDraw.setPenColor(StdDraw.BLACK);
        for (int x = 1; x <= mySize; x++) {
            for (int y = 1; y <= mySize; y++) {
                if (south[x][y]) StdDraw.line(x, y, x+1, y);
                if (north[x][y]) StdDraw.line(x, y+1, x+1, y+1);
                if (west[x][y])  StdDraw.line(x, y, x, y+1);
                if (east[x][y])  StdDraw.line(x+1, y, x+1, y+1);
            }
        }
        StdDraw.show();
        StdDraw.pause(1000);
    }


    public static void main(String[] args) {
        int n = 40;
        MazeDemo maze = new MazeDemo(n);
        boolean isDFS = true; // true for dfs, false for bfs
        String title = "Maze Demo with ";
        title += isDFS ? "DFS" : "BFS";
        StdDraw.enableDoubleBuffering();
        StdDraw.setTitle(title);
        maze.generate();
        //maze.generateUF();
        maze.draw();

        while (! StdDraw.isMousePressed()) {
            // pause until mouse pressed for recording
        }
        maze.solve(isDFS);
    }
}