/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jamie
 */
public interface GameDriver {
    
    public void showWelcome(GameGrid data);

    public void showGrid(GameGrid data);

    public String getChoice(GameGrid data);

    public void showExit(GameGrid data);

    public void showReset(GameGrid data);

    public int getGuessCell1(GameGrid data);

    public int getGuessCell2(GameGrid data);

    public void showGuessSuccess(GameGrid data);

    public void showGuessFailed(GameGrid data);
    
}
