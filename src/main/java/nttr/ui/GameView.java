package nttr.ui;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import nttr.ui.GameOverPopup;
import javafx.util.Duration;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.scene.effect.DropShadow;
import javafx.scene.Group;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.util.Duration;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import nttr.building.Building;
import nttr.model.*;
import nttr.ui.map.MapGraph;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * In-game UI inspired by No Time To Relax:
 * - Map with PATHS (graph). Avatar walks along routed waypoints.
 * - Background timer thread (GameTimer) tick every 1s.
 * - Stress + Food + Clothes + Bank shown in HUD.
 * - Inventory 4 slots with unequip + shop random item.
 */
public class GameView {

    private final BorderPane root = new BorderPane();

    // Fullscreen overlays (game over, etc.)
    private final StackPane host = new StackPane(root);


    private final GameState gameState;
    private final Player player;

    private final Pane mapPane = new Pane();
    private final Group world = new Group();
    private final Group camera = new Group(world);

    private static final double BASE_MAP_W = 760;
    private static final double BASE_MAP_H = 470;


    private final Node avatarNode;
    private double avatarX;
    private double avatarY;

    private List<MapGraph.Node> route = List.of();
    private int routeIndex = 0;
    private boolean moving = false;
    private String routeGoal = null;

    private final MapGraph graph = new MapGraph();

    // Top HUD
    private final Label dayLabel = new Label();
    private final Label timeLabel = new Label();
    private final Label scoreLabel = new Label();
    private final Button startPauseBtn = new Button("Start");
    private final Button endDayBtn = new Button("End Day");

    // Stats
    private final ProgressBar moneyBar = new ProgressBar();
    private final ProgressBar healthBar = new ProgressBar();
    private final ProgressBar skillBar = new ProgressBar();
    private final ProgressBar happinessBar = new ProgressBar();
    private final ProgressBar stressBar = new ProgressBar();

    private final Label moneyLabel = new Label();
    private final Label healthLabel = new Label();
    private final Label skillLabel = new Label();
    private final Label happinessLabel = new Label();
    private final Label stressLabel = new Label();
    private final Label bankLabel = new Label();
    private final Label foodLabel = new Label();
    private final Label clothesLabel = new Label();
    private final Label rentLabel = new Label();

    // Action area
    private final Button actionBtn = new Button("Do Action (at location)");
    private final Label locationLabel = new Label();

    // Inventory UI
    private final VBox invBox = new VBox(10);
    private final Label shopItemLabel = new Label();
    private Item shopItem;

    // Log
    private final TextArea logArea = new TextArea();
    private final TextArea shopDescArea = new TextArea();

    // Threads/animations
    private final GameTimer timerThread;
    private final AnimationTimer mover;

