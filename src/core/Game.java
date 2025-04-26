package core;

import java.util.List;

public class Game {
    
    public static final int MINE = -1; //Constant for mines

    public static final int LOST = 0; // Constant for losing the game
    public static final int WON = 1; // Constant for winning the game
    public static final int NOT_FINISHED = 2; // Constant if the game is still running

    // Constants for the size of the grid in each level

    private static final int EASY_SIZE = 8;
    private static final int MEDIUM_SIZE = 15;
    private static final int HARD_SIZE = 20;

    // Constants for the number of mines in each level

    private static final int EASY_MINES = 20;
    private static final int MEDIUM_MINES = 60;
    private static final int HARD_MINES = 120;

    // Properties of the game

    private int _mines; // Number of mines at the grid
    private int _size; // Size of the grid
    private Grid _grid; // The grid of the game
    private int _state; // the state of the game

    /**
     * Creates a game with a specified difficulty
     * @param difficulty - the dificulty of the game
     */
    public Game(String difficulty){

        _state = NOT_FINISHED;
        if(difficulty.equals("EASY")){ 
            _grid = new Grid(EASY_SIZE, EASY_MINES); _size = EASY_SIZE; _mines = EASY_MINES;
        }
        if(difficulty.equals("MEDIUM")){ 
            _grid = new Grid(MEDIUM_SIZE, MEDIUM_MINES); _size = MEDIUM_SIZE; _mines = MEDIUM_MINES;
        }
        if(difficulty.equals("HARD")){ 
            _grid = new Grid(HARD_SIZE, HARD_MINES); _size = HARD_SIZE; _mines = HARD_MINES;
        }
    }

    /**
     * Updates the _grid of the Core and returns the cells around the 
     * specified cell
     * @param line - the line where the specified cell is at
     * @param column - the column where the specified cell is at
     * @return a list with the cells around the specified cell
     */
    public List<List<Integer>> play(int line, int column){
        if(_grid.allChecked()){ _state = WON; return null;}
        if(_grid.checkIfIsMine(line, column)){ _state = LOST; return null;}
        return _grid.getAndSetAllCellsFromSurroundings(line, column);
    }

    /**
     * Gets the state of the game
     * @return WOn if the player has won the game, LOST if the player
     * lost the game or NOT_FINISHED otherwise.
     */
    public int getState(){ return _state; }

    /**
     * Gets the _grid of the game
     * @return the _grid of the game
     */
    public List<List<Integer>> getGrid(){ return _grid.getMatrix();}

    /**
     * Gets the size of the game
     * @return the _size of the game
     */
    public int getSize(){ return _size;}

    /**
     * Gets the number of mines that are in the game
     * @return the number of mines at the game
     */
    public int getMines(){ return _mines;}
}
