
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;


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
        if (driver == null){
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
        try {
            classTest();
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(GameLoop.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Perform class tests
     */
    public static void classTest() throws UnsupportedEncodingException {

        //ARRANGE
        GameGrid grid = new GameGrid(2);       
        
        grid.initializeGrids(42);
        //42 -> [B, A, A, B]

        String result;

        //Read and write from custom streams
        InputStream in = new ByteArrayInputStream("Q\r\n".getBytes("UTF-8"));
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(outputStream);
        TextGameDriver driver = new TextGameDriver(in, out);

        GameLoop loop = new GameLoop(driver, grid);
    }

}
