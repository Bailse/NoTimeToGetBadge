package nttr.model;

import nttr.building.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Consumer;

/**
 * Game rules inspired by No Time to Relax (simplified single-player):
 * - Each day is a "turn" with limited seconds.
 * - Stress reduces next day's available seconds.
 * - Must eat each day (or lose time next day).
 * - Rent every 4 days, clothing check, random event cards.
 * - Bank protects money from "Crime Wave" event.
 */
public class GameState {

    public static final int TOTAL_DAYS = 14;
    private static final int TARGET_SCORE = 500;
    private final List<Consumer<GameOverResult>> gameOverListeners = new ArrayList<>();
    public static final int BASE_SECONDS_PER_DAY = 60;
    public static final int MIN_SECONDS_PER_DAY = 20;

    private final Player player;
    private final Inventory inventory;

    private final Map<String, Building> buildings = new LinkedHashMap<>();

    // location (used by GameView)
    private String currentLocation = "Home";

    private int day = 1;
    private int timeLeftSeconds = BASE_SECONDS_PER_DAY;
    private boolean running = false;
    private boolean gameOver = false;

    // economy / schedule rules
    private int rentDebt = 0;             // unpaid rent accumulates here
    private int rentBase = 40;            // rent amount every 4 days
    private int clothingDayCounter = 0;   // every 6 days -> clothes may "wear out"

    // log + listeners
    private final List<String> logs = new ArrayList<>();
    private final List<Consumer<String>> logListeners = new ArrayList<>();

    private final Random rng = new Random();

    public GameState(Player player) {
        this.player = player;
        this.inventory = new Inventory();

        buildings.put("Home", new Home());
        buildings.put("Office", new Office());
        buildings.put("University", new University());
        buildings.put("Gym", new Gym());
        buildings.put("Party", new Party());

        // extra buildings to feel more like NTTR
        buildings.put("Bank", new Bank());
        buildings.put("Market", new Market());
        buildings.put("Clothing", new ClothingStore());

        addLog("Welcome! Survive " + TOTAL_DAYS + " days. Eat, pay rent, manage stress.");
        startDay();
    }

    public static GameState defaultGame(Player player) {
        GameState gs = new GameState(player);

        // Starter items (feel like "shop upgrades")
        gs.inventory.equip(new VehicleItem("Old Bike", "/images/car.png", 1.05));
        gs.inventory.equip(new EducationItem("Notebook", "/images/book.png", 1.05));
        gs.inventory.equip(new HealthItem("Bandage", "/images/medkit.png", 1.05));
        gs.inventory.equip(new MoneyItem("Piggy Bank", "/images/wallet.png", 1.05));
        gs.inventory.applyBonuses(gs.player);

        gs.addLog("Starter items equipped (+5% each category).");
        return gs;
    }

    // ===== day lifecycle =====
    private void startDay() {
        // apply time budget from stress and food penalty
        int stress = player.getStress();
        int stressPenalty = (int) Math.round((stress / 100.0) * 20.0); // up to -20s
        int seconds = BASE_SECONDS_PER_DAY - stressPenalty;

        if (!player.isAteToday()) {
            // like NTTR: skipping food costs a big chunk next turn
            seconds = (int) Math.round(seconds * 0.67);
            addLog("You skipped food yesterday → time penalty today!");
        }

        if (seconds < MIN_SECONDS_PER_DAY) seconds = MIN_SECONDS_PER_DAY;
        timeLeftSeconds = seconds;

        // auto-eat from fridge if possible
        player.setAteToday(false);
        boolean ate = player.consumeMealIfAny();
        if (ate) {
            addLog("Auto-eat from fridge at home (meals left: " + player.getFridgeMeals() + ").");
        } else {
            addLog("No food at home. Consider going to Market/Fastfood (Market action).");
        }

        // clothes wear counter
        clothingDayCounter += 1;

        addLog("Day " + day + " starts. Time = " + timeLeftSeconds + "s, Stress=" + player.getStress() + ", Clothes=" + player.getClothingLevel().getLabel());
    }

    public void tickOneSecond() {
        if (!running || gameOver) return;

        timeLeftSeconds -= 1;
        if (timeLeftSeconds <= 0) {
            endDay(true);
            return;
        }

        // slow passive stress increase if you do nothing
        if (timeLeftSeconds % 15 == 0) {
            player.addStress(1);
        }
    }

    public void consumeTime(int seconds) {
        if (gameOver) return;
        if (seconds < 0) seconds = 0;
        timeLeftSeconds -= seconds;
        if (timeLeftSeconds <= 0) {
            endDay(true);
        }
    }

