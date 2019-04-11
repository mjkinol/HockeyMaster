package hockey.java;

import java.util.Random;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Striker extends Pane {
	
	private double mass;

    private PVector location;
    private PVector velocity;
    
    private Random ran = new Random();

    double width = 50;
    double height = width;
    double centerX = width / 2.0;
    double centerY = height / 2.0;
    double radius = width / 2.0;

    Circle circle;
	
	public Striker() {
		mass = 10;
		
		location = new PVector(ran.nextDouble() * width, ran.nextDouble() * height);
        velocity = new PVector(0, 0);

        circle = new Circle(radius);
        circle.setCenterX(radius);
        circle.setCenterY(radius);

        circle.setStroke(Color.BLUE);
        circle.setFill(Color.BLUE.deriveColor(1, 1, 1, 0.3));

        getChildren().add(circle);
	}
	
	public void step(PVector mouse) {
		velocity = PVector.sub(mouse, location, velocity);
        location.copy(mouse);
    }

    public void checkBoundaries() {
        if (location.x > Settings.SCENE_WIDTH) {
            location.x = Settings.SCENE_WIDTH;
        } else if (location.x < 0) {
            location.x = 0;
        }

        if (location.y > Settings.SCENE_HEIGHT) {
        	location.y = Settings.SCENE_HEIGHT;
        } else if (location.y < 0) {
        	location.y = 0;
        }
    }

    public void display() {
        relocate(location.x - centerX, location.y - centerY);
    }
    
    public PVector getLocation() {
    	return location;
    }
    
    public PVector getVelocity() {
    	return velocity;
    }
    
    public double getRadius() {
    	return radius;
    }
    
    public double getMass() {
    	return mass;
    }
    
}