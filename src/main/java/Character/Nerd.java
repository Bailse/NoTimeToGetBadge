package Character;

import javafx.scene.image.Image;

import java.util.Objects;

public class Nerd extends BasePlayer {
    public Nerd() {
        super(
                100,
                5000,
                10,
                80,
                1.0,
                1.20,
                2,
                10,
                100
        );
        setImagePath("/Huh.jpg");
        setImgUp(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Avatar/Normal_up.png"))));
        setImgDown(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Avatar/Normal_down.png"))));
        setImgRight(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Avatar/Normal_right.png"))));
        setImgLeft(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Avatar/Normal_left.png"))));


    }


}
