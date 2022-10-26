package com.main;

import com.main.controller.Singleton;
import javafx.scene.image.Image;

import java.net.URISyntaxException;

/**
 * This class will hold all of our images which will be used in our class.
 */
public class Images {

    public static Image PINK_BRICK;

    public static Image SKY_BRICK;

    public static Image STEEL_BRICK;

    public static Image PADDLE;

    public static Image DARK_BALL;
    public static Image PINK_BALL;
    public static Image SKY_BALL;

    public static void loadSprites(){
        try {
            PINK_BRICK = Singleton.getInstance().load_image("pink_brick");
            SKY_BRICK = Singleton.getInstance().load_image("sky_brick");
            STEEL_BRICK = Singleton.getInstance().load_image("steel_brick");
            PADDLE = Singleton.getInstance().load_image("paddle");
            DARK_BALL = Singleton.getInstance().load_image("ball");
            PINK_BALL = Singleton.getInstance().load_image("pink_ball");
            SKY_BALL = Singleton.getInstance().load_image("skyball");
        } catch (URISyntaxException ignored) {

        }
    }
}
