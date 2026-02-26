package Screen;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.Objects;

public class TitleScreen extends VBox {

    private ScreenManager begin;
    private final Text title;
    private final Button start;
    private final Button how_to_play;
    private final Button exit;

    public TitleScreen(ScreenManager begin) {
        this.begin = begin;
        this.setPrefSize(1200, 1200);
        // พื้นหลังสีเข้มสไตล์ Retro
        //this.setStyle("-fx-background-color: #0d0d0d;");
        //this.setBackground(new Background(getClass().getResourceAsStream("/")));

        Image image = new Image(Objects.requireNonNull(getClass().getResource("/tt4-2.png")).toExternalForm());

        BackgroundSize backgroundSize = new BackgroundSize(
                100, 100, true, true, true, false);

        BackgroundImage backgroundImage = new BackgroundImage(
                image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                backgroundSize);

        Background background = new Background(backgroundImage);

        this.setBackground(background);


        this.setSpacing(30);
        this.setAlignment(Pos.CENTER);
        //this.setPadding(new Insets(50));

        // ===== หัวข้อเกม (Title) =====
        this.title = new Text("NO TIME\nTO GET BADGE");
        this.title.setFont(Font.font("Garamond", FontWeight.BLACK, 60));
        this.title.setFill(Color.web("#00FF41")); // สีเขียวสไตล์ Matrix/Pixel
        this.title.setStroke(Color.WHITE);
        this.title.setStrokeWidth(1);
        this.title.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);

        // ===== การตกแต่งปุ่ม (Button Styling) =====
        this.start = createPixelButton("START GAME");
        this.how_to_play = createPixelButton("HOW TO PLAY");
        this.exit = createPixelButton("EXIT");

        // ===== Event Handlers =====
        this.start.setOnAction(e -> begin.showChoose());
        this.how_to_play.setOnAction(e -> begin.showHowToPlay());
        this.exit.setOnAction(e -> begin.endGame());

        this.getChildren().addAll(title, start, how_to_play, exit);
    }

    /**
     * Helper Method สำหรับสร้างปุ่มสไตล์ Pixel Art
     */
    private Button createPixelButton(String text) {
        Button btn = new Button(text);

        // สไตล์ปุ่มแบบ 8-bit: กรอบหนา, สีตัดกัน, ไม่มีความโค้ง (Radius 0)
        String normalStyle =
                "-fx-background-color: #252525; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-family: 'Courier New'; " +
                        "-fx-font-weight: bold; " +
                        "-fx-font-size: 20px; " +
                        "-fx-border-color: #555555; " +
                        "-fx-border-width: 4; " +
                        "-fx-background-radius: 0; " +
                        "-fx-border-radius: 0; " +
                        "-fx-cursor: hand;";

        String hoverStyle =
                "-fx-background-color: #00FF41; " +
                        "-fx-text-fill: black; " +
                        "-fx-border-color: #ffffff; " +
                        "-fx-border-width: 4; " +
                        "-fx-background-radius: 0; " +
                        "-fx-border-radius: 0;";

        btn.setStyle(normalStyle);
        btn.setMinWidth(250);
        btn.setPadding(new Insets(10, 20, 10, 20));

        // เอฟเฟกต์เมื่อกด
        btn.setOnMousePressed(e -> btn.setTranslateY(2));
        btn.setOnMouseReleased(e -> btn.setTranslateY(-2));

        return btn;
    }





}