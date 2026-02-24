
package Screen.Game;

import Character.Player;
import Logic.GameSession;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class StatusTab extends VBox {

    public StatusTab(){

        Player player = GameSession.getPlayer();

        this.setSpacing(20);
        this.setAlignment(Pos.TOP_CENTER);

        Text title = new Text("Status");
        title.setFont(Font.font(40));

        ImageView avatar;

        if(player != null && player.getImagePath() != null){
            avatar = new ImageView(new Image(getClass().getResourceAsStream(player.getImagePath())));
        }else{
            avatar = new ImageView(new Image(getClass().getResourceAsStream("/player.png")));
        }

        avatar.setFitWidth(200);
        avatar.setFitHeight(200);

        Circle clip = new Circle(100,100,100);
        avatar.setClip(clip);

        Text stamina = new Text("Stamina: " + (player != null ? player.getStamina() : 0));
        Text health = new Text("Health: " + (player != null ? player.getHealth() : 0));
        Text money = new Text("Money: " + (player != null ? player.getMoney() : 0));
        Text education = new Text("Education: " + (player != null ? player.getEducation() : 0));

        this.getChildren().addAll(title, avatar, stamina, health, money, education);
    }
}
