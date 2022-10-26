package com.main.controller;

import com.main.Settings;
import com.main.Images;
import com.main.data.FileHandling;
import com.main.model.Player;
import com.main.view.GameBoard;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

/**
 * Our StartMenu Class.
 */
public class StartMenu{

    @FXML
    private TextField name_input;

    @FXML
    private Circle ball_1;
    @FXML
    private Circle ball_2;
    @FXML
    private Circle ball_3;
    @FXML
    private Circle selected_ball;

    @FXML
    private Rectangle bg_1;
    @FXML
    private Rectangle bg_2;
    @FXML
    private Rectangle bg_3;
    @FXML
    private Rectangle bg_selected;

    @FXML
    void initialize(){

        ball_1.setFill(new ImagePattern(Images.DARK_BALL));
        ball_2.setFill(new ImagePattern(Images.PINK_BALL));
        ball_3.setFill(new ImagePattern(Images.SKY_BALL));

        bg_1.setFill(Color.web(Settings.bg_color1));
        bg_2.setFill(Color.web(Settings.bg_color2));
        bg_3.setFill(Color.web(Settings.bg_color3));

        ball_1.setOnMouseClicked(e-> selectBall(ball_1.getFill()));
        ball_2.setOnMouseClicked(e-> selectBall(ball_2.getFill()));
        ball_3.setOnMouseClicked(e-> selectBall(ball_3.getFill()));

        bg_1.setOnMouseClicked(e-> select_bg(Settings.bg_color1));
        bg_2.setOnMouseClicked(e-> select_bg(Settings.bg_color2));
        bg_3.setOnMouseClicked(e-> select_bg(Settings.bg_color3));
    }

    void selectBall(Paint color){
        selected_ball.setFill(color);
        Singleton.getInstance().ball_fill.set(selected_ball.getFill());
    }

    void select_bg(String fill){
        bg_selected.setFill(Color.web(fill));
        Singleton.getInstance().bg_color.set("-fx-background-color: "+fill);
    }

    @FXML
    private void start_game(ActionEvent event){
        if(name_input.getText().isEmpty()) return;
        Singleton.getInstance().highScore = FileHandling.load_highScore();
        Singleton.getInstance().active_player = new Player(name_input.getText());
        Singleton.getInstance().switchScene("Game Board", new GameBoard().getScene());
    }

}