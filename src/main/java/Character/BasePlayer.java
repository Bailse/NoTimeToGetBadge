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

/**
 * this class is the baseClass for PlayerType like Gymbro , Nerd , NormalGuy , Otaku
 * that have same method to use with.
 */


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

    /**
     * constructor that initialize value for everyPlayer by using BasePlayer
     */


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



    // ===== Actions =====

    /**
     * method to use for walk in this game by using stamina to walk
     */

    public void walk() {
        int cost = getStaminaDecrease();

        if (getItemManager().getInventory().get(2) != null) {
            cost = (int)(getStaminaDecrease()*0.5);
        }

        setStamina(getStamina() - cost);
    }



    // ==================== STAMINA ====================

    /**
     * get Stamina value
     */

    public int getStamina() {
        return stamina;
    }

    /**
     * set Stamina value if stamina less than 0 set this to 0
     */

    public void setStamina(int staminaValue) {
        this.stamina = Math.max(0, staminaValue);
    }

    /**
     * get StaminaDecrease value
     */

    public int getStaminaDecrease() {
        return staminaDecrease;
    }

    /**
     * set StaminaDecrease value if stamina decrease less than 0 set this to 0
     */

    public void setStaminaDecrease(int value) {
        staminaDecrease = Math.max(value,0);
    }

    // ==================== MONEY ====================

    /**
     * get PlayerMoney value
     */

    public int getMoney() {
        return money;
    }

    /**
     * set StaminaDecrease value if PlayerMoney decrease less than 0 set this to 0
     */

    public void setMoney(int moneyValue) {
        this.money = Math.max(0, moneyValue);
    }

    // ==================== EDUCATION ====================

    /**
     * get Education value
     */

    public int getEducation() {
        return education;
    }

    /**
     * set Education value if education more than 200 set this to 200
     */

    public void setEducation(int educationValue) {
        // จำกัดช่วงให้อยู่ระหว่าง 0 ถึง 200
        this.education = Math.max(0, Math.min(educationValue, 200));
    }

    // ==================== HEALTH ====================
    /**
     * get Health value
     */
    public int getHealth() {
        return health;
    }

    /**
     * set Health value if Health is more than 200 set this to 200 and if Health is less than 0 set this to 0
     */

    public void setHealth(int healthValue) {
        // จำกัดช่วงให้อยู่ระหว่าง 0 ถึง 200
        this.health = Math.max(0, Math.min(healthValue, 200));
    }

    /**
     * get HealthDecrease value
     */

    public int getHealthDecrease() {
        return healthDecrease;
    }

    /**
     * set HealthDecrease value if value less than 0 set this to 0
     */


    public void setHealthDecrease(int value) {
        healthDecrease = Math.max(value,0);
    }

    // =================== HAPPINESS ====================

    /**
     * get Happiness value
     */

    public int getHappiness() {
        return happiness;
    }

    /**
     * set Happiness value if happiness more than 500 set this to 500 and if happiness less than 0 set this to 0
     */

    public void setHappiness(int happinessValue) {
        this.happiness = Math.max(0, Math.min(happinessValue, 500));
    }


    // ==================== PLAYER_TYPE ================

    /**
     * get characterType
     */

    public CharacterType getType() {
        return type;
    }
    /**
     * set characterType
     */

    public void setType(CharacterType type) {
        this.type = type;
    }

    /**
     * get ImagePath
     */

    public String getImagePath() {
        return imagePath;
    }

    /**
     * set ImagePath
     */

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    /**
     * get itemManager
     */

    public Item getItemManager() {
        return itemManager;
    }

    /**
     * get MaxUnlockedLevel (education)
     */

    public int getMaxUnlockedLevel() { return maxUnlockedLevel; }

    /**
     * set MaxUnlockedLevel (education) if MaxUnlockedLevel less than 0 set this to 0
     */

    public void setMaxUnlockedLevel(int level) { this.maxUnlockedLevel = Math.max(level,0); }

    /**
     * get ImgDown (for action move down)
     */

    public Image getImgDown() {
        return imgDown;
    }

    /**
     * set ImgDown (for action move down)
     */

    public void setImgDown(Image imgDown) {
        this.imgDown = imgDown;
    }

    /**
     * get ImgLeft (for action move left)
     */

    public Image getImgLeft() {
        return imgLeft;
    }

    /**
     * set ImgLeft (for action move left)
     */

    public void setImgLeft(Image imgLeft) {
        this.imgLeft = imgLeft;
    }

    /**
     * get ImgRight (for action move right)
     */

    public Image getImgRight() {
        return imgRight;
    }

    /**
     * set ImgRight (for action move right)
     */

    public void setImgRight(Image imgRight) {
        this.imgRight = imgRight;
    }

    /**
     * get ImgRight (for action move up)
     */

    public Image getImgUp() {
        return imgUp;
    }

    /**
     * set ImgRight (for action move up)
     */

    public void setImgUp(Image imgUp) {
        this.imgUp = imgUp;
    }

    /**
     * work method for decrease stamina when Player work by using staminaCost , increase money player by using moneyGain and check stamina condition
     * if less than staminaCost player cant do work
     */

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

    /**
     * study method for decrease stamina , health , happiness but increase education stat
     * if less than staminaCost player cant do work
     */

    public String study(int eduGain, int staminaCost) {

        if (this.getEducation() >= 200) {
            return "EDU_MAX";
        }


        if (getStamina() < staminaCost) {
            return "NO_STAMINA";
        }

        setStamina(getStamina()-staminaCost);
        this.setEducation(this.getEducation() + eduGain);

        setHealth(getHealth()-5);
        setHappiness(getHappiness()-5);




        System.out.println("Studying... Edu +" + eduGain + " | Stamina -" + staminaCost);
        return "SUCCESS";
    }

}
