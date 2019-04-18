package hockey.java.front;

import java.io.IOException;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

import hockey.java.Master;

import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Game extends Application{
	
    Pane playfield;
 
    Player p1;
    Player p2;
    Striker s1;
    Striker s2;
    Puck puck;
    Goal goal1;
    Goal goal2;
    Walls walls1, walls2;
    Midline mid;
    CenterCircle center;
    double friction;
    
    public void displayWinner(Pane playfield, String name) {
    	
    }
    
    @Override
    public void start(Stage stage) {
    	 p1 = new Player("p1", 1);
    	 p1.setPlayerID(1);
    	 p2 = new Player("p2", 2);
    	 p2.setPlayerID(2);
    	 //s1 = new Striker(p1);
    	 //s2 = new Striker();
    	
    	//u1 = new User();
    	//u2 = new User();
    	//u1.initStriker();
    	//u2.initStriker();
    	
    	puck = new Puck();

    	//goal1 = new Goal(1, puck, u1.getStriker().getPlayer());
    	//goal2 = new Goal(2, puck, u2.getStriker().getPlayer());
    	goal1 = new Goal(1, puck, p1);
    	goal2 = new Goal(2, puck, p2);
    	walls1 = new Walls(1);
    	walls2 = new Walls(2);

    	 mid = new Midline();
    	 center = new CenterCircle();
    	 friction = .988;
    	 
    	 //Text p1s = new Text(Integer.toString(u1.getStriker().getPlayer().getScore()));
    	 Text p1s = new Text(Integer.toString(p1.getScore()));
     	 p1s.setFont(Font.font ("Verdana", 50));
     	 p1s.setFill(Color.RED);
     	 p1s.setX(350);
     	 p1s.setY(400);

     	 //Text p2s = new Text(Integer.toString(u2.getStriker().getPlayer().getScore()));
     	Text p2s = new Text(Integer.toString(p2.getScore()));
    	 p2s.setFont(Font.font ("Verdana", 50));
    	 p2s.setFill(Color.RED);
    	 p2s.setX(350);
    	 p2s.setY(335);
    	 
    	 // create containers
    	 BorderPane root = new BorderPane();
         StackPane layerPane = new StackPane();
         // playfield for our strikers 
         playfield = new Pane();
         layerPane.getChildren().addAll(playfield);
         root.setCenter(layerPane);
         Scene scene = new Scene(root, BoardSettings.SCENE_WIDTH, BoardSettings.SCENE_HEIGHT);
         stage.setScene(scene);
         stage.show();
         playfield.getChildren().add(walls1);
         playfield.getChildren().add(walls2);
         playfield.getChildren().add(mid);
         playfield.getChildren().add(center);
         //playfield.getChildren().add(u1.getStriker());
         playfield.getChildren().add(s1);
         playfield.getChildren().add(puck);
         playfield.getChildren().add(goal1);
         playfield.getChildren().add(goal2);
         playfield.getChildren().add(p1s);
         playfield.getChildren().add(p2s);
         //display static shapes
         center.display();
         goal1.display();
         goal2.display();
         walls1.display();
         walls2.display();
         PowerUp pu = new PowerUp();
         PowerUpPuckSize puckPU = new PowerUpPuckSize();
    	 playfield.getChildren().add(pu);
    	 playfield.getChildren().add(puckPU);
         mid.display();
         // capture mouse position
         scene.addEventFilter(MouseEvent.ANY, e -> {
        	 //u1.getStriker().getPlayer().getMouse().set(e.getX(), e.getY());
        	 p1.getMouse().set(e.getX(), e.getY());
         });
         // process all strikers
         AnimationTimer loop = new AnimationTimer() {
        	 int time = 0;
        	 Random r = new Random();
        	 int ran = (int) (r.nextDouble() * 1000);
             @Override
             public void handle(long now) {
                 // move
            	 pu.display();
            	 puckPU.display();
            	 //u1.getStriker().step(u1.getStriker().getPlayer().getMouse(), mid);
            	 s1.step(p1.getMouse(), mid);
                 //s2.step(p1.getMouse());
                 s1.checkBoundaries(puck);
                 
                 //s2.checkBoundaries();
                 if (puck.checkBoundaries()) {
                	 //striker can't overlap with puck
                 }
                 //puck.collision(u1.getStriker());
                 puck.collision(s1);
                 //puck.collision(s2);
                 puck.step(friction);
                 // update in fx scene
                 //u1.getStriker().display();
                 s1.display();
                 //s2.display();
                 puck.display();
                 if (goal1.goalDetection(1)) {
                	 //u1.getStriker().getPlayer().score();
                	 p1.score();
                	 //p1s.setText(Integer.toString(u1.getStriker().getPlayer().getScore()));
                	 p1s.setText(Integer.toString(p1.getScore()));
                	 stage.show();
                	 //u1.getStriker().reset(1);
                	 s1.reset(1);
                	 mid.reset();
                	 puck.resetSize();
                	 //s2.reset(2);
                 }
                 if (goal2.goalDetection(2)) {
                	 //u2.getStriker().getPlayer().score();
                	 p2.score();
                	//p2s.setText(Integer.toString(u1.getStriker().getPlayer().getScore()));
                	 p2s.setText(Integer.toString(p2.getScore()));
                	 stage.show();
                	 //u2.getStriker().reset(2);
                	 s1.reset(1);
                	 mid.reset();
                	 puck.resetSize();
                	 //s2.reset(2);
                 }
                 //if (u1.getStriker().getPlayer().getScore() == 7) {
                	 //Text text = new Text("  " + u1.getUsername() + " wins!");
                 if (p1.getScore() == 7) {
                	 Text text = new Text("  " + p1.getUsername() + " wins!");
                 	 text.setFont(Font.font ("Verdana", 50));
                 	 text.setFill(Color.RED);
                 	 text.setY(350);
                 	 playfield.getChildren().add(text);
                 	 stage.show();
                	 stop();
                 }
//                 if (u2.getStriker().getPlayer().getScore() == 7) {
//                	 Text text = new Text("  " + u2.getUsername() + " wins!");
                 if (p2.getScore() == 7) {
                	 Text text = new Text("  " + p2.getUsername() + " wins!");
                	 text.setFont(Font.font ("Verdana", 50));
                 	 text.setFill(Color.RED);
                 	 text.setY(350);
                 	 playfield.getChildren().add(text);
                 	 stage.show();
                	 stop();
                 } 
                 puck.collision(mid, pu);
                 puck.collision(puckPU);
                 time++;
                 if (time == ran) {
                	 time = 0;
                	 int choose = new Random().nextInt() % 2;
                	 if (choose == 0) {
                		 if (pu.hidden() && mid.inMiddle()) {
                    		 pu.reset();
                    	 }
                	 }
                	 else {
                		 if (puckPU.hidden() && puck.width == 30) {
                    		 puckPU.reset(puck);
                    	 }
                	 }
                 }
             }
         };
         loop.start();
    }
    /*
    public static Game getGame() {
        return game;
    }
    
    public static void register(EndPoint endPoint) {
    	Kryo kryo = endPoint.getKryo();
    	kryo.register(Puck.class);
    	kryo.register(Striker.class);
    	kryo.register(Player.class);
    	kryo.register(PacketMessage.class);
    }

    public static void main(String[] args) {  	
    	if (args.length > 0) {
    		server = new Master();            
            game = new Game();
        } else {
            launch(args);
        }
    } */
    
}