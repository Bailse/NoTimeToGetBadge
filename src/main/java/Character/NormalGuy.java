package Character;

import javafx.scene.image.Image;

import java.util.Objects;

public class NormalGuy extends BasePlayer{
    public NormalGuy() {
        super(
                100,
                5000,
                5,
                90,
                1.0,
                1.0,
                3,
                10,
                100
        );
        setImgUp(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Avatar/Normal_up.png"))));
        setImgDown(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Avatar/Normal_down.png"))));
        setImgRight(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Avatar/Normal_right.png"))));
        setImgLeft(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Avatar/Normal_left.png"))));

        setImagePath("/deku_nerd.jpg");

    }
}
