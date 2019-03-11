package model;

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
        snakeBody.setNextBody(snakeTail);
        snakeTail = snakeBody;
        setSnakeSize(snakeSize + 1);
    }
    
    public void insertTop(SnakeBody snakeBody) {
        snakeHead.setNextBody(snakeBody);
        snakeHead = snakeBody;
        setSnakeSize(snakeSize + 1);
    }
    
    public void move() {
        SnakeBody tempSnakeBody = snakeTail;
        snakeTail = snakeTail.getNextBody();
        snakeHead.setNextBody(tempSnakeBody);
        tempSnakeBody = null;
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
