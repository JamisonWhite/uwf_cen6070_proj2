/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multiconcentration;

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
        
        if (showHelp) {
            showHelp();
        }
        
        if (useTextApplication) {
            startTextApplication();
        }

        if (useGuiApplication) {
            startGuiApplication();
        }


    }

    public static boolean useTextApplication;

    public static boolean useGuiApplication;

    public static boolean showHelp;

    public static void readParameters() {
        System.out.println("NotImplementedException: read parameters is not implemented.");
    }

    public static boolean validateParameters() {
        System.out.println("NotImplementedException: validate parameters is not implemented.");
        return false;
    }

    public static void startGuiApplication() {
        System.out.println("NotImplementedException: start GUI application is not implemented.");
    }

    public static void startTextApplication() {
        System.out.println("NotImplementedException: start text application is not implemented.");
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
