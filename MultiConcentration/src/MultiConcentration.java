/**
 * Main class for MultiConcentration game
 * @author jwhite
 */
public class MultiConcentration {

    /**
     * Read parameters and start game
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (!readParameters(args)) {
            showUsage();
            return;
        }
        startGame();
    }

    /**
     * Start as text
     */
    public static boolean useTextApplication;

    /**
     * Start as GUI
     */
    public static boolean useGuiApplication;

    /**
     * Grid size
     */
    public static int size;

    /**
     * Read parameters and write any errors
     * @param args
     * @return 
     */
    public static boolean readParameters(String[] args) {
        if (args.length >= 1) {
            String driver = args[0];
            if ("-t".equals(driver)) {
                useTextApplication = true;
            } else if ("-g".equals(driver)) {
                useGuiApplication = true;
            } else {
                //todo validate size
                return false;
            }
        } else {
            return false;
        }
        if (args.length >= 2) {
            String sizeStr = args[1];
            size = Integer.parseInt(sizeStr);
            //todo validate size
        } else {
            return false;
        }
        return true;
    }

    /**
     * Show command line usage
     */
    public static void showUsage() {
        System.out.println("********************************");
        System.out.println("* MultiConcentration Game");
        System.out.println("********************************");
        System.out.println("Command Line:");
        System.out.println(" java -ea MultiConcentration [-g|-t] gridSize");
        System.out.println("Options");
        System.out.println(" -g for the GUI interface");
        System.out.println(" -t for the text interface");
        System.out.println(" size - number of rows and columns - must have a valid value");
    }

    /**
     * Start game based on given size and driver
     */
    public static void startGame() {
        GameGrid grid = new GameGrid(size);
        GameDriver driver = useTextApplication ? new TextGameDriver() : new GuiGameDriver();
        GameLoop loop = new GameLoop(driver, grid);
        loop.Start();
    }

}
