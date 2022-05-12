package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("sample.fxml")));
        primaryStage.setTitle("Snakes and Ladders");
        primaryStage.setScene(new Scene(root, 762, 830, Color.BLUE));
        primaryStage.show();
        //primaryStage.setFullScreen(true);
    }


    public static void main(String[] args) {
        launch(args);
    }
}

