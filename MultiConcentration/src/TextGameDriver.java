import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Text game driver
 * @author jwhite
 */
public class TextGameDriver implements GameDriver {

    /**
     * Initialize state
     * @param inputStream
     * @param outputStream
     */
    public TextGameDriver(InputStream inputStream, PrintStream  outputStream) {
        this.outputStream = outputStream;
        this.inputScanner = new Scanner(inputStream);
        cell1 = -1;
        cell2 = -1;
    }
    private final Scanner inputScanner;
    private final PrintStream outputStream;

    
    /**
     * Show new game message and time limited data grid
     * @param data 
     */
    @Override
    public void showNewGameDisplay(GameGrid data) {
        printGrid(data.getDataGrid());
        outputStream.print("Memorize the above grid! ");
        for (int i = MultiConcentration.MemorizeSeconds; i >= 0; i--) {
            try {
                outputStream.print(i + " ");
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(TextGameDriver.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        for (int i = 0; i < 25; i++) {
            outputStream.println();
        }
    }

    /**
     * Show the data grid
     * @param data 
     */
    @Override
    public void showGrid(GameGrid data) {
        if (cell1 < 0 || cell2 < 0) {
            printGrid(data.getDisplayGrid());

        } else {
            printGrid(data.getDisplayGrid(cell1, cell2));
        }
    }

    /**
     * Get users choice
     * @param data
     * @return 
     */
    @Override
    public String getChoice(GameGrid data) {
        outputStream.print("Enter a pair of numbers, or \"R\" to reset, or \"Q\" to quit: ");
        String result;
        cell1 = -1;
        cell2 = -1;
        while (inputScanner.hasNext()) {
            result = inputScanner.next();
            if ("R".equals(result)) {
                return "R";
            }
            if ("Q".equals(result)) {
                return "Q";
            }
            //Read the numbers; Display is 1-based
            if (cell1 < 0) {
                cell1 = Integer.parseInt(result) - 1;
                continue;
            }
            cell2 = Integer.parseInt(result) - 1;
            break;
        }
        //todo add "Please reentered" message for invalid stuff
        result = cell1 + " " + cell2;
        outputStream.println("Selection : " + result);
        return result;
    }

    /**
     * Show exit screen
     * @param data 
     */
    @Override
    public void showExit(GameGrid data) {
        outputStream.println("Game Over");
    }

    private int cell1;
    private int cell2;

    /**
     * Get last cell1 guess
     * @param data
     * @return 
     */
    @Override
    public int getGuessCell1(GameGrid data) {
        return cell1;
    }

    /**
     * Get last cell2 guess
     * @param data
     * @return 
     */
    @Override
    public int getGuessCell2(GameGrid data) {
        return cell2;
    }

    /**
     * Show success message
     * @param data 
     */
    @Override
    public void showGuessSuccess(GameGrid data) {
        outputStream.println("Good Guess!");
    }

    /**
     * Show failed message
     * @param data 
     */
    @Override
    public void showGuessFailed(GameGrid data) {
        outputStream.println("Sorry...");
    }

    /** 
     * Show exception message
     * @param data
     * @param ex 
     */
    @Override
    public void showException(GameGrid data, Exception ex) {
        outputStream.println("Error: " + ex.getMessage());
    }

    /**
     * helper function to print grids
     *
     * @param grid
     */
    public void printGrid(String[] grid) {
        Integer size = ((Number) Math.sqrt(grid.length)).intValue();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                outputStream.print(String.format("%5s", grid[i * size + j]));
            }
            outputStream.println();
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
    public static void classTest()  {

        //todo read and write from stream
        InputStream in;
        try {
            in = new ByteArrayInputStream("Q\r\n".getBytes("UTF-8"));
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(TextGameDriver.class.getName()).log(Level.SEVERE, null, ex);
        }
        PrintStream out = new PrintStream(new ByteArrayOutputStream());
        
        TextGameDriver driver = new TextGameDriver(System.in, out);
        GameGrid grid = new GameGrid(4);
        grid.initializeGrids(42);
        //42 -> [B, A, A, B]
        
        
        driver.showNewGameDisplay(grid);
        TestDriver.printTestCase("TC000", "showNewGameDisplay", true, true);
        
        driver.showGrid(grid);
        TestDriver.printTestCase("TC000", "showGrid",  true, true);
        
        driver.showGuessFailed(grid);
        TestDriver.printTestCase("TC000", "showGuessFailed",  true, true);
        
        driver.showGuessSuccess(grid);
        TestDriver.printTestCase("TC000", "showGuessSuccess",  true, true);
        
        driver.getGuessCell1(grid);
        TestDriver.printTestCase("TC000", "getGuessCell1",  true, true);
        
        driver.getGuessCell2(grid);
        TestDriver.printTestCase("TC000", "getGuessCell2",  true, true);
        
        driver.showException(grid, new UnsupportedOperationException());
        TestDriver.printTestCase("TC000", "showException",  true, true);
        
        driver.showExit(grid);
        TestDriver.printTestCase("TC000", "showExit",   true, true);
        
//        driver.getChoice(grid);
//        TestDriver.printTestCase("TC000", "getChoice",  true, true);
        
    }
}
