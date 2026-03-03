package Screen.HowToPlay;

import Screen.ScreenManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.Objects;
/**
 * How to play Screen
 */
public class HowToPlayScreen extends BorderPane {

    /**
     * constructor initialize object in HowtoPlayScreen
     */

    public HowToPlayScreen(ScreenManager manager){

        this.setPrefSize(1200, 800);

        // ================= BACKGROUND =================
        try {
            Image bgImage = new Image(
                    Objects.requireNonNull(
                            getClass().getResource("/Screen/h2p.png")
                    ).toExternalForm()
            );

            BackgroundImage bgView = new BackgroundImage(
                    bgImage,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.CENTER,
                    new BackgroundSize(100, 100, true, true, true, true)
            );

            this.setBackground(new Background(bgView));
        } catch (Exception e) {
            this.setStyle("-fx-background-color: #0a0a0a;");
        }

        // ================= HEADER =================
        Label title = new Label("HOW TO PLAY");

        title.setFont(Font.font("Garamond", FontWeight.BOLD, 60));
        title.setTextFill(Color.WHITE);
        title.setStyle("-fx-effect: dropshadow(gaussian, black, 20, 0.6, 0, 0);");

        VBox header = new VBox(title);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(50));

        // ================= CONTENT =================
        VBox contentBox = new VBox(35);
        contentBox.setPadding(new Insets(50));
        contentBox.setMaxWidth(900);

        contentBox.setStyle("""
            -fx-background-color: rgba(0,0,0,0.8);
            -fx-border-color: #000000;
            -fx-border-width: 2;
            -fx-background-radius: 20;
            -fx-border-radius: 20;
        """);

        contentBox.getChildren().addAll(
                createSection("01. GAME OBJECTIVE",
                        "Compete to build the best life.\n\n" +
                                "Increase your Education, Earn Money,\n" +
                                "Maintain your Health and Stamina.\n\n" ,
                        "#ff00ff"),

                createSection("02. CORE STATS",
                        "• MONEY – Used to buy items to reduce your stamina or activity\n" +
                                "• HEALTH – If it reaches 0, you may stress\n" +
                                "• EDUCATION – study for gain education\n" +
                                "• STAMINA – Required to perform actions",
                        "#00ff00"),

                createSection("03. BUILDINGS & ACTIONS",
                        "• gym – Earn money but lose stamina and do some workout\n" +
                                "• University – Increase education\n" +
                                "• Park – Increase do little workout or increase your status for free\n" +
                                "• Travel – Increase your happiness but you need to pay some money\n" +
                                "• Mall – Buy some item to help you to complete this game easy\n" +
                                "• Restaurant – Buy some buy some food to get stamina and health but you need to pay\n" +
                                "• Dome – Recover some health and happiness",

                        "#ffff00"),

                createSection("04. ITEMS",
                        "You can buy special items:\n" +
                                "• Vehicle – Move faster and spent less stamina\n" +
                                "• Education Item – Boost knowledge gain\n" +
                                "• Health Item – Increase health bonus \n"
                                ,
                        "#00ffff"),

                createSection("05. TURN SYSTEM",
                        "Each turn represents time passing.\n" +
                                "Choose wisely what action to take.\n" +
                                "Poor decisions may cost you the game.",
                        "#ff4444"),

                createSection("06. WIN CONDITION",
                        "Reach the target status to goal \n" +
                                "Manage your life better than others!",
                        "#ffffff")
        );

        ScrollPane scrollPane = new ScrollPane(contentBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setPannable(true);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");

        VBox center = new VBox(scrollPane);
        center.setAlignment(Pos.CENTER);
        center.setPadding(new Insets(0, 120, 0, 120));

        this.setTop(header);
        this.setCenter(center);

        // ================= BACK BUTTON =================
        Button back = new Button("< RETURN");
        back.setFont(Font.font("Garamond", FontWeight.BOLD, 22));
        back.setStyle("""
            -fx-background-color: white;
            -fx-text-fill: black;
            -fx-padding: 12 40 12 40;
            -fx-background-radius: 8;
            -fx-cursor: hand;
        """);


        back.setOnAction(e -> manager.showTitle());

        HBox bottom = new HBox(back);
        bottom.setAlignment(Pos.CENTER_LEFT);
        bottom.setPadding(new Insets(30));

        this.setBottom(bottom);
    }

    /**
     * Create Box of Section
     */

    private VBox createSection(String title, String text, String colorHex) {

        Label lblTitle = new Label(title);
        lblTitle.setFont(Font.font("Courier New", FontWeight.BOLD, 26));
        lblTitle.setTextFill(Color.web(colorHex));

        Label lblText = new Label(text);
        lblText.setFont(Font.font("Courier New", 18));
        lblText.setTextFill(Color.WHITE);
        lblText.setWrapText(true);
        lblText.setLineSpacing(6);

        return new VBox(12, lblTitle, lblText);
    }
}