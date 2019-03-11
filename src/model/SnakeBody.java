package model;

public class SnakeBody {
    private SnakeBody next;
    private double x;
    private double y;
    private double width;
    private double height;
    private int direction;
    
    public SnakeBody (double x, 
                  double y, 
                  double widht, 
                  double height) {
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

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }
}
