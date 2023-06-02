import edu.princeton.cs.algs4.QuickFindUF;

public class Percolation {

    public QuickFindUF quickFind;
    private boolean[] open;
    int openSites;
    int sites;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {

        sites = n;
        if(n <= 0){
            throw new IllegalArgumentException("n is less than or equal to 0");
        }

        quickFind = new QuickFindUF((n*n)+2);
        open = new boolean[(n*n)+2];
        open[0] = true;
        open[(n*n)+1] = true;
        openSites = 0;
    }

    private boolean validateIndices(int row, int col){
        if((row >= 1 && row <= sites) && (col >= 1 && col <= sites)){
            return true;
        }
        return false;
    }

    public int queryIndex(int row, int col){

        if(row == 0){
            return 0;
        }
        else if(row == (sites + 1)){
            return (sites*sites) + 1;
        }
        
        return (row*sites) - (sites - col);
    }
    
    // opens the site (row, col) if it is not open already
    public void open(int row, int col) throws IllegalArgumentException {

        if( validateIndices(row, col) == false) {
            throw new IllegalArgumentException("Row or Column out of prescribed range");
        }

        int index = queryIndex(row, col);
        boolean isOpen = open[index];
        if(isOpen == false){
            openSites++;
            open[index] = true;

            if(this.isOpen(row-1, col)){
                quickFind.union(index, queryIndex(row-1, col)); // top
            }
            if(this.isOpen(row+1, col)){
                quickFind.union(index, queryIndex(row+1, col)); // bottom
            }
            
            if(col < sites && this.isOpen(row, col+1)){
                quickFind.union(index, queryIndex(row, col+1)); //right
            }
                
            if(col > 1 && this.isOpen(row, col-1)){
                quickFind.union(index, queryIndex(row, col-1)); // left
            }
        }

    }
    
    // is the site (row, col) open?
    public boolean isOpen(int row, int col){
        if(validateIndices(row, col)){
            return open[queryIndex(row, col)];
        }
        throw new IllegalArgumentException("Row or Col number is out of range");
        
    }
    
    // is the site (row, col) full?
    public boolean isFull(int row, int col){
        
        if(validateIndices(row, col)){
            int conElement = quickFind.find(queryIndex(row, col));
            int con2Element = quickFind.find(0);
    
            return conElement == con2Element; 
        }
        throw new IllegalArgumentException("Row or Col is out of range");
       
    }

    // issue: 
    // because of how it checks for fullness, because of the bottom site, it considers it full
    // since they are in the same set. Any neighboring sites are also considered full because
    // they are unioned with such block that isn't actually full but is at the bottom.
    
    // returns the number of open sites
    public int numberOfOpenSites(){
        return openSites;
    }
    
    // does the system percolate?
    public boolean percolates(){
        if(quickFind.find((sites*sites)+1) == quickFind.find(0)){
            return true;
        }
        return false;
    }
    
    // test client (optional)
    public static void main(String[] args){
        
        Percolation perci = null;
        try{
            perci = new Percolation(4);
            perci.open(1, 1);
            perci.open(1, 2);
            perci.open(2, 1);
            perci.open(3, 1);
            perci.open(4, 1);
            int one = perci.quickFind.find(1);
            int two = perci.quickFind.find(2);
            int three = perci.quickFind.find(0);
            if(one == two && one == three && perci.openSites == 5){
                System.out.println("Both are connected");
            }
            if(perci.percolates() == true){
                System.out.println("Percolates seems to work");
            }

        }
        catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }




    }
}
    
    

