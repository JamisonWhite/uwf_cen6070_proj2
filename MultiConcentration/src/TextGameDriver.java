
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
 *
 * @author jwhite
 */
public class TextGameDriver implements GameDriver {

    /**
     * Initialize state
     *
     * @param inputStream
     * @param outputStream
     */
    public TextGameDriver(InputStream inputStream, PrintStream outputStream) {
        this.outputStream = outputStream;
        this.inputScanner = new Scanner(inputStream);
        cell1 = -1;
        cell2 = -1;
    }
    private final Scanner inputScanner;
    private final PrintStream outputStream;

    /**
     * Show new game message and time limited data grid
     *
     * @param data
     */
    @Override
    public void showNewGameDisplay(GameGrid data) {
        printGrid(data.getDataGrid());
        outputStream.print("Memorize the above grid! ");
        for (int i = Config.MemorizeSeconds; i >= 0; i--) {
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
     *
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
     *
     * @param data
     * @return
     */
    @Override
    public String getChoice(GameGrid data) {
        outputStream.print("Enter a pair of numbers, or \"R\" to reset, or \"Q\" to quit: ");
        String next;
        String cellChoice = "";
        cell1 = -1;
        cell2 = -1;
        while (inputScanner.hasNext()) {
            next = inputScanner.next();
            if ("R".equals(next)) {
                return "R";
            }
            if ("Q".equals(next)) {
                return "Q";
            }
            //Read the numbers; Display is 1-based
            if (cell1 < 0) {
                cellChoice = next;
                cell1 = Integer.parseInt(next) - 1;
                continue;
            }
            cell2 = Integer.parseInt(next) - 1;
            cellChoice = cellChoice + " " + next;
            break;
        }
        //todo add "Please reentered" message for invalid stuff
        return cellChoice;
    }

    /**
     * Show exit screen
     *
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
     *
     * @param data
     * @return
     */
    @Override
    public int getGuessCell1(GameGrid data) {
        return cell1;
    }

    /**
     * Get last cell2 guess
     *
     * @param data
     * @return
     */
    @Override
    public int getGuessCell2(GameGrid data) {
        return cell2;
    }

    /**
     * Show success message
     *
     * @param data
     */
    @Override
    public void showGuessSuccess(GameGrid data) {
        outputStream.println("Good Guess!");
    }

    /**
     * Show failed message
     *
     * @param data
     */
    @Override
    public void showGuessFailed(GameGrid data) {
        outputStream.println("Sorry...");
    }

    /**
     * Show exception message
     *
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
        try {
            classTest();
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(TextGameDriver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Perform class tests
     */
    public static void classTest() throws UnsupportedEncodingException {

        GameGrid grid = new GameGrid(4);
        grid.initializeGrids(42);
        //42 -> [B, A, A, B]
        
        String result;
        
        //Read and write from custom streams
        InputStream in = new ByteArrayInputStream("Q\r\n".getBytes("UTF-8"));
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(outputStream);
        TextGameDriver driver = new TextGameDriver(in, out);

        outputStream.reset();
        driver.showNewGameDisplay(grid);
        result = outputStream.toString("UTF-8");
        TestDriver.printTestCase("TC000", "TextGameDriver. showNewGameDisplay", true, result.length() > 0);

        outputStream.reset();
        driver.showGrid(grid);
        result = outputStream.toString("UTF-8");
        TestDriver.printTestCase("TC000", "TextGameDriver. showGrid", true, result.length() > 0);

        outputStream.reset();
        driver.showGuessFailed(grid);
        result = outputStream.toString("UTF-8");
        TestDriver.printTestCase("TC000", "TextGameDriver. showGuessFailed", "Sorry...", result.trim());

        outputStream.reset();
        driver.showGuessSuccess(grid);
        result = outputStream.toString("UTF-8");
        TestDriver.printTestCase("TC000", "TextGameDriver. showGuessSuccess", "Good Guess!", result.trim());

        outputStream.reset();
        driver.showException(grid, new UnsupportedOperationException("TestError"));
        result = outputStream.toString("UTF-8");
        TestDriver.printTestCase("TC000", "TextGameDriver. showException", "Error: TestError", result.trim());

        outputStream.reset();
        driver.showExit(grid);
        result = outputStream.toString("UTF-8");
        TestDriver.printTestCase("TC000", "TextGameDriver. showExit", "Game Over", result.trim());

        driver.getGuessCell1(grid);
        TestDriver.printTestCase("TC000", "TextGameDriver. getGuessCell1", -1, -1);

        driver.getGuessCell2(grid);
        TestDriver.printTestCase("TC000", "TextGameDriver. getGuessCell2", -1, -1);

        outputStream.reset();
        driver.getChoice(grid);
        result = outputStream.toString("UTF-8");
        TestDriver.printTestCase("TC000", "TextGameDriver. getChoice",  "Enter a pair of numbers, or \"R\" to reset, or \"Q\" to quit: ", result);
        
        //play the game. Display is ONE-BASED; Data is ZERO-BASED
        in = new ByteArrayInputStream("1 2\r\n1 4\r\nR\r\nQ\r\n".getBytes("UTF-8"));
        driver = new TextGameDriver(in, out);
        result = driver.getChoice(grid);
        TestDriver.printTestCase("TC000", "TextGameDriver. getChoice cells",  "1 2", result);
        TestDriver.printTestCase("TC000", "TextGameDriver. getGuessCell1",  0, driver.getGuessCell1(grid));
        TestDriver.printTestCase("TC000", "TextGameDriver. getGuessCell2",  1, driver.getGuessCell2(grid));   
        result = driver.getChoice(grid);
        TestDriver.printTestCase("TC000", "TextGameDriver. getChoice cells",  "1 4", result);
        TestDriver.printTestCase("TC000", "TextGameDriver. getGuessCell1",  0, driver.getGuessCell1(grid));
        TestDriver.printTestCase("TC000", "TextGameDriver. getGuessCell2",  3, driver.getGuessCell2(grid)); 
        result = driver.getChoice(grid);
        TestDriver.printTestCase("TC000", "TextGameDriver. getChoice R",  "R", result);
        result = driver.getChoice(grid);
        TestDriver.printTestCase("TC000", "TextGameDriver. getChoice Q",  "Q", result);
        
        
        
//        outputStream.reset();
//        //do stuff
//        result = outputStream.toString("UTF-8");
//        TestDriver.printTestCase("TC000", "play the game",  true, result.length() > 0);
        
        
        
        
    }
}
