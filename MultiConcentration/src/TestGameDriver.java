
import java.util.LinkedList;
import java.util.Queue;

/**
 * Test game driver uses predefined choices and counts number of times each
 * method is called.
 *
 * @author jwhite
 */
public class TestGameDriver implements GameDriver {

    private final Queue<String> choices;    
    private int cell1;
    private int cell2;

    public TestGameDriver(Queue<String> choices) {
        this.choices = choices;
    }

    @Override
    public void showNewGameDisplay(GameGrid data) {
        showNewGameDisplayCount++;
    }

    public int showNewGameDisplayCount;

    @Override
    public void showGrid(GameGrid data) {
        showGridCount++;
    }

    public int showGridCount;

    @Override
    public String getChoice(GameGrid data) {
        getChoiceCount++;
        String choice = choices.remove();
        if (choice.contains(" ")) {
            String[] cells = choice.split(" ");
            cell1 = Integer.parseInt(cells[0]);
            cell2 = Integer.parseInt(cells[1]);
        }
        return choice;
    }
    public int getChoiceCount;

    @Override
    public void showExit(GameGrid data) {
        showExitCount++;
    }
    public int showExitCount;

    @Override
    public int getGuessCell1(GameGrid data) {
        getGuessCell1Count++;
        return cell1;
    }
    public int getGuessCell1Count;

    @Override
    public int getGuessCell2(GameGrid data) {
        getGuessCell2Count++;
        return cell2;
    }
    public int getGuessCell2Count;

    @Override
    public void showGuessSuccess(GameGrid data) {
        showGuessSuccessCount++;
    }
    public int showGuessSuccessCount;

    @Override
    public void showGuessFailed(GameGrid data) {
        showGuessFailedCount++;
    }
    public int showGuessFailedCount;

    @Override
    public void showException(GameGrid data, Exception ex) {
        showExceptionCount++;
        lastException = ex.toString();
    }
    public int showExceptionCount;
    public String lastException = "";

    /**
     * Print the counts
     */
    public void printCounts() {
        System.out.println("showNewGameDisplayCount: " + showNewGameDisplayCount);
        System.out.println("showGridCount: " + showGridCount);
        System.out.println("getChoiceCount: " + getChoiceCount);
        System.out.println("getGuessCell1Count: " + getGuessCell1Count);
        System.out.println("getGuessCell2Count: " + getGuessCell2Count);
        System.out.println("showGuessFailedCount: " + showGuessFailedCount);
        System.out.println("showGuessSuccessCount: " + showGuessSuccessCount);
        System.out.println("showExceptionCount: " + showExceptionCount);
        System.out.println("showExitCount: " + showExitCount);
        System.out.println();
    }

    /**
     * Initialize counts and cell values
     */
    public void initialize() {
        showNewGameDisplayCount = 0;
        showGridCount = 0;
        getChoiceCount = 0;
        getGuessCell1Count = 0;
        getGuessCell2Count = 0;
        showGuessFailedCount = 0;
        showGuessSuccessCount = 0;
        showExceptionCount = 0;
        showExitCount = 0;
        cell1 = 0;
        cell2 = 0;
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

        Queue<String> choices = new LinkedList<String>();
        choices.add("Q");
        TestGameDriver driver = new TestGameDriver(choices);
        
        
        driver.showNewGameDisplay(null);
        TestDriver.printTestCase("TC000", "showNewGameDisplayCount", 1, driver.showNewGameDisplayCount);
        
        driver.showGrid(null);
        TestDriver.printTestCase("TC000", "showGridCount", 1, driver.showGridCount);
        
        driver.showGuessFailed(null);
        TestDriver.printTestCase("TC000", "showGuessFailedCount", 1, driver.showGuessFailedCount);
        
        driver.showGuessSuccess(null);
        TestDriver.printTestCase("TC000", "showGuessSuccessCount", 1, driver.showGuessSuccessCount);
        
        driver.getChoice(null);
        TestDriver.printTestCase("TC000", "getChoiceCount", 1, driver.getChoiceCount);
        
        driver.getGuessCell1(null);
        TestDriver.printTestCase("TC000", "getGuessCell1Count", 1, driver.getGuessCell1Count);
        
        driver.getGuessCell2(null);
        TestDriver.printTestCase("TC000", "getGuessCell2Count", 1, driver.getGuessCell2Count);
        
        driver.showException(null, new UnsupportedOperationException());
        TestDriver.printTestCase("TC000", "showExceptionCount", 1, driver.showExceptionCount);
        
        driver.showExit(null);
        TestDriver.printTestCase("TC000", "showExitCount", 1, driver.showExitCount);
        
       
        driver.initialize();
        TestDriver.printTestCase("TC000", "initialize", 0, 
                driver.showNewGameDisplayCount +
                driver.showGridCount +
                driver.showGuessFailedCount +
                driver.showGuessSuccessCount +
                driver.getChoiceCount +
                driver.getGuessCell1Count +
                driver.getGuessCell2Count +
                driver.showExceptionCount +
                driver.showExitCount 
        );
        
        
    }

}
