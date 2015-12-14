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
    public static boolean accel = false;
    public static boolean left = false;
    public static boolean right = false;
    public static Asteroid asterioid;
    Point2D vector = new Point2D(0, .4);
    private double dy = 0;
    private double dx = 0;

    public ShipGame(Main App, Terrain terrain){
        ship = new ImageView(new Image("images/rocket.png", 50, 75, false, false));
        asterioid = new Asteroid(App, terrain);
        ship.xProperty().setValue(500);
        Main.pane.getChildren().add(ship);
        fire = new ImageView(new Image("images/rocket_fire.png", 50 , 75, false, false));

    }

    void showfire(){
        if(show) {
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
        dy -= vector.getY();
        dx -= vector.getX();
    }

    void left(){
        //vector = vector.subtract(-., 0);
        double xp = ((vector.getX() * Math.cos(Math.toRadians(-10)) - (vector.getY() * Math.sin(Math.toRadians(-10)))));
        double yp = ((vector.getY() * Math.cos(Math.toRadians(-10)) + (vector.getX() * Math.sin(Math.toRadians(-10)))));
        vector = new Point2D(xp, yp);
        ship.rotateProperty().set(ship.getRotate() - 10);
    }

    void right(){
        double xp = ((vector.getX() * Math.cos(Math.toRadians(10)) - (vector.getY() * Math.sin(Math.toRadians(10)))));
        double yp = ((vector.getY() * Math.cos(Math.toRadians(10)) + (vector.getX() * Math.sin(Math.toRadians(10)))));
        vector = new Point2D(xp, yp);
        ship.rotateProperty().set(ship.getRotate() + 10);
    }

    void update(){
        if(ship.getBoundsInParent().intersects(asterioid.asteroid.getBoundsInParent())){
            ship.imageProperty().setValue(new Image("images/rocket_crashed.png", 50, 75, false, false));
        }
        if(left){ left(); }
        if(right){right();}
        if(accel){
            accel();
            showfire();
        }
        if(!accel){
            removeFire();
        }
        dy += .1;
        ship.yProperty().setValue(ship.getY() + dy);
        ship.xProperty().setValue(ship.getX() + dx);

    }

    void removeFire(){
        show = true;
        Main.pane.getChildren().remove(fire);

    }

}
