package visual;

import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import core.Game;

import java.awt.Image;
import java.util.ArrayList;

public class Grid {
    
    private List<List<Cell>> _gridVisual;
    private Game _game;
    private JLabel _mines;
    private JLabel _win;
    
    /**
     * Builds a Visual Grid with a game with a specified difficulty.
     * Receives two JLabels, one with the counter of mines and 
     * the other to notify if the player won the game
     * @param difficulty - difficulty of the game
     * @param mines - the JLabel that will show the counter with the mines
     * @param win - the JLabel that will show if the player won or lost
     */
    public Grid(String difficulty, JLabel mines, JLabel win){

        _game = new Game(difficulty);
        int size = _game.getSize();
        _gridVisual = new ArrayList<>(size);
        _mines = mines;
        _mines.setText(Integer.toString(_game.getMines()));
        _win = win;

        ImageIcon image = new ImageIcon("images/neutral.png");
        Image resizedImage = image.getImage().getScaledInstance(36, 36, Image.SCALE_SMOOTH);
        image= new ImageIcon(resizedImage);
        _win.setIcon(image);
        _win.setText("  ...");

        List<List<Integer>> gridCore = _game.getGrid();

        for (int i = 0; i < size; i++) {

            List<Cell> row = new ArrayList<Cell>(size);

            for (int j = 0; j < size; j++) {
                row.add(new Cell(gridCore.get(i).get(j), i, j, this));
            }
            _gridVisual.add(row);
        }

    }

    /**
     * Gets the surroundings cells around a specified cell
     * @param line - the line where the cell is at
     * @param column - the column where the cell is at
     * @return a list with the cells that are around the specified cells
     */
    public List<Cell> getSurroundings(int line, int column){
        List<Cell> surroundings = new ArrayList<>();
        int[] dx = {-1, -1, -1,  0, 0, 1, 1, 1};
        int[] dy = {-1,  0,  1, -1, 1,-1, 0, 1};
        for (int i = 0; i < 8; i++) {
            int lineAux = line + dx[i];
            int columnAux = column + dy[i];
            if(lineAux < 0 || lineAux >= _game.getSize() ||
                columnAux < 0 || columnAux >= _game.getSize())
                continue;

            surroundings.add(
                _gridVisual.get(lineAux).get(columnAux)
            );
        }
        return surroundings;
    }

    /**
     * Updates the grid in order to know which cells have been already
     * seen and if the player has won or lost the game
     * @param line - the line of the cell the player clicked
     * @param column - the column of the cell the player clicked
     */
    public void updateGrid(int line, int column){ 
        
        List<List<Integer>> seenCells = _game.play(line, column);
        if(_game.getState() == Game.WON){
            ImageIcon image = new ImageIcon("images/smile.png");
            Image resizedImage = image.getImage().getScaledInstance(36, 36, Image.SCALE_SMOOTH);
            image= new ImageIcon(resizedImage);
            _win.setIcon(image);
            _win.setText("YOU WON");

            for(List<Cell> row : _gridVisual)
                for(Cell cell : row)
                    cell.markAsSeen();

            return;
        }
        if(_game.getState() == Game.LOST){
            ImageIcon image = new ImageIcon("images/dead.png");
            Image resizedImage = image.getImage().getScaledInstance(36, 36, Image.SCALE_SMOOTH);
            image= new ImageIcon(resizedImage);
            _win.setIcon(image);
            _win.setText("YOU LOST");

            for(List<Cell> row : _gridVisual)
                for(Cell cell : row)
                    cell.markAsSeen();
                
            return;
        }
        if(seenCells == null) return;
        for(List<Integer> cell : seenCells) 
            _gridVisual.get(cell.get(0)).get(cell.get(1)).seeCell();
    }

    /**
     * Gets a specific cell from the grid 
     * @param line - the line in which the cell is at
     * @param column - the column in which the cell is at
     * @return the cell with the specified coordinates
     */
    public Cell getCellAt(int line, int column){ return _gridVisual.get(line).get(column);}

    /**
     * Gets the size of the game
     * @return the size of the game
     */
    public int getGameSize() {return _game.getSize();}

    /**
     * Gets the number of mines of the game
     * @return the number of mines of the game
     */
    public int getGameMines(){return _game.getMines();}

    /**
     * Gets the number of mines of the JLabel that contains the supposed
     * number of mines
     * @return the number of mines
     */
    public int getMines(){return Integer.parseInt(_mines.getText());}

    /**
     * Sets the text of the JLabel that contains the supposed number of mines
     * @param mines - the mines that are at the board
     */
    public void setMines(int mines){_mines.setText(Integer.toString(mines));}

}
