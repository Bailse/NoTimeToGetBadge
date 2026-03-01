package Character;

import javafx.scene.image.Image;

import java.util.Objects;

public class NormalGuy extends BasePlayer{
    public NormalGuy() {
        super(
                200,
                5000,
                5,
                90,
                1.0,
                1.0,
                3,
                5,
                100
        );
        setImgUp(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Avatar/Normal/Normal_up.png"))));
        setImgDown(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Avatar/Normal/Normal_down.png"))));
        setImgRight(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Avatar/Normal/Normal_right.png"))));
        setImgLeft(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Avatar/Normal/Normal_left.png"))));

        setImagePath("/deku_nerd.jpg");

    }

    @Override
    public boolean work(int staminaCost, int moneyGain) {
        // 1. เรียกใช้ Logic ของ BasePlayer แต่ส่งค่าที่ "ดีกว่า" เข้าไป
        // เช่น ถ้ากดทำงาน 10 หน่วย NormalGuy จะเสียแค่ 9 และได้เงินเพิ่ม 100
        boolean isSuccess = super.work(staminaCost - 1, moneyGain + 100);

        // 2. ส่งคืนผลลัพธ์ที่ได้จากคลาสแม่กลับไปให้ Popup
        // ถ้า super.work คืนค่า true (พลังพอ) ตรงนี้ก็จะส่ง true กลับไป
        return isSuccess;
    }

    private int workCount = 0;

    public String earnWorkBonus() { //Restuarant
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
