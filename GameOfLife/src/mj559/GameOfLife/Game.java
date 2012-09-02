package mj559.GameOfLife;

// Represents a single "Game of Life"
public class Game {

	private CellGrid grid;
	
	public Game(int size){
		grid = new CellGrid(size);
	}

	// Executes one iteration on the current board.
	public void iterate(){
		// create a copy to hold new info
		CellGrid shadow = new CellGrid(grid);
		
				
		for(int r = 1; r < grid.getSize() - 1; r++){
			for(int c = 1; c < grid.getSize() - 1; c++){
				int tot = countNeighbours(r, c);
				//rules for living cell
				if (grid.isAlive(r, c)){
					if (tot < 2){
						shadow.setDead(r, c);
					} 
					else if (tot == 2 || tot == 3){
						shadow.setAlive(r, c);
					}
					else {
						shadow.setDead(r, c);
					}
				}
				// rules for dead cell
				else if(tot == 3){
					shadow.setAlive(r, c);
				}
			}
		}
		
		//update the board
		grid = shadow;
	}
	
	private int countNeighbours(int r, int c){
		int sum=0;
		for(int i = r-1; i <= r+1; i++){
			for(int j = c-1; j <= c+1; j++){
				sum += grid.getSpace(i,j);
			}
		}
		sum -= grid.getSpace(r, c);
		return sum;
	}
	
	public int getSize(){
		return grid.getSize();
	}
	
	// Returns the current game state.
	public CellGrid getBoard(){
		return grid;
	}

	// Clears the current game state.
	public void clearBoard() {
		grid = new CellGrid(grid.getSize());
	}

	
}
