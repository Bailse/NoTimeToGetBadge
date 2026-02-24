
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

    private Text staminaText;
    private Text healthText;
    private Text moneyText;
    private Text educationText;
    private Text roundText;

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

        staminaText = new Text();
        healthText = new Text();
        moneyText = new Text();
        educationText = new Text();
        roundText = new Text();

        updateStatus();

        this.getChildren().addAll(title, avatar, roundText, staminaText, healthText, moneyText, educationText);
    }

    public void updateStatus(){
        Player player = GameSession.getPlayer();
        if(player == null) return;

        roundText.setText("Round: " + GameSession.getRound() + "/" + GameSession.getMaxRounds());
        staminaText.setText("Stamina: " + player.getStamina());
        healthText.setText("Health: " + player.getHealth());
        moneyText.setText("Money: " + player.getMoney());
        educationText.setText("Education: " + player.getEducation());
    }
}