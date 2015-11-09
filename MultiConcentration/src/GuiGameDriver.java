
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
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="createGameBoard">
    /**
     * Construct the game board in the center of the application
     */
    private void createGameBoard(GameGrid data) {
        if (this.gameBoard != null) {
            return;
        }

        this.gameBoard = new JPanel();
        int gameButtonIndex = 1;
        Integer size = ((Number) Math.sqrt(data.getSize())).intValue();

        // Set the application window dimensions and don't allow resizing
        int generatedGameBoardWidth = (64 * size);
        int generatedGameBoardHeight = (32 * size) + 50;

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
        for (int i = 0; i < data.getSize(); i++) {
            JButton button = new JButton();
            if (!"".equals(data.getDataGrid()[i])) {
                button.addActionListener(new CellButtonListener());
                button.setActionCommand(data.getDisplayGrid()[i] + " " + data.getDataGrid()[i]);
            }
            this.gameButtons.add(button);
            this.gameBoard.add(this.gameButtons.get(i));
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

    private void redrawGameBoard(String[] gridData) {
        for (int i = 0; i < gridData.length; i++) {
            this.gameButtons.get(i).setText(String.format("%5s", gridData[i]));
        }
    }

    private GameGrid data;
    private Boolean resetRequested;
    private Boolean exitRequested;
    private Boolean guessRequested;

    private void resetChoices() {        
        this.guess1 = -1;
        this.guess2 = -1;
        exitRequested = false;
        resetRequested = false;
        guessRequested = false;
    }
    
    
    /**
     * Setup the GUI elements
     */
    @Override
    public void setup(GameGrid data) {
        if (data == null) {
            throw new IllegalArgumentException("Game grid may not be null.");
        }
        resetChoices();
        this.data = data;

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

    /**
     * Show new game message and time limited data grid
     *
     */
    @Override
    public void showNewGameDisplay() {
        resetChoices();
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
        this.redrawGameBoard(data.getDisplayGrid());
        this.gameStatus.setText("Select a pair of numbers.");
    }

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

    /**
     * Get users choice
     *
     */
    @Override
    public void getChoice() {
        //ugh, tight loop.
        resetChoices();
        try {
            while (!(exitRequested || resetRequested || guessRequested)) {
                Thread.sleep(5);
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(GuiGameDriver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Show exit screen
     *
     */
    @Override
    public void showExit() {
        resetChoices();
        this.gameStatus.setText("Game Over");
    }

    /**
     * Get last cell1 guess
     *
     * @return
     */
    @Override
    public int getGuessCell1() {
        return this.guess1 - 1;
    }

    /**
     * Get last cell2 guess
     *
     * @return
     */
    @Override
    public int getGuessCell2() {
        return this.guess2 - 1;
    }

    /**
     * Show success message
     *
     */
    @Override
    public void showGuessSuccess(int cell1, int cell2) {
        resetChoices();
        this.redrawGameBoard(data.getDisplayGrid(cell1, cell2));
        this.gameStatus.setText("Good Guess!");
    }

    /**
     * Show failed message
     *
     */
    @Override
    public void showGuessFailed(int cell1, int cell2) {
        resetChoices();
        this.redrawGameBoard(data.getDisplayGrid(cell1, cell2));
        this.gameStatus.setText("Sorry...");
    }
    private Boolean guessFailed; //todo use this ...

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

    /**
     *
     */
    @Override
    public void cleanup() {
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }

    // =============================================
    // Event Listener classes
    // =============================================
    // <editor-fold defaultstate="collapsed" desc="ResetMenuListener">
    private class CellButtonListener implements ActionListener {

        public CellButtonListener() {
        }

        @Override
        public void actionPerformed(ActionEvent ae) {
            String action = ae.getActionCommand();
            int x;
            String text = action.split("\\s")[0].trim();
            x = Integer.parseInt(text);

            if (x >= 0) {
                if (guess1 < 0) {
                    guess1 = x;
                } else if (guess2 < 0) {
                    guess2 = x;
                    guessRequested = true;
                } else {
                    guessRequested = true;
                }
            } else {
                guess1 = -1;
                guess2 = -1;
            }
        }
    }

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

        driver.setup(grid);
        TestDriver.printTestCase("TC000", "GuiGameDriver. setup", true, true);

        driver.showNewGameDisplay();
        TestDriver.printTestCase("TC000", "GuiGameDriver. showNewGameDisplay", true, driver.gameStatus.getText().length() > 0);

        driver.showGuessFailed(1, 2);
        TestDriver.printTestCase("TC000", "GuiGameDriver. showGuessFailed", "Sorry...", driver.gameStatus.getText().trim());

        driver.showGuessSuccess(1, 4);
        TestDriver.printTestCase("TC000", "GuiGameDriver. showGuessSuccess", "Good Guess!", driver.gameStatus.getText().trim());

        driver.showException(new UnsupportedOperationException("TestError"));
        TestDriver.printTestCase("TC000", "GuiGameDriver. showException", "Error: TestError", driver.gameStatus.getText().trim());

        driver.getGuessCell1();
        TestDriver.printTestCase("TC000", "GuiGameDriver. getGuessCell1", -1, -1);

        driver.getGuessCell2();
        TestDriver.printTestCase("TC000", "GuiGameDriver. getGuessCell2", -1, -1);

        driver.getChoice();
        TestDriver.printTestCase("TC000", "GuiGameDriver. getChoice", "Enter a pair of numbers, or \"R\" to reset, or \"Q\" to quit: ", driver.gameStatus.getText());

        driver.showExit();
        TestDriver.printTestCase("TC000", "GuiGameDriver. showExit", "Game Over", driver.gameStatus.getText().trim());

        driver.cleanup();
        TestDriver.printTestCase("TC000", "GuiGameDriver. cleanup", true, true);

    }

}
