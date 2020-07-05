import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final int n;
    private boolean[] open;
    private boolean[] full;
    private int[] max;
    private int openSites;
    private boolean percolated;
    // these are data-structures for union-find
    private final WeightedQuickUnionUF uf;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be larger than 0");
        }
        this.n = n;
        open = new boolean[n * n];
        full = new boolean[n * n];
        max = new int[n * n];
        for (int i = 0; i < n * n; i++) {
            open[i] = false;
            full[i] = false;
            max[i] = i;
        }
        openSites = 0;
        uf = new WeightedQuickUnionUF(n * n);
        percolated = false;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        checkIndices(row, col);
        if (!isOpen(row, col)) {
            int id = getId(row, col);
            open[id] = true;
            if (row == 1) {
                // this site is at the top
                full[id] = true;
            }
            int root = id;
            if (row - 1 > 0 && isOpen(row - 1, col)) {
                root = connect(root, id - n);
            }
            if (row + 1 <= n && isOpen(row + 1, col)) {
                root = connect(root, id + n);
            }
            if (col - 1 > 0 && isOpen(row, col - 1)) {
                root = connect(root, id - 1);
            }
            if (col + 1 <= n && isOpen(row, col + 1)) {
                root = connect(root, id + 1);
            }
            if (full[root] && max[root] >= n * (n - 1)) {
                percolated = true;
            }
            openSites++;
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        checkIndices(row, col);
        return open[getId(row, col)];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        checkIndices(row, col);
        if (!isOpen(row, col)) {
            return false;
        }
        int root = uf.find(getId(row, col));
        return full[root];
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return percolated;
    }

    private int connect(int p, int q) {
        int rootQ = uf.find(q);
        uf.union(p, rootQ);
        // now p and q are united under root
        int root = uf.find(p);
        full[root] = full[p] || full[rootQ];
        max[root] = Math.max(max[p], max[rootQ]);
        return root;
    }

    private int getId(int row, int col) {
        return (row - 1) * n + (col - 1);
    }

    private void checkIndices(int row, int col) {
        if (row <= 0 || row > n) {
            throw new IllegalArgumentException("row is not in range");
        }
        if (col <= 0 || col > n) {
            throw new IllegalArgumentException("col is not in range");
        }
    }

//    public void print() {
////        StdDraw.setScale(0, n);
//        for (int i = 0; i < n; i++) {
//            for (int j = 0; j < n; j++) {
//                System.out.print(open[getId(i + 1, j + 1)] ? "1" : "0");
//                System.out.print(" ");
////                StdDraw.setPenColor(sites[i][j] ? Color.BLUE : Color.BLACK);
////                StdDraw.filledRectangle(j + 0.5, n - i - 1 + 0.5, 0.5, 0.5);
//            }
//            System.out.println();
//        }
//        System.out.println();
//    }

    // test client (optional)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        Percolation percolation = new Percolation(n);
//        StdDraw.enableDoubleBuffering();
        while (!percolation.percolates()) {
            int r = StdRandom.uniform(n) + 1;
            int c = StdRandom.uniform(n) + 1;
            System.out.println("Row and Col to open: " + r + "; " + c);
            System.out.println();
            percolation.open(r, c);
//            StdDraw.clear();
//            percolation.print();
//            StdDraw.show();
//            StdDraw.pause(1000);
            System.out.println("------------------------------------------------------------------");
        }
        System.out.println("The system percolated");
//        percolation.print();
    }
}
