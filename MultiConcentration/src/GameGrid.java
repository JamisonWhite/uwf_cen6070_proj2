
import java.util.Random;

/**
 * GameGrid stores the game state
 *
 * @author jwhite
 */
public class GameGrid {

    public GameGrid(int size) {
        //todo validate size
        //must be even
        //muste less than 10?
        this.size = size;
        sizeSquared = size * size;
        dataGrid = new String[sizeSquared];
        displayGrid = new String[sizeSquared];
        foundCell = " ";
    }

    private final int size;
    private final int sizeSquared;
    private final String foundCell;
    private final String[] dataGrid;
    private final String[] displayGrid;

    /**
     * Get the size of the grid
     * @return 
     */
    public int getSize() {
        return size;
    }

    
    /**
     * Get the display grid
     * @return 
     */
    public String[] getDisplayGrid() {
        return displayGrid;
    }    

    /**
     * Get the display grid showing cell's data
     * @param cell
     * @return 
     */
    public String[] showCell(int cell) {
        if (cell < 0 || cell >= sizeSquared) {
            throw new IllegalArgumentException("Cell out of range.");
        }
        String[] result = displayGrid.clone();
        result[cell] = dataGrid[cell];
        return result;
    }

    /**
     * Guess and set the cell
     * @param cell1
     * @param cell2
     * @return 
     */
    public Boolean isMatch(int cell1, int cell2) {
        if (cell1 < 0 || cell1 >= sizeSquared) {
            throw new IllegalArgumentException("Cell1 out of range.");
        }
        if (cell2 < 0 || cell2 >= sizeSquared) {
            throw new IllegalArgumentException("Cell2 out of range.");
        }
        if (dataGrid[cell1].equals(dataGrid[cell2])) {
            displayGrid[cell1] = foundCell;
            displayGrid[cell2] = foundCell;
            return true;
        }
        return false;
    }
    
    /**
     * Number of remaining matches
     * @return 
     */
    public int remaining() {
        int result = 0;
        for(int i = 0; i < sizeSquared; i++) {
            if (!foundCell.equals(displayGrid[i])) {
                result++;
            }
        }
        return result / 2;
    }

    /**
     * Initialize display and data grids
     * @param seed 
     */
    public void initializeGrids(int seed) {

        //initialize values
        char data = 'A';
        for (int i = 0; i < sizeSquared; i++) {
            //display grid is 1 to size
            displayGrid[i] = Integer.toString(i + 1);
            //data grid doubles each value and increments if more than 2 * 26 
            //are needed
            String value = "" + data;
            if (sizeSquared > 52) {
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
        if (sizeSquared % 2 == 1) {
            dataGrid[sizeSquared - 1] = " ";
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
        if (sizeSquared % 2 == 1) {
            for (int i = 0; i < dataGrid.length; i++) {
                if (foundCell.equals(dataGrid[i])) {
                    displayGrid[i] = foundCell;
                    break;
                }
            }
        }

    }

    /**
     * Print grids to system out. This is just a helper function.
     */
    private void printGrids() {
        //todo move to text game
        System.out.println("Display Grid");
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(String.format("%5s", displayGrid[i * size + j]));
            }
            System.out.println();
        }
        System.out.println();

        System.out.println("Data Grid");

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(String.format("%5s", dataGrid[i * size + j]));
            }
            System.out.println();
        }
        System.out.println();

    }

    public static void main(String[] args) {

        GameGrid grid = new GameGrid(5); //52 is an interesting case
        grid.initializeGrids(42);
        grid.printGrids();
        grid.initializeGrids(43);
        grid.printGrids();

    }

}
