package Screen.Choosing;

import Audio.SoundManager;
import Logic.GameSession;
import Screen.ScreenManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.io.InputStream;

import Character.*;

/**
 * Screen for ChoosingScreen
 */

public class ChoosingScreen extends BorderPane {

    private final String[] avatarPaths = new String[]{
            "/Avatar/Gymbro/GymBroPic.png",
            "/Avatar/Nerd/NerdPic.png",
            "/Avatar/Otaku/OtakuPic.png",
            "/Avatar/Normal/NormalPic.png"
    };

    private final String[] archetypeNames = new String[]{
            "GymBro",
            "Nerd",
            "Otaku",
            "NormalGuy"
    };

    private final String[] prosCons = new String[]{
            "Pros:\n• Earn more money when It work at Gym \n • Start with health more than others \nCons:\n• Education need more stamina than others",
            "Pros:\n• Study gain boosted \n Cons:\n• Start with less health \n • When walk stamina spent more than others",
            "Pros:\n• Earn more money when It work at Mall \n• Start with money more than others \n\nCons:\n• Education and health less than others when start game",
            "Pros:\n• He just chill guy\nCons:\n• Nothing because he just normal guy"
    };

    private int selectedIndex = 0;

    private final Label selectedTitle = new Label();
    private final Label selectedDesc = new Label();

    /**
     * constructor that initialize object in ChoosingScreen
     *
     */


    public ChoosingScreen(ScreenManager manager) {

        SoundManager.playBackground("background2.mp3");

        setPrefSize(1200, 1200);
        setPadding(new Insets(18));
        setStyle("-fx-background-color: #161616; -fx-font-family: 'Segoe UI';");


        Label title = new Label("Create Character");
        title.setStyle("-fx-text-fill: white; -fx-font-size: 30px; -fx-font-weight: 900;");

        Label hint = new Label("Choose 1 of 4 characters. Each has different advantages and tradeoffs.");
        hint.setStyle("-fx-text-fill: #cfcfcf; -fx-font-size: 13px;");

        VBox top = new VBox(10, title, hint);
        top.setPadding(new Insets(10, 10, 12, 10));
        top.setAlignment(Pos.CENTER_LEFT);
        setTop(top);


        HBox avatarsRow = new HBox(14);
        avatarsRow.setAlignment(Pos.CENTER);
        avatarsRow.setPadding(new Insets(10));

        StackPane[] cards = new StackPane[4];

        for (int i = 0; i < 4; i++) {
            ImageView createImage = safeAvatar(avatarPaths[i]);
            createImage.setFitWidth(120);
            createImage.setFitHeight(120);
            createImage.setPreserveRatio(true);

            Label aName = new Label(archetypeNames[i]);
            aName.setStyle("-fx-text-fill: white; -fx-font-size: 12px; -fx-font-weight: 900;");
            aName.setWrapText(true);
            aName.setMaxWidth(150);
            aName.setAlignment(Pos.CENTER);

            VBox cardBox = new VBox(10, createImage, aName);
            cardBox.setAlignment(Pos.CENTER);
            cardBox.setPadding(new Insets(12));

            StackPane card = new StackPane(cardBox);
            card.setMinSize(175, 195);
            card.setStyle(cardStyle(i == selectedIndex));

            int idx = i;
            card.setOnMouseClicked(e -> {
                selectedIndex = idx;
                for (int k = 0; k < cards.length; k++) {
                    cards[k].setStyle(cardStyle(k == selectedIndex));
                }
                refreshDescription();
            });

            cards[i] = card;
            avatarsRow.getChildren().add(card);
        }


        selectedTitle.setStyle("-fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: 900;");
        selectedDesc.setStyle("-fx-text-fill: #d6d6d6; -fx-font-size: 13px;");
        selectedDesc.setWrapText(true);

        VBox descBox = new VBox(8, selectedTitle, selectedDesc);
        descBox.setPadding(new Insets(16));
        descBox.setStyle(
                "-fx-background-color: rgba(255,255,255,0.05);" +
                        "-fx-background-radius: 16;" +
                        "-fx-border-radius: 16;" +
                        "-fx-border-color: rgba(255,255,255,0.14);"
        );
        descBox.setMaxWidth(780);

        VBox center = new VBox(14, avatarsRow, descBox);
        center.setAlignment(Pos.CENTER);
        center.setPadding(new Insets(10));
        setCenter(center);

        refreshDescription();


        Button back = new Button("Back");
        back.setOnAction(e -> manager.showTitle());

        Button start = new Button("Start Game");
        start.setStyle("-fx-font-weight: 900;");
        start.setOnAction(e -> {
        CharacterType type;
        switch(selectedIndex){
            case 0: type = CharacterType.GYMBRO; break;
            case 1: type = CharacterType.NERD; break;
            case 2: type = CharacterType.OTAKU; break;
            default: type = CharacterType.NORMAL;
        }
        BasePlayer p = PlayerCreate.create(type);
        GameSession.setBasePlayer(p);
        manager.showGame();
    });

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox bottom = new HBox(10, back, spacer, start);
        bottom.setAlignment(Pos.CENTER);
        bottom.setPadding(new Insets(10));
        setBottom(bottom);
    }

    private void refreshDescription() {
        selectedTitle.setText(archetypeNames[selectedIndex]);
        selectedDesc.setText(prosCons[selectedIndex]);
    }

    private String cardStyle(boolean selected) {
        if (selected) {
            return "-fx-background-color: rgba(90,160,255,0.18);" +
                    "-fx-background-radius: 18;" +
                    "-fx-border-radius: 18;" +
                    "-fx-border-color: rgba(90,160,255,0.65);" +
                    "-fx-border-width: 2;";
        }
        return "-fx-background-color: rgba(255,255,255,0.04);" +
                "-fx-background-radius: 18;" +
                "-fx-border-radius: 18;" +
                "-fx-border-color: rgba(255,255,255,0.12);" +
                "-fx-border-width: 1;";
    }

    private ImageView safeAvatar(String path) {
        try (InputStream is = getClass().getResourceAsStream(path)) {
            if (is != null) {
                return new ImageView(new Image(is));
            }
        } catch (Exception ignored) {
        }
        // fallback: generate a tiny placeholder image so the UI never crashes
        WritableImage placeholder = new WritableImage(1, 1);
        return new ImageView(placeholder);
    }

}
