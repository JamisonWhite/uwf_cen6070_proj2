
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * <p>
 * Desktop application version of Multi Concentration game.</p>
 *
 * @author Justin Lambert, Salina Hall, Jamie (Robert) White, Mike Worn
 * @version 1.0.0 November 15, 2015 CEN6070	Project #: 2 File Name:
 * GuiGameDriver.java
 */
public class GuiGameDriver extends JFrame implements GameDriver {

    //=============================================
    // Attributes - Instance variables
    //=============================================
    
    private JMenuBar menubar;
    private JMenuItem fileMenuItem; // main file menu
    private JMenuItem resetMenuItem; // To reset the application grid
    private JMenuItem exitMenuItem; // To exit the application

    private JPanel gameBoard;
    private ArrayList<JButton> gameButtons;

    private JPanel statusBar;
    private JLabel gameStatus;

    private int guess1;
    private int guess2;
    private String choice;

    //=============================================
    // Static Attributes/variables
    //=============================================
    
    private static final int MIN_FRAME_WIDTH = 350;
    private static final int MIN_FRAME_HEIGHT = 350;

    // <editor-fold defaultstate="collapsed" desc="Default Constructor">
    /**
     * Default Constructor
     */
    public GuiGameDriver() {

    }
    // </editor-fold>

    // =============================================
    // Private Implementation Methods
    // =============================================
    
