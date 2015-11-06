/**
 * Game drivers are responsible for showing screens and getting input.
 * @author jamie
 */
public interface GameDriver {
    
    /**
     * Setup the driver
     */
    public void setup();
    
    /**
     * Clean up the driver and release any resources
     */
    public void cleanup();
    
    
    /**
     * Show new game message and time limited data grid
     * @param data 
     */
    public void showNewGameDisplay(GameGrid data);

    /**
     * Show the data grid
     * @param data 
     */
    public void showGrid(GameGrid data);

    /**
     * Get users choice
     * @param data
     * @return 
     */
    public String getChoice(GameGrid data);

    /**
     * Show exit screen
     * @param data 
     */
    public void showExit(GameGrid data);

    /**
     * Get last cell1 guess
     * @param data
     * @return 
     */
    public int getGuessCell1(GameGrid data);

    /**
     * Get last cell2 guess
     * @param data
     * @return 
     */
    public int getGuessCell2(GameGrid data);

    /**
     * Show success message
     * @param data 
     */
    public void showGuessSuccess(GameGrid data);

    /**
     * Show failed message
     * @param data 
     */
    public void showGuessFailed(GameGrid data);

    /** 
     * Show exception message
     * @param data
     * @param ex 
     */
    public void showException(GameGrid data, Exception ex);
    
}
