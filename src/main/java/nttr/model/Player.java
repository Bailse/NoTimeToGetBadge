package nttr.model;

/**
 * Player stats and multipliers.
 * Rules:
 * - Money never below 0.
 * - Health/Skill/Happiness/Stress clamped to [0..100].
 */
public class Player {

    private String name;
    private String avatarPath;

    private int money;
    private int health;
    private int skill;
    private int happiness;

    private int stress;        // new: affects next-day time
    private int workHours;     // new: promotions
    private int bankBalance;   // new: safe money (crime wave doesn't steal)
    private int fridgeMeals;   // new: meals stored at home (0..4)
    private boolean ateToday;  // new: if false, next day time penalty
    private ClothingLevel clothingLevel; // new: requirements for some actions

    // Multipliers from items
    private double workMoneyMultiplier;
    private double studySkillMultiplier;
    private double exerciseHealthMultiplier;
    private double relaxHappinessMultiplier;
    private double travelTimeMultiplier;

    public Player(String name, String avatarPath) {
        this.name = name;
        this.avatarPath = avatarPath;

        money = 50;
        bankBalance = 0;

        health = 70;
        happiness = 70;
        skill = 25;

        stress = 10;
        workHours = 0;

        fridgeMeals = 0;
        ateToday = false;

        clothingLevel = ClothingLevel.CASUAL;

        resetBonuses();
    }

    // ===== clamps =====
    private int clamp01To100(int v) {
        if (v < 0) return 0;
        if (v > 100) return 100;
        return v;
    }

    private int clampMoney(int v) {
        if (v < 0) return 0;
        return v;
    }

    private int clampMeals(int v) {
        if (v < 0) return 0;
        if (v > 4) return 4;
        return v;
    }

    // ===== delta methods (used by buildings/state) =====
    public void addMoney(int delta) { setMoney(money + delta); }
    public void addHealth(int delta) { setHealth(health + delta); }
    public void addSkill(int delta) { setSkill(skill + delta); }
    public void addHappiness(int delta) { setHappiness(happiness + delta); }
    public void addStress(int delta) { setStress(stress + delta); }
    public void addWorkHours(int delta) { setWorkHours(workHours + delta); }

    public void depositToBank(int amount) {
        if (amount <= 0) return;
        int actual = Math.min(amount, money);
        setMoney(money - actual);
        bankBalance = bankBalance + actual;
    }

    public void withdrawFromBank(int amount) {
        if (amount <= 0) return;
        int actual = Math.min(amount, bankBalance);
        bankBalance = bankBalance - actual;
        setMoney(money + actual);
    }

    public void addFridgeMeals(int meals) { fridgeMeals = clampMeals(fridgeMeals + meals); }

    public boolean consumeMealIfAny() {
        if (fridgeMeals <= 0) return false;
        fridgeMeals = fridgeMeals - 1;
        ateToday = true;
        return true;
    }

    public void setAteToday(boolean v) { ateToday = v; }

    // ===== score =====
    public int score() {
        return money + bankBalance + skill * 2 + health + happiness;
    }

    // ===== bonuses =====
    public void resetBonuses() {
        workMoneyMultiplier = 1.0;
        studySkillMultiplier = 1.0;
        exerciseHealthMultiplier = 1.0;
        relaxHappinessMultiplier = 1.0;
        travelTimeMultiplier = 1.0;
    }

    // ===== getters/setters =====
    public String getName() { return name; }
    public void setName(String newName) { name = newName; }

    public String getAvatarPath() { return avatarPath; }
    public void setAvatarPath(String newPath) { avatarPath = newPath; }

    public int getMoney() { return money; }
    public void setMoney(int v) { money = clampMoney(v); }

    public int getHealth() { return health; }
    public void setHealth(int v) { health = clamp01To100(v); }

    public int getSkill() { return skill; }
    public void setSkill(int v) { skill = clamp01To100(v); }

    public int getHappiness() { return happiness; }
    public void setHappiness(int v) { happiness = clamp01To100(v); }

    public int getStress() { return stress; }
    public void setStress(int v) { stress = clamp01To100(v); }

    public int getWorkHours() { return workHours; }
    public void setWorkHours(int v) { 
        if (v < 0) v = 0;
        workHours = v; 
    }

    public int getBankBalance() { return bankBalance; }
    public void setBankBalance(int v) { 
        if (v < 0) v = 0;
        bankBalance = v; 
    }

    public int getFridgeMeals() { return fridgeMeals; }

    public boolean isAteToday() { return ateToday; }

    public ClothingLevel getClothingLevel() { return clothingLevel; }
    public void setClothingLevel(ClothingLevel level) { clothingLevel = level; }

    public double getWorkMoneyMultiplier() { return workMoneyMultiplier; }
    public void setWorkMoneyMultiplier(double v) { workMoneyMultiplier = v; }

    public double getStudySkillMultiplier() { return studySkillMultiplier; }
    public void setStudySkillMultiplier(double v) { studySkillMultiplier = v; }

    public double getExerciseHealthMultiplier() { return exerciseHealthMultiplier; }
    public void setExerciseHealthMultiplier(double v) { exerciseHealthMultiplier = v; }

    public double getRelaxHappinessMultiplier() { return relaxHappinessMultiplier; }
    public void setRelaxHappinessMultiplier(double v) { relaxHappinessMultiplier = v; }

    public double getTravelTimeMultiplier() { return travelTimeMultiplier; }
    public void setTravelTimeMultiplier(double v) { travelTimeMultiplier = v; }

public int getScore() {
    // NTTR-like score: cash + bank + education*2 + health + happiness
    return getMoney() + getBankBalance() + getSkill() * 2 + getHealth() + getHappiness();
}

}
