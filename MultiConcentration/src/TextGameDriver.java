import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Text game driver
 * @author jwhite
 */
public class TextGameDriver implements GameDriver {

    public TextGameDriver() {
        inScanner = new Scanner(System.in);
        cell1 = -1;
        cell2 = -1;
    }
    private final Scanner inScanner;

    @Override
    public void showNewGameDisplay(GameGrid data) {
        printGrid(data.getDataGrid());
        System.out.print("Memorize the above grid! ");
        for (int i = 3; i >= 0; i--) {
            try {
                System.out.print(i + " ");
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(TextGameDriver.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        for (int i = 0; i < 25; i++) {
            System.out.println();
        }
    }

    @Override
    public void showGrid(GameGrid data) {
        if (cell1 < 0 || cell2 < 0) {
            printGrid(data.getDisplayGrid());

        } else {
            printGrid(data.getDisplayGrid(cell1, cell2));
        }
    }

    @Override
    public String getChoice(GameGrid data) {
        System.out.print("Enter a pair of numbers, or \"R\" to reset, or \"Q\" to quit: ");
        String result;
        cell1 = -1;
        cell2 = -1;
        while (inScanner.hasNext()) {
            result = inScanner.next();
            if ("R".equals(result)) {
                return "R";
            }
            if ("Q".equals(result)) {
                return "Q";
            }
            //Read the numbers
            if (cell1 < 0) {
                cell1 = Integer.parseInt(result) - 1;
                continue;
            }
            cell2 = Integer.parseInt(result) - 1;
            break;
        }
        //todo add "Please reentered" message for invalid stuff
        result = cell1 + " " + cell2;
        System.out.println("Selection : " + result);
        return result;
    }

    @Override
    public void showExit(GameGrid data) {
        System.out.println("Game Over");
    }

    private int cell1;
    private int cell2;

    @Override
    public int getGuessCell1(GameGrid data) {
        return cell1;
    }

    @Override
    public int getGuessCell2(GameGrid data) {
        return cell2;
    }

    @Override
    public void showGuessSuccess(GameGrid data) {
        System.out.println("Good Guess!");
    }

    @Override
    public void showGuessFailed(GameGrid data) {
        System.out.println("Sorry...");
    }

    @Override
    public void showException(GameGrid data, Exception ex) {
        System.out.println("Error: " + ex.getMessage());
    }

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

    }
}
