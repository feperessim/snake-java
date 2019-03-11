package enums;

public class Items {
    public enum  Item {
        EMPTY(0), 
        FOOD(1),
        SNAKE_BODY(2);

        private final int item;
        Item(int item) {
            this.item = item;
        }
    }
}
