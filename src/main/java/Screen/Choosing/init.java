package Screen.Choosing;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;


public class init extends VBox {

    private String des;
    private String name_button;
    private Text descirption;
    private String picture;
    private Circle circle;
    private ImageView imageView;
    private Button Player_button;



    public init(String des, String picture , String button){

        this.setAlignment(Pos.CENTER);
        this.setSpacing(20);
        this.setMinWidth(100);
        this.setMaxWidth(1000);


        setPicture(picture);
        setDes(des);
        setName_button(button);



        this.imageView = new ImageView(new Image(getClass().getResourceAsStream(getPicture())));

        this.imageView.setFitWidth(200);
        this.imageView.setFitHeight(200);
        this.imageView.setPreserveRatio(false);

        this.circle = new Circle();
        this.circle.setCenterX(100);
        this.circle.setCenterY(100);
        this.circle.setRadius(100);

        this.imageView.setClip(this.circle);

        this.Player_button = new Button(getName_button());

        this.descirption = new Text(getDes());



       // this.getChildren().addAll(this.imageView,this.Player_button,this.descirption);


    }

    public void addAll(){
        this.getChildren().addAll(this.imageView,this.Player_button,this.descirption);
    }

    public Button getPlayer_button() {
        return Player_button;
    }

    public void setPlayer(Button player) {
        Player_button = player;
    }

    public String getName_button() {
        return name_button;
    }

    public void setName_button(String name_button) {
        this.name_button = name_button;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }









}
