import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {
    // TODO: Add any necessary instance variables.
    private boolean[][] items;
    private int cnt;

    public Percolation(int N) {
        // TODO: Fill in this constructor.
        if (N <= 0) {
            throw new IllegalArgumentException();
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
