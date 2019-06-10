package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
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
    @FXML 
    private BorderPane borderPane;
    
    private GraphicsContext gc;
    private int difficult;
    private int[][] screen;
    private SnakeBody snakeHead;
    private SnakeBody snakeTail;
    private int MAX_HEIGHT;
    private int MAX_WIDTH;
    private int SNAKE_BODY_HEIGHT;
    private int SNAKE_BODY_WIDTH;
    private int SCREEN_VERTICAL_BOUND;
    private int SCREEN_HORIZONTAL_BOUND;
    
    private enum  Difficult {
        EASY(300), 
        MEDIUM(100),
        HARD(50);

        private final int dif;
        Difficult(int dif) {
            this.dif = dif;
        }
        
        public int getDifficult() {
            return this.dif;
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        difficult = Difficult.EASY.getDifficult();
        setScreenItems(false);
        borderPane.getScene();
    }
    
    @FXML
    public void handleMenuItemComecar() {
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
    
    @FXML
    public void handleMenuButtonFacil() {
        this.difficult = Difficult.EASY.getDifficult();
    }
    
    @FXML
    public void handleMenuButtonMediano() {
        this.difficult = Difficult.MEDIUM.getDifficult();
    }
    
    @FXML
    public void handleMenuButtonDificil() {
        this.difficult = Difficult.HARD.getDifficult();
    }
}
