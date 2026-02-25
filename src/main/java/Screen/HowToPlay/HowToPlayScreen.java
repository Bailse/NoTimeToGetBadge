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

public class HowToPlayScreen extends BorderPane {

    public HowToPlayScreen(ScreenManager manager){

        this.setPrefSize(1200, 1200);

        // ================= BACKGROUND =================
        try {
            Image bgImage = new Image(Objects.requireNonNull(getClass().getResource("/tuntung.jpg")).toExternalForm());

            BackgroundImage bgView = new BackgroundImage(
                    bgImage,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.CENTER,
                    //ปรับขนาดตามหน้าจออัตโนมัติ
                    new BackgroundSize(100, 100, true, true, true, true)
            );
            this.setBackground(new Background(bgView));
        } catch (Exception e) {
            // ถ้าโหลดภาพไม่ติด
            this.setStyle("-fx-background-color: #0a0a0a;");
        }

        // ================= HEADER =================
        Label title = new Label("HOW TO PLAY");

        title.setFont(Font.font("Garamond", FontWeight.BOLD, 50));
        title.setTextFill(Color.web("#ffffff"));
        title.setStyle("-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.8), 15, 0.5, 0, 0);");

        VBox header = new VBox(title);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(50, 0, 20, 0));

        // ================= CONTENT BOX =================
        VBox contentBox = new VBox(30);
        contentBox.setPadding(new Insets(40));
        contentBox.setAlignment(Pos.TOP_LEFT);
        contentBox.setMaxWidth(850);

        contentBox.setStyle("""
            -fx-background-color: rgba(10, 10, 10, 0.75);
            -fx-border-color: #00ffff;
            -fx-border-width: 2;
            -fx-background-radius: 15;
            -fx-border-radius: 15;
        """);

        contentBox.getChildren().addAll(
                createSection("01. GOAL", "Escape the chaser and survive the pixel life cycle.", "#ff00ff"),
                createSection("02. CORE STATS",
                        "• MONEY: Needed for essential expenses\n" +
                                "• HEALTH: Vital sign. Don't let it reach zero!\n" +
                                "• EDUCATION: Unlocks better career paths\n" +
                                "• STAMINA: Energy for actions and running", "#00ff00"),
                createSection("03. ACTIONS",
                        "• WORK: Earn Money but lose Stamina\n" +
                                "• STUDY: Gain Knowledge for future growth\n" +
                                "• REST: Recover Health and Stamina at safe points", "#ffff00")
        );

        ScrollPane scrollPane = new ScrollPane(contentBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setPannable(true);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-border-color: transparent;");

        VBox centerContainer = new VBox(scrollPane);
        centerContainer.setAlignment(Pos.CENTER);
        centerContainer.setPadding(new Insets(0, 100, 0, 100));

        this.setTop(header);
        this.setCenter(centerContainer);

        // ================= BOTTOM BAR =================
        Button back = new Button("< RETURN TO GAME");
        back.setFont(Font.font("Garamond", FontWeight.BOLD, 22));
        back.setStyle("""
            -fx-background-color: #ffffff;
            -fx-text-fill: #000000;
            -fx-background-radius: 5;
            -fx-padding: 12 35 12 35;
            -fx-cursor: hand;
        """);

        back.setOnMouseEntered(e -> back.setStyle("-fx-background-color: #00ffff; -fx-text-fill: black; -fx-background-radius: 5; -fx-padding: 12 35 12 35;"));
        back.setOnMouseExited(e -> back.setStyle("-fx-background-color: white; -fx-text-fill: black; -fx-background-radius: 5; -fx-padding: 12 35 12 35;"));

        back.setOnAction(e -> manager.showTitle());

        HBox bottom = new HBox(back);
        bottom.setPadding(new Insets(35));
        bottom.setAlignment(Pos.CENTER_LEFT);

        this.setBottom(bottom);
    }

    private VBox createSection(String title, String text, String colorHex) {
        Label lblTitle = new Label(title);
        lblTitle.setFont(Font.font("Courier New", FontWeight.BOLD, 28));
        lblTitle.setTextFill(Color.web(colorHex));
        lblTitle.setStyle("-fx-effect: dropshadow(one-pass-box, black, 5, 0.5, 2, 2);");

        Label lblText = new Label(text);
        lblText.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
        lblText.setTextFill(Color.WHITE);
        lblText.setWrapText(true);
        lblText.setLineSpacing(8);

        return new VBox(10, lblTitle, lblText);
    }
}