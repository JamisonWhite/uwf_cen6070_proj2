/**
 *
 * @author jamie
 */
public interface GameDriver {
    
    public void showNewGameDisplay(GameGrid data);

    public void showGrid(GameGrid data);

    public String getChoice(GameGrid data);

    public void showExit(GameGrid data);

    public int getGuessCell1(GameGrid data);

    public int getGuessCell2(GameGrid data);

    public void showGuessSuccess(GameGrid data);

    public void showGuessFailed(GameGrid data);

    public void showException(GameGrid data, Exception ex);
    
}