    public GameView(GameState gameState) {
        this.gameState = gameState;
        this.player = gameState.getPlayer();

        root.setStyle("-fx-background-color: #161616; -fx-font-family: 'Segoe UI';");

        // ===== Top bar =====
        HBox top = new HBox(12, dayLabel, timeLabel, scoreLabel, spacer(), startPauseBtn, endDayBtn);
        top.setAlignment(Pos.CENTER_LEFT);
        top.setPadding(new Insets(10));
        top.setStyle("-fx-background-color: rgba(255,255,255,0.04);");
        styleTopLabel(dayLabel);
        styleTopLabel(timeLabel);
        styleTopLabel(scoreLabel);

        startPauseBtn.setOnAction(e -> toggleRunning());
        endDayBtn.setOnAction(e -> {
            gameState.endDay(false);
            refreshAll();
        });

        root.setTop(top);

        // ===== Left panel (stats) =====
        VBox left = new VBox(10,
                statRow("Money", moneyBar, moneyLabel),
                statRow("Health", healthBar, healthLabel),
                statRow("Skill", skillBar, skillLabel),
                statRow("Happiness", happinessBar, happinessLabel),
                statRow("Stress", stressBar, stressLabel),
                miniInfo("Bank", bankLabel),
                miniInfo("Food (fridge)", foodLabel),
                miniInfo("Clothes", clothesLabel),
                miniInfo("Rent debt", rentLabel)
        );
        left.setPadding(new Insets(10));
        left.setPrefWidth(270);
        left.setStyle("-fx-background-color: rgba(255,255,255,0.04);");
        left.getStyleClass().add("panel");
        left.setMinWidth(260);
        left.setPrefWidth(260);
        left.setMaxWidth(260);
        root.setLeft(left);

        // ===== Right panel (inventory + shop) =====
        invBox.setPadding(new Insets(10));
        invBox.setPrefWidth(360);
        invBox.setStyle("-fx-background-color: rgba(255,255,255,0.04);");
        Label invTitle = new Label("Inventory (4 slots)");
        invTitle.setStyle("-fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: 800;");

        Button rollShop = new Button("Roll Shop Item");
        Button buyEquip = new Button("Buy & Equip (cost 30)");
        shopItemLabel.setStyle("-fx-text-fill: #d8d8d8;");

        rollShop.setOnAction(e -> {
            shopItem = gameState.generateRandomShopItem();
            shopItemLabel.setText("Shop: " + shopItem.getName() + " (" + shopItem.getType() + ")");
            shopDescArea.setText(shopItem.getDescription());
        });

        buyEquip.setOnAction(e -> {
            if (shopItem == null) return;
            if (player.getMoney() < 30) {
                gameState.addLog("Shop: Not enough money (need 30).");
                refreshAll();
                return;
            }
            player.addMoney(-30);
            gameState.getInventory().equip(shopItem);
            gameState.getInventory().applyBonuses(player);
            gameState.addLog("Equipped: " + shopItem.getName() + " into slot " + Inventory.slotLabel(Inventory.slotIndex(shopItem.getType())));
            shopItem = null;
            shopItemLabel.setText("Shop: (empty) Roll again.");
            refreshInventory();
            refreshAll();
        });

        invBox.getChildren().addAll(invTitle, rollShop, shopItemLabel, shopDescArea, buyEquip, new Separator());
        invBox.setMinWidth(360);
        invBox.setPrefWidth(360);
        invBox.setMaxWidth(360);
        root.setRight(invBox);
        refreshInventory();

        // ===== Center map =====
        VBox center = new VBox(10);
        center.setPadding(new Insets(10));

        mapPane.setPrefSize(560, 470);
        mapPane.setMinSize(520, 420);
        mapPane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        // Put everything into 'world' at BASE coordinates, then scale to fit available mapPane size.
        if (!mapPane.getChildren().contains(world)) {
            mapPane.getChildren().add(camera);

        // Clip viewport for clean camera edges
        Rectangle viewportClip = new Rectangle();
        viewportClip.widthProperty().bind(mapPane.widthProperty());
        viewportClip.heightProperty().bind(mapPane.heightProperty());
        mapPane.setClip(viewportClip);

        // center world properly inside mapPane
        world.layoutXProperty().bind(mapPane.widthProperty().subtract(BASE_MAP_W).divide(2));
        world.layoutYProperty().bind(mapPane.heightProperty().subtract(BASE_MAP_H).divide(2));
        }
        world.scaleXProperty().bind(mapPane.widthProperty().divide(BASE_MAP_W));
        world.scaleYProperty().bind(mapPane.heightProperty().divide(BASE_MAP_H));
        mapPane.setStyle("-fx-background-color: rgba(255,255,255,0.04); -fx-background-radius: 16; -fx-border-radius: 16; -fx-border-color: rgba(255,255,255,0.12);");

        world.getChildren().add(tryMapBackground());

        buildGraph();
        drawPaths();
        placeBuildings();

        // avatar at Home node
        MapGraph.Node home = graph.getNode("Home");
        avatarX = home.x;
        avatarY = home.y;
        avatarNode = createAvatarNode();
        setAvatarPos(avatarX, avatarY);
        world.getChildren().add(avatarNode);

        locationLabel.setStyle("-fx-text-fill: white; -fx-font-weight: 700;");
        actionBtn.setOnAction(e -> {
            if (moving) return;
            String loc = gameState.getCurrentLocation();
            if (loc == null) return;
            Building b = gameState.getBuildings().get(loc);
            if (b == null) return;
            b.perform(player, gameState);
            refreshAll();
        });

        HBox actionBar = new HBox(10, locationLabel, spacer(), actionBtn);
        actionBar.setAlignment(Pos.CENTER_LEFT);
        actionBar.setPadding(new Insets(10));
        actionBar.setStyle("-fx-background-color: rgba(255,255,255,0.04); -fx-background-radius: 12;");

        center.getChildren().addAll(mapPane, actionBar);
        root.setCenter(center);

        // ===== Bottom log =====
        logArea.setEditable(false);

        shopDescArea.setEditable(false);
        shopDescArea.setWrapText(true);
        shopDescArea.setFocusTraversable(false);
        shopDescArea.setPrefRowCount(3);
        shopDescArea.setMinHeight(64);
        shopDescArea.setMaxHeight(90);
        shopDescArea.setStyle("-fx-control-inner-background: rgba(0,0,0,0.30); -fx-text-fill: #eaeaea; -fx-background-radius: 12; -fx-border-radius: 12; -fx-border-color: rgba(255,255,255,0.10);");
        logArea.setWrapText(true);
        logArea.getStyleClass().add("log-area");
        logArea.setPrefRowCount(7);
        root.setBottom(logArea);

        // log listener
        gameState.addLogListener(line -> logArea.appendText(line + "\n"));

gameState.addGameOverListener(r -> Platform.runLater(() -> {
    GameOverPopup.showFullScreen(
            host,
            r.isWin(),
            r.getScore(),
            () -> { if (nttr.MainApp.RESTART_TO_TITLE != null) nttr.MainApp.RESTART_TO_TITLE.run(); }
    );
}));

        // movement loop (walk along route waypoints)
        mover = new AnimationTimer() {
            private long last = -1;
            @Override
            public void handle(long now) {
                if (last < 0) { last = now; return; }
                double dt = (now - last) / 1_000_000_000.0;
                last = now;

                if (!moving || route.isEmpty()) return;
                if (routeIndex >= route.size()) {
                    arriveAtGoal();
                    return;
                }

                MapGraph.Node target = route.get(routeIndex);
                double dx = target.x - avatarX;
                double dy = target.y - avatarY;
                double dist = Math.sqrt(dx*dx + dy*dy);

                if (dist < 2.0) {
                    avatarX = target.x;
                    avatarY = target.y;
                    setAvatarPos(avatarX, avatarY);
                    routeIndex += 1;
                    if (routeIndex >= route.size()) {
                        arriveAtGoal();
                    }
                    return;
                }

                double speed = 240.0; // px/sec
                double step = speed * dt;
                double ratio = step / dist;
                if (ratio > 1) ratio = 1;
                ratio = Math.pow(ratio, 0.75);

                avatarX = avatarX + dx * ratio;
                avatarY = avatarY + dy * ratio;
                setAvatarPos(avatarX, avatarY);
            }
        };
        mover.start();

        // background timer thread
        timerThread = new GameTimer(gameState, this::refreshAll);
        timerThread.start();

        refreshAll();
    }

