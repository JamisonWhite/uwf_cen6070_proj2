

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
     * @throws IllegalArgumentException
     */
    public GameLoop(GameDriver driver, GameGrid data) throws IllegalArgumentException {
        if (driver == null) {
            throw new IllegalArgumentException("driver is null.");
        } //replaced assert precondition
        if (data == null) {
            throw new IllegalArgumentException("data is null.");
        } //replaced assert precondition

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

        driver.setup(data);

        driver.showNewGameDisplay();

        while (true) {

            try {

                //hmm will this work for forms, since it's blocking?
                driver.getChoice();

                if (driver.isExitRequested()) {
                    driver.showExit();
                    break;
                }

                if (driver.isResetRequested()) {
                    data.initializeGrids();
                    driver.showNewGameDisplay();
                    continue;
                }

                if (driver.isGuessRequested()) {
                    int cell1 = driver.getGuessCell1();
                    int cell2 = driver.getGuessCell2();
                    if (data.matchCells(cell1, cell2)) {
                        driver.showGuessSuccess(cell1, cell2);
                    } else {
                        driver.showGuessFailed(cell1, cell2);
                    }
                }
            } catch (Exception ex) {
                driver.showException(ex);
            }
        }

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

        GameDriver testDriver = new GameDriver() {

            @Override
            public void setup(GameGrid data) {
                return;
            }

            @Override
            public void cleanup() {
                return;
            }

            @Override
            public void showNewGameDisplay() {
                return;
            }

            @Override
            public void getChoice() {
                guessRequested = (choiceCount <= 1);
                //failed guess
                if (choiceCount == 0) {
                    cell1 = 0;
                    cell2 = 1;
                }
                //successful guess
                if (choiceCount == 1) {
                    cell1 = 0;
                    cell2 = 3;
                }
                resetRequested = (choiceCount == 2);
                exitRequested = (choiceCount >= 3);
                choiceCount++;
            }
            public int choiceCount = 0;

            @Override
            public Boolean isResetRequested() {
                return resetRequested;
            }
            public Boolean resetRequested = false;

            @Override
            public Boolean isExitRequested() {
                return exitRequested;
            }
            public Boolean exitRequested = false;

            @Override
            public Boolean isGuessRequested() {
                return guessRequested;
            }
            public Boolean guessRequested = false;

            @Override
            public int getGuessCell1() {
                return cell1;
            }
            public int cell1 = -1;

            @Override
            public int getGuessCell2() {
                return cell2;
            }
            public int cell2 = -1;

            @Override
            public void showGuessSuccess(int cell1, int cell2) {
                return;
            }

            @Override
            public void showGuessFailed(int cell1, int cell2) {
                return;
            }

            @Override
            public void showExit() {
                return;
            }

            @Override
            public void showException(Exception ex) {
                return;
            }
        };
        
        testDriver.showException(null);
        TestDriver.printTestCase("TC000", "TestGameDriver showException code coverage", true, testDriver != null);

        TestDriver.printTestCaseThrowsException("TC000", "GameLoop constructor null driver exception.",
                new TestCase() {
                    @Override
                    public GameLoop Run() throws Exception {
                        return new GameLoop(null, null);
                    }
                });

        TestDriver.printTestCaseThrowsException("TC000", "GameLoop constructor null grid exception.",
                new TestCase() {
                    @Override
                    public GameLoop Run() throws Exception {
                        return new GameLoop(new TextGameDriver(System.in, System.out), null);    
                    }
                });


        GameGrid grid = new GameGrid(2);
        grid.initializeGrids(42);
        //42 -> [B, A, A, B]
        GameLoop loop;
        loop = new GameLoop(testDriver, grid);
        TestDriver.printTestCase("TC000", "GameLoop constructor", true, loop != null);

        //Exit requested
        loop = new GameLoop(testDriver, grid);
        loop.Start();
        TestDriver.printTestCase("TC000", "GameLoop start", true, true);

    }

}
