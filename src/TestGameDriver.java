
import java.util.LinkedList;
import java.util.Queue;

/**
 * Test game driver uses predefined choices and counts number of times each
 * method is called.
 *
 * @author jwhiteasdf
 */
public class TestGameDriver implements GameDriver {

    private final Queue<String> choices;
    private int cell1;
    private int cell2;

    private GameGrid data;

    public TestGameDriver(Queue<String> choices) {
        this.choices = choices;
    }

    @Override
    public void setup(GameGrid data) {
        assert choices != null;
    }

    @Override
    public void cleanup() {
        cleanupCount++;
    }
    public int cleanupCount;

    @Override
    public Boolean isResetRequested() {
        isResetRequestedCount++;
        return false;
    }
    public int isResetRequestedCount;

    @Override
    public Boolean isExitRequested() {
        isExitRequestedCount++;
        return false;
    }
    public int isExitRequestedCount;

    @Override
    public Boolean isGuessRequested() {
        isGuessRequestedCount++;
        return false;
    }
    public int isGuessRequestedCount;

    @Override
    public void showNewGameDisplay() {
        showNewGameDisplayCount++;
    }
    public int showNewGameDisplayCount;

    @Override
    public void showExit() {
        showExitCount++;
    }
    public int showExitCount;

    @Override
    public void getChoice() {
        getChoiceCount++;
    }
    public int getChoiceCount;

    @Override
    public int getGuessCell1() {
        getGuessCell1Count++;
        return cell1;
    }
    public int getGuessCell1Count;

    @Override
    public int getGuessCell2() {
        getGuessCell2Count++;
        return cell2;
    }
    public int getGuessCell2Count;

    @Override
    public void showGuessSuccess(int cell1, int cell2) {
        showGuessSuccessCount++;
    }
    public int showGuessSuccessCount;

    @Override
    public void showGuessFailed(int cell1, int cell2) {
        showGuessFailedCount++;
    }
    public int showGuessFailedCount;

    @Override
    public void showException(Exception ex) {
        showExceptionCount++;
        lastException = ex.toString();
    }
    public int showExceptionCount;
    public String lastException = "";

    /**
     * Total method calls
     *
     * @return
     */
    public int totalCounts() {
        return showNewGameDisplayCount
                + isExitRequestedCount
                + isResetRequestedCount
                + isGuessRequestedCount
                + showGuessFailedCount
                + showGuessSuccessCount
                + getGuessCell1Count
                + getGuessCell2Count
                + showExceptionCount
                + showExitCount;
    }

    /**
     * Print the counts
     */
    public void printCounts() {
        System.out.println("showNewGameDisplayCount: " + showNewGameDisplayCount);
        System.out.println("isExitRequestedCount: " + isExitRequestedCount);
        System.out.println("isResetRequestedCount: " + isResetRequestedCount);
        System.out.println("getGuessCell1Count: " + isGuessRequestedCount);
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

        driver.setup(null);
        TestDriver.printTestCase("TC000", "TestGameDriver. setup", true, true);

        driver.showNewGameDisplay();
        TestDriver.printTestCase("TC000", "TestGameDriver. showNewGameDisplayCount", 1, driver.showNewGameDisplayCount);

        driver.showGuessFailed(1, 2);
        TestDriver.printTestCase("TC000", "TestGameDriver. showGuessFailedCount", 1, driver.showGuessFailedCount);

        driver.showGuessSuccess(1, 3);
        TestDriver.printTestCase("TC000", "TestGameDriver. showGuessSuccessCount", 1, driver.showGuessSuccessCount);

        driver.getGuessCell1();
        TestDriver.printTestCase("TC000", "TestGameDriver. getGuessCell1Count", 1, driver.getGuessCell1Count);

        driver.getGuessCell2();
        TestDriver.printTestCase("TC000", "TestGameDriver. getGuessCell2Count", 1, driver.getGuessCell2Count);

        driver.showException(new UnsupportedOperationException());
        TestDriver.printTestCase("TC000", "TestGameDriver. showExceptionCount", 1, driver.showExceptionCount);

        driver.showExit();
        TestDriver.printTestCase("TC000", "TestGameDriver. showExitCount", 1, driver.showExitCount);

        TestDriver.printTestCase("TC000", "TestGameDriver. print counts", true, driver.totalCounts() > 0);

        driver.printCounts();
        TestDriver.printTestCase("TC000", "TestGameDriver. print counts", true, true);

        driver.initialize();
        TestDriver.printTestCase("TC000", "TestGameDriver. initialize", 0, driver.totalCounts());

        driver.cleanup();
        TestDriver.printTestCase("TC000", "TestGameDriver. cleanup", true, true);
        
        TestDriver.printTestCase("TC000", "TestGameDriver. getChoice cells", false, driver.isGuessRequested());
        TestDriver.printTestCase("TC000", "TestGameDriver. getGuessCell1", 0, driver.getGuessCell1());
        TestDriver.printTestCase("TC000", "TestGameDriver. getGuessCell2", 0, driver.getGuessCell2());
        driver.getChoice();
        TestDriver.printTestCase("TC000", "TestGameDriver. getChoice R", false, driver.isResetRequested());
        driver.getChoice();
        TestDriver.printTestCase("TC000", "TestGameDriver. getChoice Q", false, driver.isExitRequested());
        driver.cleanup();
        TestDriver.printTestCase("TC000", "TestGameDriver. cleanup", true, true);
        
        
    }

}
