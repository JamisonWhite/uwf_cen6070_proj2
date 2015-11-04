
/**
 * Main class for MultiConcentration game
 *
 * @author jwhite
 */
public class MultiConcentration {

    /**
     * Maximum size
     */
    public static final int MaxSize = 30;

    
    /**
     * Number of seconds to memorize
     */
    public static final int MemorizeSeconds = 5;
    
    /**
     * Read parameters and start game
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        MultiConcentration game = new MultiConcentration();

        if (game.parseArguments(args)) {
            game.startGame();
        } else {
            game.showUsage();
        }

    }

    /**
     * perform class tests
     */
    public static void classTest() {
        
        MultiConcentration game = new MultiConcentration();

        String[] args = {"-t", "2"};
        game.parseArguments(args);        
        game.showUsage();
        //game.startGame();
        
    }

    /**
     * Start as text
     */
    public boolean useTextApplication;

    /**
     * Start as GUI
     */
    public boolean useGuiApplication;

    /**
     * Grid size
     */
    public int size;

    /**
     * Parse arguments and write any errors
     *
     * @param args
     * @return
     */
    public boolean parseArguments(String[] args) {
        if (args.length >= 1) {
            String driver = args[0];
            if ("-t".equals(driver)) {
                useTextApplication = true;
            } else if ("-g".equals(driver)) {
                useGuiApplication = true;
            } else {
                System.out.println("Error: Unknown game type. Please try again.");
                return false;
            }
        } else {
            System.out.println("Error: Missing game type. Please try again.");
            return false;
        }
        if (args.length >= 2) {
            String sizeStr = args[1];
            size = Integer.parseInt(sizeStr);
            if (size < 2 || size > MaxSize) {
                System.out.println("Error: Invalid grid size. Please try again.");
                return false;
            }
        } else {
            System.out.println("Error: Missing grid size. Please try again.");
            return false;
        }
        return true;
    }

    /**
     * Show command line usage
     */
    public void showUsage() {
        System.out.println("********************************");
        System.out.println("* MultiConcentration Game");
        System.out.println("********************************");
        System.out.println("Command Line:");
        System.out.println(" java -ea MultiConcentration [-g|-t] gridSize");
        System.out.println("Options");
        System.out.println(" -g for the GUI interface");
        System.out.println(" -t for the text interface");
        System.out.println(" size - number of rows and columns - Valid values are between 2 and " + MaxSize);
    }

    /**
     * Start game based on given size and driver
     */
    public void startGame() {
        GameGrid grid = new GameGrid(size);
        GameDriver driver = useTextApplication ? new TextGameDriver() : new GuiGameDriver();
        GameLoop loop = new GameLoop(driver, grid);
        loop.Start();
    }

}
