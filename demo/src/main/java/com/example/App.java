package com.example;   
import java.security.Key;
import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Application;  
import javafx.event.ActionEvent;  
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.PointLight;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.effect.Light.Point;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Translate;  

import javafx.geometry.Point2D;

public class App extends Application{  
    private static final int WIDTH = 600;
    private static final int HEIGHT = 600;
    private static final int ROWS = 15;
    private static final int COLUMNS = 15;
    private static final int SQUARE_SIZE = WIDTH / ROWS;
    private static final int RIGHT = 1;
    private static final int LEFT = 2;
    private static final int UP = 3;
    private static final int DOWN = 4;

    private GraphicsContext gc;
    public List<Point2D> snakeBody = new ArrayList();
    private Point2D snakeHead;
    private int foodX;
    private int foodY;
    private boolean gameOver;
    private int currentDirection = RIGHT;

    @Override  
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("First JavaFX Snake Game");
        Group root = new Group();
        Canvas canvas = new Canvas(WIDTH,HEIGHT);
        root.getChildren().add(canvas);
        Scene scene=new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
        gc = canvas.getGraphicsContext2D();

        for(int i = 0 ; i<3 ; i++){
            snakeBody.add(new Point2D(4 * SQUARE_SIZE, 7 * SQUARE_SIZE));
        }
        snakeHead = snakeBody.get(0);
        generateFood();

        scene.setOnKeyPressed(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event) {
                KeyCode code = event.getCode();
                if(code == KeyCode.RIGHT || code == KeyCode.D){
                    if(currentDirection != LEFT){
                        currentDirection = RIGHT;
                    }
                }
                else if(code == KeyCode.LEFT || code == KeyCode.A){
                    if(currentDirection != RIGHT){
                        currentDirection = LEFT;
                    }
                }
                else if(code == KeyCode.UP || code == KeyCode.W){
                    if(currentDirection != DOWN){
                        currentDirection = UP;
                    }
                }
                else if(code == KeyCode.DOWN || code == KeyCode.S){
                    if(currentDirection != UP){
                        currentDirection = DOWN;
                    }
                }
            }
        });

        
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(130), e -> run(gc)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void run(GraphicsContext gc) {
        drawBackGround(gc);
        drawFood(gc);
        drawSnake(gc);

        Point2D move;
        switch(currentDirection){
            case RIGHT :
            moveRight();
            break;
            case LEFT : 
            moveLeft();
            break;
            case UP : 
            moveUp();
            break;
            case DOWN : 
            moveDown();
            break;
        }
        for (int i = 1; i < snakeBody.size(); i++) {
            move = new Point2D(snakeBody.get(i).getX(), snakeBody.get(i).getY());
            snakeBody.set(i-1,move);
        }
    }

    private void drawBackGround(GraphicsContext gc) {
        for(int i = 0 ; i < ROWS ; i++){
            for(int j = 0 ; j < COLUMNS ; j++){
                if((i+j) % 2 == 0){
                    gc.setFill(Color.BEIGE);
                }
                else{
                    gc.setFill(Color.WHITE);
                }
                gc.fillRect(i * SQUARE_SIZE, j * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
            }
        }
    }
    private void generateFood(){
        foodX =(int)( Math.random()*COLUMNS);
        foodY = (int)( Math.random()*ROWS);
    }
    private void drawFood(GraphicsContext gc){
        gc.setFill(Color.RED);
        gc.fillRect(foodX * SQUARE_SIZE, foodY * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
    }

    private void drawSnake(GraphicsContext gc){
        gc.setFill(Color.GRAY);
        gc.fillRect(snakeHead.getX(), snakeHead.getY() , SQUARE_SIZE, SQUARE_SIZE);
        for (int i = 0; i < snakeBody.size() - 1 ; i++){
            gc.fillRect(snakeBody.get(i).getX(), snakeBody.get(i).getY() , SQUARE_SIZE, SQUARE_SIZE);
        }
    }

    private void moveRight(){
        Point2D nove = new Point2D(snakeHead.getX()+SQUARE_SIZE, snakeHead.getY());
        snakeHead = nove;
        snakeBody.set(0, snakeHead);
    }
    private void moveLeft(){
        snakeHead = new Point2D(snakeHead.getX()-SQUARE_SIZE, snakeHead.getY());
        snakeBody.set(0, snakeHead);
    }
    private void moveUp(){
        snakeHead = new Point2D(snakeHead.getX(), snakeHead.getY()-SQUARE_SIZE);
        snakeBody.set(0, snakeHead);
    }
    private void moveDown(){
        snakeHead = new Point2D(snakeHead.getX(), snakeHead.getY()+SQUARE_SIZE);
        snakeBody.set(0, snakeHead);
    }

    private void gameOver() {

    }

    public static void main (String[] args)  
    {  
        launch(args);  
    }  
  
}  