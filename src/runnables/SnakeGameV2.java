package runnables;

import enums.Directions;
import enums.Items;
import java.util.Arrays;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import model.SnakeBody;

public class SnakeGameV2 implements Runnable {
    private Text pointsText;
    private Canvas canvas;
    private GraphicsContext gc;
    private int difficult;
    private int[][] screen;
    private SnakeBody[][] snake;
    private SnakeBody snakeHead;
    private SnakeBody snakeTail;
    
    private int MAX_HEIGHT;
    private int MAX_WIDTH;
    private int SNAKE_BODY_HEIGHT;
    private int SNAKE_BODY_WIDTH;
    private int SCREEN_VERTICAL_BOUND;
    private int SCREEN_HORIZONTAL_BOUND;
    private boolean endGame;
    
    
    public SnakeGameV2(Canvas canvas, 
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
        this.snake = new SnakeBody[SCREEN_VERTICAL_BOUND][SCREEN_HORIZONTAL_BOUND];
        
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
        
        snake[SCREEN_VERTICAL_BOUND/2]
             [SCREEN_HORIZONTAL_BOUND/2] = snakeHead;
        snake[SCREEN_VERTICAL_BOUND/2]
             [SCREEN_HORIZONTAL_BOUND/2 - 1] = snakeTail;
        
       // System.out.println("canvas.getHeight();" + canvas.getHeight()/20);
        //System.out.println("canvas.getWidth();" + canvas.getWidth()/20);
        
        Platform.runLater(() -> {gc.setFill(Color.LAVENDER);});
        boolean endGame = false;
    }
    
    @Override
    public void run() {
        draw(snakeTail);
        draw(snakeHead);
       
        while (!endGame) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                
            }
            endGame = move();
            eat(); 
        }
    }
    
    private boolean move() {
        SnakeBody toRunLater = new SnakeBody(snakeTail.getX()-1, 
                                             snakeTail.getY(),
                                             snakeTail.getWidth(), 
                                             snakeTail.getHeight());
             
        if (snakeHead.getDirection() == Directions.Direction.UP.ordinal()) {
            snakeHead.setY(snakeHead.getY() - snakeHead.getHeight());
        }
        else if (snakeHead.getDirection() == Directions.Direction.DOWN.ordinal()) {
            snakeHead.setY(snakeHead.getY() + snakeHead.getHeight());
        }
        else if (snakeHead.getDirection() == Directions.Direction.LEFT.ordinal()) {
            snakeHead.setX(snakeHead.getX() - 1);
        }
        else {
            int x = snakeHead.getX() + 1;
            if (x < SCREEN_HORIZONTAL_BOUND) {
                clear(toRunLater);
                SnakeBody next = getAheadOfTail();
                //snake[snakeTail.getY()][snakeTail.getX()] = null;
                snakeTail.setX(x);
                snakeTail.setDirection(snakeHead.getDirection());
                snake[snakeTail.getY()][snakeTail.getX()] = snakeTail;
                snakeHead = snake[snakeTail.getY()][snakeTail.getX()];
                snakeTail = next;
                draw(snakeHead);
               
            }
            else {
                endGame = true;
                return endGame;
            }
        }
        return false;
    }
    
    public void draw(SnakeBody snakeBody) {
        int x, y;
        

        if (snakeBody == snakeTail) {
            x = -1;
        }
        else {
            x = 0;
        }
                
        Platform.runLater(() -> {
            gc.fillOval(
                    snakeBody.getX() * snakeBody.getWidth() + x,
                    snakeBody.getY() * snakeBody.getHeight(),
                    snakeBody.getWidth(),
                    snakeBody.getHeight());
        });
    }
    
    public void clear(SnakeBody toRunLater) {
        Platform.runLater(() -> { 
            gc.clearRect(toRunLater.getX() * toRunLater.getWidth(),
                    toRunLater.getY() * toRunLater.getHeight(),
                    toRunLater.getWidth(),
                    toRunLater.getHeight());
        }); 
    }

    private SnakeBody getAheadOfTail(){
        SnakeBody next;
        /*
        if (snakeTail.getDirection() == Directions.Direction.UP.ordinal()) {
           next = snake[snakeTail.getY() -1][snakeTail.getX()];
        }
        else if (snakeTail.getDirection() == Directions.Direction.DOWN.ordinal()) {
            next = snake[snakeTail.getY() +1][snakeTail.getX()];
        }
        else if (snakeTail.getDirection() == Directions.Direction.LEFT.ordinal()) {
            next = snake[snakeTail.getY()][snakeTail.getX() - 1];
        }
        else {
            System.out.println(snake[snakeTail.getY()][snakeTail.getX()] == null);
            next = snake[snakeTail.getY()][snakeTail.getX()];
        }*/
        next = snake[snakeTail.getY()][snakeTail.getX()];
        
        return next;
    }    

    private void eat() {
        SnakeBody snakeBody = new SnakeBody(snakeTail.getX() , 
                                            snakeTail.getY(), 
                                            SNAKE_BODY_WIDTH, 
                                            SNAKE_BODY_HEIGHT);
        
        snakeBody.setDirection(snakeTail.getDirection());
        snake[snakeBody.getY()][snakeBody.getX()] = snakeBody;
        snakeTail = snake[snakeBody.getY()][snakeBody.getX()];
        draw(snakeTail);;
    }
}
