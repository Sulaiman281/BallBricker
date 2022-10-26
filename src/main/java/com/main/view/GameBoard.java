package com.main.view;

import com.main.Settings;
import com.main.Images;
import com.main.controller.Singleton;
import com.main.data.FileHandling;
import com.main.model.objects.Ball;
import com.main.model.objects.Object;
import com.main.model.objects.Paddle;
import com.main.model.level.Brick;
import javafx.animation.AnimationTimer;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.time.LocalTime;
import java.util.ArrayList;

/**
 * Our GameBoard Class is Inherited from Pane ( JavaFX Container class ) to get all the behavior of it.
 */
public class GameBoard extends Pane {

    private Paddle paddle;
    private Ball ball;

    private AnimationTimer animation;
    private boolean gameRunning = false;

    private Label msg, lives_text, score, timer_text;
    private int player_lives;

    // variables for times.
    private int seconds;
    private LocalTime lastTime;

    private Rectangle[][] grid = new Rectangle[Settings.BOX_COLS][Settings.BOX_ROWS];
    private ArrayList<Brick> bricks = new ArrayList<>();

    private IntegerProperty paddleDirection;

    /**
     * Inside the constructor we set up the environment for player.
     */
    public GameBoard() {
        new Scene(this, Settings.BOARD_WIDTH, Settings.BOARD_HEIGHT);
        this.styleProperty().bind(Singleton.getInstance().bg_color);

        setObjects();
        player_lives = 3;

        seconds = 180;

        this.getChildren().add(paddle.makePaddle());
        this.getChildren().add(ball.createBall());
        ball.setDirection(0, -1);
        menubar();
        startLoop();
        emptyGrid();
        game_labels();

        paddleDirection = new SimpleIntegerProperty();
        unpack_level(Singleton.getInstance().levelGenerator.generateLevel());
        this.getScene().setOnKeyPressed(e -> {
            KeyCode key = e.getCode();
            if (key == KeyCode.SPACE) {
                if (msg.getText().contains("Game Over")) {
                    FileHandling.save_highScore();
                    Singleton.getInstance().changeView("Start Menu", "views/startmenu.fxml");
                }
                if (gameRunning) {
                    animation.stop();
                    msg.setOpacity(1);
                    gameRunning = false;
                } else {
                    animation.start();
                    gameRunning = true;
                    msg.setOpacity(0);
                    lastTime = LocalTime.now();
                }
            }
            if (!gameRunning) return;
            int dir = 0;
            if (key == KeyCode.LEFT || key == KeyCode.A) dir = -1;
            else if (key == KeyCode.RIGHT || key == KeyCode.D) dir = 1;
            paddleDirection.set(dir);
        });
    }

    private void setObjects() {
        paddle = new Paddle(Settings.BOARD_WIDTH / 2 - 75, Settings.BOARD_HEIGHT - 50, 100, 25);
        paddle.setFill(new ImagePattern(Images.PADDLE));
        paddle.setMove_speed(4);
        ball = new Ball(Settings.BOARD_WIDTH / 2, Settings.BOARD_HEIGHT - 70, 10);
        ball.setSpeed(5);
    }

    /**
     * Creating the empty boxes which is all the grid cell in the pane.
     * so we can load and assign level boxes in it.
     */
    void emptyGrid() {
        double cell_width = Settings.BOARD_WIDTH / Settings.BOX_COLS;
        double cell_height = (Settings.BOARD_HEIGHT - 150) / Settings.BOX_ROWS;
        for (int i = 0; i < Settings.BOX_COLS; i++) {
            for (int j = 0; j < Settings.BOX_ROWS; j++) {
                Rectangle rect = new Rectangle();
                rect.setFill(Color.TRANSPARENT);
                rect.setStroke(Color.TRANSPARENT);
                rect.setWidth(cell_width);
                rect.setHeight(cell_height);
                rect.setLayoutX(i * cell_width);
                rect.setLayoutY(j * cell_height + 50);
                this.getChildren().add(rect);
                grid[i][j] = rect;
            }
        }
    }

