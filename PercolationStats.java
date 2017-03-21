import edu.princeton.cs.algs4.StdRandom;

import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private int num;
    private int trials;
    private double[] numsOfSteps;

    public PercolationStats(int n, int t) {
        if (n <= 0 || t <= 0) {
            throw new java.lang.IllegalArgumentException();
        }
        num = n;
        trials = t;

        int numOfSteps;
        numsOfSteps = new double[trials];
        Percolation perc;
        for (int i = 0; i < trials; i++) {
            perc = new Percolation(num);
            numOfSteps = 0;
            while (!perc.percolates()) {
                perc.open(StdRandom.uniform(1, num + 1), StdRandom.uniform(1, num + 1));
                numOfSteps += 1;
            }
            numsOfSteps[i] = numOfSteps * 1.0 / (num * num);
        }
    }

    public double mean() {
        return StdStats.mean(this.numsOfSteps);
    }

    public double stddev() {
        return StdStats.stddev(this.numsOfSteps);
    }

    public double confidenceLo() {
        return this.mean() - (1.96 * this.stddev() / Math.sqrt(this.trials));
    }

    public double confidenceHi() {
        return this.mean() + (1.96 * this.stddev() / Math.sqrt(this.trials));
    }

    public static void main(String[] args) {
        int num = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        new PercolationStats(num, trials);
    }
}
