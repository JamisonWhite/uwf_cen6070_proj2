/**
 * Game drivers are responsible for showing screens and getting input.
 * @author jamie
 */
public interface GameDriver {
    
    /**
     * Setup the driver
     * @param data
     */
    public void setup(GameGrid data);
    
    /**
     * Clean up the driver and release any resources
     */
    public void cleanup();
    
    
    /**
     * Show new game message and time limited data grid
     */
    public void showNewGameDisplay();

    /**
     * Get the choice
     */
    public void getChoice();
    
    /**
     * Show the data grid
     * @return 
     */
    public Boolean isResetRequested();
    
    /**
     * Show the data grid
     * @return 
     */
    public Boolean isExitRequested();

    /**
     * Show the data grid
     * @return 
     */
    public Boolean isGuessRequested();
    
    /**
     * Get last cell1 guess
     * @return 
     */
    public int getGuessCell1();

    /**
     * Get last cell2 guess
     * @return 
     */
    public int getGuessCell2();

    /**
     * Show success message
     * @param cell1
     * @param cell2
     */
    public void showGuessSuccess(int cell1, int cell2);

    /**
     * Show failed message
     * @param cell1
     * @param cell2
     */
    public void showGuessFailed(int cell1, int cell2);

       
    /**
     * Show exit screen
     */
    public void showExit();
    
    /** 
     * Show exception message
     * @param ex 
     */
    public void showException(Exception ex);
    
}
