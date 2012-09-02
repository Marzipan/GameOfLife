package mj559.GameOfLife;

public class CellGrid {

	private int[][] board;
	private int size;
	
	public CellGrid(int size){
		board = new int[size][size];
		this.size = size;
		
		// Initialise blank board
		for (int r = 0; r < size; r++){
			for (int c = 0; c < size; c++){
				board[r][c] = 0;
			}
		}
	}
	
	// Creates a new board with contents of old one
	public CellGrid(CellGrid b){
		board = new int[b.getSize()][b.getSize()];
		this.size = b.getSize();
		
		// Initialise blank board
		for (int r = 0; r < size; r++){
			for (int c = 0; c < size; c++){
				board[r][c] = b.getSpace(r, c);
			}
		}
	}
	
	// Returns the size of the grid.
	// Currently all grids are square, so returned value of n => n*n grid
	public int getSize(){
		return size;
	}
	
	public Boolean isAlive(int r, int c){
		return board[r][c] == 1;
	}
	
	public void setDead(int row, int col){
		board[row][col] = 0;
	}
	
	public void setAlive(int row, int col){
		board[row][col] = 1;
	}
	
	// Returns the state of the given cell
	public int getSpace(int row, int col){
		return board[row][col];
	}
	
	// Changes the state of the supplied cell
	// Alive -> Dead
	// Dead -> Alive
	public void toggleSpace(int row, int col){
		if(row < 0 || row >= size || col < 0 || col >= size) return;
		if (board[row][col] == 0){
			board[row][col] = 1;
		}
		else {
			board[row][col] = 0;
		}
	}

}
