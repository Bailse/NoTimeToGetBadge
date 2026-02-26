package Character;

import javafx.scene.image.Image;

import java.util.Objects;

public class GymBro extends  BasePlayer{
    public GymBro() {
        super(
                120,
                4500,
                5,
                100,
                1.0,
                1.0,
                1,
                1,
                100
        );
        setImagePath("/Annie_Zheng.jpg");
        setImgUp(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Avatar/Normal_up.png"))));
        setImgDown(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Avatar/Normal_down.png"))));
        setImgRight(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Avatar/Normal_right.png"))));
        setImgLeft(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Avatar/Normal_left.png"))));

    }
}
