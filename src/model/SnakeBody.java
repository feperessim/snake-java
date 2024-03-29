package model;

public class SnakeBody {
    private SnakeBody next;
    private int x;
    private int y;
    private int width;
    private int height;
    private int direction;

    public SnakeBody (
                  int x, 
                  int y, 
                  int widht, 
                  int height) {
        this.x = x;
        this.y = y;
        this.width = widht;
        this.height = height;
    }
    
    public void setNextBody(SnakeBody body) {
        next = body;
    }
    
    public SnakeBody getNextBody() {
        return next;
    }
    
    public void setDirection(int direction) {
        this.direction = direction;
    }
    
    public int getDirection() {
        return this.direction;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setX(int x) {
        this.x = x;
    }
    
    public void setY(int y) {
        this.y = y;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
