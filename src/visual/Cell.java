package visual;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingUtilities;

import core.Game;

public class Cell {

    private JButton _cell; // The JButton that represents visually the cell
    private int _mines; // Number of mines around the cell or Game.Mine if it is a mine
    private int _line; // Line where the cell is at
    private int _column; // Column where the cell is at
    private Grid _grid; // Grid where the cell belongs to
    private boolean _flagged; // If the cell has been flagged or not
    private boolean _end; // If the end of the game has been reached or not

    /**
     * Creates a cell from the grid of the minesweeper game
     * @param mines - Number of mines around the cell
     * @param line - the line in which the cell is at
     * @param column - the column in which the cell is at
     * @param grid - the grid in which the cell belongs
     */
    public Cell(int mines, int line, int column, Grid grid){

        // Core properties of the cell
        _line = line;
        _column = column;
        _grid = grid;
        _flagged = false;
        _end = false;
        _mines = mines;

        // Visual properties of the cell
        _cell = new JButton();
        _cell.setEnabled(true);
        _cell.setFocusable(false);
        _cell.setBackground(Color.LIGHT_GRAY);
        _cell.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createRaisedSoftBevelBorder(),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        // Set disabledIcons
        BufferedImage image_buf = null;
        if(_mines == 0) _cell.setIcon(null);
        else{
            if(_mines == Game.MINE){
                try {
                    image_buf = ResourceLoader.loadImage("images/mine.png");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else{
                try {
                    image_buf = ResourceLoader.loadImage("images/number-" + _mines + ".png");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            Image resizedImage = image_buf.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
           ImageIcon image= new ImageIcon(resizedImage);
            _cell.setDisabledIcon(image);
        }


        // Add Mouse Listeners in order to play the game
        _cell.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {}

            @Override
            public void mousePressed(MouseEvent e) {
                if(_end) return;
                if(SwingUtilities.isRightMouseButton(e) && 
                    _grid.getMines() != 0 && _cell.isEnabled()){
                    if(!_flagged){
                        BufferedImage image_buf = null;
                        try {
                            image_buf = ResourceLoader.loadImage("images/red-flag.png");
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                        Image resizedImage = image_buf.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
                        ImageIcon image= new ImageIcon(resizedImage);
                        _cell.setIcon(image);
                        _flagged = true;
                        _grid.setMines(_grid.getMines() - 1);
                    }
                    else{
                        _cell.setIcon(null);
                        _flagged = false;
                        _grid.setMines(_grid.getMines() + 1);
                    }
                }
                else if(SwingUtilities.isLeftMouseButton(e)){

                    // Detect if a player put the correct flags around the cell
                    if(!_cell.isEnabled()){
                        int flaggedCells = 0;
                        List<Cell> surroundings = _grid.getSurroundings(_line, _column); 
                        for(Cell cell : surroundings)
                            if(cell.IsFlagged()) flaggedCells++;
                        
                        if(flaggedCells == _mines){
                            for(Cell cell : surroundings){
                                if(!cell.IsFlagged() && cell.getJButton().isEnabled()){
                                    cell.seeCell();

                                    try {
                                        _grid.updateGrid(cell.getLine(), cell.getColumn());
                                    } catch (IOException e1) {
                                        
                                        e1.printStackTrace();
                                    }
                                }
                                
                            }

                            try {
                                _grid.updateGrid(_line, _column);
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
                        return;
                    }
                    
                    // Upadte the grid after clicking on a specific cell
                    _cell.setEnabled(false); 
                    _cell.setBackground(Color.LIGHT_GRAY);
                    _cell.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLoweredBevelBorder(),
                        BorderFactory.createEmptyBorder(10, 10, 10, 10)
                    ));
                    
                    if(_mines == 0);
                    else{
                    
                        
                        BufferedImage imageBuf = null;
                        if(_mines == Game.MINE){ 
                            try {
                                imageBuf = ResourceLoader.loadImage("images/mine.png");
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
                        else{
                            if(_flagged) _grid.setMines(_grid.getMines() + 1);
                            try {
                                imageBuf = ResourceLoader.loadImage("images/number-" + _mines + ".png");
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }

                        Image resizedImage = imageBuf.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
                        ImageIcon image= new ImageIcon(resizedImage);
                        _cell.setIcon(image);
                    }
                    
                    try {
                        _grid.updateGrid(_line, _column);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}

            
            
        });
    }

    /**
     * Visits the cell and makes its content visible
     */
    public void seeCell(){ 
        _cell.setEnabled(false); 
        _cell.setBackground(Color.LIGHT_GRAY);
        _cell.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLoweredBevelBorder(),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        if(_mines == 0);
        else{
                    
            BufferedImage imageBuf = null;
            if(_mines == Game.MINE){ 
                try {
                    imageBuf = ResourceLoader.loadImage("images/mine.png");
                } catch (IOException e) {
                   
                    e.printStackTrace();
                }
                
            }else{
                if(_flagged) _grid.setMines(_grid.getMines() + 1);
                try {
                    imageBuf = ResourceLoader.loadImage("images/number-" + _mines + ".png");
                } catch (IOException e) {
                    
                    e.printStackTrace();
                }
                
            }

            Image resizedImage = imageBuf.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
            ImageIcon image= new ImageIcon(resizedImage);
            _cell.setIcon(image);
        }
    }

    /**
     * Marks the end of the game for the cell
     */
    public void markAsSeen(){  _end = true;}

    /**
     * Verifies if the cell has been flagged
     * @return the boolean _flagged of the cell
     */
    public boolean IsFlagged(){ return _flagged;}

    /**
     * Gets the number of mines around the cell or Game.Mine if it is a mine
     * @return the number of mines around the cell or Game.Mine if it is a mine
     */
    public int getMines(){ return _mines;}

    /**
     * Gets the line in which the cell is present
     * @return the line in which the cell is present
     */
    public int getLine(){return _line;}

    /**
     * Gets the column in which the cell is present
     * @return the column in which the cell is present
     */
    public int getColumn(){return _column;}

    /**
     * Gets the JButton of the cell 
     * @return the JButton of the cell
     */
    public JButton getJButton(){ return _cell;}
    
}
