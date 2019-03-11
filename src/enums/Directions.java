package enums;

public class Directions {
     public enum  Direction {
        UP(0), 
        DOWN(1),
        LEFT(2),
        RGHT(3);

        private final int direction;
        Direction(int direction) {
            this.direction = direction;
        }
    }
}
