package runnables;

import com.sun.javafx.scene.traversal.Direction;
import enums.Items.Item;
import java.util.Arrays;
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
        snakeHead = new SnakeBody(this.MAX_HEIGHT - this.SNAKE_BODY_HEIGHT/2 ,
                                  this.MAX_WIDTH - this.SNAKE_BODY_WIDTH/2,
                                  this.SNAKE_BODY_WIDTH, 
                                  this.SNAKE_BODY_HEIGHT);
        snakeTail = snakeHead;
        snakeHead.setDirection(Direction.RIGHT.ordinal());
        snake = new Snake(snakeTail, snakeHead);
        canvas.setOnKeyPressed(event -> {
            if(event != null){
                handleSnakeDirection(event);
            }
        }); 
    }

    @Override
    public void run() {
        
        while (true) {
            try {
                Thread.sleep(difficult);
                draw();
                snake.move();
                endOfGame();
            } catch (InterruptedException ex) {
                
            }
            
        }
    }
    
    private void handleSnakeDirection(KeyEvent event) {
        System.out.println("Apertou uma tecla : )");
        switch (event.getCode()) {
            case UP:    snakeHead.setDirection(Direction.UP.ordinal()); break;
            case DOWN:  snakeHead.setDirection(Direction.DOWN.ordinal()); break;
            case LEFT:  snakeHead.setDirection(Direction.LEFT.ordinal());
            case RIGHT: snakeHead.setDirection(Direction.UP.ordinal());
            case SHIFT: snakeHead.setDirection(Direction.UP.ordinal()); break;
        }
    }    

    private void draw() {
        gc.setFill(Color.LAVENDER);
        gc.fillOval(0, 0, 20, 20);
        gc.setFill(Color.ANTIQUEWHITE);
    }

    private void endOfGame() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
   
}