    // <editor-fold defaultstate="collapsed" desc="createMenuBar">
    /**
     * Construct the main menu at the top of the application
     */
    private void createMenuBar() {

        this.menubar = new JMenuBar();
        //ImageIcon icon = new ImageIcon("exit.png");

        this.fileMenuItem = new JMenu("File");
        this.fileMenuItem.setMnemonic(KeyEvent.VK_F);

        this.resetMenuItem = new JMenuItem("Reset");
        this.resetMenuItem.setMnemonic(KeyEvent.VK_E);
        this.resetMenuItem.setToolTipText("Reset");
        this.resetMenuItem.addActionListener(new ResetMenuListener());

        this.exitMenuItem = new JMenuItem("Exit");
        this.exitMenuItem.setMnemonic(KeyEvent.VK_E);
        this.exitMenuItem.setToolTipText("Exit application");
        this.exitMenuItem.addActionListener(new ExitMenuListener());

        this.fileMenuItem.add(this.resetMenuItem);
        this.fileMenuItem.add(this.exitMenuItem);
        this.menubar.add(this.fileMenuItem);
        setJMenuBar(menubar);
        
        assert getJMenuBar() != null;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="createGameBoard">
    /**
     * Construct the game board in the center of the application
     */
    private void createGameBoard(GameGrid data) {
        assert data != null;
        assert this.gameBoard != null;
        
        //TODO assert: Determine if next line should stay or Assert modified
        if (this.gameBoard != null)
            return;
        
        this.gameBoard = new JPanel();
        assert this.gameBoard != null;
        
        int gameButtonIndex = 1;
        Integer size = ((Number) Math.sqrt(data.getSize())).intValue();
        assert size > 0;

        // Set the application window dimensions and don't allow resizing
        int generatedGameBoardWidth = (64 * size);
        int generatedGameBoardHeight = (32 * size) + 50;
        
        //TODO assert: MAX's?
        if (generatedGameBoardWidth < MIN_FRAME_WIDTH) {
            if (generatedGameBoardHeight < MIN_FRAME_HEIGHT) {
                setSize(MIN_FRAME_WIDTH, MIN_FRAME_HEIGHT);
            } else {
                setSize(MIN_FRAME_WIDTH, generatedGameBoardHeight);
            }
        } else {
            if (generatedGameBoardHeight < MIN_FRAME_HEIGHT) {
                setSize(generatedGameBoardWidth, MIN_FRAME_HEIGHT);
            } else {
                setSize(generatedGameBoardWidth, generatedGameBoardHeight);
            }
        }

        GridLayout layout = new GridLayout(size, size);

        this.gameBoard.setLayout(layout);

        this.gameButtons = new ArrayList<JButton>();

        // Loop through elements.
        assert size > 1; //Min Size is 2x2
        for (int i = 0; i < size; i++) {

            for (int j = 0; j < size; j++) {
                //this.gameButtons.add(new JButton(Integer.toString(gameButtonIndex)));
                this.gameButtons.add(new JButton());
                this.gameBoard.add(this.gameButtons.get(gameButtonIndex - 1));
                gameButtonIndex++;
            }
        }

        this.add(this.gameBoard, BorderLayout.CENTER);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="createStatusBar">
    /**
     * Construct the game board in the center of the application
     */
    private void createStatusBar() {
        Font font = new Font("Courier", Font.BOLD, 14);
        this.gameStatus = new JLabel("GOOD GUESS!", SwingConstants.CENTER);
        this.gameStatus.setFont(font);
        this.gameStatus.setBackground(Color.WHITE);

        this.statusBar = new JPanel();
        this.statusBar.setLayout(new BorderLayout());
        this.statusBar.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        this.statusBar.setBackground(Color.WHITE);
        this.statusBar.add(this.gameStatus, BorderLayout.CENTER);

        this.add(this.statusBar, BorderLayout.SOUTH);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="redrawGameBoard">
    /**
     * Redraws the gameboard with the specified grid data
     *
     * @param gridData Could be the displayGrid or the dataGrid
     */
    private void redrawGameBoard(String[] gridData) {
        assert gridData != null;
        assert gridData.length > 3;  //Min Size is 2x2 (sqrt(4))
        assert this.gameButtons.isEmpty() == false;
        
        Integer size = ((Number) Math.sqrt(gridData.length)).intValue();
        int gameButtonIndex = 0;

        // Loop through elements.
        assert size > 1; //Min Size is 2x2
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                this.gameButtons.get(gameButtonIndex).setText(String.format("%5s", gridData[gameButtonIndex]));
                gameButtonIndex++;
            }
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="setup">
    /**
     * Setup the GUI elements
     */
    @Override
    public void setup() {

        this.guess1 = -1;
        assert this.guess1 < 0;
        this.guess2 = -1;
        assert this.guess2 < 0;

        // Set application title and exit button
        setTitle("The Multi-Concentration Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        createMenuBar();

        // Create a BorderLayout for the control sections
        setLayout(new BorderLayout());

        createStatusBar();

        // Set the application window dimensions and don't allow resizing
        setSize(MIN_FRAME_WIDTH, MIN_FRAME_HEIGHT);

        setVisible(true);
        setResizable(true);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="showNewGameDisplay">
    /**
     * Show new game message and time limited data grid
     *
     * @param data
     */
    @Override
    public void showNewGameDisplay(GameGrid data) {

        //TODO assert: This should probably throw an IllegalArgumentException per 
        //"By convention, preconditions on public methods are enforced by explicit checks that throw particular, specified exceptions."
        //This would apply to all Public Methods of the interface
        /** EXAMPLE
  * Sets the refresh rate.
  *
  * @param  rate refresh rate, in frames per second.
  * @throws IllegalArgumentException if rate <= 0 or
  * rate > MAX_REFRESH_RATE.

public void setRefreshRate(int rate) {
  // Enforce specified precondition in public method
  if (rate <= 0 || rate > MAX_REFRESH_RATE)
    throw new IllegalArgumentException("Illegal rate: " + rate);
    setRefreshInterval(1000/rate);
  }*/
        
        this.gameStatus.setText("Memorize the above grid!");
        this.createGameBoard(data);

        this.redrawGameBoard(data.getDataGrid());

        for (int i = Config.MemorizeSeconds; i >= 0; i--) {
            try {
                this.gameStatus.setText("Memorize the above grid! " + i);
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(TextGameDriver.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="showGrid">
    /**
     * Show the data grid
     *
     * @param data
     */
    @Override
    public void showGrid(GameGrid data) {
        if (this.guess1 < 0 || this.guess2 < 0) {
            this.redrawGameBoard(data.getDisplayGrid());
        } else {
            this.redrawGameBoard(data.getDisplayGrid(this.guess1, this.guess2));
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="getChoice">
    /**
     * Get users choice
     *
     * @param data
     * @return
     */
    @Override
    public String getChoice(GameGrid data) {
        //ugh, tight loop.
        this.gameStatus.setText("Select a pair of numbers.");
        
        try {
            while (choice == null || "".equals(choice)) {
                Thread.sleep(5);
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(GuiGameDriver.class.getName()).log(Level.SEVERE, null, ex);
        }
        return choice;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="showExit">
    /**
     * Show exit screen
     *
     * @param data
     */
    @Override
    public void showExit(GameGrid data) {
        this.gameStatus.setText("Game Over");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(GuiGameDriver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="getGuessCell1">
    /**
     * Get last cell1 guess
     *
     * @param data
     * @return
     */
    @Override
    public int getGuessCell1(GameGrid data) {
        return this.guess1;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="getGuessCell2">
    /**
     * Get last cell2 guess
     *
     * @param data
     * @return
     */
    @Override
    public int getGuessCell2(GameGrid data) {
        return this.guess2;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="showGuessSuccess">
    /**
     * Show success message
     *
     * @param data
     */
    @Override
    public void showGuessSuccess(GameGrid data) {
        this.gameStatus.setText("Good Guess!");
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="showGuessFailed">
    /**
     * Show failed message
     *
     * @param data
     */
    @Override
    public void showGuessFailed(GameGrid data) {
        this.gameStatus.setText("Sorry...");
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="showException">
    /**
     * Show exception message
     *
     * @param data
     * @param ex
     */
    @Override
    public void showException(GameGrid data, Exception ex) {
        this.gameStatus.setText("Error: " + ex.getMessage());
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="cleanup">
    @Override
    public void cleanup() {
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }
    // </editor-fold>

    // =============================================
    // Event Listener classes
    // =============================================
    
    // <editor-fold defaultstate="collapsed" desc="ResetMenuListener">
    /**
     * Event handler for the Reset Menu option
     */
    private class ResetMenuListener implements ActionListener {

        /**
         * Log to a selected file and/or the database when the Log Button is
         * clicked
         *
         * @param e the event component source
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            choice = "R";
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="ExitMenuListener">
    /**
     * Event handler for the Exit Menu option
     */
    private class ExitMenuListener implements ActionListener {

        /**
         * Simply exit the application
         *
         * @param e the event component source
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            choice = "Q";
        }
    }
    // </editor-fold>

    /**
     * call classTest()
     *
     * @param args
     */
    public static void main(String[] args) {
        classTest();
    }

    /**
     * Perform class tests
     */
    public static void classTest() {
//        GameGrid grid = new GameGrid(6);
//        grid.initializeGrids(42);
//        //42 -> [B, A, A, B]
//
//        GameDriver driver = new GuiGameDriver();
//        driver.setup();
//        driver.showNewGameDisplay(grid);
//        driver.showExit(grid);
//        driver.cleanup();
        
        
        GameGrid grid = new GameGrid(4);
        grid.initializeGrids(42);
        //42 -> [B, A, A, B]
        
        String choice;
        
        //Read and write from custom streams
        GuiGameDriver driver = new GuiGameDriver();
        
        driver.setup();
        TestDriver.printTestCase("TC000", "GuiGameDriver. setup",true, true);
              
        driver.showNewGameDisplay(grid);
        TestDriver.printTestCase("TC000", "GuiGameDriver. showNewGameDisplay", true, driver.gameStatus.getText().length() > 0);

        driver.showGrid(grid);
        TestDriver.printTestCase("TC000", "GuiGameDriver. showGrid", true, driver.gameStatus.getText().length() > 0);

        driver.showGuessFailed(grid);
        TestDriver.printTestCase("TC000", "GuiGameDriver. showGuessFailed", "Sorry...", driver.gameStatus.getText().trim());

        driver.showGuessSuccess(grid);
        TestDriver.printTestCase("TC000", "GuiGameDriver. showGuessSuccess", "Good Guess!", driver.gameStatus.getText().trim());

        driver.showException(grid, new UnsupportedOperationException("TestError"));
        TestDriver.printTestCase("TC000", "GuiGameDriver. showException", "Error: TestError", driver.gameStatus.getText().trim());

        driver.getGuessCell1(grid);
        TestDriver.printTestCase("TC000", "GuiGameDriver. getGuessCell1", -1, -1);

        driver.getGuessCell2(grid);
        TestDriver.printTestCase("TC000", "GuiGameDriver. getGuessCell2", -1, -1);

        driver.choice = "1 2";
        driver.getChoice(grid);
        TestDriver.printTestCase("TC000", "GuiGameDriver. getChoice",  "Enter a pair of numbers, or \"R\" to reset, or \"Q\" to quit: ", driver.gameStatus.getText());
                
        driver.showExit(grid);
        TestDriver.printTestCase("TC000", "GuiGameDriver. showExit", "Game Over", driver.gameStatus.getText().trim());

        driver.cleanup();
        TestDriver.printTestCase("TC000", "GuiGameDriver. cleanup",true, true);
        
        
    }
}
