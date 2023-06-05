import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private WeightedQuickUnionUF quickFind;
    private int[] open;
    private int openSites;
    private int sites;
    private boolean percolated;

    /**
     * @param n : number of rows and columns
     * 
     * creates n-by-n grid, with all sites initially blocked
     */ 
    public Percolation(int n) {

        sites = n;
        if(n <= 0){
            throw new IllegalArgumentException("n is less than or equal to 0");
        }

        quickFind = new WeightedQuickUnionUF((n*n)+1);
        open = new int[(n*n)+1];
        open[0] = 2;
        percolated = false;
        openSites = 0;
    }

    /**
     * Ensures the row and col given are within the bounds of 
     * the nxn grid
     * @param row : the row number
     * @param col : the column number
     * @return: true if the given row and column are within range
     */
    private boolean validateIndices(int row, int col){
        if((row >= 1 && row <= sites) && (col >= 1 && col <= sites)){
            return true;
        }
        return false;
    }

    /**
     * Given the row and column, it returns the 2D index number
     * for the array and quickFind object.
     * @param row : row number
     * @param col : column number
     * @return : index within quickFind and array
     */
    private int queryIndex(int row, int col){

        // top virtual site
        if(row == 0){
            return 0;
        }

        //go to the end of the row and subtract based on the column
        //given to get the index number
        return (row*sites) - (sites - col);
    }
    
    // opens the site (row, col) if it is not open already
    /**
     * 
     * @param row
     * @param col
     */
    public void open(int row, int col) {

        if (validateIndices(row, col) == false) {
            throw new IllegalArgumentException("Row or Column out of prescribed range");
        }

        int index = queryIndex(row, col);
        int isOpen = open[index];

        if(isOpen == 0){
            openSites++;

            if(row == sites){
                open[index] = 3;
            }
            else{
                open[index] = 1;
            }
 
            if ((row > 1 && this.isOpen(row-1, col)) || (row==1)) {
                int union = queryIndex(row-1, col);
                int uRootStatus = open[quickFind.find(union)];
                int indexRootStatus = open[quickFind.find(index)];
                quickFind.union(index, union); // left
                if (open[index] == 3 || uRootStatus == 3 || indexRootStatus == 3) {
                    open[quickFind.find(index)] = 3;
                }
                
            }
            if (row < sites && this.isOpen(row+1, col)) {
                int union = queryIndex(row+1, col);
                int uRootStatus = open[quickFind.find(union)];
                int indexRootStatus = open[quickFind.find(index)];
                quickFind.union(index, union); // left
                if(open[index] == 3 || uRootStatus == 3 || indexRootStatus == 3) {
                    open[quickFind.find(index)] = 3;
                }
            }
            
            if (col < sites && this.isOpen(row, col+1)) {
                int union = queryIndex(row, col+1);
                int uRootStatus = open[quickFind.find(union)];
                int indexRootStatus = open[quickFind.find(index)];
                quickFind.union(index, union); // left
                if (open[index] == 3 || uRootStatus == 3 || indexRootStatus == 3) {
                    open[quickFind.find(index)] = 3;
                }
            }
                
            if (col > 1 && this.isOpen(row, col-1)) {
                int union = queryIndex(row, col-1);
                int uRootStatus = open[quickFind.find(union)];
                int indexRootStatus = open[quickFind.find(index)];
                quickFind.union(index, union); // left
                if (open[index] == 3 || uRootStatus == 3 || indexRootStatus == 3) {
                    open[quickFind.find(index)] = 3;
                }
            }

            if (this.isFull(row, col) && open[quickFind.find(index)] == 3){
                percolated = true;
            }
        }

    }
    
    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {

        if (validateIndices(row, col)) {
            return (open[queryIndex(row, col)] > 0);
        }
        throw new IllegalArgumentException("Row or Col number is out of range"); 
    }
    
    // is the site (row, col) full?
    public boolean isFull(int row, int col) {

        if (validateIndices(row, col)) {
            int conElement = quickFind.find(queryIndex(row, col));
            int con2Element = quickFind.find(0);

            return conElement == con2Element; 
        }
        throw new IllegalArgumentException("Row or Col is out of range");
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }
    
    // does the system percolate?
    public boolean percolates() {
        return percolated;
    }
    
    // test client (optional)
    public static void main(String[] args) {}

}
    
    

