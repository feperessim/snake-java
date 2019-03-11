/*Refs: 
 * https://opengameart.org/content/snake-sprites-sound
 * https://www.devmedia.com.br/tipos-enum-no-java/25729
 * https://docs.oracle.com/javafx/2/canvas/jfxpub-canvas.htm#BCFCCEFE
 * https://docs.oracle.com/javase/8/javafx/api/javafx/scene/canvas/GraphicsContext.html#fillOval-double-double-double-double-
 */
package controller;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.MenuBar;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import model.Snake;
import model.SnakeBody;
import runnables.SnakeGame;


public class FXMLGameScreenController implements Initializable {
    @FXML
    private Canvas canvas;
    @FXML
    private Rectangle leftRectangle;
    @FXML
    private Rectangle rightRectangle;
    @FXML
    private Rectangle topRectangle;
    @FXML
    private Rectangle bottomRectangle;
    @FXML
    private Rectangle pointsRectangle;
    @FXML
    private MenuBar menuBar;
    @FXML
    private Text pointsText;
    
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
    
    private enum  Difficult {
        EASY(500), 
        MEDIUM(300),
        HARD(100);

        private final int dif;
        Difficult(int dif) {
            this.dif = dif;
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        difficult = Difficult.EASY.ordinal();
        setScreenItems(false);
    }
    
    @FXML
    public void handleMenuItemComecar() {
        //gc.setFill(Color.LAVENDER);
        //gc.fillOval(0, 0, 20, 20);
        //gc.setFill(Color.ANTIQUEWHITE);
        setScreenItems(true);
        pointsText.setText("Pontos: 0");
        SnakeGame snakeGame = new SnakeGame(canvas, difficult, pointsText);
        Thread game = new Thread(snakeGame);
        game.setDaemon(true);
        game.start();
        
    }
    
    private void setScreenItems(boolean value) {
        menuBar.setVisible(!value);
        leftRectangle.setVisible(value);
        rightRectangle.setVisible(value);
        topRectangle.setVisible(value);
        bottomRectangle.setVisible(value);
        pointsRectangle.setVisible(value);
        pointsText.setVisible(value);
    }
}
