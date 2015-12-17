package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.text.DateFormat;
import java.util.Calendar;


public class Main extends Application {
    private Timeline animation;
    private ShipGame game;
    private Scene scene;
    public static long startTime;
    public Text clock;
    public static Text fuelCounter;
    private Terrain terrain;
    public static BorderPane pane;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        startTime = System.nanoTime();
        System.out.println(startTime);
        pane = new BorderPane();
        fuelCounter = new Text(50, 30, "test");
        fuelCounter.fontProperty().setValue(new Font("arial", 36));
        fuelCounter.setStroke(Paint.valueOf("5B96A3"));
        fuelCounter.setFill(Paint.valueOf("5B96A3"));
        fuelCounter.textProperty().setValue(Double.toString(ShipGame.fuel));
        fuelCounter.autosize();
        scene = new Scene(pane, 1000, 800);
        terrain = new Terrain();
        Image bckImg = new Image("images/spaceBackground.jpg");
        BackgroundSize size = new BackgroundSize(1280, 800, true, true, true, false);
        BackgroundImage backImg = new BackgroundImage(bckImg, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, size);
        Background background = new Background(backImg);
        pane.setBackground(background);
        pane.setCenter(terrain);
        pane.getChildren().add(fuelCounter);

        game = new ShipGame(this, terrain);

        primaryStage.setTitle("Hello World");
        primaryStage.setScene(scene);
        primaryStage.show();
        setUpKeyPresses();
        setUpAnimation();
        setUpTimer();
    }


    private void setUpTimer() {
        clock = new Text(50, 75, "clock");
        clock.fontProperty().setValue(new Font("arial", 36));
        clock.setStroke(Paint.valueOf("5B96A3"));
        clock.setFill(Paint.valueOf("5B96A3"));
        pane.getChildren().add(clock);
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

    private void setUpAnimation() {
        // Create a handler
        EventHandler<ActionEvent> eventHandler = (ActionEvent e) -> {
            this.pause();
            long elapsed = System.nanoTime() - startTime;
            double elapsedSecs = (double)elapsed / 1000000000.0;
            clock.textProperty().setValue(Double.toString(elapsedSecs));
            game.update();
            for(Asteroid asteroid : ShipGame.asterioids){
                asteroid.update();
            }
            for(Fuel can : ShipGame.canisters){
                can.update();
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
                case UP:
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
                case UP:
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
