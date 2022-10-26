package com.main;

import com.main.controller.Singleton;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Singleton.getInstance().stage = stage;
        Singleton.getInstance().changeView("Start Menu","views/startmenu.fxml");
        stage.show();
    }

    public static void main(String[] args) {
        Images.loadSprites();
        launch();
    }
}