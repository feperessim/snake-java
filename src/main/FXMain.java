package main;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 *
 * @author felipe
 */
public class FXMain extends Application {
    
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("view/FXMLGameScreen.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Snake Game");
        scene.getStylesheets().add("css/mainCss.css");
        stage.setResizable(false);
        //stage.setIconified(true);
        stage.show();
       
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void handleSnakeDirection(KeyEvent event) {
        System.out.println("Oi");
    }
    
}
