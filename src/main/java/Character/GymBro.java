package Character;

import javafx.scene.image.Image;

import java.util.Objects;

public class GymBro extends  BasePlayer{
    public GymBro() {
        super(
                200,
                4500,
                5,
                100,
                1.0,
                1.0,
                1,
                2,
                100
        );
        setImagePath("/Annie_Zheng.jpg");
        setImgUp(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Avatar/Gymbro/Gymbro_up.png"))));
        setImgDown(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Avatar/Gymbro/Gymbro_down.png"))));
        setImgRight(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Avatar/Gymbro/Gymbro_right.png"))));
        setImgLeft(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Avatar/Gymbro/Gymbro_left.png"))));

    }
}
