
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
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

    // <editor-fold defaultstate="collapsed" desc="Attributes - Instance variables">
    private JMenuBar menubar;
    private JMenuItem fileMenuItem; // main file menu
    private JMenuItem resetMenuItem; // To reset the application grid
    private JMenuItem exitMenuItem; // To exit the application

    private JPanel gameBoard;
    private ArrayList<JButton> gameButtons;
    private final JButton resetColorButton;

    private JPanel statusBar;
    private JLabel gameStatus;

    private GameGrid data;
    private Boolean resetRequested;
    private Boolean exitRequested;
    private Boolean guessRequested;

    private int guess1;
    private int guess2;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Static Attributes/variables">
    private static final int MIN_FRAME_WIDTH = 350;
    private static final int MIN_FRAME_HEIGHT = 350;

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Default Constructor">
    /**
     * Default Constructor
     */
    public GuiGameDriver() {
        this.resetColorButton = new JButton();
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

        assert getJMenuBar() != null; //assert postcondition
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="createGameBoard">
    /**
     * Construct the game board in the center of the application
     *
     * @param data
     * @throws IllegalArgumentException
     */
    private void createGameBoard(GameGrid data) {
        if (data == null) {
            throw new IllegalArgumentException("GameGrid may not be null.");
        }
        if (this.gameBoard != null) {
            return;
        }

        //Create the panel
        this.gameBoard = new JPanel();
        assert this.gameBoard != null; //assert run-time

        // Set the application window dimensions and don't allow resizing
        Integer size = ((Number) Math.sqrt(data.getSize())).intValue();
        assert size > 0; //assert precondition

        int generatedGameBoardWidth = (64 * size);
        int generatedGameBoardHeight = (32 * size) + 50;

        if (generatedGameBoardWidth < MIN_FRAME_WIDTH) {
                //height is always less than width
                setSize(MIN_FRAME_WIDTH, MIN_FRAME_HEIGHT); 
        } else {
            if (generatedGameBoardHeight < MIN_FRAME_HEIGHT) {
                setSize(generatedGameBoardWidth, MIN_FRAME_HEIGHT);
            } else {
                setSize(generatedGameBoardWidth, generatedGameBoardHeight);
            }
        }

        //Create the buttons
        assert data.getSize() > 1; //assert run-time Min Size is 2x2
        this.gameButtons = new ArrayList<JButton>();
        for (int i = 0; i < data.getSize(); i++) {
            JButton button = new JButton();
            button.addActionListener(new CellButtonListener());
            this.gameButtons.add(button);
            this.gameBoard.add(button);
        }

        //do the layout
        GridLayout layout = new GridLayout(size, size);
        this.gameBoard.setLayout(layout);
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
        assert gridData != null; //assert precondition
        assert gridData.length > 3; //assert run-time Min Size is 2x2 (sqrt(4))

        for (int i = 0; i < this.gameButtons.size(); i++) {
            JButton button = this.gameButtons.get(i);
            String text = gridData[i];
            button.setText(String.format("%5s", text));
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="disableGameBoard">
    /**
     * Disable the game board so the user can't interact
     */
    private void disableGameBoard() {
        assert this.gameButtons != null; //assert precondition

        for (int i = 0; i < this.gameButtons.size(); i++) {
            //this.gameButtons.get(i).setEnabled(false);
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="enableGameBoard">
    /**
     * Enable the game board if it has been disabled
     */
    private void enableGameBoard() {
        assert this.gameButtons != null; //assert precondition

        Boolean[] isFound = this.data.getIsFoundGrid();
        for (int i = 0; i < this.gameButtons.size(); i++) {
            this.gameButtons.get(i).setEnabled(!isFound[i]);
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="resetChoices">
    /**
     * Reset the user's selection choices after a match or mismatch
     */
    private void resetChoices() {
        this.guess1 = -1;
        this.guess2 = -1;
        exitRequested = false;
        resetRequested = false;
        guessRequested = false;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="setup">
    /**
     * Setup the GUI elements
     *
     * @param data
     * @throws IllegalArgumentException
     */
    @Override
    public void setup(GameGrid data) {
        if (data == null) {
            throw new IllegalArgumentException("Game grid may not be null.");
        }
        // Set application title and exit button
        setTitle("The Multi-Concentration Game");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        createMenuBar();

        // Create a BorderLayout for the control sections
        setLayout(new BorderLayout());

        createStatusBar();

        // Set the application window dimensions and don't allow resizing
        setSize(MIN_FRAME_WIDTH, MIN_FRAME_HEIGHT);

        setVisible(true);
        setResizable(true);

        this.data = data;
        this.createGameBoard(data);
        resetChoices();
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="showNewGameDisplay">
    /**
     * Show new game message and time limited data grid
     *
     */
    @Override
    public void showNewGameDisplay() {
        resetChoices();

        String[] dataGrid = data.getDataGrid();

        //Reset the buttons
        for (int i = 0; i < gameButtons.size(); i++) {
            JButton button = this.gameButtons.get(i);
            button.setEnabled(false);
            button.setBackground(this.resetColorButton.getBackground());
            button.setActionCommand(data.getDisplayGrid()[i] + " " + data.getDataGrid()[i]);
        }
        this.redrawGameBoard(dataGrid);
        this.disableGameBoard();
        this.fileMenuItem.setEnabled(false);
        for (int i = Config.MemorizeSeconds; i >= 0; i--) {
            try {
                this.gameStatus.setText("Memorize the above grid! " + i);
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(GuiGameDriver.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.fileMenuItem.setEnabled(true);
        this.redrawGameBoard(data.getDisplayGrid());
        this.enableGameBoard();
        this.gameStatus.setText("Select a pair of numbers.");
    }
    // </editor-fold>

    @Override
    public Boolean isResetRequested() {
        return resetRequested;
    }

    @Override
    public Boolean isExitRequested() {
        return exitRequested;
    }

    @Override
    public Boolean isGuessRequested() {
        return guessRequested;
    }

    // <editor-fold defaultstate="collapsed" desc="getChoice">
    /**
     * Get users choice
     *
     */
    @Override
    public void getChoice() {
        //ugh, tight loop.
        //resetChoices();
        try {
            while (!(exitRequested || resetRequested || guessRequested)) {
                Thread.sleep(5);
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(GuiGameDriver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="showExit">
    /**
     * Show exit screen
     *
     */
    @Override
    public void showExit() {
        resetChoices();
        this.gameStatus.setText("Game Over");
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="getGuessCell1">
    /**
     * Get last cell1 guess
     *
     * @return
     */
    @Override
    public int getGuessCell1() {
        assert this.guess1 > -1; //assert precondition 
        return this.guess1 - 1;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="getGuessCell2">
    /**
     * Get last cell2 guess
     *
     * @return
     */
    @Override
    public int getGuessCell2() {
        assert this.guess2 > -1; //assert precondition 
        return this.guess2 - 1;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="showGuessSuccess">
    /**
     * Show success message
     *
     */
    @Override
    public void showGuessSuccess(int cell1, int cell2) {
        resetChoices();
        this.gameButtons.get(cell1).setBackground(Color.ORANGE);
        this.gameButtons.get(cell1).setEnabled(false);
        this.gameButtons.get(cell2).setBackground(Color.ORANGE);
        this.gameButtons.get(cell2).setEnabled(false);
        this.redrawGameBoard(data.getDisplayGrid(cell1, cell2));

        if (data.remaining() > 0) {
            enableGameBoard();
            this.gameStatus.setText("Good Guess!");
        } else {
            // game over, require reset
            disableGameBoard();
            gameStatus.setText("Congratulations!  Reset to play again.");
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="showGuessFailed">
    /**
     * Show failed message
     *
     */
    @Override
    public void showGuessFailed(int cell1, int cell2) {
        resetChoices();
        this.enableGameBoard();
        this.gameButtons.get(cell1).setBackground(this.resetColorButton.getBackground());
        this.gameButtons.get(cell1).setEnabled(true);
        this.gameButtons.get(cell2).setBackground(this.resetColorButton.getBackground());
        this.gameButtons.get(cell2).setEnabled(true);
        this.redrawGameBoard(data.getDisplayGrid(cell1, cell2));
        this.gameStatus.setText("Sorry...");
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="showException">
    /**
     * Show exception message
     *
     * @param ex
     */
    @Override
    public void showException(Exception ex) {
        resetChoices();
        this.redrawGameBoard(data.getDisplayGrid());
        this.gameStatus.setText("Error: " + ex.getMessage());
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="cleanup">
    /**
     *
     */
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
            resetRequested = true;
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
            exitRequested = true;
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="GameButtonClickListener">
    /**
     * Event handler for when a game tile/button is clicked
     */
    private class CellButtonListener implements ActionListener {

        public CellButtonListener() {
        }

        @Override
        public void actionPerformed(ActionEvent ae) {
            JButton button = (JButton) ae.getSource();
            String action = ae.getActionCommand();
            String[] text = action.split("\\s");
            if (text.length != 2) {
                return;
            }
            int cellDisplayNumber = Integer.parseInt(text[0].trim());
            if (cellDisplayNumber < 0) {
                return;
            }
            String cellData = text[1].trim();
            
            redrawGameBoard(data.getDisplayGrid());
            button.setText(cellData);
            button.setBackground(Color.ORANGE); 
            
            if (guess1 < 0) {
                guess1 = cellDisplayNumber;
            } else if (guess2 < 0) {
                guess2 = cellDisplayNumber;
                guessRequested = true;
                disableGameBoard();
            } 
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

        Config.MemorizeSeconds = 1;
        GuiGameDriver driver;
        GameGrid grid; //Initialize(42) -> [B, A, A, B]

        /**
         * Test the setup methods
         */
        driver = new GuiGameDriver();
        grid = new GameGrid(2);
        grid.initializeGrids(42);
        
        driver.createMenuBar();
        TestDriver.printTestCase("TC000", "GuiGameDriver. createMenuBar", true, true);

        driver.createStatusBar();
        TestDriver.printTestCase("TC000", "GuiGameDriver. createStatusBar", true, true);

        TestDriver.printTestCaseThrowsException("TC000", "GuiGameDriver createGameBoard null grid exception.",
                new TestCase() {
                    @Override
                    public GuiGameDriver Run() throws Exception {
                        GuiGameDriver driver = new GuiGameDriver();
                        driver.createGameBoard(null);
                        return driver;
                    }
                });

        TestDriver.runTestCase("TC000", "GuiGameDriver createGameBoard 2 grid is < min width and < min height.", true,
                new TestCase<Boolean>() {
                    @Override
                    public Boolean Run() throws Exception {
                        GuiGameDriver driver = new GuiGameDriver();
                        driver.createGameBoard(new GameGrid(2));
                        return true;
                    }
                });
        
        //width = 64 * size
        //height = 32*size+50        
        TestDriver.runTestCase("TC000", "GuiGameDriver createGameBoard 6 grid is > min width and < min height.", true,
                new TestCase<Boolean>() {
                    @Override
                    public Boolean Run() throws Exception {
                        GuiGameDriver driver = new GuiGameDriver();
                        driver.createGameBoard(new GameGrid(6));
                        return true;
                    }
                });

        TestDriver.runTestCase("TC000", "GuiGameDriver createGameBoard 20 grid is > min width and > min height.", true,
                new TestCase<Boolean>() {
                    @Override
                    public Boolean Run() throws Exception {
                        GuiGameDriver driver = new GuiGameDriver();
                        driver.createGameBoard(new GameGrid(20));
                        return true;
                    }
                });

        driver.createGameBoard(grid);
        TestDriver.printTestCase("TC000", "GuiGameDriver. createGameBoard", true, true);

        driver.createGameBoard(grid);
        TestDriver.printTestCase("TC000", "GuiGameDriver. createGameBoard after already initialized", true, true);

        driver.resetMenuItem.doClick();
        TestDriver.printTestCase("TC000", "GuiGameDriver. ResetMenuListener", true, true);

        driver.exitMenuItem.doClick();
        TestDriver.printTestCase("TC000", "GuiGameDriver. ExitMenuListener", true, true);

        driver.resetChoices();
        TestDriver.printTestCase("TC000", "GuiGameDriver. resetChoices", true, true);

        TestDriver.printTestCaseThrowsException("TC000", "GuiGameDriver setup null grid exception.",
                new TestCase() {
                    @Override
                    public GuiGameDriver Run() throws Exception {
                        GuiGameDriver driver = new GuiGameDriver();
                        driver.setup(null);
                        return driver;
                    }
                });
        
        driver.cleanup();

        /**
         * Test the driver methods
         */
        
        //Reset the driver and grid
        
        driver = new GuiGameDriver();
        grid = new GameGrid(2);
        grid.initializeGrids(42);
        driver.setup(grid);
        
        TestDriver.printTestCase("GUI001", "GuiGameDriver. setup", true, true);

        TestDriver.printTestCase("TC000", "GuiGameDriver. isResetRequested", false, driver.isResetRequested());
        
        TestDriver.printTestCase("TC000", "GuiGameDriver. isExitRequested", false, driver.isExitRequested());
        
        TestDriver.printTestCase("TC000", "GuiGameDriver. isGuessRequested", false, driver.isGuessRequested());
        
        driver.guess1 = 1;
        TestDriver.printTestCase("GUI006", "GuiGameDriver. getGuessCell1", 0, driver.getGuessCell1());
        
        driver.guess2 = 1;
        TestDriver.printTestCase("GUI007", "GuiGameDriver. getGuessCell2", 0, driver.getGuessCell2());

        driver.showNewGameDisplay();
        TestDriver.printTestCase("GUI002", "GuiGameDriver. showNewGameDisplay", "Select a pair of numbers.", driver.gameStatus.getText());

        driver.showGuessFailed(0, 1);
        TestDriver.printTestCase("GUI003", "GuiGameDriver. showGuessFailed", "Sorry...", driver.gameStatus.getText().trim());

        driver.showGuessSuccess(0, 3);
        TestDriver.printTestCase("GUI004", "GuiGameDriver. showGuessSuccess guess", "Good Guess!", driver.gameStatus.getText().trim());

        grid.matchCells(0, 3);
        grid.matchCells(1, 2);
        driver.showGuessSuccess(0, 3);
        TestDriver.printTestCase("GUI004", "GuiGameDriver. showGuessSuccess win the game", "Congratulations!  Reset to play again.", driver.gameStatus.getText().trim());

        driver.showException(new UnsupportedOperationException("TestError"));
        TestDriver.printTestCase("GUI005", "GuiGameDriver. showException", "Error: TestError", driver.gameStatus.getText().trim());       

        driver.showExit();
        TestDriver.printTestCase("GUI008", "GuiGameDriver. showExit", "Game Over", driver.gameStatus.getText().trim());

        driver.cleanup();
        TestDriver.printTestCase("GUI009", "GuiGameDriver. cleanup", true, true);
        
        /**
         * Test the clicks and cell selection. 
         */
        
        //Reset the driver and grid
        driver = new GuiGameDriver();
        grid = new GameGrid(2);
        grid.initializeGrids(42);

        
        driver.setup(grid);
        driver.showNewGameDisplay();
        
        //Click to win the game        
        driver.gameButtons.get(0).doClick();
        TestDriver.printTestCase("TC000", "GuiGameDriver. CellButtonListener cell1 0", 1, driver.guess1);

        driver.gameButtons.get(3).doClick();
        TestDriver.printTestCase("TC000", "GuiGameDriver. CellButtonListener cell2 3", 4, driver.guess2);
        driver.getChoice();
        driver.resetChoices();
        
        driver.gameButtons.get(1).doClick();
        TestDriver.printTestCase("TC000", "GuiGameDriver. CellButtonListener cell1 1", 2, driver.guess1);

        driver.gameButtons.get(2).doClick();
        TestDriver.printTestCase("TC000", "GuiGameDriver. CellButtonListener cell2 2", 3, driver.guess2);
        driver.getChoice();
        

        driver.cleanup();
    }

}
