package hockey.java.front;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Goal extends Pane {
	
	private PVector location;
	private PVector puckLocation;
	private Puck currPuck;
	private boolean goalDetected;
	private String name;
	private Player player;
	
	int id;
	double width = 110;
	double height = BoardSettings.BOARDER_HEIGHT;
	double xstart = 145;
	double bottomYstart = BoardSettings.SCENE_HEIGHT - BoardSettings.BOARDER_HEIGHT;
	
	Rectangle goal;
	
	public Goal(int num, Puck puck, Player p){
		currPuck = puck;
		this.player = p;
		id = num;
		puckLocation = puck.getLocation();
		if(num == 1) {
			location = new PVector(xstart, 0);
			goal = new Rectangle();
			goal.setWidth(width);
			goal.setHeight(height);
			goal.setStroke(Color.RED);
	        goal.setFill(Color.RED);
		}
		else {
			location = new PVector(xstart, bottomYstart);
			goal = new Rectangle();
			goal.setWidth(width);
			goal.setHeight(height);
			goal.setStroke(Color.RED);
	        goal.setFill(Color.RED);
			
		}
		getChildren().add(goal);
	}
	
	public boolean goalDetection(int num){
		if(num == 1) {
			if((puckLocation.x+(currPuck.width/2) > location.x) && 
					puckLocation.x-(currPuck.width/2) < location.x+width && 
					puckLocation.y < location.y+(height/2))
			{
				currPuck.move(200, 250);
				return true;
				//System.out.println("GOAL1");
			}
		}
		else {
			if((puckLocation.x+(currPuck.width/2) > location.x) && 
					puckLocation.x-(currPuck.width/2) < location.x+width && 
					puckLocation.y > location.y-(height/2))
			{
				currPuck.move(200, 450);
				return true;
				//System.out.println("GOAL2");
			}
		}
		return false;
	}
	
	public PVector getGoalLoc() {
		return location;
	}
	
	public void display() {
        relocate(location.x, location.y);
    }

}
