import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JButton;

public class Cell {
	
	boolean Alive;
	ArrayList<Cell> cell;
	JButton icon;
	ArrayList<Cell> neighbours;
	
	boolean AliveNextGen;
	Cell(){
		Alive = false;
		icon = new JButton();
		cellDies();
		neighbours = new ArrayList<Cell>();
	}
	
	private boolean isAlive(){
		return this.Alive;
	}
	
	private int countActiveNeighbours(){
		int activeNeighbours = 0;
		if (neighbours.size() == 0) return 0;
		for (int n = 0; n< neighbours.size() ; n++){
			
			Cell neighbour = neighbours.get(n);
			
			if (neighbour.isAlive()){
				activeNeighbours++;
			}
		}
		return activeNeighbours;
	}
	
	public void checkStatus(){
		int activeNeighbours = countActiveNeighbours();
		if (activeNeighbours < 2 || activeNeighbours > 3 ){
			AliveNextGen = false;
		} else if (activeNeighbours == 3) {
			AliveNextGen = true;
		} else{
			AliveNextGen = isAlive();
		}
	}
	
	public void speciate(){
		if (AliveNextGen){
			cellIsBorn();
		} else{
			cellDies();
		}
	}
	
	public void cellIsBorn(){
		Alive = true;
		icon.setBackground(Color.yellow);
	}
	
	public void cellDies(){
		Alive = false;
		icon.setBackground(Color.DARK_GRAY);
	}
	
	public void click(){
		if (isAlive()){
			cellDies();
		} else {
			cellIsBorn();
		}
	}
	
}

