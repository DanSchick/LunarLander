package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.*;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.text.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;

public class Main extends Application {
    private Timeline animation;
    private ShipGame game;
    private Scene scene;
    public static Text fuelCounter;
    private Terrain terrain;
    public static BorderPane pane;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        pane = new BorderPane();
        fuelCounter = new Text(50, 30, "test");
        fuelCounter.setStroke(Paint.valueOf("5B96A3"));
        fuelCounter.textProperty().setValue(Double.toString(ShipGame.fuel));
        fuelCounter.autosize();
        scene = new Scene(pane, 1000, 800);
        scene.setFill(javafx.scene.paint.Paint.valueOf("151010"));
        terrain = new Terrain();
        pane.setCenter(terrain);
        pane.getChildren().add(fuelCounter);

        game = new ShipGame(this, terrain);


        primaryStage.setTitle("Hello World");
        primaryStage.setScene(scene);
        primaryStage.show();
        setUpKeyPresses();
        setUpAnimation();
    }

    public static void addLine(Line line){
        pane.getChildren().add(line);
    }


    public static void main(String[] args) {
        Application.launch(args);
    }

    private void setUpAnimation() {
        // Create a handler
        EventHandler<ActionEvent> eventHandler = (ActionEvent e) -> {
            this.pause();
            game.update();
            for(Asteroid asteroid : ShipGame.asterioids){
                asteroid.update();
            }
            this.resume();
        };
        // Create an animation for alternating text
        animation = new Timeline(new KeyFrame(Duration.millis(40), eventHandler));
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play();
    }

    private void setUpKeyPresses() {
        terrain.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case LEFT:
                    ShipGame.left = true;
                    break;
                case RIGHT:
                    ShipGame.right = true;
                    break;
                case SPACE:
                    ShipGame.accel = true;
                    break;

            }
        });

        terrain.setOnKeyReleased(e -> {
            switch (e.getCode()) {
                case LEFT:
                    ShipGame.left = false;
                    break;
                case RIGHT:
                    ShipGame.right = false;
                    break;
                case SPACE:
                    ShipGame.accel = false;
            }
        });
        terrain.requestFocus();

    }

    /**
     * Pauses the animation.
     */
    private void pause() {
        animation.pause();
    }

    /**
     * Resumes the animation.
     */
    private void resume() {
        animation.play();
    }


}
