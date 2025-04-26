package core;
import java.util.List;
import java.util.ArrayList;


public class Grid {
    
    private List<List<Integer>> _grid; // Grid with the values of the game
    private List<List<Boolean>> _seen; // Grid with values if the cell has been seen or not
    private int _size;

    /**
     * Creates a square game grid - for test porpuses only
     * 
     * @param size - size of the square field
     */
    private Grid(int size){
        _size = size;
        _grid = new ArrayList<>(size);
        _seen = new ArrayList<>(); 

        for(int i = 0; i < size; i++){
            List<Integer> row = new ArrayList<>(size);
            List<Boolean> row_seen = new ArrayList<>(size);
            for(int j = 0; j <size; j++){
                row.add(0);
                row_seen.add(false);
            }

            _grid.add(row);
            _seen.add(row_seen);
        }
    }

    /**
     * Gets the cells around a specific cell
     * @param line - line where that cell is
     * @param column - the column where that cell is
     * @return List with all the surroundings of the specified cell
     */
    private List<List<Integer>> getSurroundings(int line, int column) {
        List<List<Integer>> surroundings = new ArrayList<>();
        int[] dx = {-1, -1, -1,  0, 0, 1, 1, 1};
        int[] dy = {-1,  0,  1, -1, 1,-1, 0, 1};
        for (int i = 0; i < 8; i++) {
            List<Integer> neighbor = new ArrayList<>();
            neighbor.add(line + dx[i]);
            neighbor.add(column + dy[i]);
            surroundings.add(neighbor);
        }
        return surroundings;
    }


    /**
     * Creates a game grid with a certain number of mines
     * 
     * @param size - size of the square field
     * @param mines - number of mines
     */
    public Grid(int size, int mines){
        this(size);

        // Choose randomly the posion of the different mines
        for(int mine = 0; mine < mines; mine++){
            int line = (int)(Math.random()*size), column = (int)(Math.random()*size);

            // Avoid overlay of mines
            while(_grid.get(line).get(column) == Game.MINE){
                line = (int)(Math.random()*size); column = (int)(Math.random()*size);
            }
            _grid.get(line).set(column, Game.MINE);
        }
        
        for(int i = 0; i < size; i++){

            for(int j = 0; j < size; j++){

                // Skip if it is a bomb
                if(_grid.get(i).get(j) == Game.MINE){
                    _seen.get(i).set(j, true);
                    continue;
                }

                //Count the mines around the cell
                int count_mines = 0;

                for(List<Integer> cell : getSurroundings(i, j)){
                    if(cell.get(0) >= 0 && cell.get(0) < size &&
                        cell.get(1)>= 0 && cell.get(1) < size &&
                        _grid.get(cell.get(0)).get(cell.get(1)) == Game.MINE)
                        count_mines++;
                }

                _grid.get(i).set(j, count_mines);

            }
        }
    }

    /**
     * Verifies if a specific cell is a mine or not
     * @param line - the lines of the cell on the grid
     * @param column - the column of the cell on the grid
     * @return true if it is a mine, false otherwise
     */
    public boolean checkIfIsMine(int line, int column){ return _grid.get(line).get(column) == Game.MINE;}

    /**
     * Gets and sets as seen all the cells surrounding the specified cell
     * @param line - line where the specified cell is
     * @param column - column where the specified cell is
     * @return  A list with all the cells obtained from the surroundings of cells with 0 bombs;
     */
    public List<List<Integer>> getAndSetAllCellsFromSurroundings(int line, int column){
        List<List<Integer>> surroundings = getSurroundings(line, column);
        List<List<Integer>> neutralZone = new ArrayList<>();

        _seen.get(line).set(column, true);
        while(!surroundings.isEmpty()){
            List<Integer> cell = surroundings.getFirst();
            surroundings.removeFirst();
            int lineAux = cell.get(0);
            int columnAux = cell.get(1);

            if (lineAux < 0 || lineAux >= _size||
                columnAux < 0 || columnAux >= _size ||
                _seen.get(lineAux).get(columnAux)) continue;
            
            _seen.get(lineAux).set(columnAux, true);
            if(_grid.get(lineAux).get(columnAux) == 0){

                neutralZone.add(cell);

                surroundings.addAll(getSurroundings(
                    lineAux, 
                    columnAux
                ));
            }
            else neutralZone.add(cell); 

            
        }
        return neutralZone;
    }

    /**
     * Prints the _seen matrix
     */
    public void seenPrint(){

        System.out.println("\t0 1 2 3 4 5 6 7");
        for(int i = 0; i < _size; i++){
            System.out.print(""+i+"\t");
            for(int j = 0; j < _size; j++){
                System.out.print((_seen.get(i).get(j))? 1 : 0);
                System.out.print(" ");
            }
            System.out.println("");
        }
        System.out.println("\n");
    }

    /**
     * Checks if all the game grid has been seen
     * @return true if all the grid has been checked, false otherwise
     */
    public boolean allChecked(){
        for(int i = 0; i < _size; i++)
            for(int j = 0; j < _size; j++)
                if(!_seen.get(i).get(j)) return false;
        
        return true;
    }

    /**
     * Gets the matrix representation of the grid
     * @return the matrix representation of the grid
     */
    public List<List<Integer>> getMatrix(){ return _grid; }
    
}
