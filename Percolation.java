import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int size;
    private WeightedQuickUnionUF weightedQuickUnionUFMatrix;
    private boolean[][] helpingMatrix;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }

        size = n;
        weightedQuickUnionUFMatrix = new WeightedQuickUnionUF(n * n + 2);
        // matrix size is n + 1 for indeces to start from 1
        helpingMatrix = new boolean[n + 1][n + 1];
        // create virtual top and virtual bottom
        helpingMatrix[0][0] = true;
        helpingMatrix[0][1] = true;
        // connect virtual top and bottom to real ones
        for (int i = 1; i < n + 1; i++) {
            weightedQuickUnionUFMatrix.union(xyTo1D(1, i), xyTo1D(0, 0));
            weightedQuickUnionUFMatrix.union(xyTo1D(n, i), xyTo1D(0, 1));
        }
        for (int i = 1; i < n + 1; i++) {
            for (int j = 1; j < n + 1; j++) {
                helpingMatrix[i][j] = false;
            }
        }
    }

    public void open(int row, int col) {
        // validate indices
        if (!validateIndices(row, col)) {
            throw new IndexOutOfBoundsException();
        }

        // don't perform actions if the site is opened, except first row sites
        if (!helpingMatrix[row][col] || row == 1) {
            // open site (row, col) if it is not open already
            helpingMatrix[row][col] = true;

            // connect just opened site with its open neighbors
            // connect to top site
            int topRow = row - 1;

            if (validateIndices(topRow, col) &&
                    helpingMatrix[topRow][col]) {
                weightedQuickUnionUFMatrix.union(xyTo1D(row, col), xyTo1D(topRow, col));
            }

            // connect to middle sites
            if (validateIndices(row, col - 1) &&
                    helpingMatrix[row][col - 1]) {
                weightedQuickUnionUFMatrix.union(xyTo1D(row, col), xyTo1D(row, col - 1));

            }
            if (validateIndices(row, col + 1) &&
                    helpingMatrix[row][col + 1]) {
                weightedQuickUnionUFMatrix.union(xyTo1D(row, col), xyTo1D(row, col + 1));

            }

            // connect to bottom site
            int bottomRow = row + 1;
            if (validateIndices(bottomRow, col) &&
                    helpingMatrix[bottomRow][col]) {
                weightedQuickUnionUFMatrix.union(xyTo1D(row, col), xyTo1D(bottomRow, col));

            }
        }
    }

    public boolean isOpen(int row, int col) {
        if (!validateIndices(row, col)) {
            throw new IndexOutOfBoundsException();
        }

        return helpingMatrix[row][col];
    }

    public boolean isFull(int row, int col) {
        if (!validateIndices(row, col)) {
            throw new IndexOutOfBoundsException();
        }

        if (row == 1) {
            return helpingMatrix[row][col];
        } else if (row == size) {
            return helpingMatrix[row][col] && weightedQuickUnionUFMatrix.connected(xyTo1D(0, 0),
                    xyTo1D(row, col - 1));
        }

        return weightedQuickUnionUFMatrix.connected(xyTo1D(0, 0),
                xyTo1D(row, col));
    }

    public int numberOfOpenSites() {
        int numberOfOpenSitesInt = 0;
        for (int i = 1; i < size + 1; i++) {
            for (int j = 1; j < size + 1; j++) {
                if (helpingMatrix[i][j]) numberOfOpenSitesInt++;
            }
        }

        return numberOfOpenSitesInt;
    }

    public boolean percolates() {
        if (size == 1 && !helpingMatrix[1][1]) {
            return false;
        }

        return weightedQuickUnionUFMatrix.connected(xyTo1D(0, 0),
                xyTo1D(0, 1));
    }

    private int xyTo1D(int row, int col) {
        // condition for virtual top and bottom
        if (row == 0 && col == 0) {
            return size * size;
        } else if (row == 0 && col == 1)  {
            return size * size + 1;
        }

        return size * (row - 1) + col - 1;
    }

    private boolean validateIndices(int row, int col) {
        if (row < 1 || row > size || col < 1 || col > size) {
            return false;
        }
        return true;
    }

    public static void main(String[] args) { }
}
