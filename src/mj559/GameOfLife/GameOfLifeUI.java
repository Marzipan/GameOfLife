package mj559.GameOfLife;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class GameOfLifeUI extends JFrame {

	private static final long serialVersionUID = 3852805494679574380L;
	
	// Simulation Speed slider settings (in milliseconds)
	private final int SLIDER_MAX = 300;
	private final int SLIDER_DEFAULT = SLIDER_MAX/2;
	
	
	Game gol = new Game(50);
	
	// UI Components

	GamePanel gamePanel = new GamePanel(gol, 10, 10);
	JPanel controls = new JPanel();
	JButton bNext = new JButton("Next");
	JButton bClear = new JButton("Clear");
	JButton bToggleRunning = new JButton("Start");
	JSlider speedSlider = new JSlider(0, SLIDER_MAX, SLIDER_DEFAULT);
	
	public GameOfLifeUI(){
		
		super("Game of Life");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		gamePanel.setUpdateSpeed(speedSlider.getValue());
		
		// Set up the panel containing controls
		controls.add(bNext);
		controls.add(bClear);
		controls.add(bToggleRunning);
		controls.add(speedSlider);
		speedSlider.setInverted(true);

		// Assemble main GUI window
		setLayout(new BorderLayout());
		add(gamePanel);
		add(controls, BorderLayout.SOUTH);
		this.pack();
		this.setResizable(false);
		this.setVisible(true);
		
		// Set up Listeners for components
		bNext.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				gol.iterate();
				gamePanel.repaint();
			}
		});
		
		bClear.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				gol.clearBoard();
				gamePanel.repaint();
			}
		});
		
		bToggleRunning.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				toggleRunning();
			}
		});
		
		speedSlider.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent arg0) {
				gamePanel.setUpdateSpeed(speedSlider.getValue());
				
			}
		});
	}
	
	// Start/stop the simulation
	private void toggleRunning(){
		if(gamePanel.isRunning()){
			gamePanel.stopRunning();
			bToggleRunning.setText("Start");
		} else {
			Thread T = new Thread(gamePanel);
			T.start();
			bToggleRunning.setText("Stop");
		}
	}
	
	public static void main(String[] args) {
		new GameOfLifeUI();
	}

}
