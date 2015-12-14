package sample;

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

    public Asteroid(Main app, Terrain terrain){
        asteroid = new Circle(50.0, Paint.valueOf("5B96A3"));
        double randX = Math.random() * Main.pane.getWidth();
        asteroid.setCenterX(randX);
        asteroid.setCenterY(50);
        Main.pane.getChildren().add(asteroid);
    }

    void update(){
        asteroid.setCenterY(asteroid.getCenterY() + 2);

    }
}
