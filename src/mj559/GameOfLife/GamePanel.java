package mj559.GameOfLife;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {
	
	private static final long serialVersionUID = 1L;
	
	private int cellCount;
	private int cellHeight, cellWidth;
	
	private Game gol;
	
	// Simulation settings
	private int runInterval = 500;
	private boolean running = false;
	
	// Tracks cell whose state has changed most recently. 
	// Format: [row, column]
	private int[] lastCellChanged = {-1,-1};
	boolean lastCellAlive = false;
	
	public GamePanel(Game gol, final int cellHeight, final int cellWidth){
		super();
		this.cellCount = gol.getSize();
		this.cellHeight = cellHeight;
		this.cellWidth = cellWidth;
		this.gol = gol;
		setPreferredSize(new Dimension(cellCount*cellWidth+1, cellCount*cellHeight+1)); 
		
		//
		this.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
			}
			
			// Toggles cell state on mouse click
			@Override
			public void mousePressed(MouseEvent e) {
				Point p = e.getPoint();
				int cellCol = p.x/cellWidth;
				int cellRow = p.y/cellHeight;
				if (getBoard().isAlive(cellRow, cellCol)) {
					lastCellAlive = true;
				} else {
					lastCellAlive = false;
				}
				
				getBoard().toggleSpace(cellRow, cellCol);
				lastCellChanged[0] = cellRow;
				lastCellChanged[1] = cellCol;
				repaint();
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		this.addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseMoved(MouseEvent e) {
				
			}
			
			// Allow for click-dragging mouse to change states
			@Override
			public void mouseDragged(MouseEvent e) {
				Point p = e.getPoint();
				int cellCol = p.x/cellWidth;
				int cellRow = p.y/cellHeight;
				if(isValidCellLocation(cellCol, cellRow) 
						&& mouseCellChanged(cellCol, cellRow) 
						&& getBoard().isAlive(cellRow, cellCol) == lastCellAlive){
					getBoard().toggleSpace(cellRow, cellCol);
					repaint();
				}
				lastCellChanged[0] = cellRow;
				lastCellChanged[1] = cellCol;
			}
			
			// Returns whether point (col,row) is inside a cell or not
			private boolean isValidCellLocation(int col, int row) {
				if(col < 0 || row < 0 || col >= getBoard().getSize() || row >= getBoard().getSize()){
					return false;
				}
				return true;
			}
			
			private boolean mouseCellChanged(int col, int row) {
				if (row == lastCellChanged[0] && col == lastCellChanged[1]){
					return false;
				}
				return true;
			}
		});
	}

	
	public CellGrid getBoard(){
		return gol.getBoard();
	}
	
	@Override
	public void paint(Graphics g){
		
		Graphics2D g2 = (Graphics2D) g;
		
		renderGridContents(g2);
		renderGridLines(g2);
		
	}
	
	public boolean isRunning(){
		return running;
	}
	
	
	private void renderGridContents(Graphics2D g){
		// Black if cell is 'alive', white if not
		for (int r = 0; r < cellCount; r++){
			for (int c = 0; c < cellCount; c++){
				if (getBoard().getSpace(r, c) == 1){
					Color current = g.getColor();
					g.setColor(Color.black);
					g.fillRect(c*cellWidth, r*cellHeight, cellWidth, cellHeight);
					g.setColor(current);
				} else {
					Color current = g.getColor();
					g.setColor(Color.white);
					g.fillRect(c*cellWidth, r*cellHeight, cellWidth, cellHeight);
					g.setColor(current);
				}
			}
		}
	}
	
	private void renderGridLines(Graphics2D g){
		Color current = g.getColor();
		int width = cellCount*cellWidth;
		int height = cellCount*cellHeight;
		g.setColor(Color.lightGray);
		for (int i = 0; i <= cellCount; i++){
			g.drawLine(i*cellWidth, 0, i*cellWidth, height);
			g.drawLine(0, i*cellHeight, width, i*cellHeight);
		}
		g.setColor(current);
	}

	public void stopRunning(){
		running = false;
	}
	
	public void setUpdateSpeed(int intervalInms){
		runInterval = intervalInms;
	}
	
	@Override
	public void run() {
		running = true;
		while (running){
			gol.iterate();
			repaint();
			try {
				Thread.sleep(runInterval);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
