package Character;

import javafx.scene.image.Image;

import java.util.Objects;

public class Otaku extends BasePlayer {
    public Otaku() {
        super(
                200,
                4000,
                5,
                70,
                0.95,
                1.05,
                3,
                12,
                100
        );
        setImagePath("/Lily.jpg");
        setImgUp(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Avatar/Otaku/Otaku_up.png"))));
        setImgDown(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Avatar/Otaku/Otaku_down.png"))));
        setImgRight(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Avatar/Otaku/Otaku_right.png"))));
        setImgLeft(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Avatar/Otaku/Otaku_left.png"))));

    }
}
