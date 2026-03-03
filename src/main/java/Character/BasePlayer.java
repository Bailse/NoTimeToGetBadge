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
    private int healthDecrease;
    private int staminaDecrease;
    private CharacterType type;
    private String imagePath;
    private Item itemManager;
    private Image imgDown;
    private Image imgLeft;
    private Image imgRight;
    private Image imgUp;
    private int maxUnlockedLevel = 0;


    public BasePlayer(int stamina, int money, int education, int health,
                         int healthDecrease, int staminaDecrease , int happiness) {

        setStamina(stamina);
        setMoney(money);
        setEducation(education);
        setHealth(health);
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



    // ==================== STAMINA ====================

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
        staminaDecrease = Math.max(value,0);
    }

    // ==================== MONEY ====================

    public int getMoney() {
        return money;
    }
    public void setMoney(int moneyValue) {
        this.money = Math.max(0, moneyValue);
    }

    // ==================== EDUCATION ====================

    public int getEducation() {
        return education;
    }
    public void setEducation(int educationValue) {
        // จำกัดช่วงให้อยู่ระหว่าง 0 ถึง 200
        this.education = Math.max(0, Math.min(educationValue, 200));
    }

    // ==================== HEALTH ====================

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
        healthDecrease = Math.max(value,0);
    }

    // =================== HAPPINESS ====================

    public int getHappiness() {
        return happiness;
    }
    public void setHappiness(int happinessValue) {
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



    public int getMaxUnlockedLevel() { return maxUnlockedLevel; }
    public void setMaxUnlockedLevel(int level) { this.maxUnlockedLevel = Math.max(level,0); }

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
            return true;
        } else {
            System.out.println("Not enough stamina to work!");
            return false;
        }
    }

    public String study(int eduGain, int staminaCost) {

        if (this.getEducation() >= 200) {
            return "EDU_MAX";
        }


        if (getStamina() < staminaCost) {
            return "NO_STAMINA";
        }


        setStamina(getStamina()-staminaCost);
        this.setEducation(this.getEducation() + eduGain);

        if (this.getEducation() > 200) {
            this.setEducation(200);
        }



        setHealth(getHealth()-5);
        setHappiness(getHappiness()-5);




        System.out.println("Studying... Edu +" + eduGain + " | Stamina -" + staminaCost);
        return "SUCCESS";
    }

}
