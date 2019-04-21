package hockey.java.controller;

import java.util.Random;

import hockey.java.Hockey;
import hockey.java.front.CenterCircle;
import hockey.java.front.Goal;
import hockey.java.front.Midline;
import hockey.java.front.Player;
import hockey.java.front.PowerUpMidline;
import hockey.java.front.PowerUpPuckSize;
import hockey.java.front.Puck;
import hockey.java.front.Striker;
import hockey.java.front.Walls;
import hockey.java.packet.PacketMouse;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class GameController {
	@FXML
	private BorderPane borderPane;

	@FXML
	private StackPane stackPane;

	private Pane playfield;

	
    //private static boolean started = false;
    
	private AnimationTimer loop;
    private static Striker selfStriker;
    private static Striker otherStriker;
    private static Puck puck;
    private static Goal otherGoal;
    private static Goal selfGoal;
    private static Walls walls1, walls2;
    private static Midline mid;
    private static CenterCircle center;
    private static PowerUpMidline puMidline;
    private static PowerUpPuckSize puPuck;
  	Text p1s;
   	Text p2s;
   	Text goalMessage = new Text("");

	public void init(int playerId) {
		 System.out.println("init game start");
	    
	   	 selfStriker = new Striker(new Player(playerId));
	   	 otherStriker = new Striker(new Player(3-playerId));
	   	 puck = new Puck();
	
	   	 otherGoal = new Goal(1, puck, selfStriker.getPlayer());
	   	 selfGoal = new Goal(2, puck, otherStriker.getPlayer());
	   	 walls1 = new Walls(1);
	   	 walls2 = new Walls(2);
	
	   	 mid = new Midline();
	   	 center = new CenterCircle();
	   	 
	   	 puMidline = new PowerUpMidline();
	     puPuck = new PowerUpPuckSize();
		 
	     p1s = new Text("0");
	   	 p1s.setFont(Font.font ("Verdana", 50));
	   	 p1s.setFill(Color.RED);
	   	 p1s.setX(350);
	   	 p1s.setY(400);
	   	 
	   	 p2s = new Text("0");
	   	 p2s.setFont(Font.font ("Verdana", 50));
	   	 p2s.setFill(Color.RED);
	   	 p2s.setX(350);
	   	 p2s.setY(335);
	   	 
	   	 goalMessage.setFont(Font.font ("Verdana", 50));
	   	 goalMessage.setFill(Color.RED);
	   	 goalMessage.setX(200);
	   	 goalMessage.setY(350);
	   	 
	   	 // create containers // playfield for our strikers 
	     playfield = new Pane();
	     borderPane.getChildren().addAll(playfield);
	     borderPane.setCenter(stackPane);
	     
	     playfield.getChildren().add(walls1);
	     playfield.getChildren().add(walls2);
	     playfield.getChildren().add(mid);
	     playfield.getChildren().add(center);
	     playfield.getChildren().add(otherStriker);
	     playfield.getChildren().add(selfStriker);
	     playfield.getChildren().add(puck);
	     playfield.getChildren().add(otherGoal);
	     playfield.getChildren().add(selfGoal);
	     playfield.getChildren().add(p1s);
	     playfield.getChildren().add(p2s);
	     //display static shapes
	     center.display();
	     otherGoal.display();
	     selfGoal.display();
	     walls1.display();
	     walls2.display();
	     playfield.getChildren().add(puMidline);
		 playfield.getChildren().add(puPuck);
	     mid.display();
       
    	 // capture mouse position
    	 Hockey.getGameScene().addEventFilter(MouseEvent.ANY, e -> {
	       	 //u1.getStriker().getPlayer().getMouse().set(e.getX(), e.getY());
	       	 selfStriker.getPlayer().getMouse().set(e.getX(), e.getY());
    	 });

    	 System.out.println("init game end");
     	
    	
       
	}
	
	public void gameLoop() {
				
   	 	loop = new AnimationTimer() {
	      	 Random r = new Random();
	      	 int ran = (int) (r.nextDouble() * 1000);
	      	 @Override
	      	 public void handle(long now) {

	           	 // send self mouse to server
	      		 System.out.println("sending mouse to server");
	           	 Hockey.getNetwork().getClient().sendTCP(new PacketMouse(selfStriker.getPlayer().getPlayerID(),selfStriker.getPlayer().getMouse().x,selfStriker.getPlayer().getMouse().y));
           }
       };
       System.out.println("starting game loop");
       loop.start();
              
	}
	
	public void stopGame() {
		
		loop.stop();
	    System.out.println("stopped game loop");
	}

	public static Striker getSelfStriker() {
		return selfStriker;
	}

	public static void setSelfStriker(Striker selfStriker) {
		GameController.selfStriker = selfStriker;
	}

	public static Striker getOtherStriker() {
		return otherStriker;
	}

	public static void setOtherStriker(Striker otherStriker) {
		GameController.otherStriker = otherStriker;
	}

	public static Puck getPuck() {
		return puck;
	}

	public static void setPuck(Puck puck) {
		GameController.puck = puck;
	}

	
	public void setScore(int playerNum) {
		if(playerNum == 1) {
			int oldScore = Integer.parseInt(p1s.getText());
			p1s.setText(Integer.toString(oldScore+1));
		}else if(playerNum == 2) {
			int oldScore = Integer.parseInt(p2s.getText());
			p2s.setText(Integer.toString(oldScore+1));
		}
	}
	
	public void showGoalMessage(String message) {
		//TODO
	}

	public static PowerUpMidline getPuMidline() {
		return puMidline;
	}

	public static void setPuMidline(PowerUpMidline puMidline) {
		GameController.puMidline = puMidline;
	}

	public static PowerUpPuckSize getPuPuck() {
		return puPuck;
	}

	public static void setPuPuck(PowerUpPuckSize puPuck) {
		GameController.puPuck = puPuck;
	}
	
}
