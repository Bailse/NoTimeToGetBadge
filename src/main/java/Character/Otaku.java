package Character;

import javafx.scene.image.Image;

import java.util.Objects;
/**
 * Otaku class extends from BasePlayer
 * Otaku that have special interact with Mall building
 */
public class Otaku extends BasePlayer {
    /**
     * constructor that initialize value for Otaku
     */
    public Otaku() {
        super(
                200,
                1500,
                5,
                0,
                3,
                4,
                45
        );
        setImagePath("/Avatar/Otaku/OtakuPic.png");
        setImgUp(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Avatar/Otaku/Otaku_up.png"))));
        setImgDown(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Avatar/Otaku/Otaku_down.png"))));
        setImgRight(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Avatar/Otaku/Otaku_right.png"))));
        setImgLeft(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Avatar/Otaku/Otaku_left.png"))));

    }

    private int workCount = 0;

    /**
     * Counting work if work in Mall and is equal 5 this Otaku get bonus money and reset WorkCount to 0
     */

    public String earnWorkBonus() { //Mall
        this.workCount++;

        if (this.workCount >= 5) {
            int bonusMoney = 700;
            this.setMoney(this.getMoney() + bonusMoney);

            this.workCount = 0;
            return "OTAKU_BONUS_ACTIVATED";
        }

        return "OTAKU_PROGRESS_" + this.workCount;
    }


}
