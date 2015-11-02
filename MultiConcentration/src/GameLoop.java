/**
 * Game loop interacts with the driver and the data.
 * 
 * @author jamie
 */
public class GameLoop {

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
        driver.showWelcome(data);

        while (true) {

            driver.showGrid(data);

            String choice = driver.getChoice(data); //hmm will this work for forms, since it's blocking?

            if ("Q".equals(choice)) {
                break;
            }

            if ("R".equals(choice)) {
                data.initializeGrids();
                driver.showReset(data);
                continue;
            }

            int cell1 = driver.getGuessCell1(data);
            int cell2 = driver.getGuessCell2(data);

            if (data.matchCells(cell1, cell2)) {
                driver.showGuessSuccess(data);
            } else {
                driver.showGuessFailed(data);
            }

        }

        driver.showExit(data);

    }

}
