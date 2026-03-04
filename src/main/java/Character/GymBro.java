package Character;

import javafx.scene.image.Image;

import java.util.Objects;

/**
 * Gymbro class extends from BasePlayer
 * Gymbro that have special interact with gym building
 */

public class GymBro extends  BasePlayer{

    /**
     * constructor that initialize value for GymBro
     */

    public GymBro() {
        super(
                200,
                500,
                0,
                25,
                1,
                2,
                50
        );
        setImagePath("/Avatar/Gymbro/GymbroPic.png");
        setImgUp(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Avatar/Gymbro/Gymbro_up.png"))));
        setImgDown(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Avatar/Gymbro/Gymbro_down.png"))));
        setImgRight(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Avatar/Gymbro/Gymbro_right.png"))));
        setImgLeft(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Avatar/Gymbro/Gymbro_left.png"))));

    }

    private int workCount = 0;

    /**
     * Counting work if work in Gym and is equal 5 this Gymbro get bonus money and reset WorkCount to 0
     */

    public String earnWorkBonus() { //Gym
        this.workCount++;

        if (this.workCount >= 5) {
            int bonusMoney = 500;
            this.setMoney(this.getMoney() + bonusMoney);

            this.workCount = 0;
            return "GYM_BONUS_ACTIVATED";
        }

        return "GYM_PROGRESS_" + this.workCount;
    }

}
