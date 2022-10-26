package com.main.model.objects;

import com.main.Settings;
import javafx.scene.shape.Rectangle;

/**
 * Paddle is Inheritance from GameObject.
 */
public class Paddle extends Object {

    private double width;
    private double height;

    private double move_speed;

    public Paddle(double x, double y, double width, double height) {
        super(x, y);

        this.width = width;
        this.height = height;
    }

    /**
     * Creating the shape of the Paddle.
     * @return
     */
    public Rectangle makePaddle(){
        Rectangle rectangle = new Rectangle();
        rectangle.setWidth(width);
        rectangle.setHeight(height);
        rectangle.fillProperty().bind(fillProperty());
        rectangle.layoutXProperty().bind(xProperty());
        rectangle.layoutYProperty().bind(yProperty());
        setBounds(rectangle.boundsInParentProperty());
        return rectangle;
    }

    /**
     * moving paddle based on input.
     * @param dir
     */
    public void move(int dir){
        if(getX() < 0) dir = 1;
        else if(getX() > Settings.BOARD_WIDTH - width) dir = -1;
        update_pos(move_speed*dir, 0);
    }

    public double getWidth() {
        return width;
    }

    public void setMove_speed(double move_speed) {
        this.move_speed = move_speed;
    }
}
