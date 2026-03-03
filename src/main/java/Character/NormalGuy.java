package Character;

import javafx.scene.image.Image;

import java.util.Objects;
/**
 * NormalGuy class extends from BasePlayer
 * NormalGuy that have noting special but the stat is balance
 */
public class NormalGuy extends BasePlayer{
    /**
     * constructor that initialize value for NormalGuy
     */
    public NormalGuy() {
        super(
                200,
                500,
                5,
                10,
                3,
                3,
                35
        );
        setImgUp(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Avatar/Normal/Normal_up.png"))));
        setImgDown(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Avatar/Normal/Normal_down.png"))));
        setImgRight(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Avatar/Normal/Normal_right.png"))));
        setImgLeft(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Avatar/Normal/Normal_left.png"))));

        setImagePath("/Avatar/Normal/NormalPic.png");

    }

}