    private void toggleRunning() {
        gameState.setRunning(!gameState.isRunning());
        startPauseBtn.setText(gameState.isRunning() ? "Pause" : "Start");
        gameState.addLog(gameState.isRunning() ? "Timer started." : "Timer paused.");
        refreshAll();
    }

    private void arriveAtGoal() {
        moving = false;
        actionBtn.getStyleClass().add("btn-primary");
        actionBtn.setDisable(false);
        if (routeGoal != null) {
            gameState.setCurrentLocation(routeGoal);
            gameState.addLog("Arrived at " + routeGoal + ".");
        }
        routeGoal = null;
        refreshAll();
    }

    private void beginTravelTo(String destination) {
        if (destination == null) return;
        if (moving) return;

        String start = gameState.getCurrentLocation();
        if (start == null) start = "Home";

        route = graph.route(start, destination);
        if (route.isEmpty()) return;

        // compute travel time from path length, affected by vehicle multiplier
        double dist = 0;
        for (int i = 1; i < route.size(); i++) {
            dist += MapGraph.distance(route.get(i-1), route.get(i));
        }
        int travelSeconds = (int) Math.ceil((dist / 120.0) * player.getTravelTimeMultiplier());
        if (travelSeconds < 1) travelSeconds = 1;

        gameState.consumeTime(travelSeconds);
        if (gameState.isGameOver()) return;

        routeGoal = destination;
        routeIndex = 0;
        moving = true;
        actionBtn.setDisable(true);

        gameState.addLog("Walking along paths → " + destination + " (cost " + travelSeconds + "s).");
        refreshAll();
    }

    private void buildGraph() {
        // Node positions (map coordinates)
        graph.addNode("Home", 140, 120);
        graph.addNode("Office", 640, 110);
        graph.addNode("University", 690, 360);
        graph.addNode("Gym", 200, 380);
        graph.addNode("Party", 420, 250);
        graph.addNode("Bank", 520, 420);
        graph.addNode("Market", 320, 120);
        graph.addNode("Clothing", 90, 280);

        // Edges = walkable paths (like a board map)
        graph.addEdge("Home", "Market");
        graph.addEdge("Market", "Office");
        graph.addEdge("Market", "Party");
        graph.addEdge("Party", "Office");
        graph.addEdge("Party", "University");
        graph.addEdge("Party", "Gym");
        graph.addEdge("Gym", "Clothing");
        graph.addEdge("Clothing", "Home");
        graph.addEdge("Gym", "Bank");
        graph.addEdge("Bank", "University");
    }

