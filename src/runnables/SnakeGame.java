package runnables;

import enums.Directions;
import enums.Items;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import model.SnakeBody;

public class SnakeGame implements Runnable {
    private Text pointsText;
    private Canvas canvas;
    private GraphicsContext gc;
    private int difficult;
    private int[][] screen;
    private List<SnakeBody> snake;
    private SnakeBody snakeHead;
    private SnakeBody snakeTail;
    private int MAX_HEIGHT;
    private int MAX_WIDTH;
    private int SNAKE_BODY_HEIGHT;
    private int SNAKE_BODY_WIDTH;
    private int SCREEN_VERTICAL_BOUND;
    private int SCREEN_HORIZONTAL_BOUND;
    private int points;
    private final int QTD_POINTS;
    private boolean endGame;
    
    
    public SnakeGame(Canvas canvas, 
                     int difficult,
                     Text pointsText) {
        this.canvas = canvas;
        this.difficult = difficult;
        this.pointsText = pointsText;
        this.gc = canvas.getGraphicsContext2D();
        this.MAX_HEIGHT = (int) canvas.getHeight();
        this.MAX_WIDTH =  (int) canvas.getWidth();
        this.SNAKE_BODY_HEIGHT = 20;
        this.SNAKE_BODY_WIDTH = 20;
        this.SCREEN_VERTICAL_BOUND = MAX_HEIGHT / SNAKE_BODY_HEIGHT;
        this.SCREEN_HORIZONTAL_BOUND = MAX_WIDTH / SNAKE_BODY_WIDTH;
        this.screen = new int[SCREEN_VERTICAL_BOUND][SCREEN_HORIZONTAL_BOUND];
        this.snake = new ArrayList<>();
        this.points = 0;
        this.QTD_POINTS = 5;
        
        for (int[] row: screen) {
            Arrays.fill(row, Items.Item.EMPTY.ordinal());
        }
        
        snakeHead = new SnakeBody(SCREEN_HORIZONTAL_BOUND/2,
                                  SCREEN_VERTICAL_BOUND/2,
                                  this.SNAKE_BODY_WIDTH, 
                                  this.SNAKE_BODY_HEIGHT);
        
        snakeTail = new SnakeBody(SCREEN_HORIZONTAL_BOUND/2 - 1,
                                  SCREEN_VERTICAL_BOUND/2,
                                  this.SNAKE_BODY_WIDTH, 
                                  this.SNAKE_BODY_HEIGHT);
        
         snakeHead.setDirection(Directions.Direction.RIGHT.ordinal());
         snakeTail.setDirection(Directions.Direction.RIGHT.ordinal());
        
        snake.add(snakeHead);
        snake.add(snakeTail);     
        
        Platform.runLater(() -> {gc.setFill(Color.LAVENDER);});
        boolean endGame = false;
        Scene scene = canvas.getScene();
        scene.setOnKeyPressed(event -> {
            handleSnakeDirection(event);
            
        });
    }
    
