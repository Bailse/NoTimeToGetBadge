package Screen.Choosing;

import Screen.ScreenManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;


public class ChoosingScreen extends VBox {



    public ChoosingScreen(ScreenManager manager) {

        this.setPrefSize(1200, 1200);
        this.setSpacing(20);
        this.setPadding(new Insets(20));

        // menu top bar //

        Button goBack = new Button("Go Back");
        Button start = new Button("Start Game");

        goBack.setOnAction(e -> manager.showTitle());
        start.setOnAction(e -> manager.showGame());

        HBox controlBar = new HBox(20, goBack, start);
        controlBar.setAlignment(Pos.CENTER);

        //player choose//

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(30);
        grid.setVgap(30);

        ColumnConstraints col = new ColumnConstraints();
        col.setPercentWidth(50);

        RowConstraints row = new RowConstraints();
        row.setPercentHeight(50);

        grid.getColumnConstraints().addAll(col, col);
        grid.getRowConstraints().addAll(row, row);

        init player1 = new init("nerd boy", "/deku_nerd.jpg", "Play");
        // set player //
        player1.addAll();

        init player2 = new init("young man", "/hunter_song.jpeg", "Play");
        // set player //
        player2.addAll();

        init player3 = new init("young man", "/Huh.jpg", "Play");
        // set player //
        player3.addAll();

        init player4 = new init("young man", "/Lily.jpg", "Play");
        // set player //

        player4.addAll();

        grid.add(player1, 0, 0);
        grid.add(player2, 1, 0);
        grid.add(player3, 0, 1);
        grid.add(player4, 1, 1);



        this.getChildren().addAll(controlBar, grid);

        VBox.setVgrow(grid, Priority.ALWAYS);
    }


    }







