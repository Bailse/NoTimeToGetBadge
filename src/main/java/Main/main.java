package Main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.*;

public class application extends Application{

    @Override
    public void start(Stage stage) throws Exception {

        Button startButton = new Button("Start");
        HBox order = new HBox();

        order.getChildren().addAll(startButton);

        Scene scene1 = new Scene(order, 300 , 250);
        stage.setTitle("No time to get badge");
        stage.setScene(scene1);
        stage.show();

    }

    public static void main(String[] args){
        launch(args);
    }










}
