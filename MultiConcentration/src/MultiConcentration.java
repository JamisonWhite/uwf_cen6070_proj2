/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author jwhite
 */
public class MultiConcentration {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        readParameters();
        
        if (!validateParameters()) {
            showHelp();
            return;
        }
        
        startGame();
    }

    public static boolean useTextApplication;

    public static boolean useGuiApplication;

    public static int size;
    
    public static void readParameters() {
        System.out.println("NotImplementedException: read parameters is not implemented. Using test values: -t 10");
        size = 10;
        useTextApplication = true;
    }

    public static boolean validateParameters() {
        System.out.println("NotImplementedException: validate parameters is not implemented. Using test values: true");
        return true;
    }

    public static void startGame() {
        GameGrid grid = new GameGrid(size);
        GameDriver driver = useTextApplication ? new TextGameDriver() : new GuiGameDriver();
        GameLoop loop = new GameLoop(driver, grid);
        loop.Start();
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

}
