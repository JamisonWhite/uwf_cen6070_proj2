
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Random;

/**
 * GameGrid stores the game state
 *
 * @author jwhite
 */
public class GameGrid {

    /**
     * Initialize grid
     * @param size 
     */
    public GameGrid(int size) throws IllegalArgumentException {
        if (size < 2 || size >= Config.MaxGridSize) {
            throw new IllegalArgumentException("Size out of range. 2-" + Config.MaxGridSize);
        }
        this.size = size * size;
        dataGrid = new String[this.size];
        displayGrid = new String[this.size];
        isFoundGrid = new Boolean[this.size];
    }

    private final int size;
    private final String[] dataGrid;
    private final String[] displayGrid;
    private final Boolean[] isFoundGrid;

    /**
     * Get the size of the grid
     *
     * @return
     */
    public int getSize() {
        int retVal = size;
        
        assert retVal > 0; //assert postcondition
        return retVal;
    }

    /**
     * Get the data grid
     *
     * @return
     */
    public String[] getDataGrid() {
        return dataGrid.clone();
    }

    /**
     * Get the data grid
     *
     * @return
     */
    public Boolean[] getIsFoundGrid() {
        return isFoundGrid.clone();
    }
    /**
     * Get the display grid
     *
     * @return
     */
    public String[] getDisplayGrid() {
        String[] result = displayGrid.clone();
        for (int i = 0; i < size; i++) {
            if (isFoundGrid[i]) {
                result[i] = dataGrid[i];
            }
        }
        
        assert result.length > 0; //assert postcondition
        return result;
    }

    /**
     * Get the display grid
     *
     * @param cell1
     * @param cell2
     * @return
     */
    public String[] getDisplayGrid(int cell1, int cell2) {
        String[] result = displayGrid.clone();
        for (int i = 0; i < size; i++) {
            if (isFoundGrid[i] || i == cell1 || i == cell2) {
                result[i] = dataGrid[i];
            }
        }
        
        assert result.length > 0; //assert postcondition
        return result;
    }

    /**
     * Is the cell found.
     *
     * @param cell
     * @return
     */
    public Boolean isCellFound(int cell) throws IllegalArgumentException {
        if (cell < 0 || cell >= size) {
            throw new IllegalArgumentException("Cell out of range.");
        }
        
        Boolean retVal = isFoundGrid[cell];
        assert retVal != null;
        
        return isFoundGrid[cell];
    }

    /**
     * Guess and set the cell
     *
     * @param cell1
     * @param cell2
     * @return
     * @throws IllegalArgumentException
     */
    public Boolean matchCells(int cell1, int cell2) throws IllegalArgumentException {
        if (cell1 < 0 || cell1 >= size) {
            throw new IllegalArgumentException("Cell1 out of range.");
        }
        if (cell2 < 0 || cell2 >= size) {
            throw new IllegalArgumentException("Cell2 out of range.");
        }
        if (cell1 == cell2) {
            throw new IllegalArgumentException("Cell1 and cell2 must not be the same cell.");
        }
        
        if (dataGrid[cell1].equals(dataGrid[cell2])) {
            isFoundGrid[cell1] = true;
            isFoundGrid[cell2] = true;
            return true;
        }
        return false;
    }

    /**
     * Number of remaining matches
     *
     * @return
     */
    public int remaining() {
        int result = 0;
        for (int i = 0; i < size; i++) {
            if (!isFoundGrid[i]) {
                result++;
            }
        }
        return result;
    }

    /**
     * Initialize grids with random number
     */
    void initializeGrids() {
        Random rnd = new Random();
        initializeGrids(rnd.nextInt());
    }

