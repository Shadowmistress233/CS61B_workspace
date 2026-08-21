import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {
    // TODO: Add any necessary instance variables.
    private boolean[][] items;
    private int cnt;
    private WeightedQuickUnionUF uf;
    private final int TOP;
    private final int BOTTOM;
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException();
        }
        TOP = N * N;
        BOTTOM = N * N + 1;
        uf = new WeightedQuickUnionUF(N*N+2);
        for (int i = 0; i < N; i++) {
            uf.union(i, TOP);
            uf.union(N * (N - 1) + i, BOTTOM);
        }
        items = new boolean[N][N];
        cnt = 0;
    }
    private void vaild(int row, int col) {
        if (row >= Math.sqrt(items.length) || col >= Math.sqrt(items.length) ||
            row < 0 || col < 0) {
            throw new IndexOutOfBoundsException();
        }
    }
    public void open(int row, int col) {
        // TODO: Fill in this method.
        vaild(row, col);
        items[row][col] = true;
        cnt++;
    }

    public boolean isOpen(int row, int col) {
        // TODO: Fill in this method.
        vaild(row, col);
        return items[row][col];
    }

    public boolean isFull(int row, int col) {
        // TODO: Fill in this method.
        vaild(row, col);
        return false;
    }

    public int numberOfOpenSites() {
        // TODO: Fill in this method.
        return 0;
    }

    public boolean percolates() {
        // TODO: Fill in this method.
        return false;
    }

    // TODO: Add any useful helper methods (we highly recommend this!).
    // TODO: Remove all TODO comments before submitting.

}
