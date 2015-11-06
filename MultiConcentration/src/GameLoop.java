
import java.util.LinkedList;
import java.util.Queue;

/**
 * Game loop interacts with the driver and the data.
 *
 * @author jamie
 */
public class GameLoop {

    /**
     * Pass in driver and data to use
     *
     * @param driver
     * @param data
     */
    public GameLoop(GameDriver driver, GameGrid data) {
        this.driver = driver;
        this.data = data;
    }

    private final GameDriver driver;

    private final GameGrid data;

    /**
     * Start the game loop.
     */
    public void Start() {

        
        driver.setup();
        
        data.initializeGrids();
        
        driver.showNewGameDisplay(data);

        while (true) {

            try {

                driver.showGrid(data);

                //hmm will this work for forms, since it's blocking?
                String choice = driver.getChoice(data);

                if ("Q".equals(choice)) {
                    break;
                }

                if ("R".equals(choice)) {
                    data.initializeGrids();
                    driver.showNewGameDisplay(data);
                    continue;
                }

                int cell1 = driver.getGuessCell1(data);
                int cell2 = driver.getGuessCell2(data);
                if (data.matchCells(cell1, cell2)) {
                    driver.showGuessSuccess(data);
                } else {
                    driver.showGuessFailed(data);
                }

            } catch (Exception ex) {
                driver.showException(data, ex);
            }
        }

        driver.showExit(data);
        
        driver.cleanup();

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

        //ARRANGE
        GameGrid grid = new GameGrid(2);
        grid.initializeGrids(42);
        //42 -> [B, A, A, B]
        Queue<String> choices = new LinkedList<String>();
        TestGameDriver driver = new TestGameDriver(choices);
        
        GameLoop loop = new GameLoop(driver, grid);
       
        //TC000 Execute full loop
        driver.initialize();
        choices.add("Q"); //showExit
        loop.Start();
        TestDriver.printTestCase("TC000", "GameLoop. quit game", true, driver.totalCounts() > 0);

        //TC000 Execute loop
        driver.initialize();
        choices.add("R"); //showreset
        choices.add("Q"); //showExit
        loop.Start();
        TestDriver.printTestCase("TC000", "GameLoop. reset game", true, driver.totalCounts() > 0);


        
        driver.initialize();
        choices.add("1 2"); //showGuessFailed
        choices.add("Q"); //showExit
        loop.Start();
        TestDriver.printTestCase("TC000", "GameLoop. guess failed", true, driver.totalCounts() > 0);

        
        driver.initialize();
        choices.add("1 4"); //showGuessSuccess
        choices.add("Q"); //showExit
        loop.Start();
        TestDriver.printTestCase("TC000", "GameLoop. guess success", true, driver.totalCounts() > 0);
        
        driver.initialize();
        choices.add("0 100"); //showException
        choices.add("0 A"); //showException
        choices.add("Q"); //showExit
        loop.Start();
        TestDriver.printTestCase("TC000", "GameLoop. exception", true, driver.totalCounts() > 0);

        
        
        //TC000 Execute full loop
        driver.initialize();
        choices.add("1 2"); //showGuessFailed
        choices.add("1 4"); //showGuessSuccess
        choices.add("2 3"); //showException
        choices.add("Q"); //showExit
        loop.Start();
        TestDriver.printTestCase("TC000", "GameLoop. play full game", true, driver.totalCounts() > 0);
        
    }

}
