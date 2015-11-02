
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jwhite
 */
public class TextGameDriver implements GameDriver {
    
    /**
     * helper function to print grids
     *
     * @param grid
     */
    public void printGrid(String[] grid) {
        Integer size = ((Number) Math.sqrt(grid.length)).intValue();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(String.format("%5s", grid[i * size + j]));
            }
            System.out.println();
        }

    }
    
    @Override
    public void showWelcome(GameGrid data) {
        System.out.println("Welcome to Concentration! (TODO Check Requirements)"); //todo check specs
    }

    @Override
    public void showGrid(GameGrid data) {
        printGrid(data.getDisplayGrid());        
    }

    @Override
    public String getChoice(GameGrid data) {        
        System.out.println("Choose..... Using test value: Q (TODO Check Requirements)"); //todo check specs
        return "Q";
    }

    @Override
    public void showExit(GameGrid data) {
        System.out.println("Good bye..... (TODO Check Requirements)"); //todo check specs
    }

    @Override
    public void showReset(GameGrid data) {
        System.out.println("Reset completed. (TODO Check Requirements)"); //todo check specs
    }

    @Override
    public int getGuessCell1(GameGrid data) {
        System.out.println("Using test value: 1 (TODO Check Requirements)"); //todo check specs
        return 1;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getGuessCell2(GameGrid data) {
        System.out.println("Using test value: 2 (TODO Check Requirements)"); //todo check specs
        return 2;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void showGuessSuccess(GameGrid data) {
        System.out.println("Sucess..... (TODO Check Requirements)"); //todo check specs
        
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void showGuessFailed(GameGrid data) {
        System.out.println("Failure..... (TODO Check Requirements)"); //todo check specs        
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void showException(GameGrid data, Exception ex) {

        System.out.println("Error: " + ex.getMessage() + "  (TODO Check Requirements)"); //todo check specs        
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
