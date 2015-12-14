package sample;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

/**
 * Created by Danny on 12/11/2015.
 */
public class ShipGame {
    ImageView ship;
    ImageView fire;
    boolean show = true;
    Point2D vector = new Point2D(0, .4);
    private double dy = 0;
    private double dx = 0;

    public ShipGame(Main App, Terrain terrain){
        ship = new ImageView(new Image("images/rocket.png", 50, 75, false, false));
        ship.xProperty().setValue(500);
        Main.pane.getChildren().add(ship);
        fire = new ImageView(new Image("images/rocket_fire.png", 50 , 75, false, false));

    }

    void showfire(){
        if(show == true) {
            System.out.println(ship.getLayoutX());
            fire.xProperty().unbind();
            fire.yProperty().unbind();
            fire.rotateProperty().unbind();
            double xBind = Math.sin(ship.getRotate()) * 60;
            double yBind = (-1) * Math.cos(ship.getRotate()) * 60;
            fire.xProperty().bind(ship.xProperty());
            fire.yProperty().bind(ship.yProperty());
            fire.rotateProperty().bind(ship.rotateProperty());
            System.out.println(ship);
            System.out.print(ship.getRotate());
            Main.pane.getChildren().add(fire);
            show = false;
        }

    }
    void accel(){
        System.out.println(vector.angle(0, 1));
        dy -= vector.getY();
        dx -= vector.getX();
    }

    void left(){
        //vector = vector.subtract(-., 0);
        double xp = ((vector.getX() * Math.cos(Math.toRadians(-5)) - (vector.getY() * Math.sin(Math.toRadians(-5)))));
        double yp = ((vector.getY() * Math.cos(Math.toRadians(-5)) + (vector.getX() * Math.sin(Math.toRadians(-5)))));
        vector = new Point2D(xp, yp);
        ship.rotateProperty().set(ship.getRotate() - 5);
    }

    void right(){
        double xp = ((vector.getX() * Math.cos(Math.toRadians(5)) - (vector.getY() * Math.sin(Math.toRadians(5)))));
        double yp = ((vector.getY() * Math.cos(Math.toRadians(5)) + (vector.getX() * Math.sin(Math.toRadians(5)))));
        vector = new Point2D(xp, yp);
        ship.rotateProperty().set(ship.getRotate() + 5);
    }

    void update(){
        dy += .1;
        ship.yProperty().setValue(ship.getY() + dy);
        ship.xProperty().setValue(ship.getX() + dx);

    }

    void removeFire(){
        show = true;
        System.out.print("flag");
        Main.pane.getChildren().remove(fire);

    }

}