    /**
     * adding menu bar and menu options at the top of screen.
     */
    public void menubar() {
        MenuBar menuBar = new MenuBar();
        menuBar.setLayoutX(0);
        menuBar.setLayoutY(0);
        menuBar.prefWidthProperty().bind(this.widthProperty());
        Menu file = new Menu("File");
        file.getItems().add(menuItem("New Game", e -> {
            // start from the new game.
            gamePause();
            Singleton.getInstance().levelGenerator.setComplexity(3);
            unpack_level(Singleton.getInstance().levelGenerator.generateLevel());
            seconds = 180;
            msg.setOpacity(1);
            msg.setText("Press \"Space\" To Start");
        }));
        file.getItems().add(menuItem("Exit", e -> {
            gamePause();
            Singleton.getInstance().changeView("Start Menu", "views/startmenu.fxml");
        }));
        Menu about = new Menu("Help");
        about.getItems().add(menuItem("About", e -> {
            gamePause();
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setHeaderText("About me");
            dialog.setContentText(Settings.about_me);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            dialog.showAndWait();
        }));
        about.getItems().add(menuItem("High Score", e -> {
            gamePause();
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setHeaderText("High Score");
            dialog.setContentText(
                    Singleton.getInstance().highScore.isEmpty() ? "No High Score Yet." :
                            Singleton.getInstance().highScore
            );
            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            dialog.showAndWait();
        }));
        menuBar.getMenus().addAll(file, about);
        this.getChildren().add(menuBar);
    }

    private void gamePause() {
        if (gameRunning) {
            animation.stop();
            gameRunning = false;
        }
    }

    /**
     * Create MenuItem with given attributes.
     *
     * @param str
     * @param event
     * @return
     */
    public MenuItem menuItem(String str, EventHandler<ActionEvent> event) {
        MenuItem item = new MenuItem(str);
        item.setOnAction(event);
        return item;
    }

