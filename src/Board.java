import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Board implements Constants {
	
	JFrame f;
	JPanel c;
	JButton stopStart;
	JPanel south;
	JLabel timeLabel;
	int time;
	JTextField interval;
	Cell[][] cellPool;
	StopWatch stopwatch;
	JButton buildCoolThing;
	
	Board(){
		JFrame f = new JFrame("Conway's Game of Life");
		f.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		buildSouthPanel(f);
		time = 0;
		stopwatch = new StopWatch();
		
		setUpCellPool(f);
		//buildGliderGun();
		f.setSize(1280,800);  
        f.setVisible(true);
        f.setLocation(100, 100);
		f.requestFocus();
	}

	private void setUpCellPool(JFrame f) {
		JPanel c = new JPanel();
		f.add(c);
		c.setLayout(null);
		
		cellPool = new Cell[rows][cols];
		for (int row = 0 ;  row < cellPool.length ; row++){
			for (int col = 0 ; col < cellPool[0].length ; col++){
				Cell cell = new Cell();
				cell.icon.setBounds(row * 10, col * 10, 10, 10);
				cell.icon.addActionListener(new ActionListener (){
					@Override
					public void actionPerformed(ActionEvent e) {
						cell.click();
					}
				});
				cellPool[row][col] = cell;
				c.add(cell.icon);
			}
		}
		
		//have to add neighbours after all the cells have been populated
		for (int row = 1 ;  row < cellPool.length - 1 ; row++){
			for (int col = 1 ; col < cellPool[0].length - 1 ; col++){
				Cell cell = cellPool[row][col];
				//System.err.println(col + "" +  row);
				cell.neighbours.add(cellPool[row-1][col-1]);
				cell.neighbours.add(cellPool[row-1][col]);
				cell.neighbours.add(cellPool[row-1][col+1]);
				cell.neighbours.add(cellPool[row][col-1]);
				//cell is not it's own neighbour
				cell.neighbours.add(cellPool[row][col+1]);
				cell.neighbours.add(cellPool[row+1][col-1]);
				cell.neighbours.add(cellPool[row+1][col]);
				cell.neighbours.add(cellPool[row+1][col+1]);
			}
		}
	}
	
	private String[] getGliderGun(){
		String input = "5#1-5#2-6#1-6#2-5#11-6#11-7#11-4#12-3#13-3#14-8#12-9#13-9#14-6#15-4#16-5#17-6#17-7#17-6#18-8#16-3#21-4#21-5#21-3#22-4#22-5#22-2#23-6#23-1#25-2#25-6#25-7#25-3#35-4#35-3#36-4#36";
		return input.split("-");
	}
	
	private void buildGliderGun(){
		
		for (String s : getGliderGun()){
			String[] s2 = s.split("#");
			int row = Integer.parseInt(s2[0]);
			int col = Integer.parseInt(s2[1]);
			cellPool[row][col].cellIsBorn();
		}
		
	}
	
	private void resetAllTiles(){
		for (int row = 0 ;  row < cellPool.length ; row++){
			for (int col = 0 ; col < cellPool[0].length ; col++){
				cellPool[row][col].Alive = false;
			}
		}
		time = 0;
		buildCoolThing.setEnabled(true);
		stopwatch.stop();
		interval.setEditable(true);
		stopwatch.time = 0;
		stopwatch.evolve(cellPool);
		
	}

	private void buildSouthPanel(JFrame f) {
		JPanel south = new JPanel();
		interval = new JTextField(10);
		interval.setText("100");
		timeLabel = new JLabel("Generation: " + time);
		
		JButton startStop = new JButton("Start");
		startStop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (startStop.getText() == "Start"){
					stopwatch.start(cellPool, timeLabel, interval);
					startStop.setText("Stop");
				} else {
					stopwatch.stop();
					startStop.setText("Start");
					interval.setEditable(true);
				}
			}
		});
		
		buildCoolThing = new JButton("Build Glider Gun!");
		buildCoolThing.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				buildGliderGun();
				buildCoolThing.setEnabled(false);
			}
		});
		
		
		JButton reset = new JButton("reset");
		reset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				resetAllTiles();
			}
		});
		
		south.setLayout(new GridLayout(1,6));
		south.add(startStop);
		south.add(timeLabel);
		timeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		JLabel intervalLabel =  new JLabel("Interval (ms):");
		intervalLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		south.add(intervalLabel);
		south.add(interval);
		south.add(buildCoolThing);
		south.add(reset);
		f.add(south, BorderLayout.SOUTH );
	}
	
}
