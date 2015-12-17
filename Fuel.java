package sample;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

/**
 * Created by Danny on 12/15/2015.
 */
public class Fuel {
        private ImageView canister;
        private Point2D vector;

        public Fuel(Main app, Terrain terrain){
                canister = new ImageView(new Image("images/jerryCan.png", 25, 25, false, false));
                if(Math.random() < .5) {
                        double randX = Math.random() * Main.pane.getWidth();
                        canister.setX(randX);
                        if (Math.random() <.5) {
                                canister.setY(-50);
                        } else {
                                canister.setY(Main.pane.getHeight() + 50);
                        }
                } else {
                        double randY = Math.random() * Main.pane.getHeight();
                        if(Math.random() < .5){
                                canister.setX(-25);
                                canister.setY(randY);

                        } else {
                                canister.setX(Main.pane.getWidth() + 50);
                                canister.setY(randY);
                        }

                }
                Main.pane.getChildren().add(canister);
                vector = getDirection();
        }

        void update() {
                canister.setX(canister.getX() + vector.getX());
                canister.setY(canister.getY() + vector.getY() );
        }

        Point2D getDirection(){
                // now we get a random direction vector
                // must be towards the center of screen-ish
                Point2D oldLoc = new Point2D(canister.getX(), canister.getY());

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

        public void captureCan(){
                canister.setY(1000);
                Main.pane.getChildren().remove(canister);
                ShipGame.fuel += 25;
                Main.fuelCounter.textProperty().setValue(Double.toString(ShipGame.fuel));
        }

        Bounds getBounds(){
                return canister.getBoundsInParent();
        }
}
