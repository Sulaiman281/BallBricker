package com.main.controller;

import com.main.Main;
import com.main.Settings;
import com.main.model.Player;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;

public class Singleton {

    public Stage stage;

    public Player active_player;

    public String highScore;

    public LevelGenerator levelGenerator;

    private static Singleton singleton;

    public StringProperty bg_color;

    public ObjectProperty<Paint> ball_fill;


    private Singleton() {
        levelGenerator = new LevelGenerator();
        ball_fill = new SimpleObjectProperty<>();
        bg_color = new SimpleStringProperty("-fx-background-color: " + Settings.bg_color1);

    }

    /**
     * Singleton Constructor.
     *
     * @return
     */
    public static Singleton getInstance() {
        if (singleton == null) singleton = new Singleton();
        return singleton;
    }

    /**
     * Switch Scene method Switch the scene from current to next scene provided.
     *
     * @param scene
     */
    public void switchScene(String title, Scene scene) {
        stage.setTitle(title);
        stage.setScene(scene);
        stage.centerOnScreen();
    }

    /**
     * load the image from given path.
     *
     * @param path
     * @return
     * @throws URISyntaxException
     */
    public Image load_image(String path) throws URISyntaxException {
        path = "images/".concat(path).concat(".png");
        return new Image(Main.class.getResource(path).toURI().toString());
    }

    public FXMLLoader fxmlLoader(String path) {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource(path));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return loader;
    }

    public void changeView(String title, String path) {
        FXMLLoader fxmlLoader = fxmlLoader(path);

        switchScene(title, new Scene(fxmlLoader.getRoot()));
    }

}
