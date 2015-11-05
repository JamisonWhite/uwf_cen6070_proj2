
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
        Queue<String> choices = new LinkedList();
        TestGameDriver driver = new TestGameDriver(choices);
        
        GameLoop loop = new GameLoop(driver, grid);
       
        //ACT
        
        //TC000 Execute full loop
        driver.initialize();
        choices.add("0 1"); //showGuessFailed
        choices.add("0 3"); //showGuessSuccess
        choices.add("0 100"); //showException
        choices.add("0 A"); //showException
        choices.add("R"); //showNewGameDisplay
        choices.add("Q"); //showExit
        loop.Start();
        driver.printCounts();
        

        //TC000 Execute full loop
        driver.initialize();
        choices.add("0 1"); //showGuessFailed
        choices.add("Q"); //showExit
        loop.Start();
        driver.printCounts();

        //TC000 Execute loop
        driver.initialize();
        choices.add("Q"); //showExit
        loop.Start();
        driver.printCounts();

        
        
        //ASSERT
        
        return;
        
    }

}
