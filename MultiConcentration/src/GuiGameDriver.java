

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;


/**
 * GUI game driver
 *
 * @author jwhite
 */
public class GuiGameDriver extends JFrame implements GameDriver {

    //=============================================
    // Attributes - Instance variables
    //=============================================
    
    private JMenuBar menubar;
    private JMenuItem fileMenuItem; // main file menu
    private JMenuItem resetMenuItem; // To reset the application grid
    private JMenuItem exitMenuItem; // To exit the application

    //=============================================
    // Static Attributes/variables
    //=============================================

    private static final int FRAME_WIDTH = 512;
    private static final int FRAME_HEIGHT = 512;
   
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

        // Set the application window dimensions and don't allow resizing
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setVisible(true);
        setResizable(true);
    }

    // =============================================
    // Private Implementation Methods
    // =============================================

    /**
     * Construct the Log Data and Stop Server button at the top of the
     * application
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
