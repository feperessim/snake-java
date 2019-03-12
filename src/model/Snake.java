package model;

import enums.Directions.Direction;

public class Snake {
    private SnakeBody snakeHead;
    private SnakeBody snakeTail;
    private int snakeSize;
    
    public Snake(SnakeBody tail, 
                 SnakeBody head) {
        this.snakeTail = tail;
        this.snakeHead = head;
    }
    
    public void insertBottom(SnakeBody snakeBody) {
        snakeTail.setNextBody(snakeBody);
        snakeTail = snakeBody;
       // snakeBody.setNextBody(null);
        setSnakeSize(snakeSize + 1);
    }
    
    public void insertTop(SnakeBody snakeBody) {
        snakeBody.setNextBody(snakeHead);
        snakeHead = snakeBody;
        //setSnakeSize(snakeSize + 1);
    }
    
    public int size() {
        int i = 0;
        SnakeBody head = snakeHead;
        
        while (head.getNextBody() != null) {
            head = head.getNextBody();
            i++;
        }
        return i;
    }
    
    public void removeLast() {
        SnakeBody snakeBodyCurrent = snakeHead;;
        SnakeBody snakeBodyPrevious = null;
        
        while (snakeBodyCurrent != null) {
            snakeBodyPrevious = snakeBodyCurrent;
            snakeBodyCurrent = snakeBodyCurrent.getNextBody();
            System.out.println("Preso");
        }
        snakeTail = snakeBodyPrevious;
        //snakeBodyCurrent.setNextBody(null);
        //snakeBodyCurrent = null;
    }
    
    public void move() {
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
            snakeHead.setX(snakeHead.getX() + snakeHead.getWidth());
        }
    }
    
    public SnakeBody getSnakeHead() {
        return this.snakeHead;
    }
    
    public SnakeBody getSnakeTail() {
        return this.snakeTail;
    }
    
    public void setSnakeSize(int size) {
        this.snakeSize = size;
    }
}
