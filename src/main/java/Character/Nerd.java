package Character;

import javafx.scene.image.Image;

import java.util.Objects;

public class Nerd extends BasePlayer {
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
