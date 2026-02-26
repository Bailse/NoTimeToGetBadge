package Character;

import Item.Item;

public abstract class BasePlayer {

    private CharacterType characterType;
    private int stamina;
    private int money;
    private int education;
    private int health;
    private double moneyDiscount;      // เช่น 0.80
    private double educationMultiply;  // เช่น 1.10
    private int healthDecrease;        // ลด health เวลากิจกรรม
    private int staminaDecrease;       // ลด stamina เวลาเดิน
    private CharacterType type;
    private String imagePath;
    private Item itemManager;

    public BasePlayer(int stamina, int money, int education, int health,
                         double moneyDiscount, double educationMultiply,
                         int healthDecrease, int staminaDecrease) {

        setStamina(stamina);
        setMoney(money);
        setEducation(education);
        setHealth(health);
        setMoneyDiscount(moneyDiscount);
        setEducationMultiply(educationMultiply);
        setHealthDecrease(healthDecrease);
        setStaminaDecrease(staminaDecrease);
        this.itemManager = new Item();
    }

    public BasePlayer() {
    }

    // ===== Actions =====

    public void walk() {
        setStamina(getStamina() - getStaminaDecrease());
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

}