    @Override
    public void run() {
        MusicPlayer musicPlayerGame = new MusicPlayer("src/musics_and_sounds/Pat_Metheny_Always_and_Forever.mp3", true);
        Thread musicGame = new Thread(musicPlayerGame);
        musicGame.setDaemon(true);
        musicGame.start();
        
        int counter = 0;
        while (!endGame) {
            try {
                Thread.sleep(difficult);
            } catch (InterruptedException ex) {
                
            }
            clear();
            move();
            if (!endGame) {
                eat();
                if (counter % 30 == 0) {
                    genFoodAtScreen();
                    counter = 0;
                }
                counter++;
            }
            draw();
        }
        pointsText.setText("Game over");
        MusicPlayer musicPlayer = new MusicPlayer("src/musics_and_sounds/DieSound_CC0_by_EugeneLoza.mp3");
        Thread music = new Thread(musicPlayer);
        music.setDaemon(true);
        music.start();
        musicPlayerGame.stopMusic();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            
        }
    }
    
    private void move() {
        SnakeBody s = new SnakeBody(
                snakeHead.getX(), 
                snakeHead.getY(),
                snakeHead.getWidth(), 
                snakeHead.getHeight());
        s.setDirection(snakeHead.getDirection());
        
        if (snakeHead.getDirection() == Directions.Direction.UP.ordinal()) {
            int y = snakeHead.getY() - 1;
            if (y >= 0) {
                s.setY(y);
            }
            else {
                endGame = true;
            }
        }
        else if (snakeHead.getDirection() == Directions.Direction.DOWN.ordinal()) {
            int y = snakeHead.getY() + 1;
            if (y < SCREEN_VERTICAL_BOUND) {
                s.setY(y);
            }
            else {
                endGame = true;
            }
        }
        else if (snakeHead.getDirection() == Directions.Direction.LEFT.ordinal()) {
            int x = snakeHead.getX() - 1;
            if (x >= 0) {
                s.setX(x);
            }
            else {
                endGame = true;
            }
        }
       else {
            int x = snakeHead.getX() + 1;
            if (x < SCREEN_HORIZONTAL_BOUND) {
                s.setX(x);
            }
            else {
                endGame = true;
            }
        }
        if (!endGame) {
            snakeTail = snake.get(snake.size()-1);
            snake.remove(snakeTail);
            snake.add(0, s);
            snakeHead = s;
        }
    }
    
    
    
    public void draw() {
        for (SnakeBody s : snake) {
            Platform.runLater(() -> {
            gc.fillOval(
                    s.getX() * s.getWidth() ,
                    s.getY() * s.getHeight(),
                    s.getWidth(),
                    s.getHeight());
        });
        }
        
        for (int y = 0; y < SCREEN_VERTICAL_BOUND; y++) {
            for (int x = 0; x < SCREEN_HORIZONTAL_BOUND; x++) {            
                if (this.screen[y][x] == Items.Item.FOOD.ordinal()) {
                    final int a = x;
                    final int b = y;
                    Platform.runLater(() -> {    
                        gc.setFill(Color.SKYBLUE);
                        gc.fillRoundRect(a*SNAKE_BODY_WIDTH, b*SNAKE_BODY_HEIGHT, SNAKE_BODY_WIDTH-10, SNAKE_BODY_HEIGHT, 10, 10);
                    });
                }
            }
        }
        Platform.runLater(() -> { 
                gc.setFill(Color.DARKGRAY);
                gc.fillOval(snakeHead.getX()*SNAKE_BODY_WIDTH, snakeHead.getY()*SNAKE_BODY_HEIGHT, SNAKE_BODY_WIDTH, SNAKE_BODY_HEIGHT);
        });
        
        Platform.runLater(() -> {gc.setFill(Color.LAVENDER);});
    }
    
    public void clear() {
        Platform.runLater(() -> { 
            gc.clearRect(0, 0, MAX_WIDTH, MAX_HEIGHT);
        }); 
    }

    
    private void eat() {
        int x, y;
        
        if (snakeHead.getDirection() == Directions.Direction.UP.ordinal()) {
            x = 0;
            y = -1;
        }
        else if (snakeHead.getDirection() == Directions.Direction.DOWN.ordinal()) {
            x = 0;
            y = 1;
        }
        else if (snakeHead.getDirection() == Directions.Direction.LEFT.ordinal()) {
            x = -1;
            y = 0;
        }
        else {
            x = 1;
            y = 0;
        }
        
        if (inBounds() && this.screen[snakeHead.getY()][snakeHead.getX()] == Items.Item.FOOD.ordinal()) {
            points += QTD_POINTS;
            pointsText.setText("Pontos: " + points);
            this.screen[snakeHead.getY()][snakeHead.getX()] = Items.Item.SNAKE_BODY.ordinal();
            SnakeBody snakeBody = new SnakeBody(
                    snakeHead.getX() + x , 
                    snakeHead.getY() + y, 
                    SNAKE_BODY_WIDTH, 
                    SNAKE_BODY_HEIGHT);
            snakeBody.setDirection(snakeHead.getDirection());
            snake.add(0,snakeBody);
            snakeHead = snakeBody;
            if (inBounds()) {
                this.screen[snakeHead.getY()][snakeHead.getX()] = Items.Item.SNAKE_BODY.ordinal();
                draw();
                MusicPlayer musicPlayer = new MusicPlayer("src/musics_and_sounds/EatSound_CC0_by_EugeneLoza.mp3");
                Thread music = new Thread(musicPlayer);
                music.setDaemon(true);
                music.start();
            }
        }
    }
    
    private void handleSnakeDirection(KeyEvent event) {
        switch (event.getCode()) {
            case UP:   
                if (snakeHead.getDirection() != Directions.Direction.DOWN.ordinal()) {
                    snakeHead.setDirection(Directions.Direction.UP.ordinal());
                }
                break;
            case DOWN:  
                if (snakeHead.getDirection() != Directions.Direction.UP.ordinal()) {
                    snakeHead.setDirection(Directions.Direction.DOWN.ordinal());
                }
                break;
            case LEFT: 
                if (snakeHead.getDirection() != Directions.Direction.RIGHT.ordinal()) {
                    snakeHead.setDirection(Directions.Direction.LEFT.ordinal());
                }
                break;
                
            case RIGHT:
                if (snakeHead.getDirection() != Directions.Direction.LEFT.ordinal()) {
                    snakeHead.setDirection(Directions.Direction.RIGHT.ordinal());
                }
                break;
        }
    }
    
    private void genFoodAtScreen() {
        Random rand = new Random();
        int y = rand.nextInt(SCREEN_VERTICAL_BOUND);
        int x = rand.nextInt(SCREEN_HORIZONTAL_BOUND);
        
        this.screen[y][x] = Items.Item.FOOD.ordinal();
        Platform.runLater(() -> {
            gc.setFill(Color.SKYBLUE);
            gc.fillRoundRect(x*SNAKE_BODY_WIDTH, y*SNAKE_BODY_HEIGHT, SNAKE_BODY_WIDTH-10, SNAKE_BODY_HEIGHT, 10, 10);
        });
       Platform.runLater(() -> {gc.setFill(Color.LAVENDER);});
        
    }
    
    private boolean inBounds() {
        int x = snakeHead.getX();
        int y = snakeHead.getY();
        
        return x >= 0 && x < SCREEN_HORIZONTAL_BOUND 
            && y >= 0 && y < SCREEN_VERTICAL_BOUND; 
    }
    
}
