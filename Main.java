package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {
    private Timeline animation;
    private ShipGame game;
    private Terrain terrain;
    public static BorderPane pane;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        pane = new BorderPane();
        terrain = new Terrain();
        pane.setCenter(terrain);

        game = new ShipGame(this, terrain);

        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(pane, 1000, 800));
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
            this.resume();
        };
        // Create an animation for alternating text
        animation = new Timeline(new KeyFrame(Duration.millis(40), eventHandler));
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play();
    }

    //TODO: catch multiple keys pressed at once
    // http://stackoverflow.com/questions/31539250/javafx-catch-more-then-one-key-press-event-at-the-same-time
    private void setUpKeyPresses() {
        terrain.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case LEFT:
                    game.left();
                    break;
                case RIGHT:
                    game.right();
                    break;
                case SPACE:
                    game.showfire();
                    game.accel();
                    break;

            }
        });

        terrain.setOnKeyReleased(e -> {
            System.out.println(e.getCode().toString());
            if(e.getCode() == KeyCode.SPACE){
                game.removeFire();
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