import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JLabel;
import javax.swing.JTextField;

public class StopWatch implements Constants{
    private TimerTask task;
    int time;
    
    public int getInterval(JTextField interval){
    	
    	try {
			if (Integer.parseInt( interval.getText()) > 5){
				return Integer.parseInt( interval.getText());
			}
		} catch (NumberFormatException e) {
			return minInterval;
		}
    	return minInterval;
    }
    
    public void start(Cell[][] cellPool, JLabel timeLabel, JTextField interval) {
        task = new TimerTask() {
       
            @Override
            public void run() {
				evolve(cellPool);
				timeLabel.setText("Generation: " + time++);
            }

        };
        new Timer().scheduleAtFixedRate(task, 0, getInterval(interval));
        interval.setEditable(false);
    }
	public void evolve(Cell[][] cellPool) {
		for (int row = 0 ; row < cellPool.length ; row++){
			for ( int col  = 0 ; col < cellPool[0].length ; col++){
				cellPool[row][col].checkStatus();;
			}
		}
		for (int row = 0 ; row < cellPool.length ; row++){
			for ( int col  = 0 ; col < cellPool[0].length ; col++){
				cellPool[row][col].speciate();
			}
		}
	}


    public void stop() {
        if (task == null) return;
        task.cancel();
    }
}