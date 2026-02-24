package Screen;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import javafx.scene.control.Button;
import javafx.geometry.Pos;
import javafx.stage.Stage;

public class TitleScreen extends VBox {

    private ScreenManager begin;
    private final Text title;
    private final Button start;
    private final Button how_to_play;
    private final Button exit;


    public TitleScreen(ScreenManager begin) {

        this.begin = begin;


        this.title = new Text("No time to get badge");
        this.title.setFont(Font.font(70));

        this.start = new Button("   Start   ");
        this.start.setMinWidth(100);

        this.how_to_play = new Button("How to play");
        this.how_to_play.setMinWidth(100);

        this.exit = new Button("Exit");
        this.exit.setMinWidth(100);

        this.setSpacing(40);
        this.setAlignment(Pos.CENTER);


        this.start.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                begin.showChoose();

            }
        });

        this.exit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                begin.endGame();
            }
        });

        this.getChildren().addAll(title, start, how_to_play, exit);


    }
}