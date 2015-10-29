
/**
 * GameGrid stores the game state
 *
 * @author jwhite
 */
public class GameGrid {

    public GameGrid(int size) {
        //todo validate size
        //must be even
        //muste less than 10?
        this.size = size;

        grid = new String[size * size];

    }

    private final int size;

    public int getSize() {
        return size;
    }

    private final String[] grid;

    public void initializeGrid() {

        for (int i = 0; i < grid.length; i++) {
            grid[i] = "x";
        }

    }

    public void printGrid() {

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(String.format("%5s",  grid[i * size + j]));
            }
            System.out.println();
        }

    }

    public static void main(String[] args) {

        GameGrid grid = new GameGrid(3);
        grid.initializeGrid();
        grid.printGrid();

    }

}
