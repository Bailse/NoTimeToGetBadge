package Screen.HowToPlay;

import Screen.ScreenManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class HowToPlayScreen extends BorderPane {

    public HowToPlayScreen(ScreenManager manager){

        this.setPrefSize(1200,1200);
        this.setStyle("-fx-background-color: #161616; -fx-font-family: 'Segoe UI';");

        Label title = new Label("How To Play");
        title.setStyle("-fx-text-fill: white; -fx-font-size: 36px; -fx-font-weight: bold;");

        Label content = new Label(
                "Goal:\n" +
                "Balance your stats and survive daily life.\n\n" +

                "Core Stats:\n" +
                "• Money – needed for expenses\n" +
                "• Study – improves career opportunities\n" +
                "• Health – affects performance\n" +
                "• Happiness – keeps your character motivated\n\n" +

                "Actions:\n" +
                "• Work → earn money\n" +
                "• Study → increase knowledge\n" +
                "• Exercise → improve health\n" +
                "• Relax → improve happiness\n\n" +

                "Each character has different strengths and weaknesses.\n" +
                "Choose wisely and balance your life to succeed."
        );

        content.setStyle("-fx-text-fill: #d6d6d6; -fx-font-size: 16px;");
        content.setWrapText(true);
        content.setMaxWidth(800);

        VBox center = new VBox(20, title, content);
        center.setAlignment(Pos.CENTER);
        center.setPadding(new Insets(40));

        this.setCenter(center);

        Button back = new Button("Back");
        back.setOnAction(e -> manager.showTitle());

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox bottom = new HBox(10, back, spacer);
        bottom.setPadding(new Insets(10));

        this.setBottom(bottom);
    }
}