    /**
     * Initialize display and data grids
     *
     * @param seed
     */
    public void initializeGrids(int seed) throws IllegalArgumentException {
        if (dataGrid.length == 0){
            throw new IllegalArgumentException("dataGrid.length > 0");
        } //replaced assert precondition
        if (dataGrid.length != size) {
            throw new IllegalArgumentException("dataGrid.length == size");
        } //replaced assert precondition
        if (dataGrid.length != displayGrid.length) {
            throw new IllegalArgumentException("dataGrid.length == displayGrid.length");
        } //replaced assert precondition
        if (displayGrid.length != isFoundGrid.length) {
            throw new IllegalArgumentException("displayGrid.length == isFoundGrid.length");
        } //replaced assert precondition
        
        //initialize values
        char data = 'A';
        for (int i = 0; i < size; i++) {
            isFoundGrid[i] = false;
            //display grid is 1-based counting
            displayGrid[i] = Integer.toString(i + 1);
            //data grid doubles each value and increments if more than 2 * 26 
            //are needed
            String value = "" + data;
            if (size > 52) {
                value = value + (i / 52);
            }
            dataGrid[i] = value;
            if (i % 2 == 1) {
                data++;
                if (data > 'Z') {
                    data = 'A';
                }
            }
        }
        //set the foundcell for odd sizes
        if (size % 2 == 1) {
            dataGrid[size - 1] = "";
        }

        //shuffle datagrid
        Random rnd = new Random(seed);
        for (int i = dataGrid.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            String a = dataGrid[index];
            dataGrid[index] = dataGrid[i];
            dataGrid[i] = a;
        }

        //set foundcell for odd numbers in display grid
        if (size % 2 == 1) {
            for (int i = 0; i < dataGrid.length; i++) {
                if ("".equals(dataGrid[i])) {
                    displayGrid[i] = "";
                    isFoundGrid[i] = true;
                    break;
                }
            }
        }
    }

    /**
     * call classTest()
     *
     * @param args
     */
    public static void main(String[] args) {
        classTest();
    }

    /**
     * Perform class tests
     */
    public static void classTest() {

        PrintStream out = new PrintStream(new ByteArrayOutputStream());
        
        GameGrid grid;
        
        try {
            grid = new GameGrid(0);
        } catch (Exception ex) {
            grid = null;
        }        
        TestDriver.printTestCase(
                "TC000", 
                "GameGrid. Create GameGrid(0) throws exception.", 
                true, 
                grid == null);
        
        try {
            grid = new GameGrid(Config.MaxGridSize + 1);
        } catch (Exception ex) {
            grid = null;
        }        
        TestDriver.printTestCase(
                "TC000", 
                "GameGrid. Create GameGrid(MaxSize + 1) throws exception.", 
                true, 
                grid == null);
        
        TestDriver.printTestCase(
                "TC000", 
                "GameGrid. Brute force solve a GameGrid", 
                true, 
                solveTest(out));
    }

    /**
     * Brute force solve a GameGrid
     * @param out
     * @return 
     */
    public static Boolean solveTest(PrintStream out) {

        Integer gridSize = 5;
        GameGrid grid = new GameGrid(gridSize); //52 is an interesting case

        grid.initializeGrids(42);

        out.println("*****************************");
        out.println("* Super Secret Data Grid");
        out.println("*****************************");
        printGrid(out, grid.getDataGrid());
        out.println("*****************************\r\n");

        out.println("Start Solving");
        printGrid(out, grid.getDisplayGrid());

        Integer guesses = 0;
        String[] dispalyGrid = grid.getDisplayGrid();
        for (int i = 0; i < grid.getSize(); i++) {
            if (grid.isCellFound(i)) {
                continue;
            }
            out.println("\r\nSolving: " + dispalyGrid[i]);
            for (int j = i + 1; j < grid.getSize(); j++) {
                guesses++;
                if (grid.matchCells(i, j)) {
                    out.println("Matched: " + dispalyGrid[i] + " and " + dispalyGrid[j]);
                    out.println("Remaining: " + grid.remaining());
                    printGrid(out, grid.getDisplayGrid(i, j));
                    break;
                }
            }
        }
        out.println("\r\nSolved in " + guesses + " guesses.\r\n");
        
        return true;
    }

    /**
     * helper function to print grids
     *
     * @param grid
     */
    private static void printGrid(PrintStream out, String[] grid) {
        Integer size = ((Number) Math.sqrt(grid.length)).intValue();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                out.print(String.format("%5s", grid[i * size + j]));
            }
            out.println();
        }
    }
}