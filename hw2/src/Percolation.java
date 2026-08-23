import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {
    private boolean[][] items;
    private int cnt;
    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF uf_2;
    private final int TOP;
    private final int BOTTOM;
    private static final int[] DX = {1, -1, 0, 0};
    private static final int[] DY = {0, 0, 1, -1};
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException();
        }
        TOP = N * N;
        BOTTOM = N * N + 1;
        uf = new WeightedQuickUnionUF(N * N + 2);
        uf_2 = new WeightedQuickUnionUF(N * N + 1);
        items = new boolean[N][N];
        cnt = 0;
    }
    private boolean isValid(int row, int col) {
        if (row >= items.length || col >= items.length
                || row < 0 || col < 0) {
            return false;
        }
        return true;
    }
    private void valid(int row, int col) {
        if (!isValid(row, col)) {
            throw new IndexOutOfBoundsException();
        }
    }
    private int index(int row, int col) {
        return row * items.length + col;
    }

    public void open(int row, int col) {
        valid(row, col);
        if (isOpen(row, col)) {
            return;
        }
        items[row][col] = true;
        if (row == 0) {
            uf.union(TOP, index(row, col));
            uf_2.union(TOP, index(row, col));
        }
        if (row == items.length - 1) {
            uf.union(BOTTOM, index(row, col));
        }
        cnt++;
        for (int i = 0; i < 4; i++) {
            int otherRow = row + DX[i];
            int otherCol = col + DY[i];
            if (isValid(otherRow, otherCol) && isOpen(otherRow, otherCol)) {
                uf.union(index(otherRow, otherCol), index(row, col));
                uf_2.union(index(otherRow, otherCol), index(row, col));
            }
        }

    }

    public boolean isOpen(int row, int col) {
        valid(row, col);
        return items[row][col];
    }

    public boolean isFull(int row, int col) {
        valid(row, col);
        return uf_2.connected(index(row, col), TOP);
    }

    public int numberOfOpenSites() {
        return cnt;
    }

    public boolean percolates() {
        return uf.connected(TOP, BOTTOM);
    }

}
