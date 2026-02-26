package Screen.Result;

import Audio.SoundManager;
import Logic.GameSession;
import Screen.ScreenManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.Objects;
import Character.BasePlayer;

public class ResultScreen extends StackPane {

    private final String[] Badge = new String[]{
            "/SBadge.png",
            "/SstarBadge.png",
            "/UBadge.png"
    };

    // ================= BADGE THRESHOLDS =================
    // 🔥 แก้ตัวเลขตรงนี้ได้เลยในอนาคต

    // Money
    private static final int MONEY_SSTAR = 10000;
    private static final int MONEY_S = 5000;

    // Health
    private static final int HEALTH_SSTAR = 90;
    private static final int HEALTH_S = 70;

    // Education
    private static final int EDUCATION_SSTAR = 90;
    private static final int EDUCATION_S = 70;

    // Happiness
    private static final int HAPPINESS_SSTAR = 90;
    private static final int HAPPINESS_S = 70;

    public ResultScreen(ScreenManager manager) {

        SoundManager.stopBackground();
        BasePlayer player = GameSession.getPlayer();

        setPrefSize(1200, 1200);

        VBox mainBox = new VBox(40);
        mainBox.setAlignment(Pos.TOP_CENTER);
        mainBox.setPadding(new Insets(60, 40, 40, 40));
        mainBox.setMaxWidth(1000);
        mainBox.setMaxHeight(1000);
        mainBox.setEffect(new javafx.scene.effect.DropShadow(30, Color.BLACK));

        Image bgImage = new Image(
                Objects.requireNonNull(
                        getClass().getResource("/sky.png")
                ).toExternalForm()
        );

        BackgroundImage bgView = new BackgroundImage(
                bgImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(1200, 1200, false, false, false, false)
        );

        this.setBackground(new Background(bgView));

        // ================= TITLE =================
        Label title = new Label("RESULT!");
        title.setFont(Font.font("Garamond", FontWeight.BLACK, 60));
        title.setTextFill(Color.web("#FFD966"));
        title.setStyle("""
            -fx-effect: dropshadow(gaussian, black, 15, 0.7, 0, 0);
        """);

        VBox.setMargin(title, new Insets(150, 0, 0, 0));

        // ================= AVATAR =================
        ImageView avatar = new ImageView(
                new Image(getClass().getResourceAsStream(player.getImagePath()))
        );
        avatar.setFitWidth(250);
        avatar.setFitHeight(250);
        avatar.setClip(new Circle(125, 125, 125));
        avatar.setStyle("""
            -fx-effect: dropshadow(gaussian, rgba(255,255,255,0.4), 30, 0.6, 0, 0);
        """);

        Circle border = new Circle(130);
        border.setFill(Color.TRANSPARENT);
        border.setStroke(Color.WHITE);
        border.setStrokeWidth(3);

        StackPane avatarPane = new StackPane(border, avatar);

        // ================= STATUS GRID =================
        GridPane statusGrid = new GridPane();
        statusGrid.setHgap(120);
        statusGrid.setVgap(50);
        statusGrid.setAlignment(Pos.CENTER);

        statusGrid.add(createStatusRow("Money", player.getMoney()), 0, 0);
        statusGrid.add(createStatusRow("Education", player.getEducation()), 1, 0);
        statusGrid.add(createStatusRow("Health", player.getHealth()), 0, 1);
        statusGrid.add(createStatusRow("Happiness", player.getHappiness()), 1, 1);

        StackPane descBox = new StackPane(statusGrid);
        descBox.setPadding(new Insets(40));
        descBox.setStyle("""
            -fx-background-color: rgba(0, 0, 0, 0.65);
            -fx-background-radius: 25;
            -fx-border-color: rgba(255,255,255,0.1);
            -fx-border-radius: 25;
        """);

        // ================= BUTTON =================
        Button backButton = new Button("GO BACK");
        backButton.setFont(Font.font("Garamond", FontWeight.BLACK, 16));

        backButton.setStyle("""
            -fx-background-color: linear-gradient(#ffffff, #e6e6e6);
            -fx-text-fill: black;
            -fx-background-radius: 20;
            -fx-padding: 20 40 20 40;
            -fx-min-height: 60;
            -fx-border-color: #cccccc;
            -fx-border-radius: 20;
            -fx-border-width: 2;
        """);

        backButton.setOnAction(e -> manager.showTitle());

        HBox buttonBox = new HBox(backButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(0, 60, 10, 60));
        VBox.setMargin(buttonBox, new Insets(-30, 60, 140, 60));

        mainBox.getChildren().addAll(
                title,
                avatarPane,
                descBox,
                buttonBox
        );

        this.getChildren().add(mainBox);
        StackPane.setAlignment(mainBox, Pos.CENTER);
    }

    // ================= STATUS ROW =================
    private HBox createStatusRow(String name, int value) {

        Label statLabel = new Label(name + " : " + value);
        statLabel.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 26));
        statLabel.setTextFill(Color.WHITE);
        statLabel.setStyle("""
            -fx-effect: dropshadow(gaussian, black, 6, 0.8, 0, 0);
        """);

        ImageView badge = createBadge(name, value);

        HBox row = new HBox(20, statLabel, badge);
        row.setAlignment(Pos.CENTER_LEFT);

        return row;
    }

    // ================= BADGE LOGIC =================
    private ImageView createBadge(String statName, int value) {

        String path;

        switch (statName) {

            case "Money":
                if (value >= MONEY_SSTAR) {
                    path = Badge[1];
                } else if (value >= MONEY_S) {
                    path = Badge[0];
                } else {
                    path = Badge[2];
                }
                break;

            case "Health":
                if (value >= HEALTH_SSTAR) {
                    path = Badge[1];
                } else if (value >= HEALTH_S) {
                    path = Badge[0];
                } else {
                    path = Badge[2];
                }
                break;

            case "Education":
                if (value >= EDUCATION_SSTAR) {
                    path = Badge[1];
                } else if (value >= EDUCATION_S) {
                    path = Badge[0];
                } else {
                    path = Badge[2];
                }
                break;

            case "Happiness":
                if (value >= HAPPINESS_SSTAR) {
                    path = Badge[1];
                } else if (value >= HAPPINESS_S) {
                    path = Badge[0];
                } else {
                    path = Badge[2];
                }
                break;

            default:
                path = Badge[2];
        }

        ImageView badge = new ImageView(
                new Image(Objects.requireNonNull(
                        getClass().getResourceAsStream(path)))
        );

        badge.setFitWidth(60);
        badge.setFitHeight(60);
        badge.setEffect(new javafx.scene.effect.DropShadow(15, Color.GOLD));

        return badge;
    }
}