    private void drawPaths() {
        for (MapGraph.Edge e : graph.getEdges()) {
            MapGraph.Node a = graph.getNode(e.a);
            MapGraph.Node b = graph.getNode(e.b);
            Line line = new Line(a.x, a.y, b.x, b.y);
            line.setStroke(Color.web("#ffffff22"));
            line.setStrokeWidth(6);
            line.setSmooth(true);
            world.getChildren().add(line);
        }
    }

    private void placeBuildings() {
        for (Map.Entry<String, Building> entry : gameState.getBuildings().entrySet()) {
            String id = entry.getKey();
            Building b = entry.getValue();
            MapGraph.Node n = graph.getNode(id);
            if (n == null) continue;

            StackPane icon = buildingIcon(id, b.getImagePath(), n.x, n.y);
            world.getChildren().add(icon);
        }
    }

    private StackPane buildingIcon(String name, String imagePath, double x, double y) {
        Node iconNode = tryImage(imagePath, 44, 44);
        if (iconNode == null) {
            Rectangle rect = new Rectangle(44, 44);
            rect.setArcWidth(12);
            rect.setArcHeight(12);
            rect.setFill(Color.web("#3b4a66"));
            iconNode = rect;
        }

        Label lbl = new Label(name);
        lbl.setStyle("-fx-text-fill: white; -fx-font-size: 11px;");

        VBox box = new VBox(4, iconNode, lbl);
        box.setAlignment(Pos.CENTER);

        StackPane card = new StackPane(box);

        DropShadow glow = new DropShadow();
        glow.setRadius(18);
        glow.setSpread(0.15);
        glow.setColor(Color.web("#4da3ff66"));

        card.setPrefSize(70, 70);
        card.setStyle(cardStyle(false));
        card.setLayoutX(x - 35);
        card.setLayoutY(y - 35);

        card.setOnMouseEntered(e -> card.setStyle(cardStyle(true)));
        card.setOnMouseExited(e -> card.setStyle(cardStyle(false)));

        card.setOnMouseClicked(e -> beginTravelTo(name));
        return card;
    }

    private String cardStyle(boolean hover) {
        if (hover) {
            return "-fx-background-color: rgba(255,255,255,0.11); -fx-background-radius: 14; -fx-border-radius: 14; -fx-border-color: rgba(255,255,255,0.26);";
        }
        return "-fx-background-color: rgba(255,255,255,0.06); -fx-background-radius: 14; -fx-border-radius: 14; -fx-border-color: rgba(255,255,255,0.14);";
    }

    private Node tryMapBackground() {
        try {
            Image img = new Image(getClass().getResourceAsStream("/images/map.png"));
            ImageView iv = new ImageView(img);
            iv.setFitWidth(760);
            iv.setFitHeight(470);
            iv.setOpacity(0.35);
            iv.setPreserveRatio(false);
            iv.setSmooth(true);
            return iv;
        } catch (Exception e) {
            Rectangle r = new Rectangle(760, 470);
            r.setFill(Color.web("#101a2b"));
            r.setArcWidth(24);
            r.setArcHeight(24);
            return r;
        }
    }

    private Node tryImage(String path, double w, double h) {
        if (path == null) return null;
        try {
            Image img = new Image(getClass().getResourceAsStream(path));
            ImageView iv = new ImageView(img);
            iv.setFitWidth(w);
            iv.setFitHeight(h);
            iv.setPreserveRatio(true);
            iv.setSmooth(true);
            return iv;
        } catch (Exception e) {
            return null;
        }
    }

    private Node createAvatarNode() {
        Node img = tryImage(player.getAvatarPath(), 40, 40);
        if (img != null) return img;

        Circle c = new Circle(16, Color.web("#6d4dff"));
        c.setStroke(Color.web("#ffffff33"));
        c.setStrokeWidth(2);
        return c;
    }

    private void setAvatarPos(double x, double y) {
        avatarNode.setLayoutX(x - 16);
        avatarNode.setLayoutY(y - 16);
    }

    private HBox statRow(String name, ProgressBar bar, Label value) {
        Label lbl = new Label(name);
        lbl.getStyleClass().add("stat-label");
        lbl.setMinWidth(80);
        bar.setPrefWidth(120);
        value.getStyleClass().add("stat-value");
        HBox row = new HBox(10, lbl, bar, value);
        row.setAlignment(Pos.CENTER_LEFT);
        return row;
    }