    public void endDay(boolean auto) {
        if (gameOver) return;

        addLog((auto ? "Time's up! " : "") + "Day " + day + " ended.");

        // rent every 4 days
        if (day % 4 == 0) {
            int due = rentBase + rentDebt;
            if (player.getMoney() >= due) {
                player.addMoney(-due);
                rentDebt = 0;
                addLog("Paid rent: -" + due + " (every 4 days).");
            } else {
                int paid = player.getMoney();
                player.addMoney(-paid);
                int remaining = due - paid;
                rentDebt = remaining + (int) Math.ceil(remaining * 0.15); // interest
                addLog("Couldn't pay rent. Paid -" + paid + ", debt now " + rentDebt + " (interest added).");
                player.addHappiness(-6);
                player.addStress(8);
            }
        }

        // clothing wear-out check every 6 days
        if (clothingDayCounter >= 6) {
            clothingDayCounter = 0;
            // 35% chance your clothes wear out one tier
            if (rng.nextDouble() < 0.35) {
                ClothingLevel cur = player.getClothingLevel();
                if (cur == ClothingLevel.BUSINESS) {
                    player.setClothingLevel(ClothingLevel.SEMI_CASUAL);
                } else if (cur == ClothingLevel.SEMI_CASUAL) {
                    player.setClothingLevel(ClothingLevel.CASUAL);
                }
                addLog("Event: Clothes worn out! Downgraded to " + player.getClothingLevel().getLabel() + ".");
                player.addHappiness(-3);
            }
        }

        // small recovery
        player.addHealth(2);
        player.addHappiness(1);
        player.addStress(-3);

        // random event card (one per day)
        applyRandomEvent();

        // game over checks
        if (player.getHealth() <= 0 || player.getHappiness() <= 0) {
            if (!gameOver) {
            gameOver = true;
            fireGameOver();
        }
            running = false;
            addLog("GAME OVER! You burned out. Final score = " + player.score());
            return;
        }

        day += 1;
        if (day > TOTAL_DAYS) {
            if (!gameOver) {
            gameOver = true;
            fireGameOver();
        }
            running = false;
            addLog("GAME OVER! Final score = " + player.score());
            return;
        }

        startDay();
    }

    private void applyRandomEvent() {
        int r = rng.nextInt(6);
        if (r == 0) {
            // Crime wave: steals cash (not bank)
            int cash = player.getMoney();
            int stolen = (int) Math.ceil(cash * 0.25);
            player.addMoney(-stolen);
            addLog("Event Card: Crime Wave! Cash stolen -" + stolen + " (Bank is safe).");
            player.addStress(6);
        } else if (r == 1) {
            player.addHealth(-8);
            player.addStress(6);
            addLog("Event Card: Got sick. Health -8, Stress +6.");
        } else if (r == 2) {
            player.addMoney(20);
            player.addHappiness(3);
            addLog("Event Card: Side gig success! Money +20, Happiness +3.");
        } else if (r == 3) {
            player.addSkill(6);
            player.addStress(3);
            addLog("Event Card: Free workshop! Skill +6, Stress +3.");
        } else if (r == 4) {
            player.addHappiness(8);
            player.addStress(-5);
            addLog("Event Card: Great hangout. Happiness +8, Stress -5.");
        } else {
            // lucky groceries
            player.addFridgeMeals(1);
            addLog("Event Card: Someone gave you food. +1 meal (fridge).");
        }
    }

    // ===== logging =====
    public void addLogListener(Consumer<String> listener) {
        logListeners.add(listener);
    }

    public void addLog(String text) {
        logs.add(text);
        for (Consumer<String> l : logListeners) {
            l.accept(text);
        }
    }

    public List<String> getLogView() {
        return List.copyOf(logs);
    }

    // ===== shop items =====
    public Item generateShopItem(ItemType type) {
        if (type == ItemType.VEHICLE) {
            return new VehicleItem("Electric Scooter", "/images/car.png", 1.15);
        }
        if (type == ItemType.EDUCATION) {
            return new EducationItem("Online Course", "/images/book.png", 1.15);
        }
        if (type == ItemType.HEALTH) {
            return new HealthItem("Protein Pack", "/images/medkit.png", 1.15);
        }
        return new MoneyItem("Emergency Fund", "/images/wallet.png", 1.15);
    }

    public Item generateRandomShopItem() {
        ItemType[] types = ItemType.values();
        ItemType t = types[rng.nextInt(types.length)];
        return generateShopItem(t);
    }

    // ===== getters/setters =====
    public Player getPlayer() { return player; }
    public Inventory getInventory() { return inventory; }
    public Map<String, Building> getBuildings() { return buildings; }

    public int getDay() { return day; }
    public int getTimeLeftSeconds() { return timeLeftSeconds; }

    public boolean isRunning() { return running; }
    public void setRunning(boolean newRunning) { running = newRunning; }

    public boolean isGameOver() { return gameOver; }

    public String getCurrentLocation() { return currentLocation; }
    public void setCurrentLocation(String name) { currentLocation = name; }

    public int getRentDebt() { return rentDebt; }

    public void addGameOverListener(Consumer<GameOverResult> listener) {
        gameOverListeners.add(listener);
    }

    private void fireGameOver() {
        GameOverResult r = new GameOverResult(getPlayer().getScore() >= TARGET_SCORE, getPlayer().getScore());
        for (Consumer<GameOverResult> l : gameOverListeners) {
            l.accept(r);
        }
    }

}
