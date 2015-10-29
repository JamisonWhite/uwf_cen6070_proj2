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

        printHelp();      
        
        
        
    }
    
    public static void printHelp() {
        
        System.out.println("MultiConcentration Game");
        System.out.println("Command Line:");
        System.out.println(" java -ea MultiConcentration [-g|-t] gridSize");
        System.out.println("Options");
        System.out.println(" -g for the GUI interface");
        System.out.println(" -t for the text interface");
        System.out.println(" size - number of rows and columns - must have a valid value");
        
    }
    
}
