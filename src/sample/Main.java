package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.Random;

public class Main extends Application implements EventHandler<KeyEvent>, Engine.OnAction {

    private int refreshMilliSec = 4;

    private float xBreak = 0.0f;
    private float centerBreakX;
    private float yBreak = 640.0f;

    private int breakWidth = 130;
    private int breakHeight = 30;
    private int halfBreakWidth = breakWidth / 2;

    private int sceneWidth = 500;
    private int sceneHeigt = 700;

    private static int LEFT = 1;
    private static int RIGHT = 2;

    private Circle ball;
    private float xBall;
    private float yBall;

    private Rectangle rect;
    private Thread moveThread;
    private int ballRadius = 15;

    private Engine engine;

    @Override
    public void start(Stage primaryStage) throws Exception {
        initBall();
        initBreak();

        Pane root = new Pane();
        Label scoreLabel = new Label("Score: " + score);
        root.getChildren().addAll(rect, ball, scoreLabel);
        Scene scene = new Scene(root, sceneWidth, sceneHeigt);
        scene.getStylesheets().add("sample/style.css");
        scene.setOnKeyPressed(this);

        primaryStage.setTitle("Hello World");
        primaryStage.setScene(scene);
        primaryStage.show();


        engine = new Engine();
        engine.setOnAction(this);
        engine.setFps(8);
        engine.start();

    }


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void handle(KeyEvent event) {
        switch (event.getCode()) {
            case LEFT:
                move(LEFT);
                break;
            case RIGHT:
                move(RIGHT);
                break;
            case DOWN:
                //setPhysicsToBall();
                break;
        }
    }

    private void move(final int direction) {
        for (int i = 0; i < 20; i++) {
            if (xBreak == (sceneWidth - breakWidth) && direction == RIGHT) {
                break;
            }
            if (xBreak == 0 && direction == LEFT) {
                break;
            }
            if (direction == RIGHT) {
                xBreak++;
            } else {
                xBreak--;
            }
            centerBreakX = xBreak + halfBreakWidth;
        }
    }


    private void initBall() {
        Random random = new Random();
        xBall = random.nextInt(sceneWidth) + 1;
        yBall = random.nextInt(sceneHeigt - 200) + 1;
        ball = new Circle();
        ball.setRadius(ballRadius);
        ball.setFill(Color.ORANGE);
    }

    private void initBreak() {
        rect = new Rectangle();
        rect.setWidth(breakWidth);
        rect.setHeight(breakHeight);
        rect.setX(xBreak);
        rect.setY(yBreak);
        rect.setFill(Color.AQUA);
    }

    private int score;

    boolean goDownBall = true;
    boolean colideToBreak = false;
    boolean colideToBreakAndMoveToRight = true;
    boolean colideToRightWall = false;
    boolean colideToLeftWall = false;

    private void resetColideFlags() {
        colideToBreak = false;
        colideToBreakAndMoveToRight = false;
        colideToRightWall = false;
        colideToLeftWall = false;
    }

    private  void setPhysicsToBall() {
        if (goDownBall) {
            yBall++;
        } else {
            yBall--;
        }

        if (yBall <= 0) {
            goDownBall = true;
            return;
        }
        if (yBall >= sceneHeigt) {
            goDownBall = false;
            System.out.println("Game Over!");
            return;
        }

        if (yBall == yBreak - ballRadius) {
            //System.out.println("Colide1");
            if (xBall >= xBreak && xBall <= xBreak + breakWidth) {
                resetColideFlags();
                colideToBreak = true;
                goDownBall = false;
                if (xBall - centerBreakX > 0) {
                    colideToBreakAndMoveToRight = true;
                } else {
                    colideToBreakAndMoveToRight = false;
                }
                //System.out.println("Colide2");
            }
        }

        if (xBall == sceneWidth) {
            resetColideFlags();
            colideToRightWall = true;
        }

        if (xBall == 0) {
            resetColideFlags();
            colideToLeftWall = true;
        }

        if (colideToBreak) {
            if (colideToBreakAndMoveToRight) {
                xBall++;
            } else {
                xBall--;
            }
        }

        if (colideToRightWall) {
            xBall--;
        }

        if (colideToLeftWall) {
            xBall++;
        }


    }


    @Override
    public void onUpdate() {

        rect.setX(xBreak);
        rect.setY(yBreak);
        ball.setCenterX(xBall);
        ball.setCenterY(yBall);
    }

    @Override
    public void onInit() {

    }

    @Override
    public void onPhysicsUpdate() {
        setPhysicsToBall();
    }


}
