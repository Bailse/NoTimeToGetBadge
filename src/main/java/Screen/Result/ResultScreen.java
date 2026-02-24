package Screen.Result;

import Screen.ScreenManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;

public class ResultScreen extends BorderPane {

    public ResultScreen(ScreenManager manager) {

        // ===== วงกลม =====
        Circle circle = new Circle(200);
        circle.setFill(Color.TRANSPARENT);
        circle.setStroke(Color.BLACK);
        circle.setStrokeWidth(3);

        Label circleText = new Label("PIC");
        circleText.setFont(new Font(40));

        StackPane circlePane = new StackPane(circle, circleText);

        // ===== คำอธิบาย =====
        Label description = new Label("DESCRIPTION");
        description.setFont(new Font(32));
        description.setPrefSize(600,150);
        description.setAlignment(Pos.CENTER);
        description.setStyle("-fx-border-color: black; -fx-border-width: 3;");

        VBox centerBox = new VBox(50, circlePane, description);
        centerBox.setAlignment(Pos.CENTER);

        // ===== ปุ่มล่างขวา =====
        Button playButton = new Button("Go Back");
        playButton.setFont(new Font(24));
        playButton.setOnAction(e -> manager.showTitle());

        HBox bottomBox = new HBox(playButton);
        bottomBox.setAlignment(Pos.BOTTOM_RIGHT);
        bottomBox.setPadding(new Insets(10));

        // ===== ใส่ลง BorderPane =====
        setCenter(centerBox);
        setBottom(bottomBox);
    }
}