    private HBox miniInfo(String name, Label v) {
        Label lbl = new Label(name);
        lbl.setStyle("-fx-text-fill: #cfcfcf; -fx-min-width: 120; -fx-font-weight: 700;");
        v.setStyle("-fx-text-fill: white; -fx-font-weight: 700;");
        HBox row = new HBox(10, lbl, v);
        row.setAlignment(Pos.CENTER_LEFT);
        return row;
    }

    private void styleTopLabel(Label l) {
        l.setStyle("-fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: 800;");
    }

    private Region spacer() {
        Region r = new Region();
        HBox.setHgrow(r, Priority.ALWAYS);
        return r;
    }

    private void refreshInventory() {
        // clear slot UI (keep first elements up to separator)
        while (invBox.getChildren().size() > 5) {
            invBox.getChildren().remove(invBox.getChildren().size() - 1);
        }

        List<Item> slots = gameState.getInventory().getSlotsView();

        for (int i = 0; i < slots.size(); i++) {
            final int idx = i;
            Item it = slots.get(i);

            Label slotName = new Label("Slot " + idx + " (" + Inventory.slotLabel(idx) + ")");
            slotName.setStyle("-fx-text-fill: white; -fx-font-weight: 800;");

            Label itemName = new Label(it == null ? "(empty)" : it.getName());
            itemName.setStyle("-fx-text-fill: #d8d8d8;");

            Button unequip = new Button("Unequip");
            unequip.setDisable(it == null);
            unequip.setOnAction(e -> {
                // set slot to null by equipping null manually
                // easiest: rebuild list
                // Inventory has no unequip API, so we replace by equipping a null is not allowed.
                // We'll do a small hack: equip a dummy then remove? Better: add an unequip method.
                // For now: call a helper in GameState via inventory access.
                tryUnequip(idx);
            });

            VBox card = new VBox(6, slotName, itemName, unequip);
            card.setPadding(new Insets(8));
            card.setStyle("-fx-background-color: rgba(255,255,255,0.06); -fx-background-radius: 12; -fx-border-radius: 12; -fx-border-color: rgba(255,255,255,0.14);");
            invBox.getChildren().add(card);
        }
    }

    private void tryUnequip(int slotIndex) {
        // We add Inventory.unequipSlot in this project (see Inventory patch).
        gameState.getInventory().unequipSlot(slotIndex);
        gameState.getInventory().applyBonuses(player);
        gameState.addLog("Unequipped slot " + slotIndex + " (" + Inventory.slotLabel(slotIndex) + ").");
        refreshInventory();
        refreshAll();
    }

    private void refreshAll() {
        // top
        dayLabel.setText("Day " + gameState.getDay() + "/" + GameState.TOTAL_DAYS);
        timeLabel.setText("Time " + gameState.getTimeLeftSeconds() + "s");
        scoreLabel.setText("Score " + player.score());

        // bars
        moneyBar.setProgress(Math.min(1.0, player.getMoney() / 150.0));
        healthBar.setProgress(player.getHealth() / 100.0);
        skillBar.setProgress(player.getSkill() / 100.0);
        happinessBar.setProgress(player.getHappiness() / 100.0);
        stressBar.setProgress(player.getStress() / 100.0);

        moneyLabel.setText(String.valueOf(player.getMoney()));
        healthLabel.setText(String.valueOf(player.getHealth()));
        skillLabel.setText(String.valueOf(player.getSkill()));
        happinessLabel.setText(String.valueOf(player.getHappiness()));
        stressLabel.setText(String.valueOf(player.getStress()));

        bankLabel.setText(String.valueOf(player.getBankBalance()));
        foodLabel.setText(player.getFridgeMeals() + " meals");
        clothesLabel.setText(player.getClothingLevel().getLabel());
        rentLabel.setText(String.valueOf(gameState.getRentDebt()));

        locationLabel.setText("Location: " + gameState.getCurrentLocation() + (moving ? " (walking...)" : ""));
        actionBtn.setDisable(moving || gameState.isGameOver());

        if (gameState.isGameOver()) {
            startPauseBtn.setDisable(true);
            endDayBtn.setDisable(true);
            actionBtn.setDisable(true);
        }
    }

    public Parent getRoot() {
        return host;
    }

    public void stop() {
        mover.stop();
        timerThread.requestStop();
    }
}
