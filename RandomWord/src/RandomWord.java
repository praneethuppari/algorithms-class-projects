import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args){
        
        int count = 0;
        String champion = "";
        while (!StdIn.isEmpty()) {

            String input = StdIn.readString();
        	count++;
        	
        	double p = 1.0/count;
        	if (StdRandom.bernoulli(p)) {
        		champion = input;
            }
            
        
       

        }

        StdOut.println(champion);
    }
}
