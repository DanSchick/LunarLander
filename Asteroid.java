package sample;

import javafx.scene.image.Image;
import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;
import javafx.scene.paint.*;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

import java.awt.*;

/**
 * Created by Danny on 12/14/2015.
 */
public class Asteroid {
    public ImageView asteroid;
    private Point2D vector;
    private double randRotate;

    public Asteroid(Main app, Terrain terrain){
        double randRadius = Math.random() * 70 + 15;
        randRotate = Math.random() * 10 - 5;
        asteroid = new ImageView(new Image("images/asteroid.png", randRadius, randRadius, false, false));
        // we set the circle to start in a random area
        if(Math.random() < .5) {
            double randX = Math.random() * Main.pane.getWidth();
            asteroid.setX(randX);
            if (Math.random() <.5) {
                asteroid.setY(-50);
            } else {
                asteroid.setY(Main.pane.getHeight() + 50);
            }
        } else {
            double randY = Math.random() * Main.pane.getHeight();
            if(Math.random() < .5){
                asteroid.setX(-25);
                asteroid.setY(randY);

            } else {
                asteroid.setX(Main.pane.getWidth() + 50);
                asteroid.setY(randY);
            }

        }
        Main.pane.getChildren().add(asteroid);
        vector = getDirection();
    }

    void update(){
        asteroid.setX(asteroid.getX() + vector.getX());
        asteroid.setY(asteroid.getY() + vector.getY() );
        asteroid.rotateProperty().setValue(asteroid.getRotate() + randRotate);
    }

    Point2D getDirection(){
        // now we get a random direction vector
        // must be towards the center of screen-ish
        Point2D oldLoc = new Point2D(asteroid.getX(), asteroid.getY());

        // this algorithm creates a point in a circle of radius 300 in center of screen
        double u = Math.random();
        double v = Math.random();
        double w = 300 * Math.sqrt(u);
        double t = 2 * Math.PI * v;
        double x = w * Math.cos(t);
        double y = w * Math.sin(t);
        Point2D newLoc = new Point2D(x + (Main.pane.getWidth() / 2), y + (Main.pane.getHeight() / 2));
        // return the vector needed to get to that location
        double randSpeed = Math.random() * 4 + 1;
        return newLoc.subtract(oldLoc).normalize().multiply(randSpeed);

    }
}
