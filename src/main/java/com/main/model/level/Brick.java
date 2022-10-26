package com.main.model.level;

import com.main.Images;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

/**
 * Box is like out brick class which hold the information of a brick object.
 */
public class Brick {

    private char box;
    private int hits;
    private Rectangle rect;

    public Brick(char box, Rectangle rect) {
        this.box = box;
        this.hits = 1;
        this.rect = rect;
        switch (box) {
            case 'P' -> rect.setFill(new ImagePattern(Images.PINK_BRICK));
            case 'B' -> rect.setFill(new ImagePattern(Images.SKY_BRICK));
            case 'S' -> rect.setFill(new ImagePattern(Images.STEEL_BRICK));
            default -> rect.setStroke(Color.BLACK);
        }
    }

    public char getBox() {
        return box;
    }

    public int getHits() {
        return hits;
    }

    public Rectangle getRect() {
        return rect;
    }

    public void ballHit() {
        hits--;
        if (hits <= 0)
            rect.setFill(Color.TRANSPARENT);
    }
}
