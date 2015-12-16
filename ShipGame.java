package sample;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

import java.util.ArrayList;

/**
 * Created by Danny on 12/11/2015.
 */
public class ShipGame {
    static ImageView ship;
    ImageView fire;
    boolean show = true;
    public boolean lost = false;
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
        if(!lost) {
            stepCounter += 1;
            if (Math.random() < .02) {
                asterioids.add(new Asteroid(app, terrainApp));
            }

            if (Math.random() < .005){
                canisters.add(new Fuel(app, terrainApp));
            }
            for (Asteroid asteroid : asterioids) {
                if (ship.getBoundsInParent().intersects(asteroid.asteroid.getBoundsInParent())) {
                    ship.imageProperty().setValue(new Image("images/rocket_crashed.png", 50, 75, false, false));
                    lost = true;
                }
            }
            Fuel removeCan = null;
            for (Fuel can : canisters) {
                if (ship.getBoundsInParent().intersects(can.getBounds())) {
                    can.captureCan();
                    removeCan = can;
                }
            }
            canisters.remove(removeCan);
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
        }

    }

    void removeFire(){
        show = true;
        ship.imageProperty().setValue(new Image("images/rocket.png", 50, 75, false, false));
        Main.pane.getChildren().remove(fire);

    }

}
