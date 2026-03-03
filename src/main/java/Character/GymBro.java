package Character;

import javafx.scene.image.Image;

import java.util.Objects;

public class GymBro extends  BasePlayer{
    public GymBro() {
        super(
                200,
                500,
                0,
                25,
                1.0,
                1.0,
                1,
                2,
                50
        );
        setImagePath("/Avatar/Gymbro/GymBroPic.png");
        setImgUp(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Avatar/Gymbro/Gymbro_up.png"))));
        setImgDown(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Avatar/Gymbro/Gymbro_down.png"))));
        setImgRight(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Avatar/Gymbro/Gymbro_right.png"))));
        setImgLeft(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Avatar/Gymbro/Gymbro_left.png"))));

    }

    private int workCount = 0;

    public String earnWorkBonus() { //Gym
        this.workCount++;

        if (this.workCount >= 5) {
            int bonusMoney = 500;
            this.setMoney(this.getMoney() + bonusMoney);

            this.workCount = 0; // รีเซ็ตแต้ม
            return "GYM_BONUS_ACTIVATED"; // สถานะเมื่อได้โบนัสใหญ่
        }

        return "GYM_PROGRESS_" + this.workCount; // สถานะสะสมแต้มปกติ
    }

    // เพิ่ม Getter ไว้ดึงค่าไปแสดงผลใน UI
    public int getWorkCount() {
        return workCount;
    }
}
