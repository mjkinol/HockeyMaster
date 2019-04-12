package hockey.java;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Puck extends Pane{
	private double mass;
	
	private PVector location;
    private PVector velocity;

    double width = 30;
    double height = width;
    double centerX = width / 2.0;
    double centerY = height / 2.0;
    double radius = width / 2.0;
    
    Circle circle;
    
    int collisioncounter = 0;
	
	public Puck() {
		mass = 5;
		
		location = new PVector(200, 350);
        velocity = new PVector(0, 0);

        circle = new Circle(radius);
        circle.setCenterX(radius);
        circle.setCenterY(radius);

        circle.setStroke(Color.RED);
        circle.setFill(Color.RED.deriveColor(1, 1, 1, 0.3));

        getChildren().add(circle);
	}
	
	public void step(double friction) {
		location.add(velocity);
		velocity.mult(friction);
	}
	
	public void checkBoundaries() {
		//todo
		//if puck is in the goal, keep it moving
		//Alot of constants here.. be careful of changing goal size
		if((location.x-radius > 145-5) && 
				(location.x+radius < (145+110)+5) && 
				(location.y-radius < 0+Settings.BOARDER_HEIGHT))
		{}
		else if((location.x-radius > 145-5) && 
				(location.x+radius < (145+110)+5) && 
				(location.y+radius > (Settings.SCENE_HEIGHT-Settings.BOARDER_HEIGHT)))
		{}
		else {	
	        if (location.x > Settings.SCENE_WIDTH - radius - Settings.BOARDER_HEIGHT) {
	        	location.x = Settings.SCENE_WIDTH - radius - Settings.BOARDER_HEIGHT;
	        	velocity.x *= -1;
	        } 
	        else if (location.x <= 0+Settings.BOARDER_HEIGHT + radius) {
	        	location.x = 0 + radius + Settings.BOARDER_HEIGHT;
	        	velocity.x *= -1;
	        }
	
	        if (location.y > Settings.SCENE_HEIGHT - radius - Settings.BOARDER_HEIGHT) {
	        	location.y = Settings.SCENE_HEIGHT - radius - Settings.BOARDER_HEIGHT;
	            velocity.y *= -1;
	        } 
	        else if (location.y <= 0 + radius + Settings.BOARDER_HEIGHT) {
	        	location.y = 0 + radius + Settings.BOARDER_HEIGHT;
	        	velocity.y *= -1;
	        }
		}
    }
	
	
	public void collision(Striker s) {
		double px = location.x;
		double py = location.y;
		double sx = s.getLocation().x;
		double sy = s.getLocation().y;
		double sr = s.getRadius();
		if (Math.sqrt((px - sx) * (px - sx) + (py - sy) * (py - sy)) <= radius + sr) {
			recalculate(s);
		}
	}
	
	public void recalculate(Striker s) {
		PVector sV = new PVector(s.getVelocity().x, s.getVelocity().y);
		PVector pV = new PVector(velocity.x, velocity.y);
		pV.mult(mass - s.getMass());
		sV.mult(2 * s.getMass());
		pV.add(sV);
		pV.div(mass + s.getMass());
		velocity.copy(pV);
		velocity.limit(30);
	}
	
	public void display() {
        relocate(location.x - centerX, location.y - centerY);
    }
    
    public PVector getVelocity() {
    	return velocity;
    }
    
    public PVector getLocation() {
    	return location;
    }
    
    

}
