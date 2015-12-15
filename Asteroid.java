package sample;

import javafx.geometry.Point2D;
import javafx.scene.paint.*;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

import java.awt.*;

/**
 * Created by Danny on 12/14/2015.
 */
public class Asteroid {
    public Circle asteroid;
    private Point2D vector;

    public Asteroid(Main app, Terrain terrain){
        double randRadius = Math.random() * 40 + 10;
        asteroid = new Circle(randRadius, Paint.valueOf("5B96A3"));
        // we set the circle to start in a random area
        if(Math.random() < .5) {
            double randX = Math.random() * Main.pane.getWidth();
            asteroid.setCenterX(randX);
            if (Math.random() <.5) {
                asteroid.setCenterY(-50);
            } else {
                asteroid.setCenterY(Main.pane.getHeight() + 50);
            }
        } else {
            double randY = Math.random() * Main.pane.getHeight();
            if(Math.random() < .5){
                asteroid.setCenterX(-25);
                asteroid.setCenterY(randY);

            } else {
                asteroid.setCenterX(Main.pane.getWidth() + 50);
                asteroid.setCenterY(randY);
            }

        }
        Main.pane.getChildren().add(asteroid);
        vector = getDirection();
    }

    void update(){
        asteroid.setCenterX(asteroid.getCenterX() + vector.getX());
        asteroid.setCenterY(asteroid.getCenterY() + vector.getY() );
    }

    Point2D getDirection(){
        // now we get a random direction vector
        // must be towards the center of screen-ish
        Point2D oldLoc = new Point2D(asteroid.getCenterX(), asteroid.getCenterY());

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
