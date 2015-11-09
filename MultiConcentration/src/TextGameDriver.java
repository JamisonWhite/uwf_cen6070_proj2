
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

    private GameGrid data;

    /**
     * Assert class is ready
     */
    @Override
    public void setup(GameGrid data) {
        this.data = data;
        assert outputStream != null;
        assert inputScanner != null;
    }

    /**
     * No cleanup
     */
    @Override
    public void cleanup() {
        //nothing
    }

    /**
     * Show new game message and time limited data grid
     *
     */
    @Override
    public void showNewGameDisplay() {
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
        printGrid(data.getDisplayGrid());
    }

    @Override
    public Boolean isResetRequested() {
        return resetRequested;
    }
    private Boolean resetRequested = false;

    @Override
    public Boolean isExitRequested() {
        return exitRequested;
    }
    private Boolean exitRequested = false;

    @Override
    public Boolean isGuessRequested() {
        return guessRequested;
    }
    private Boolean guessRequested = false;

    /**
     * Get users choice
     *
     */
    public void getChoice() {
        outputStream.print("Enter a pair of numbers, or \"R\" to reset, or \"Q\" to quit: ");
        String next;
        String cellChoice = "";
        cell1 = -1;
        cell2 = -1;
        exitRequested = false;
        resetRequested = false;
        guessRequested = false;
        while (inputScanner.hasNext()) {
            next = inputScanner.next();
            if ("R".equals(next)) {
                resetRequested = true;
                return;
            }
            if ("Q".equals(next)) {
                exitRequested = true;
                return;
            }
            if (cell1 < 0) {
                cellChoice = next;
                cell1 = Integer.parseInt(next);
                continue;
            }
            cell2 = Integer.parseInt(next);
            guessRequested = true;
            return;
        }
    }

    /**
     * Show exit screen
     *
     */
    @Override
    public void showExit() {
        printGrid(data.getDisplayGrid());
        outputStream.println("Game Over");
    }

    private int cell1;
    private int cell2;

    /**
     * Get last cell1 guess
     *
     * @return
     */
    @Override
    public int getGuessCell1() {
        return cell1 - 1; //one-based
    }

    /**
     * Get last cell2 guess
     *
     * @return
     */
    @Override
    public int getGuessCell2() {
        return cell2 - 1; //one-based
    }

    /**
     * Show success message
     *
     */
    @Override
    public void showGuessSuccess(int cell1, int cell2) {
        printGrid(data.getDisplayGrid(cell1, cell2));
        outputStream.println("Good Guess!");
    }

    /**
     * Show failed message
     *
     */
    @Override
    public void showGuessFailed(int cell1, int cell2) {
        printGrid(data.getDisplayGrid(cell1, cell2));
        outputStream.println("Sorry...");
    }

    /**
     * Show exception message
     *
     * @param ex
     */
    @Override
    public void showException(Exception ex) {
        printGrid(data.getDisplayGrid());
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

        driver.setup(grid);
        TestDriver.printTestCase("TC000", "TextGameDriver. setup", true, true);

        outputStream.reset();
        driver.showNewGameDisplay();
        result = outputStream.toString("UTF-8");
        TestDriver.printTestCase("TC000", "TextGameDriver. showNewGameDisplay", true, result.length() > 0);
        
        TestDriver.printTestCase("TC000", "TextGameDriver. isExitRequested", false, driver.isExitRequested());
        
        TestDriver.printTestCase("TC000", "TextGameDriver. isGuessRequested", false, driver.isGuessRequested());
        
        TestDriver.printTestCase("TC000", "TextGameDriver. isResetRequested", false, driver.isResetRequested());
        
        TestDriver.printTestCase("TC000", "TextGameDriver. getGuessCell1", true, driver.getGuessCell1() < 0);
        
        TestDriver.printTestCase("TC000", "TextGameDriver. getGuessCell2", true, driver.getGuessCell2() < 0);
        
        outputStream.reset();
        driver.showGuessFailed(1, 2);
        result = outputStream.toString("UTF-8");
        TestDriver.printTestCase("TC000", "TextGameDriver. showGuessFailed", true, result.trim().endsWith("Sorry..."));

        outputStream.reset();
        driver.showGuessSuccess(1, 4);
        result = outputStream.toString("UTF-8");
        TestDriver.printTestCase("TC000", "TextGameDriver. showGuessSuccess", true, result.trim().endsWith("Good Guess!"));

        outputStream.reset();
        driver.showException(new UnsupportedOperationException("TestError"));
        result = outputStream.toString("UTF-8");
        TestDriver.printTestCase("TC000", "TextGameDriver. showException", true, result.trim().endsWith("Error: TestError"));

        outputStream.reset();
        driver.showExit();
        result = outputStream.toString("UTF-8");
        TestDriver.printTestCase("TC000", "TextGameDriver. showExit", true, result.trim().endsWith("Game Over"));


        outputStream.reset();
        driver.getChoice();
        result = outputStream.toString("UTF-8");
        TestDriver.printTestCase("TC000", "TextGameDriver. getChoice", "Enter a pair of numbers, or \"R\" to reset, or \"Q\" to quit: ", result);

        //play the game. Display is ONE-BASED; Data is ZERO-BASED
        in = new ByteArrayInputStream("1 2\r\n1 4\r\nR\r\nQ\r\n".getBytes("UTF-8"));
        driver = new TextGameDriver(in, out);
        driver.getChoice();
        TestDriver.printTestCase("TC000", "TextGameDriver. getChoice cells", true, driver.isGuessRequested());
        TestDriver.printTestCase("TC000", "TextGameDriver. getGuessCell1", 0, driver.getGuessCell1());
        TestDriver.printTestCase("TC000", "TextGameDriver. getGuessCell2", 1, driver.getGuessCell2());
        driver.getChoice();
        TestDriver.printTestCase("TC000", "TextGameDriver. getChoice cells", true, driver.isGuessRequested());
        TestDriver.printTestCase("TC000", "TextGameDriver. getGuessCell1", 0, driver.getGuessCell1());
        TestDriver.printTestCase("TC000", "TextGameDriver. getGuessCell2", 3, driver.getGuessCell2());
        driver.getChoice();
        TestDriver.printTestCase("TC000", "TextGameDriver. getChoice R", true, driver.isResetRequested());
        driver.getChoice();
        TestDriver.printTestCase("TC000", "TextGameDriver. getChoice Q", true, driver.isExitRequested());

        driver.cleanup();
        TestDriver.printTestCase("TC000", "TextGameDriver. cleanup", true, true);

//        outputStream.reset();
//        //do stuff
//        result = outputStream.toString("UTF-8");
//        TestDriver.printTestCase("TC000", "play the game",  true, result.length() > 0);
    }

}
