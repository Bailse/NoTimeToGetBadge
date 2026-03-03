package Character;

import javafx.scene.image.Image;

import java.util.Objects;

/**
 * Nerd class extends from BasePlayer
 * Nerd that have special interact with Chula building
 */
public class Nerd extends BasePlayer {
    /**
     * constructor that initialize value for Nerd
     */
    public Nerd() {
        super(
                200,
                1000,
                30,
                0,
                2,
                4,
                25
        );
        setImagePath("/Avatar/Nerd/NerdPic.png");
        setImgUp(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Avatar/Nerd/Nerd_up.png"))));
        setImgDown(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Avatar/Nerd/Nerd_down.png"))));
        setImgRight(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Avatar/Nerd/Nerd_right.png"))));
        setImgLeft(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Avatar/Nerd/Nerd_left.png"))));


    }

    private int studyCount = 0;
    /**
     * Counting study if work in Chula and is equal 5 this Nerd get bonus Education and reset WorkCount to 0
     */
    public String earnStudyBonus() {
        this.studyCount++;

        if (this.studyCount >= 5) {
            int bonusEdu = 20;
            this.setEducation(this.getEducation() + bonusEdu);
            this.studyCount = 0;
            return "BONUS_ACTIVATED";
        }

        return "PROGRESS_" + this.studyCount;
    }

}
