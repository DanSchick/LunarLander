package sample;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Reader;
import java.util.ArrayList;

/**
 * Created by Danny on 12/11/2015.
 */
public class ShipGame {
    static ImageView ship;
    ImageView fire;
    boolean show = true;
    public static boolean lost = false;
    public static double fuel = 100;
    public static ArrayList<Fuel> canisters = new ArrayList<Fuel>();
    private double stepCounter = 0;
    public static boolean accel = false;
    public static boolean left = false;
    public static boolean right = false;
    public static ArrayList<Asteroid> asterioids = new ArrayList<Asteroid>();
    private Main app;
    private Terrain terrainApp;
    Point2D vector = new Point2D(0, .4);
    private double dy = 0;
    private double dx = 0;
    private long delayTime;
    // this is absolutely gross and a horrible fix i'm cringing writing this code
    private boolean finished = false;

    public ShipGame(Main App, Terrain terrain){
        ship = new ImageView(new Image("images/rocket.png", 50, 75, false, false));
        ship.setX(Main.pane.getWidth()/2);
        ship.setY(Main.pane.getHeight()/2);
        app = App;
        canisters.add(new Fuel(app, terrain));
        terrainApp = terrain;
        asterioids.add(new Asteroid(App, terrain));
        ship.xProperty().setValue(500);
        Main.pane.getChildren().add(ship);
        fire = new ImageView(new Image("images/rocket_fire.png", 50 , 75, false, false));

    }

    void showfire(){
        if(show) {
            ship.imageProperty().setValue(new Image("images/rocket_fired.png", 50, 75, false, false));
            show = false;
        }

    }
    void accel(){
        fuel -= .5;
        Main.fuelCounter.textProperty().setValue(Double.toString(fuel));
        Main.fuelCounter.setFill(Paint.valueOf("C32335"));
        Main.fuelCounter.setStroke(Paint.valueOf("C32335"));
        dy -= vector.getY();
        dx -= vector.getX();
    }

    void left(){
        // trust me
        double xp = ((vector.getX() * Math.cos(Math.toRadians(-10)) - (vector.getY() * Math.sin(Math.toRadians(-10)))));
        double yp = ((vector.getY() * Math.cos(Math.toRadians(-10)) + (vector.getX() * Math.sin(Math.toRadians(-10)))));
        vector = new Point2D(xp, yp);
        ship.rotateProperty().set(ship.getRotate() - 10);
    }

    void right(){
        // trust me
        double xp = ((vector.getX() * Math.cos(Math.toRadians(10)) - (vector.getY() * Math.sin(Math.toRadians(10)))));
        double yp = ((vector.getY() * Math.cos(Math.toRadians(10)) + (vector.getX() * Math.sin(Math.toRadians(10)))));
        vector = new Point2D(xp, yp);
        ship.rotateProperty().set(ship.getRotate() + 10);
    }

    void update(){
        ship.yProperty().setValue(ship.getY() + dy);
        ship.xProperty().setValue(ship.getX() + dx);
        if(delayTime != 0) {
            Main.fuelCounter.setFill(Paint.valueOf("5B96A3"));
            Main.fuelCounter.setStroke(Paint.valueOf("5B96A3"));
        }
        if(!lost) {
            stepCounter += 1;
            if (Math.random() < .02) {
                asterioids.add(new Asteroid(app, terrainApp));
            }

            if (Math.random() < .005){
                canisters.add(new Fuel(app, terrainApp));
            }

            Fuel removeCan = null;
            for (Fuel can : canisters) {
                if (ship.getBoundsInParent().intersects(can.getBounds())) {
                    can.captureCan();
                    removeCan = can;
                    Main.fuelCounter.setFill(Paint.valueOf("3AC337"));
                    Main.fuelCounter.setStroke(Paint.valueOf("3AC337"));
                    delayTime = System.nanoTime();
                }
            }
            if(delayTime != 0 && (System.nanoTime() - delayTime)/1000000000.0 > 1.5){
                Main.fuelCounter.setFill(Paint.valueOf("5B96A3"));
                Main.fuelCounter.setStroke(Paint.valueOf("5B96A3"));
                delayTime = 0;
            }
            canisters.remove(removeCan);
            if(fuel <= 0){
                lost = true;
                finished = true;
            }
            if (left) {
                left();
            }
            if (right) {
                right();
            }
            if (accel) {
                accel();
                showfire();
            }
            if (!accel) {
                removeFire();
            }
            for (Asteroid asteroid : asterioids) {
                if (ship.getBoundsInParent().intersects(asteroid.asteroid.getBoundsInParent())) {
                    ship.imageProperty().setValue(new Image("images/rocket_crashed.png", 50, 75, false, false));
                    lost = true;
                    finished = true;
                }
            }
        } else if(finished){ // if game is lost
            finished = false;
            Text gameOver = new Text(Main.pane.getWidth()/2 - 100, Main.pane.getHeight()/2, "Game over!");
            gameOver.fontProperty().setValue(new Font("arial", 48));
            Text textScore = new Text(Main.pane.getWidth()/2-100, Main.pane.getHeight()/2 +100, "Your score was : " + Double.toString((double)(System.nanoTime() - Main.startTime) / 1000000000.0));
            textScore.fontProperty().setValue(gameOver.getFont());
            textScore.setFill(Paint.valueOf("7B8CC3"));
            gameOver.setFill(Paint.valueOf("7B8CC3"));
            Main.pane.getChildren().add(gameOver);
            Main.pane.getChildren().add(textScore);


        }

    }

    void removeFire(){
        show = true;
        ship.imageProperty().setValue(new Image("images/rocket.png", 50, 75, false, false));
        Main.pane.getChildren().remove(fire);

    }


}
