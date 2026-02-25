package Main;

import Screen.ScreenManager;
import Screen.TitleScreen;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javafx.scene.control.Button;



public class main extends Application{

    @Override
    public void start(Stage TitleStage) throws Exception {

        Scene scene = new Scene(new VBox(), 1200, 1200);
        TitleStage.setScene(scene);

        ScreenManager manager = new ScreenManager(TitleStage);
        manager.showTitle();
        TitleStage.setWidth(1200);
        TitleStage.setHeight(800);

        TitleStage.setTitle("No time to get badge");
        TitleStage.setResizable(false);
        TitleStage.show();

    }














    public static void main(String[] args){
        launch(args);
    }










}
