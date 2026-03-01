package Character;

import Item.Item;
import Logic.GameSession;
import Screen.BuildingScreen.Chula.ChulaPopup;
import Screen.ScreenManager;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public abstract class BasePlayer {

    private CharacterType characterType;
    private int stamina;
    private int money;
    private int education;
    private int health;
    private int happiness;
    private double moneyDiscount;      // เช่น 0.80
    private double educationMultiply;  // เช่น 1.10
    private int healthDecrease;        // ลด health เวลากิจกรรม
    private int staminaDecrease;       // ลด stamina เวลาเดิน
    private CharacterType type;
    private String imagePath;
    private Item itemManager;
    private Image imgDown;
    private Image imgLeft;
    private Image imgRight;
    private Image imgUp;


    public BasePlayer(int stamina, int money, int education, int health,
                         double moneyDiscount, double educationMultiply,
                         int healthDecrease, int staminaDecrease , int happiness) {

        setStamina(stamina);
        setMoney(money);
        setEducation(education);
        setHealth(health);
        setMoneyDiscount(moneyDiscount);
        setEducationMultiply(educationMultiply);
        setHealthDecrease(healthDecrease);
        setStaminaDecrease(staminaDecrease);
        setHappiness(happiness);
        
        this.itemManager = new Item();
    }

    public BasePlayer() {
    }

    // ===== Actions =====

    public void walk() {
        int cost = getStaminaDecrease();

        if (getItemManager().getInventory().get(2) != null) {
            cost = (int)(getStaminaDecrease()*0.5);
        }

        setStamina(getStamina() - cost);
    }

    public void buyItem(int price) {
        int finalPrice = (int)(price * getMoneyDiscount());
        setMoney(getMoney() - finalPrice);
    }

    // ==================== STAMINA ====================
// เงื่อนไข: No Limit (แต่ไม่ให้ติดลบ)
    public int getStamina() {
        return stamina;
    }
    public void setStamina(int staminaValue) {
        this.stamina = Math.max(0, staminaValue);
    }

    public int getStaminaDecrease() {
        return staminaDecrease;
    }
    public void setStaminaDecrease(int value) {
        this.staminaDecrease = value;
    }

    // ==================== MONEY ====================
// เงื่อนไข: No Limit (แต่ไม่ให้ติดลบ)
    public int getMoney() {
        return money;
    }
    public void setMoney(int moneyValue) {
        this.money = Math.max(0, moneyValue);
    }

    public double getMoneyDiscount() {
        return moneyDiscount;
    }
    public void setMoneyDiscount(double value) {
        this.moneyDiscount = value;
    }

    // ==================== EDUCATION ====================
// เงื่อนไข: Cap 200
    public int getEducation() {
        return education;
    }
    public void setEducation(int educationValue) {
        // จำกัดช่วงให้อยู่ระหว่าง 0 ถึง 200
        this.education = Math.max(0, Math.min(educationValue, 200));
    }

    public double getEducationMultiply() {
        return educationMultiply;
    }
    public void setEducationMultiply(double value) {
        this.educationMultiply = value;
    }

    // ==================== HEALTH ====================
// เงื่อนไข: Cap 200
    public int getHealth() {
        return health;
    }
    public void setHealth(int healthValue) {
        // จำกัดช่วงให้อยู่ระหว่าง 0 ถึง 200
        this.health = Math.max(0, Math.min(healthValue, 200));
    }

    public int getHealthDecrease() {
        return healthDecrease;
    }
    public void setHealthDecrease(int value) {
        this.healthDecrease = value;
    }

    // =================== HAPPINESS ====================
// เงื่อนไข: Cap 500
    public int getHappiness() {
        return happiness;
    }
    public void setHappiness(int happinessValue) {
        // จำกัดช่วงให้อยู่ระหว่าง 0 ถึง 500
        this.happiness = Math.max(0, Math.min(happinessValue, 500));
    }


    // ==================== PLAYER_TYPE ================

    public CharacterType getType() {
        return type;
    }

    public void setType(CharacterType type) {
        this.type = type;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Item getItemManager() {
        return itemManager;
    }

    // ในไฟล์ Player.java
    private int maxUnlockedLevel = 0; // 0=HighSchool, 1=Bachelor, 2=Master, 3=Doctorate

    public int getMaxUnlockedLevel() { return maxUnlockedLevel; }
    public void setMaxUnlockedLevel(int level) { this.maxUnlockedLevel = level; }

    public Image getImgDown() {
        return imgDown;
    }

    public void setImgDown(Image imgDown) {
        this.imgDown = imgDown;
    }

    public Image getImgLeft() {
        return imgLeft;
    }

    public void setImgLeft(Image imgLeft) {
        this.imgLeft = imgLeft;
    }

    public Image getImgRight() {
        return imgRight;
    }

    public void setImgRight(Image imgRight) {
        this.imgRight = imgRight;
    }

    public Image getImgUp() {
        return imgUp;
    }

    public void setImgUp(Image imgUp) {
        this.imgUp = imgUp;
    }

    public boolean work(int staminaCost, int moneyGain) {
        if (this.stamina >= staminaCost) {
            this.stamina -= staminaCost;
            this.money += moneyGain;
            System.out.println("Working... Money: +" + moneyGain + " | Stamina: -" + staminaCost);
            return true; // ทำงานสำเร็จ
        } else {
            System.out.println("Not enough stamina to work!");
            return false; // พลังงานไม่พอ
        }
    }

    public String study(int eduGain, int staminaCost) {
        // 1. เช็คว่า Education เต็มแล้วหรือยัง
        if (this.getEducation() >= 200) {
            return "EDU_MAX"; // ส่งสถานะบอกว่าเรียนเต็มแล้ว
        }

        // 2. เช็คว่า Stamina พอหรือไม่
        if (this.stamina < staminaCost) {
            return "NO_STAMINA"; // ส่งสถานะบอกว่าพลังงานไม่พอ
        }

        // 3. ถ้าผ่านเงื่อนไข ให้ทำการเรียน (Logic การคำนวณ)
        this.stamina -= staminaCost;
        this.setEducation(this.getEducation() + eduGain);

        if (this.getEducation() > 200) {
            this.setEducation(200);
        }

        this.health -= 5;
        this.happiness -= 5;

        // ป้องกันค่าติดลบ
        if (this.health < 0) this.health = 0;
        if (this.happiness < 0) this.happiness = 0;

        System.out.println("Studying... Edu +" + eduGain + " | Stamina -" + staminaCost);
        return "SUCCESS"; // ส่งสถานะบอกว่าเรียนสำเร็จ
    }

}
