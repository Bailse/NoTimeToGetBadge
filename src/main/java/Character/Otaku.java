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
                5,
                100
        );
        setImagePath("/Lily.jpg");
        setImgUp(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Avatar/Otaku/Otaku_up.png"))));
        setImgDown(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Avatar/Otaku/Otaku_down.png"))));
        setImgRight(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Avatar/Otaku/Otaku_right.png"))));
        setImgLeft(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Avatar/Otaku/Otaku_left.png"))));

    }

    private int workCount = 0;

    public String earnWorkBonus() { //Mall
        this.workCount++;

        if (this.workCount >= 5) {
            int bonusMoney = 20000;
            this.setMoney(this.getMoney() + bonusMoney);

            this.workCount = 0; // รีเซ็ตแต้ม
            return "OTAKU_BONUS_ACTIVATED"; // สถานะเมื่อได้โบนัสใหญ่
        }

        return "OTAKU_PROGRESS_" + this.workCount; // สถานะสะสมแต้มปกติ
    }

    public int getWorkCount() {
        return workCount;
    }
}
