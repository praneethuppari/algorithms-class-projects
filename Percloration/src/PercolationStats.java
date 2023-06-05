import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;
import java.lang.Integer;
import java.lang.Math;

public class PercolationStats {

    private double[] thresholds;
    private int trials;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials){

        //throw IllegalArgumentException if n or trials <= 0
        if(n <=0 || trials <= 0){
            throw new IllegalArgumentException("Provided n or trial value is less than or equal to 0.");
        }

        thresholds = new double[trials];
        this.trials = trials;

        for(int i = 0; i < trials; i++){
            thresholds[i]= runTrial(n);
        }
    }

    private double runTrial(int n){

        /**
         * 1. Create new Percolate Object
         * 2. Pick a random site --> open it
         * 3. Check if system has percolated
         * 4. If not percolated, do the steps again
         */
        Percolation percolation = new Percolation(n);
        while(percolation.percolates() == false){
            percolation.open(StdRandom.uniformInt(1, n+1), StdRandom.uniformInt(1, n+1));
        }
        return ((double)percolation.numberOfOpenSites()/(n*n));

    }

    // sample mean of percolation threshold
    public double mean(){

        return StdStats.mean(thresholds);

    }

    // sample standard deviation of percolation threshold
    public double stddev(){

        return StdStats.stddev(thresholds);

    }

    // low endpoint of 95% confidence interval
    public double confidenceLo(){

        double mean = this.mean();
        double stddev = stddev();

        double confidenceLow = mean - ((1.96*stddev)/Math.sqrt(trials));
        return confidenceLow;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi(){

        double mean = this.mean();
        double stddev = stddev();

        double confidenceHigh = mean + ((1.96*stddev)/Math.sqrt(trials));
        return confidenceHigh;

    }

   // test client (see below)
   public static void main(String[] args){

        PercolationStats stats = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        
        StdOut.printf("mean %20s %.18f%n", "=", stats.mean());
        StdOut.printf("stddev %18s %.18f%n", "=", stats.stddev());
        StdOut.printf("95%% confidence interval = [%.18f, %.18f]%n", stats.confidenceLo(), 
            stats.confidenceHi());
   }

    
}
