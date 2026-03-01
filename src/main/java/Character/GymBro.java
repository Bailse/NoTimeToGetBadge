package Character;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

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
                5,
                100
        );
        setImagePath("/Annie_Zheng.jpg");
        setImgUp(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Avatar/Gymbro/Gymbro_up.png"))));
        setImgDown(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Avatar/Gymbro/Gymbro_down.png"))));
        setImgRight(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Avatar/Gymbro/Gymbro_right.png"))));
        setImgLeft(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Avatar/Gymbro/Gymbro_left.png"))));

    }

    private int workCount = 0;

    public String earnWorkBonus() { //Gym
        this.workCount++;

        if (this.workCount >= 5) {
            int bonusMoney = 20000;
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
