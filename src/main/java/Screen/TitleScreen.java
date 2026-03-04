package Screen;

import Audio.SoundManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.util.Objects;

/**
 * The main title screen of the game.
 * It shows the game name and basic menu buttons (Start, How To Play, Exit).
 */
public class TitleScreen extends VBox {

    private final ScreenManager begin;
    private final Text title;
    private final Button start;
    private final Button how_to_play;
    private final Button exit;

    /**
     * Creates the title screen UI and sets background, music, and button actions.
     */
    public TitleScreen(ScreenManager begin) {
        this.begin = begin;
        this.setPrefSize(1200, 1200);
        SoundManager.playBackground("background2.mp3");

        Image image = new Image(Objects.requireNonNull(getClass().getResource("/Screen/tt4-2.png")).toExternalForm());

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



        this.title = new Text("NO TIME\nTO GET BADGE");

        this.title.setFont(Font.font("Helvetica", FontWeight.BOLD, 100));
        this.title.setFill(Color.web("#222222"));
        this.title.setTextAlignment(TextAlignment.CENTER);
        this.title.setLineSpacing(10);


        this.title.setStroke(Color.WHITE);
        this.title.setStrokeWidth(2);


        this.start = createPixelButton("START GAME");
        this.how_to_play = createPixelButton("HOW TO PLAY");
        this.exit = createPixelButton("EXIT");


        this.start.setOnAction(e -> begin.showChoose());
        this.how_to_play.setOnAction(e -> begin.showHowToPlay());
        this.exit.setOnAction(e -> begin.endGame());

        this.getChildren().addAll(title, start, how_to_play, exit);
    }

    /**
     * Creates a pixel-style button with basic styling and click animation.
     */
    private Button createPixelButton(String text) {
        Button btn = new Button(text);

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


        btn.setOnMousePressed(e -> btn.setTranslateY(2));
        btn.setOnMouseReleased(e -> btn.setTranslateY(-2));

        return btn;
    }





}