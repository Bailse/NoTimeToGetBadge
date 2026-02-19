package nttr.ui.screens;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import nttr.model.Player;

import java.util.function.Consumer;

/**
 * Character selection screen (FULL details):
 * - 4 avatars
 * - Description box showing pros/cons
 * - Applies a small starting "trait" by adjusting multipliers and a couple starter stats.
 */
public class CharacterSelectScreen {

    private final BorderPane root;

    private final String[] avatarPaths = new String[]{
            "/Annie_Zheng.jpg",
            "/Huh.jpg",
            "/Lily.jpg",
            "/deku_nerd.jpg"
    };

    private final String[] archetypeNames = new String[]{
            "Hustler (Work Focus)",
            "Genius (Study Focus)",
            "Athlete (Health Focus)",
            "Socialite (Happiness Focus)"
    };

    // Simple, readable pros/cons (matches gameplay multipliers)
    private final String[] prosCons = new String[]{
            "Pros:\n• Earn more from Work (+20%)\n• Faster commute (−10% travel time)\n\nCons:\n• Work drains a bit more Happiness (−5 extra)",
            "Pros:\n• Study gain boosted (+25%)\n• Study drains less Happiness (+5 less)\n\nCons:\n• Starts with a bit less Money (−10)",
            "Pros:\n• Exercise health gain boosted (+25%)\n• Stress increases slower (−15%)\n\nCons:\n• Party costs slightly more Money (+5)",
            "Pros:\n• Relax/Happiness gain boosted (+25%)\n• Party costs less Money (−10%)\n\nCons:\n• Starts with a bit more Stress (+8)"
    };

    private int selectedIndex = 0;

    private final Label selectedTitle = new Label();
    private final Label selectedDesc = new Label();

    public CharacterSelectScreen(Consumer<Player> onStartGame, Runnable onBack) {
        root = new BorderPane();
        root.setPadding(new Insets(18));
        root.setStyle("-fx-background-color: #161616; -fx-font-family: 'Segoe UI';");

        VBox header = new VBox(8);
        header.setAlignment(Pos.CENTER_LEFT);

        Label title = new Label("Create Character");
        title.setStyle("-fx-text-fill: white; -fx-font-size: 28px; -fx-font-weight: 800;");

        Label hint = new Label("Choose 1 of 4 characters. Each has different advantages and tradeoffs.");
        hint.setStyle("-fx-text-fill: #cfcfcf;");

        header.getChildren().addAll(title, hint);

        TextField nameField = new TextField("Bright");
        nameField.setMaxWidth(280);

        Label nameLbl = new Label("Name:");
        nameLbl.setStyle("-fx-text-fill: #e8e8e8; -fx-font-weight: 700;");

        VBox top = new VBox(12, header, nameLbl, nameField);
        top.setPadding(new Insets(10));
        root.setTop(top);

        // ===== Avatar cards row =====
        HBox avatarsRow = new HBox(14);
        avatarsRow.setAlignment(Pos.CENTER);
        avatarsRow.setPadding(new Insets(10));

        StackPane[] cards = new StackPane[4];

        for (int i = 0; i < 4; i++) {
            ImageView iv = safeAvatar(avatarPaths[i]);
            iv.setFitWidth(110);
            iv.setFitHeight(110);
            iv.setPreserveRatio(true);

            Label aName = new Label(archetypeNames[i]);
            aName.setStyle("-fx-text-fill: white; -fx-font-size: 12px; -fx-font-weight: 800;");
            aName.setWrapText(true);
            aName.setMaxWidth(140);

            VBox cardBox = new VBox(8, iv, aName);
            cardBox.setAlignment(Pos.CENTER);
            cardBox.setPadding(new Insets(10));

            StackPane card = new StackPane(cardBox);
            card.setMinSize(160, 180);
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

        // ===== Description box =====
        selectedTitle.setStyle("-fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: 900;");
        selectedDesc.setStyle("-fx-text-fill: #d6d6d6; -fx-font-size: 13px;");
        selectedDesc.setWrapText(true);

        VBox descBox = new VBox(8, selectedTitle, selectedDesc);
        descBox.setPadding(new Insets(14));
        descBox.setStyle(
                "-fx-background-color: rgba(255,255,255,0.05);" +
                "-fx-background-radius: 16;" +
                "-fx-border-radius: 16;" +
                "-fx-border-color: rgba(255,255,255,0.12);"
        );
        descBox.setMaxWidth(720);

        VBox center = new VBox(14, avatarsRow, descBox);
        center.setAlignment(Pos.CENTER);
        center.setPadding(new Insets(10));
        root.setCenter(center);

        refreshDescription();

        // ===== Bottom buttons =====
        Button back = new Button("Back");
        back.setOnAction(e -> onBack.run());

        Button start = new Button("Start Game");
        start.setStyle("-fx-font-weight: 900;");
        start.setOnAction(e -> {
            String name = nameField.getText() == null ? "" : nameField.getText().trim();
            if (name.isEmpty()) name = "Player";

            Player p = new Player(name, avatarPaths[selectedIndex]);
            applyArchetype(p, selectedIndex);
            onStartGame.accept(p);
        });

        HBox bottom = new HBox(10, back, start);
        bottom.setAlignment(Pos.CENTER_RIGHT);
        bottom.setPadding(new Insets(10));
        root.setBottom(bottom);
    }

    private void refreshDescription() {
        selectedTitle.setText(archetypeNames[selectedIndex]);
        selectedDesc.setText(prosCons[selectedIndex]);
    }

    private void applyArchetype(Player p, int idx) {
        // IMPORTANT: multipliers stack with items later, so we multiply current values.
        if (idx == 0) { // Hustler
            p.setWorkMoneyMultiplier(p.getWorkMoneyMultiplier() * 1.20);
            p.setTravelTimeMultiplier(p.getTravelTimeMultiplier() * 0.90);
            // tradeoff
            p.addHappiness(-3);
        } else if (idx == 1) { // Genius
            p.setStudySkillMultiplier(p.getStudySkillMultiplier() * 1.25);
            p.addMoney(-10);
            p.addHappiness(2);
        } else if (idx == 2) { // Athlete
            p.setExerciseHealthMultiplier(p.getExerciseHealthMultiplier() * 1.25);
            // slightly lower stress baseline
            p.addStress(-3);
        } else if (idx == 3) { // Socialite
            p.setRelaxHappinessMultiplier(p.getRelaxHappinessMultiplier() * 1.25);
            p.addStress(8);
        }
    }

    private String cardStyle(boolean selected) {
        if (selected) {
            return "-fx-background-color: rgba(90,160,255,0.18);" +
                    "-fx-background-radius: 16;" +
                    "-fx-border-radius: 16;" +
                    "-fx-border-color: rgba(90,160,255,0.9);" +
                    "-fx-border-width: 2;";
        }
        return "-fx-background-color: rgba(255,255,255,0.06);" +
                "-fx-background-radius: 16;" +
                "-fx-border-radius: 16;" +
                "-fx-border-color: rgba(255,255,255,0.15);";
    }

    private ImageView safeAvatar(String path) {
        try {
            Image img = new Image(getClass().getResourceAsStream(path));
            return new ImageView(img);
        } catch (Exception e) {
            return new ImageView();
        }
    }

    public Parent getRoot() {
        return root;
    }
}
