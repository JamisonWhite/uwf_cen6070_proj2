
import java.util.LinkedList;
import java.util.Queue;


/**
 * Main class for MultiConcentration game
 *
 * @author jwhite
 */
public class MultiConcentration {

    private GameLoop loop;
    
    /**
     * Read parameters and start game
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        MultiConcentration game = new MultiConcentration();
        game.startGame(args);
    }
    
    /**
     * Start game based on given size and driver
     * @param args
     */
    public void startGame(String[] args) {
        if (parseArguments(args)) {
            assert loop != null;
            loop.Start();
        } else {
            showUsage();
        }
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
        System.out.println(" size - number of rows and columns - Valid values are between 2 and " + Config.MaxGridSize);
    }

    /**
     * Parse arguments and write any errors
     *
     * @param args
     * @return
     */
    public boolean parseArguments(String[] args) {
        if (args.length < 2) {
            System.out.println("Error: Game type and size are required. Please try again.");
            return false;
        }
        //reset objects
        GameDriver driver;
        GameGrid grid;
        loop = null;

        //Create the driver
        String driverArg = args[0];
        if ("-t".equals(driverArg)) {
            driver = new TextGameDriver(System.in, System.out);
        } else if ("-g".equals(driverArg)) {
            driver = new GuiGameDriver();
        } else if ("-test".equals(driverArg)) {
            Queue<String> choices = new LinkedList<String>();
            choices.add("Q");        
            driver = new TestGameDriver(choices);
        } else {
            System.out.println("Error: Unknown game type. Please try again.");
            return false;
        }

        //Read the size
        String sizeStr = args[1];
        Integer size = Integer.parseInt(sizeStr);
        if (size < Config.MinGridSize || size > Config.MaxGridSize) {
            System.out.println("Error: Invalid grid size. Please try again.");
            return false;
        }
        grid = new GameGrid(size);

        //Create the loop
        loop = new GameLoop(driver, grid);
        return true;
    }


    /**
     * perform class tests
     */
    public static void classTest() {

        //todo change to using custom streams and test output
        
        MultiConcentration game = new MultiConcentration();

        game.showUsage();
        
        String[] args = {""};
        TestDriver.printTestCase("TC000", "invalid arguments", false, game.parseArguments(args));
        
        args = new String[] {"-t", "2"};
        TestDriver.printTestCase("TC000", "text game valid arguments", true, game.parseArguments(args));
        
        args = new String[] {"-g", "2"};
        TestDriver.printTestCase("TC000", "gui game valid arguments", true, game.parseArguments(args));
                
        args = new String[] {"-test", "2"};
        TestDriver.printTestCase("TC000", "test game valid arguments", true, game.parseArguments(args));
        game.startGame(args);
        TestDriver.printTestCase("TC000", "invalid arguments", true, true);
        
        

    }

}
