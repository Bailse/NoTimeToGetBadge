package Screen.Game;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class StatusTab extends VBox {

    private Text Status;
    private Text Round;
    private Text Equipment;

    private String picture;
    private Circle circle;
    private ImageView imageView;

    private Text Stamina;
    private Text Health;
    private Text money;
    private Text Education;



    public StatusTab(){

        this.setSpacing(40);
        this.setAlignment(Pos.TOP_CENTER);
        this.setMaxWidth(Double.MAX_VALUE);
        Text status = new Text("Status");
        status.setFont(Font.font(50));


/// ///////////////////////////////////////////////////////


        ImageView imageView = new ImageView(
                new Image(getClass().getResourceAsStream("/Lily.jpg"))
        );
        imageView.setFitWidth(200);
        imageView.setFitHeight(200);
        imageView.setPreserveRatio(false);

        Circle clip = new Circle(100,100,100);
        imageView.setClip(clip);


/// ///////////////////////////////////////////////////////


        HBox Health = new HBox();







        this.getChildren().addAll(status, imageView);
    }










}
