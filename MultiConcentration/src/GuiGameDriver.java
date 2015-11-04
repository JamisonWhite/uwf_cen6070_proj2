import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
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

    //=============================================
    // Static Attributes/variables
    //=============================================

    private static final int MIN_FRAME_WIDTH = 350;
    private static final int MIN_FRAME_HEIGHT = 350;
    
    // *** REMOVE WHEN NOT NEEDED
    private static final int GAME_BOARD_SIZE = 4;
   
    // <editor-fold defaultstate="collapsed" desc="Default Constructor">
    /**
     * Default Constructor
     */
    public GuiGameDriver() {

        // Set application title and exit button
        setTitle("The Multi-Concentration Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        createMenuBar();

        // Create a BorderLayout for the control sections
        setLayout(new BorderLayout());
        
        createGameBoard();
        createStatusBar();

        // Set the application window dimensions and don't allow resizing
        int generatedGameBoardWidth = (56 * GuiGameDriver.GAME_BOARD_SIZE);
        int generatedGameBoardHeight = (32 * GuiGameDriver.GAME_BOARD_SIZE) + 50;
        
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

        setVisible(true);
        setResizable(true);
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
    private void createGameBoard() {
        this.gameBoard = new JPanel();
        int gameButtonIndex = 1;
        //panel.setSize(300,300);
        
        GridLayout layout = new GridLayout(GuiGameDriver.GAME_BOARD_SIZE, GuiGameDriver.GAME_BOARD_SIZE);

        this.gameBoard.setLayout(layout);
        
        this.gameButtons = new ArrayList<JButton>();

	// Loop through elements.
	for (int i = 0; i < GuiGameDriver.GAME_BOARD_SIZE; i++) {
            
            for (int j = 0; j < GuiGameDriver.GAME_BOARD_SIZE; j++) {
                this.gameButtons.add(new JButton(Integer.toString(gameButtonIndex)));
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
    
    /**
     * Show new game message and time limited data grid
     *
     * @param data
     */
    @Override
    public void showNewGameDisplay(GameGrid data) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Show the data grid
     *
     * @param data
     */
    @Override
    public void showGrid(GameGrid data) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Get users choice
     *
     * @param data
     * @return
     */
    @Override
    public String getChoice(GameGrid data) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Show exit screen
     *
     * @param data
     */
    @Override
    public void showExit(GameGrid data) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Get last cell1 guess
     *
     * @param data
     * @return
     */
    @Override
    public int getGuessCell1(GameGrid data) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Get last cell2 guess
     *
     * @param data
     * @return
     */
    @Override
    public int getGuessCell2(GameGrid data) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Show success message
     *
     * @param data
     */
    @Override
    public void showGuessSuccess(GameGrid data) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Show failed message
     *
     * @param data
     */
    @Override
    public void showGuessFailed(GameGrid data) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Show exception message
     *
     * @param data
     * @param ex
     */
    @Override
    public void showException(GameGrid data, Exception ex) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

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
         * @param e
         * the event component source
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            
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
         * @param e
         * the event component source
         */
        @Override
        public void actionPerformed(ActionEvent e) {
                System.exit(0);
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
        GameGrid grid = new GameGrid(6);
        GameDriver driver = new GuiGameDriver();
        GameLoop loop = new GameLoop(driver, grid);
        loop.Start();
    }
}
