package Character;

import Item.Item;
import javafx.scene.image.Image;

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

    public void study(int baseEducationGain) {
        int gained = (int)(baseEducationGain * getEducationMultiply());
        setEducation(getEducation() + gained);
        setHealth(getHealth() - getHealthDecrease());
    }

    public void buyItem(int price) {
        int finalPrice = (int)(price * getMoneyDiscount());
        setMoney(getMoney() - finalPrice);
    }

    // ===== Getter / Setter =====
    // ==================== STAMINA ====================
    public int getStamina() {
        return stamina;
    }
    public void setStamina(int staminaValue) {
        stamina = Math.max(staminaValue,0);
    }

    public int getStaminaDecrease() {
        return staminaDecrease;
    }
    public void setStaminaDecrease(int value) {
        staminaDecrease = value;
    }

    // ==================== MONEY ====================
    public int getMoney() {
        return money;
    }
    public void setMoney(int moneyValue) {
        money = Math.max(moneyValue,0);
    }

    public double getMoneyDiscount() {
        return moneyDiscount;
    }
    public void setMoneyDiscount(double value) {
        moneyDiscount = value;
    }

    // ==================== EDUCATION ====================
    public int getEducation() {
        return education;
    }
    public void setEducation(int educationValue) {
        education = Math.max(educationValue,0);
    }

    public double getEducationMultiply() {
        return educationMultiply;
    }
    public void setEducationMultiply(double value) {
        educationMultiply = value;
    }

    // ==================== HEALTH ====================
    public int getHealth() {
        return health;
    }
    public void setHealth(int healthValue) {
        health = Math.max(healthValue,0);
    }

    public int getHealthDecrease() {
        return healthDecrease;
    }
    public void setHealthDecrease(int value) {
        healthDecrease = value;
    }

    // =================== Happiness ====================
    public int getHappiness() {
        return happiness;
    }
    public void setHappiness(int happiness) {
        this.happiness = happiness;
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
}