    /**
     * building timeline.
     * where out all the iterations will take place
     * like updating all our game objects.
     */
    void startLoop() {
        animation = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (bricks.isEmpty()) {
                    if(Singleton.getInstance().levelGenerator.getComplexity() > Settings.BOX_ROWS){
                        msg.setOpacity(1);
                        msg.setText("Highest Complexity Winner Game Over");
                        animation.stop();
                        FileHandling.save_highScore();
                    } else {
                        Singleton.getInstance().levelGenerator.setComplexity(Singleton.getInstance().levelGenerator.getComplexity()+2);
                        unpack_level(Singleton.getInstance().levelGenerator.generateLevel());
                        msg.setOpacity(1);
                        msg.setText("Loading Next Level...");
                        seconds = (int) (180+(seconds * .3f));
                        animation.stop();
                        try {
                            Thread.sleep(1000 * 3);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                        msg.setText("Press \"Space\" To Continue");
                        paddle.setX(Settings.BOARD_WIDTH / 2 - 75);
                        paddle.setY(Settings.BOARD_HEIGHT - 50);
                        ball.setX(Settings.BOARD_WIDTH / 2);
                        ball.setY(Settings.BOARD_HEIGHT - 70);
                    }

                }
                update_ball();

                // updating paddle here.
                paddle.move(paddleDirection.get());
                bricks.removeIf(brick -> {
                    if (brick.getHits() <= 0) {
                        Singleton.getInstance().active_player.setScore(Singleton.getInstance().active_player.getScore() + 1);
                        score.setText(
                                "Score: " + Singleton.getInstance().active_player.getScore() + ""
                        );
                        return true;
                    }
                    return false;
                });
                update_time();
            }
        };
    }

    /**
     * update ball on each timeline 50 milliseconds
     */
    void update_ball() {
        ball.setPath_x(horizontalBoundary());
        ball.setPath_y(verticalBoundary());

        if (ball.getBounds().intersects(paddle.getBounds())) {
            double normalize = Object.normalize(ball.getX(), paddle.getX(), paddle.getX() + paddle.getWidth());
            ball.setPath_x((-.5 + normalize));
            ball.setPath_y(normalize > .23 && normalize < .63 ? -2 : -1);
        }
        for (Brick brick : this.bricks) {
            if (brick.getRect().getBoundsInParent().intersects(ball.getBounds())) {
                brick.ballHit();
//                ball.setDirX(ball.getX_pos() > box.getRect().getX()+box.getRect().getWidth()/2 ? .8 : -.8);
                ball.setPath_y(ball.getPath_y() * -1);//ball.getY_pos() > box.getRect().getY()+box.getRect().getHeight()/2 ? 1 : -1);
                break;
            }
        }
        ball.update();
    }

    /**
     * update time each 50 milliseconds
     */
    void update_time() {
        int newSec = LocalTime.now().getSecond() - lastTime.getSecond();
        if (seconds <= 0) {
            // times up.
            msg.setOpacity(1);
            msg.setText("Game Over Times Up");
            animation.stop();
            return;
        }
        seconds = seconds - Math.abs(newSec);
        timer_text.setText("Timer: " + seconds);
        lastTime = LocalTime.now();
    }

    /**
     * check ball X ( Horizontal ) direction that is has reached to the edged or not.
     *
     * @return
     */
    public double horizontalBoundary() {
        if (ball.getX() < ball.getSize()) return Math.abs(ball.getPath_x() * -1);
        if (ball.getX() > Settings.BOARD_WIDTH - ball.getSize()) return (-ball.getPath_x() * -1) * -1;
        return this.ball.getPath_x();
    }

    /**
     * check ball Y ( Vertical ) direction that is has reached to the edged or not.
     *
     * @return
     */
    public double verticalBoundary() {
        if (ball.getY() < 25) return 1;
        if (ball.getY() > Settings.BOARD_HEIGHT + ball.getSize() / 2) resetPlayer();
        return this.ball.getPath_y();
    }

    /**
     * Assigning Game Labels.
     */
    void game_labels() {
        msg = getLabel("Press \"Space\" to Start", Font.font("Arial", FontWeight.BOLD, 32),
                Settings.BOARD_WIDTH / 2 - 150, Settings.BOARD_HEIGHT / 2, 400, 50);
        lives_text = getLabel("Lives: 3", Font.font("Arial", 12),
                Settings.BOARD_WIDTH - 200, 0, 70, 25);
        score = getLabel("Score: 0", Font.font("Arial", 12),
                Settings.BOARD_WIDTH - 100, 0, 70, 25);

        timer_text = getLabel("Timer: " + seconds, Font.font("Arial", 12),
                Settings.BOARD_WIDTH / 2, 0, 100, 25);

        this.getChildren().addAll(msg, lives_text, score, timer_text);
    }

    /**
     * When Player lose a Life this method will be called to reset the paddle and ball position
     */
    public void resetPlayer() {
        msg.setOpacity(1);
        if (player_lives-- <= 0) {
            msg.setText("Game Over");
            msg.setOpacity(1);
            animation.stop();
            return;
        }
        lives_text.setText("Lives: " + player_lives);
        animation.stop();
        paddle.setX(Settings.BOARD_WIDTH / 2 - 75);
        paddle.setY(Settings.BOARD_HEIGHT - 50);
        ball.setX(Settings.BOARD_WIDTH / 2);
        ball.setY(Settings.BOARD_HEIGHT - 70);
    }

    void clearBoxes(){
        for (Brick brick : bricks) {
            brick.getRect().setFill(Color.TRANSPARENT);
        }
        bricks.clear();
    }

    private void unpack_level(char[][] level) {
        clearBoxes();
        for (int i = 0; i < Settings.BOX_COLS; i++) {
            for (int j = 0; j < Settings.BOX_ROWS; j++) {
                char ch = level[i][j];
                if(ch != ' '){
                    bricks.add(new Brick(ch, grid[i][j]));
                }
            }
        }
    }

    /**
     *
     * @param str
     * @param font
     * @param layoutX
     * @param layoutY
     * @param width
     * @param height
     * @return
     */
    public Label getLabel(String str, Font font, double layoutX, double layoutY, double width, double height){
        Label label = new Label(str);
        label.setFont(font);
        label.setLayoutX(layoutX);
        label.setLayoutY(layoutY);
        label.setPrefSize(width, height);
        label.setWrapText(true);
        return label;
    }
}
