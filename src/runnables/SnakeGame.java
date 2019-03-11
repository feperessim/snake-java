package runnables;

import enums.Directions.Direction;
import enums.Items.Item;
import java.util.Arrays;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import model.Snake;
import model.SnakeBody;

public class SnakeGame implements Runnable{
    private Text pointsText;
    private Canvas canvas;
    private GraphicsContext gc;
    private int difficult;
    private int[][] screen;
    private Snake snake;
    private SnakeBody snakeHead;
    private SnakeBody snakeTail;
    private int MAX_HEIGHT;
    private int MAX_WIDTH;
    private int SNAKE_BODY_HEIGHT;
    private int SNAKE_BODY_WIDTH;
    private int SCREEN_VERTICAL_BOUND;
    private int SCREEN_HORIZONTAL_BOUND;
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
        
        for (int[] row: screen) {
            Arrays.fill(row, Item.EMPTY.ordinal());
        }
        
        snakeHead = new SnakeBody(this.MAX_WIDTH/2 - this.SNAKE_BODY_WIDTH ,
                                  this.MAX_HEIGHT/2 - this.SNAKE_BODY_HEIGHT,
                                  this.SNAKE_BODY_WIDTH, 
                                  this.SNAKE_BODY_HEIGHT);
        
        snakeTail = new SnakeBody(snakeHead.getX() - this.SNAKE_BODY_WIDTH ,
                                  snakeHead.getY(),
                                  this.SNAKE_BODY_WIDTH, 
                                  this.SNAKE_BODY_HEIGHT);
       
        snakeHead.setNextBody(snakeTail);
        snakeTail.setNextBody(null);
        snakeHead.setDirection(Direction.RIGHT.ordinal());
        snake = new Snake(snakeTail, snakeHead);  
        screen[snakeHead.getY()/20][snakeHead.getX()/20] = Item.SNAKE_BODY.ordinal();
        
        canvas.setOnKeyPressed(event -> {
            if(event != null){
                handleSnakeDirection(event);
            }
        }); 
    }

    @Override
    public void run() {
        Platform.runLater(() -> {gc.setFill(Color.LAVENDER);});
        Platform.runLater(() -> {gc.fillOval(snakeTail.getX()-1,
                snakeTail.getY(), 
                snakeTail.getWidth(), 
                snakeTail.getHeight()); 
         gc.fillOval(snakeHead.getX(),
                    snakeHead.getY(),
                    snakeHead.getWidth(), 
                    snakeHead.getHeight());
 
        
        });
         
        //System.out.println((double)snakeHead.getX());
        //System.out.println((double)snakeHead.getY());
        
        while (true) {
            try {
                Thread.sleep(difficult);
                
               move();
               snake.insertBottom(new SnakeBody(snakeTail.getX() - 20, 
                                                snakeTail.getY(), 
                                                snakeTail.getWidth(),
                                                snakeTail.getHeight()));
      
                /*if (!endOfGame()) {
                    eat();
                }*/
                
            } catch (InterruptedException ex) {
                
            }
            
        }
    }
    
    public void move() {
   
        SnakeBody toRunLater = new SnakeBody(snakeTail.getX(), 
                                             snakeTail.getY(),
                                             snakeTail.getWidth(), 
                                             snakeTail.getHeight());
        Platform.runLater(() -> { 
            gc.clearRect(toRunLater.getX(),
                    toRunLater.getY(), 
                    toRunLater.getWidth(), 
                    toRunLater.getHeight());
        });
        
        SnakeBody temp = snakeHead;
        snakeTail.setNextBody(snakeHead);
        snakeHead.setNextBody(null);
        snakeHead = snakeTail        ;
        snakeTail = temp;
        snakeHead.setDirection(snakeTail.getDirection());
        snakeHead.setX(snakeTail.getX());
        snakeHead.setY(snakeTail.getY());
        
        //System.out.println("Tail: " + snakeTail.getX());
        //System.out.println("Head: " + snakeHead.getX());
        
        if (snakeHead.getDirection() == Direction.UP.ordinal()) {
            snakeHead.setX(snakeHead.getY() - snakeHead.getHeight());
        }
        else if (snakeHead.getDirection() == Direction.DOWN.ordinal()) {
            snakeHead.setX(snakeHead.getY() + snakeHead.getHeight());
        }
        else if (snakeHead.getDirection() == Direction.LEFT.ordinal()) {
            snakeHead.setX(snakeHead.getX() + snakeHead.getWidth());
        }
        else {
            snakeHead.setX(snakeHead.getX() + snakeHead.getWidth() + 1);
        }
        Platform.runLater(() -> {
            gc.fillOval(snakeHead.getX(),
                    snakeHead.getY(), 
                    snakeHead.getWidth(), 
                    snakeHead.getHeight());
        });
    }
    
    private void handleSnakeDirection(KeyEvent event) {
        System.out.println("Apertou uma tecla : )");
        switch (event.getCode()) {
            case UP:    snakeHead.setDirection(Direction.UP.ordinal()); break;
            case DOWN:  snakeHead.setDirection(Direction.DOWN.ordinal()); break;
            case LEFT:  snakeHead.setDirection(Direction.LEFT.ordinal()); break;
            case RIGHT: snakeHead.setDirection(Direction.UP.ordinal()); break;
            case SHIFT: snakeHead.setDirection(Direction.UP.ordinal()); break;
        }
    }    

    private void draw() {
        gc.setFill(Color.LAVENDER);
        gc.fillOval(snakeHead.getX(),
                    snakeHead.getY(), 
                    snakeHead.getWidth(), 
                    snakeHead.getHeight());
        
       
    }

    private boolean endOfGame() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void eat() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
   
}
