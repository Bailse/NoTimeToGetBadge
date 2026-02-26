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

    public ResultScreen(ScreenManager manager) {
        SoundManager.stopBackground();
        BasePlayer player = GameSession.getPlayer();

        setPrefSize(1200, 1200);

        // ================= MAIN BOX =================
        VBox mainBox = new VBox(40);
        mainBox.setAlignment(Pos.TOP_CENTER);
        mainBox.setPadding(new Insets(60, 40, 40, 40));
        mainBox.setMaxWidth(1000);
        mainBox.setMaxHeight(1000);

        mainBox.setStyle("""
        -fx-background-color: rgba(0, 0, 0, 0.6); 
        -fx-background-radius: 30;
        """);

    // ================= BACKGROUND =================
    // ใช้คำสั่งนี้เพื่อให้ภาพพื้นหลังขึ้นเต็มจอ 1200x1200px
        Image bgImage = new Image(Objects.requireNonNull(getClass().getResource("/galaxy.png")).toExternalForm());
        BackgroundImage bgView = new BackgroundImage(bgImage,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(1200, 1200, false, false, false, false));

        this.setBackground(new Background(bgView));

        // ================= TITLE =================
        Label title = new Label("GAME ENDED!");
        title.setFont(Font.font("Courier New", FontWeight.BLACK, 50));
        title.setTextFill(Color.WHITE);

        // ================= AVATAR (CIRCLE) =================
        ImageView avatar = new ImageView(
                new Image(Objects.requireNonNull(
                        getClass().getResourceAsStream("/SstarBadge.png")))
        );

        avatar.setFitWidth(250);
        avatar.setFitHeight(250);
        avatar.setClip(new Circle(125, 125, 125));

        // ================= STATUS GRID (Triangle: Health at Bottom) =================
        GridPane statusGrid = new GridPane();
        statusGrid.setHgap(120); // ระยะห่างระหว่าง Money และ Education
        statusGrid.setVgap(50);  // ระยะห่างระหว่างแถวบนและแถวล่าง
        statusGrid.setAlignment(Pos.CENTER);

        statusGrid.add(createStatusRow("Money", player.getMoney()), 0, 0);
        statusGrid.add(createStatusRow("Education", player.getEducation()), 1, 0);
        statusGrid.add(createStatusRow("Health", player.getHealth()), 0, 1);
        statusGrid.add(createStatusRow("Happiness", player.getHappiness()), 1, 1);

       // HBox healthRow = createStatusRow("Health", player.getHealth());
       // statusGrid.add(healthRow, 0, 1, 2, 1);
        //GridPane.setHalignment(healthRow, javafx.geometry.HPos.CENTER);

        StackPane descBox = new StackPane(statusGrid);
        descBox.setPadding(new Insets(40));
        descBox.setStyle("""
        -fx-background-color: rgba(30, 30, 30, 0.5); 
        -fx-background-radius: 20;
        """);

        // ================= BUTTON =================
        Button backButton = new Button("GO BACK");

        backButton.setFont(Font.font("Courier New", FontWeight.BLACK, 16));

        backButton.setStyle("""
        
        -fx-background-color: white;
        
        -fx-text-fill: black;
        
        -fx-background-radius: 15;
        
        -fx-padding: 10 25 10 25;
        
        """);

        backButton.setOnAction(e -> manager.showTitle());

        HBox buttonBox = new HBox(backButton);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);
        buttonBox.setPadding(new Insets(20, 40, 0, 0));

        // ================= ADD EVERYTHING =================
        mainBox.getChildren().addAll(
                title,
                avatar,
                descBox,
                buttonBox
        );

        // ล้างของเก่าและใส่ใหม่ตามลำดับ: พื้นหลัง (setBackground) -> mainBox (Foreground)
        this.getChildren().clear();
        this.getChildren().add(mainBox);
        StackPane.setAlignment(mainBox, Pos.CENTER);
    }

    // ================= STATUS ROW =================
    private HBox createStatusRow(String name, int value){
        Label statLabel = new Label(name + " : " + value);
        statLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        statLabel.setTextFill(Color.WHITE);

        // เพิ่มเงาให้ตัวหนังสืออ่านง่ายขึ้น
        statLabel.setStyle("-fx-effect: dropshadow(gaussian, black, 5, 0.5, 0, 0);");

        ImageView badge = createBadge(value);
        HBox row = new HBox(20, statLabel, badge);
        row.setAlignment(Pos.CENTER_LEFT);
        return row;
    }

    // ================= BADGE LOGIC =================
    private ImageView createBadge(int value){

        String path;

        if(value > 150){
            path = Badge[1];     // S Star
        }else if(value >= 100){
            path = Badge[0];     // S
        }else{
            path = Badge[2];     // U
        }

        ImageView badge = new ImageView(
                new Image(Objects.requireNonNull(
                        getClass().getResourceAsStream(path)))
        );

        badge.setFitWidth(60);
        badge.setFitHeight(60);

        return badge;
    }
}