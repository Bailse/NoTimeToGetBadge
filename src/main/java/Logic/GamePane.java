package Logic;

import Screen.BuildingScreen.Building;
import Screen.BuildingScreen.Chula.Chula;
import Screen.BuildingScreen.Dome.Dome;
import Screen.BuildingScreen.Gym.Gym;
import Screen.BuildingScreen.Mall.Mall;
import Screen.BuildingScreen.Park.Park;
import Screen.BuildingScreen.Restaurant.Restaurant;
import Screen.BuildingScreen.Travel.Travel;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import javafx.scene.image.ImageView;


//import javax.swing.text.html.ImageView;
import java.util.*;

import Character.BasePlayer;
import Logic.MapCreate;

/**
 * this clas this for setup game logic and map logic
 */
public class GamePane extends Pane {

    private MapCreate MapCreate;
    private final int TILE_SIZE = 40;
    private boolean[][] clickable;
    private Image imgDown;
    private Image imgLeft ;
    private Image imgRight ;
    private Image imgUp ;
    private BasePlayer playerType = GameSession.getPlayer();
    private Runnable onReachBuilding;
    private Runnable onLeaveBuilding;
    private Runnable onStatusChange;
    private int playerRow = 2;
    private int playerCol = 7;
    private ImageView player;
    private boolean isMoving = false;
    private Building[][] bmap;
    private int[][] map = {
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
            {1,0,0,0,0,0,0,10,11,0,0,0,0,0,0,1},
            {1,0,3,4,4,4,4,4,4,4,4,4,4,3,0,1},
            {1,0,3,0,0,0,0,3,3,0,0,0,0,3,0,1},
            {1,12,3,0,7,6,0,3,3,0,0,0,0,3,0,1},
            {1,13,3,0,5,6,0,3,3,0,8,0,0,3,0,1},
            {1,14,3,0,5,0,0,3,3,0,0,0,8,3,0,1},
            {1,0,3,0,0,0,15,3,3,16,0,0,0,3,23,1},
            {1,0,3,4,4,4,4,4,4,4,4,4,4,3,24,1},
            {1,0,3,0,0,18,17,3,3,21,22,0,0,3,25,1},
            {1,0,3,0,0,0,0,3,3,0,8,0,0,3,0,1},
            {1,0,3,0,0,0,0,3,3,0,0,0,0,3,0,1},
            {1,0,3,0,0,8,0,3,3,0,0,8,0,3,0,1},
            {1,0,3,0,0,0,0,3,3,0,0,0,0,3,0,1},
            {1,0,3,0,8,0,0,3,3,0,0,0,0,3,0,1},
            {1,0,3,4,4,4,4,4,4,4,4,4,4,3,0,1},
            {1,0,0,0,0,0,0,19,20,0,0,0,0,0,0,1},
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}

    };

    /**
     * constructor class to initialize playerPosition , player Image , map Image , map Location , map Logic
     */
    public GamePane() {

        setImgLeft();
        setImgDown();
        setImgRight();
        setImgUp();


        this.setPrefSize(
                map[0].length * TILE_SIZE,
                map.length * TILE_SIZE
        );

        drawMap();

        BasePlayer sessionPlayer = GameSession.getPlayer();

        if(sessionPlayer != null && sessionPlayer.getImagePath() != null){
            player = new ImageView(imgDown);

        }else{
            player = new ImageView(imgDown);
        }
        player.setFitWidth(TILE_SIZE+20);
        player.setFitHeight(TILE_SIZE+20);

        updatePlayerPosition();


        this.getChildren().add(player);

        clickablePath();

        bmap = new Building[map.length][map[0].length];

        bmap[1][7] = new Dome();
        bmap[1][8] = new Dome();
        bmap[6][1] = new Gym();
        bmap[4][1] = new Gym();
        bmap[5][1] = new Gym();
        bmap[7][6] = new Chula();
        bmap[7][9] = new Chula();
        bmap[9][6] = new Restaurant();
        bmap[9][5] = new Restaurant();
        bmap[9][9] = new Park();
        bmap[9][10] = new Park();
        bmap[8][14] = new Mall();
        bmap[7][14] = new Mall();
        bmap[9][14] = new Mall();
        bmap[16][7] = new Travel();
        bmap[16][8] = new Travel();
    }

    // set player when start game //

    /**
     * set Player image when and is start
     *
     */

    public GamePane(String avatarPath){
        this();
        if(avatarPath != null){
            player.setImage(imgDown);
        }
    }

    /**
     * get currentBuilding to get type of building
     *
     */

    public Building getCurrentBuilding() {
        return bmap[playerRow][playerCol];
    }

    /**
     * Draw map and set logic to each TILE
     *
     */

    private void drawMap() {

        for (int r = 0; r < map.length; r++) {
            for (int c = 0; c < map[0].length; c++) {

                ImageView tile;
                Logic.MapCreate s1 = new MapCreate(map[r][c]);

                tile = s1.getChoosing();

                tile.setFitWidth(TILE_SIZE);
                tile.setFitHeight(TILE_SIZE);

                tile.setTranslateX(c * TILE_SIZE);
                tile.setTranslateY(r * TILE_SIZE);

                int finalR = r;
                int finalC = c;

                tile.setOnMouseClicked(e -> {

                    BasePlayer sessionPlayer = GameSession.getPlayer();

                    if (sessionPlayer != null && sessionPlayer.getStamina() <= 0) {
                        System.out.println("No stamina! Can't move.");
                        return;
                    }

                    if (!isMoving && clickable[finalR][finalC]) {
                        List<int[]> path = findPath(playerRow, playerCol, finalR, finalC);
                        walkPath(path);
                    }
                });


                this.getChildren().add(tile);
            }
        }
    }

    /**
     * this method is find the best path by using BFS to find right path that use less stamina
     */

    private List<int[]> findPath(int startR, int startC, int targetR, int targetC) {

        int rows = map.length;
        int cols = map[0].length;


        boolean[][] visited = new boolean[rows][cols];
        Queue<int[]> queue = new LinkedList<>();
        Map<String, String> parent = new HashMap<>();

        queue.add(new int[]{startR, startC});
        visited[startR][startC] = true;

        int[][] dirs = {{1,0},{-1,0},{0,1},{0,-1}};

        while (!queue.isEmpty()) {
            int[] current = queue.poll();

            if (current[0] == targetR && current[1] == targetC)
                break;

            for (int[] d : dirs) {
                int nr = current[0] + d[0];
                int nc = current[1] + d[1];

                if (nr >= 0 && nr < rows && nc >= 0 && nc < cols &&!visited[nr][nc]) {
                     int checkmap = map[nr][nc];
                    if(checkmap != 1 && checkmap != 0) {
                        visited[nr][nc] = true;
                        queue.add(new int[]{nr, nc});
                        parent.put(nr + "," + nc, current[0] + "," + current[1]);
                    }
                }
            }
        }

        List<int[]> path = new ArrayList<>();
        String step = targetR + "," + targetC;

        if (!parent.containsKey(step))
            return path;

        while (parent.containsKey(step)) {
            String[] parts = step.split(",");
            path.add(new int[]{Integer.parseInt(parts[0]), Integer.parseInt(parts[1])});
            step = parent.get(step);
        }

        Collections.reverse(path);
        return path;
    }


    /**
     * initialize clickable path to control map which TILE you can click
     */

    private void clickablePath(){

        this.clickable = new boolean[map.length][map[0].length];


        for (int r = 0; r < map.length; r++) {
            for (int c = 0; c < map[0].length; c++) {



                if(map[r][c] >= 10 ){ // only buliding that can click
                    this.clickable[r][c] = true;
                }
                else{

                    this.clickable[r][c] = false;
                }


            }
        }
    }

    /**
     * set Image when player move from TILE to other TILE and set animation to moving
     * and use method walk from baseCharacter to walk
     */

    private void walkPath(List<int[]> path) {

        if (path.isEmpty()) return;

        isMoving = true;

        int[] next = path.remove(0);

        if (next[0] > playerRow) {
            player.setImage(imgDown);
        } else if (next[0] < playerRow) {
            player.setImage(imgUp);
        } else if (next[1] > playerCol) {
            player.setImage(imgRight);
        } else if (next[1] < playerCol) {
            player.setImage(imgLeft);
        }


        TranslateTransition move = new TranslateTransition();
        move.setNode(player);
        move.setDuration(Duration.seconds(0.15));

        double offsetX = (TILE_SIZE - player.getFitWidth()) / 2.0;
        double offsetY = (TILE_SIZE - player.getFitHeight()) / 2.0;
        
        move.setToX(next[1] * TILE_SIZE + offsetX);
        move.setToY(next[0] * TILE_SIZE + offsetY);

        move.setOnFinished(e -> {
            playerRow = next[0];
            playerCol = next[1];

            BasePlayer sessionPlayer = GameSession.getPlayer();
            if (sessionPlayer != null) {
                int oldStamina = sessionPlayer.getStamina();

                sessionPlayer.walk();   // ลด stamina

                int newStamina = sessionPlayer.getStamina();

                System.out.println("Stamina: " + oldStamina + " -> " + newStamina);

            }
            if (onStatusChange != null) {
                onStatusChange.run();
            }

            if (CheckPlayer()) {
                if (onReachBuilding != null) {
                    onReachBuilding.run();
                }
            } else {
                if (onLeaveBuilding != null) {
                    onLeaveBuilding.run();
                }
            }

            if (sessionPlayer != null && sessionPlayer.getStamina() <= 0) {
                isMoving = false;
                return;
            }

            if (!path.isEmpty()) {
                walkPath(path);
            } else {
                isMoving = false;
            }
        });

        move.play();
    }

    /**
     * check player if player stand on buliding the action button will active
     *
     */

    public boolean CheckPlayer(){

        if(this.map[playerRow][playerCol] >= 10){
            return true;
        }
        return false;
    }

    /**
     * reset position player when player use this method
     *
     */
    public void resetPlayerToStart() {
        isMoving = false;

        playerRow = 2;
        playerCol = 7;

        if (player != null) {
            updatePlayerPosition();
        }

        if (onLeaveBuilding != null) {
            onLeaveBuilding.run();
        }
    }
    /**
     * check if player moving
     */

    public boolean isPlayerMoving() {
        return isMoving;
    }

    /**
     * set player imageDown from gameSession
     */

    public void setImgDown() {
        this.imgDown = playerType.getImgDown();

    }

    /**
     * run notifyUpdate thread
     */


    public void notifyUpdate() {
        if (onStatusChange != null) {
            onStatusChange.run();
        }
    }

    /**
     * set action button to Enable
     */

    public void setOnReachBuilding(Runnable r) {
        this.onReachBuilding = r;
    }

    /**
     * set action button to Disable
     */
    public void setOnLeaveBuilding(Runnable r) {
        this.onLeaveBuilding = r;
    }

    /**
     * set status thread change while doing action
     */

    public void setOnStatusChange(Runnable r) {
        this.onStatusChange = r;
    }
    /**
     * set player imageLeft from gameSession
     */

    public void setImgLeft() {
        this.imgLeft = playerType.getImgLeft();
    }
    /**
     * set player imageRight from gameSession
     */
    public void setImgRight() {
        this.imgRight = playerType.getImgRight();
    }
    /**
     * set player imageUp from gameSession
     */
    public void setImgUp() {
        this.imgUp = playerType.getImgUp();
    }
    /**
     * get Player from GameSession
     */
    public BasePlayer getPlayer() {
        return GameSession.getPlayer();
    }
    /**
     * add item to player
     */
    public void addItem(String itemName) {

        if (getPlayer() == null) return;

        if (itemName.equals("Whey Protein")) {
            getPlayer().getItemManager().addItem(new Item.WheyProtein());
        }
        else if(itemName.equals("CAR")){
            getPlayer().getItemManager().addItem(new Item.Vehicle());
        }

        if (onStatusChange != null) onStatusChange.run();

    }
    /**
     * update item on status bar that change to active or upgrade (education thing)
     */
    public void updateEducationItem(int level) {
        BasePlayer p = GameSession.getPlayer();
        if (p == null) return;

        Item.BaseItem newBook;
        // เลือกเล่มหนังสือตาม levelIndex (0-3)
        switch (level) {
            case 1:  newBook = new Item.Book2(); break;
            case 2:  newBook = new Item.Book3(); break;
            case 3:  newBook = new Item.Book4(); break;
            default: newBook = new Item.Book1(); break;
        }

        p.getItemManager().addItem(newBook);

        if (onStatusChange != null) onStatusChange.run();
    }

    /**
     * update playerPosition
     */

    private void updatePlayerPosition() {
        double offsetX = (TILE_SIZE - player.getFitWidth()) / 2.0;
        double offsetY = (TILE_SIZE - player.getFitHeight()) / 2.0;

        player.setTranslateX(playerCol * TILE_SIZE + offsetX);
        player.setTranslateY(playerRow * TILE_SIZE + offsetY);
    }
    


}
