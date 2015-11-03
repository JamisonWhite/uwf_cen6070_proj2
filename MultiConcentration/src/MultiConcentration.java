
/**
 *
 * @author jwhite
 */
public class MultiConcentration {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (!readParameters(args)) {
            showHelp();
            return;
        }
        startGame();
    }

    public static boolean useTextApplication;

    public static boolean useGuiApplication;

    public static int size;

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

    public static void showHelp() {
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

    public static void startGame() {
        GameGrid grid = new GameGrid(size);
        GameDriver driver = useTextApplication ? new TextGameDriver() : new GuiGameDriver();
        GameLoop loop = new GameLoop(driver, grid);
        loop.Start();
    }

}
