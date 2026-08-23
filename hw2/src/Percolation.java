import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {
    // TODO: Add any necessary instance variables.
    private boolean[][] items;
    private int cnt;
    private WeightedQuickUnionUF uf;
    private final int TOP;
    private final int BOTTOM;
    private int[] dx = {1, -1, 0, 0};
    private int[] dy = {0, 0, 1, -1};
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException();
        }
        TOP = N * N;
        BOTTOM = N * N + 1;
        uf = new WeightedQuickUnionUF(N*N+2);
        for (int i = 0; i < N; i++) {
            uf.union(TOP, i);
            uf.union(BOTTOM, N * (N - 1) + i);
        }
        items = new boolean[N][N];
        cnt = 0;
    }
    private boolean isValid(int row, int col) {
        if (row >= items.length || col >= items.length ||
            row < 0 || col < 0) {
            return false;
        }
        return true;
    }
    private void valid(int row, int col) {
        if (!isValid(row, col)) {
            throw new IndexOutOfBoundsException();
        }
    }
    private int Index(int row, int col) {
        return row * items.length + col;
    }

    public void open(int row, int col) {
        // TODO: Fill in this method.
        valid(row, col);
        items[row][col] = true;
        cnt++;
        for (int i = 0; i < 4; i++) {
            int otherRow = row + dx[i];
            int otherCol = col + dy[i];
            if (isValid(otherRow, otherCol)) {
                uf.union(Index(otherRow, otherCol), Index(row, col));
            }
        }

    }

    public boolean isOpen(int row, int col) {
        // TODO: Fill in this method.
        valid(row, col);
        return items[row][col];
    }

    public boolean isFull(int row, int col) {
        // TODO: Fill in this method.
        valid(row, col);

        return uf.find (Index(row, col)) == TOP;
    }

    public int numberOfOpenSites() {
        // TODO: Fill in this method.
        return cnt;
    }

    public boolean percolates() {
        // TODO: Fill in this method.
        return uf.connected(TOP, BOTTOM);
    }

    // TODO: Add any useful helper methods (we highly recommend this!).
    // TODO: Remove all TODO comments before submitting.

}
