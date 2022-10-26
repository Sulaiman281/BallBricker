package com.main.model.objects;

import javafx.beans.property.*;
import javafx.geometry.Bounds;
import javafx.scene.paint.Paint;

/**
 * Abstract GameObject class has all the common functionality
 * which will be shared in all the game object
 * like Position, Color, and Bounds.
 */
public abstract class Object {

    private DoubleProperty x;
    private DoubleProperty y;

    private ObjectProperty<Paint> fill;

    private ReadOnlyObjectProperty<Bounds> bounds;

    public Object(double x, double y) {
        this.x = new SimpleDoubleProperty(x);
        this.y = new SimpleDoubleProperty(y);
        fill = new SimpleObjectProperty<>();
    }

    public void update_pos(double x, double y) {
        setX(getX() + x);
        setY(getY() + y);
    }

    public double getX() {
        return x.get();
    }

    public DoubleProperty xProperty() {
        return x;
    }

    public void setX(double x) {
        this.x.set(x);
    }

    public double getY() {
        return y.get();
    }

    public DoubleProperty yProperty() {
        return y;
    }

    public void setY(double y) {
        this.y.set(y);
    }

    public ObjectProperty<Paint> fillProperty() {
        return fill;
    }

    public void setFill(Paint fill) {
        this.fill.set(fill);
    }

    public Bounds getBounds() {
        return bounds.get();
    }

    public void setBounds(ReadOnlyObjectProperty<Bounds> bounds) {
        this.bounds = bounds;
    }

    public static double normalize(double value, double min, double max) {
        return (value - min) / (max - min);
    }
}
