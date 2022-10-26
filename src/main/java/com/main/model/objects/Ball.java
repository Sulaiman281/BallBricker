package com.main.model.objects;

import com.main.controller.Singleton;
import javafx.scene.shape.Circle;

/**
 * Ball inheritance from Objects class.
 * Ball Model class
 */
public class Ball extends Object {

    private double path_x;
    private double path_y;

    private double size;
    private double speed;

    public Ball(double x, double y, double size) {
        super(x, y);
        this.size = size;
    }

    /**
     * Create the Circle Shape of Ball to implement in game.
     * @return
     */
    public Circle createBall(){
        Circle ball = new Circle();
        ball.fillProperty().bind(Singleton.getInstance().ball_fill);
        ball.setRadius(size);
        ball.layoutXProperty().bind(xProperty());
        ball.layoutYProperty().bind(yProperty());
        setBounds(ball.boundsInParentProperty());
        return ball;
    }

    /**
     * this will call the parent class Object to update the
     * x and y coordinate with direction and speed.
     */
    public void update(){
        update_pos(speed * path_x, speed * path_y);
    }

    /**
     * set the direction of ball where it will go.
     * @param dirX
     * @param dirY
     */
    public void setDirection(double dirX, double dirY){
        this.path_x = dirX;
        this.path_y = dirY;
    }

    public double getPath_x() {
        return path_x;
    }

    public void setPath_x(double path_x) {
        this.path_x = path_x;
    }

    public double getPath_y() {
        return path_y;
    }

    public void setPath_y(double path_y) {
        this.path_y = path_y;
    }

    public double getSize() {
        return size;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }
}
