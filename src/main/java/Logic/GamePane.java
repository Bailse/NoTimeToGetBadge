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

public class GamePane extends Pane {

    private final int TILE_SIZE = 40;
    private boolean[][] clickable;
    private Image wallImage = new Image(getClass().getResourceAsStream("/block.png"));
    private Image streetImage90 = new Image(getClass().getResourceAsStream("/street90.png"));
    private Image streetImage180 = new Image(getClass().getResourceAsStream("/street180.png"));
    private Image floorImage = new Image(getClass().getResourceAsStream("/grass2.png"));
    private Image gym = new Image(getClass().getResourceAsStream("/store.png"));
    private Image imgDown;
    private Image imgLeft ;
    private Image imgRight ;
    private Image imgUp ;
    private BasePlayer playerType = GameSession.getPlayer();
    private Runnable onReachBuilding;
    private Runnable onLeaveBuilding;
    private Runnable onStatusChange;



    private int[][] map = {

            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
            {1,0,0,0,0,0,0,2,2,0,0,0,0,0,0,1},
            {1,0,3,4,4,4,4,4,4,4,4,4,4,3,0,1},
            {1,0,3,0,0,0,0,3,3,0,0,0,0,3,0,1},
            {1,0,3,0,0,0,0,3,3,0,0,0,0,3,0,1},
            {1,2,3,0,0,0,0,3,3,0,0,0,0,3,0,1},
            {1,0,3,0,0,0,0,3,3,0,0,0,0,3,0,1},
            {1,0,3,0,0,0,2,3,3,2,0,0,0,3,0,1},
            {1,0,3,4,4,4,4,4,4,4,4,4,4,3,2,1},
            {1,0,3,0,0,0,2,3,3,2,0,0,0,3,0,1},
            {1,0,3,0,0,0,0,3,3,0,0,0,0,3,0,1},
            {1,2,3,0,0,0,0,3,3,0,0,0,0,3,0,1},
            {1,0,3,0,0,0,0,3,3,0,0,0,0,3,0,1},
            {1,0,3,0,0,0,0,3,3,0,0,0,0,3,0,1},
            {1,0,3,0,0,0,0,3,3,0,0,0,0,3,0,1},
            {1,0,3,4,4,4,4,4,4,4,4,4,4,3,0,1},
            {1,0,0,0,0,0,0,2,2,0,0,0,0,0,0,1},
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}

    };

    private int playerRow = 2;
    private int playerCol = 7;

    private ImageView player;
    private boolean isMoving = false;

    //Building
    private Building[][] bmap;

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
            //player = new ImageView(new Image(getClass().getResourceAsStream(sessionPlayer.getImagePath())));
            player = new ImageView(imgDown);

        }else{
            player = new ImageView(imgDown);
        }
        player.setFitWidth(TILE_SIZE+20);
        player.setFitHeight(TILE_SIZE+20);

       // player.setTranslateX(playerCol * TILE_SIZE + 5);
       // player.setTranslateY(playerRow * TILE_SIZE + 5);

        updatePlayerPosition();


        this.getChildren().add(player);

        clickablePath();

        bmap = new Building[map.length][map[0].length]; // กำหนดขนาดให้พอ

        //bmap[3][2] = new Dome();
        //bmap[3][3] = new Travel();
        //bmap[3][4] = new Gym();
        //bmap[3][5] = new Chula();
        //bmap[4][2] = new Park();
        //bmap[4][3] = new Mall();
        //bmap[4][4] = new Restaurant();

        bmap[1][7] = new Dome();
        bmap[1][8] = new Dome();
        bmap[5][1] = new Gym();
        bmap[7][6] = new Chula();
        bmap[7][9] = new Chula();
        bmap[9][6] = new Restaurant();
        bmap[9][9] = new Travel();
        bmap[16][7] = new Park();
        bmap[16][8] = new Park();
        

    }

    public GamePane(String avatarPath){
        this();

        if(avatarPath != null){
            player.setImage(imgDown);
        }
    }

    //Building ACTION
    public Building getCurrentBuilding() {
        return bmap[playerRow][playerCol];
    }


    private void drawMap() {

        for (int r = 0; r < map.length; r++) {
            for (int c = 0; c < map[0].length; c++) {

                ImageView tile;

                if (map[r][c] == 1) {
                    tile = new ImageView(wallImage);
                } else if (map[r][c] == 2) {
                    tile = new ImageView(gym);
                    
                } else if (map[r][c] == 3) {
                    tile = new ImageView(streetImage90);

                } else if (map[r][c] == 4) {
                    tile = new ImageView(streetImage180);

                } else {
                    tile = new ImageView(floorImage);
                }

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

    // BFS pathfinding (เหมือนเดิม)
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

    private void clickablePath(){

        this.clickable = new boolean[map.length][map[0].length];


        for (int r = 0; r < map.length; r++) {
            for (int c = 0; c < map[0].length; c++) {


                if(map[r][c] != 2){ // only buliding that can click
                    this.clickable[r][c] = false;
                }
                else{
                    this.clickable[r][c] = true;
                }


            }
        }
    }







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
       // move.setToX(next[1] * TILE_SIZE + 5);
       // move.setToY(next[0] * TILE_SIZE + 5);


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

    public boolean CheckPlayer(){

        if(this.map[playerRow][playerCol] == 2){
            return true;
        }
        return false;
    }

    public void setOnReachBuilding(Runnable r) {
        this.onReachBuilding = r;
    }

    public void setOnLeaveBuilding(Runnable r) {
        this.onLeaveBuilding = r;
    }


    public int getPlayerRow() {
        return playerRow;
    }


    public int getPlayerCol() {
        return playerCol;
    }



    public void setPlayerRow(int playerRow) {
        this.playerRow = playerRow;
    }


    public void setPlayerCol(int playerCol) {
        this.playerCol = playerCol;
    }

    public void setOnStatusChange(Runnable r) {
        this.onStatusChange = r;
    }

    /** Reset player position back to the starting tile (1,1). */
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

    /** Returns true if player is currently moving */
    public boolean isPlayerMoving() {
        return isMoving;
    }


    public void setImgDown() {
        this.imgDown = playerType.getImgDown();

    }



    public void setImgLeft() {
        this.imgLeft = playerType.getImgLeft();
    }



    public void setImgRight() {
        this.imgRight = playerType.getImgRight();
    }



    public void setImgUp() {
        this.imgUp = playerType.getImgUp();
    }

    // ===== [เพิ่มใหม่: Player Status] =====
    public int getPlayerMoney() {
        BasePlayer p = GameSession.getPlayer();
        return (p != null) ? p.getMoney() : 0;
    }

    public void setPlayerMoney(int money) {
        BasePlayer p = GameSession.getPlayer();
        if (p != null) {
            p.setMoney(money);
            if (onStatusChange != null) onStatusChange.run();
        }
    }

    public int getPlayerStamina() {
        BasePlayer p = GameSession.getPlayer();
        return (p != null) ? p.getStamina() : 0;
    }

    public void setPlayerStamina(int stamina) {
        BasePlayer p = GameSession.getPlayer();
        if (p != null) {
            p.setStamina(stamina);
            if (onStatusChange != null) onStatusChange.run();
        }
    }

    public int getPlayerHealth() {
        BasePlayer p = GameSession.getPlayer();
        return (p != null) ? p.getHealth() : 100;
    }

    public void setPlayerHealth(int health) {
        BasePlayer p = GameSession.getPlayer();
        if (p != null) {
            p.setHealth(health);
            if (onStatusChange != null) onStatusChange.run();
        }
    }

    // ===== [เพิ่มใหม่: Education System] =====
    public int getPlayerEducation() {
        BasePlayer p = GameSession.getPlayer();
        // ดึงค่าจาก class Player (อย่าลืมไปเพิ่ม field นี้ใน Player.java)
        return (p != null) ? p.getEducation() : 0;
    }

    public void setPlayerEducation(int education) {
        BasePlayer p = GameSession.getPlayer();
        if (p != null) {
            p.setEducation(education); // อย่าลืมไปเพิ่ม Method ใน Player.java
            if (onStatusChange != null) onStatusChange.run();
        }
    }

    public int getPlayerHappiness() {
        BasePlayer p = GameSession.getPlayer();
        return (p != null) ? p.getHappiness() : 0;
    }

    public void setPlayerHappiness(int happiness) {
        BasePlayer p = GameSession.getPlayer();
        if (p != null) {
            // จำกัดค่าไม่ให้เกิน 100 หรือต่ำกว่า 0 (Optional: แล้วแต่คุณออกแบบ)
            int cappedHappiness = Math.min(500,happiness);
            p.setHappiness(cappedHappiness);

            if (onStatusChange != null) onStatusChange.run();
        }
    }
    // เพิ่ม/แก้ไขใน Logic/GamePane.java

    public void addItem(String itemName) {
        BasePlayer p = GameSession.getPlayer();
        if (p == null) return;

        if (itemName.equals("Whey Protein")) {
            p.getItemManager().addItem(new Item.WheyProtein()); // ใส่ในช่อง index 0
        }
        else if(itemName.equals("CAR")){
            p.getItemManager().addItem(new Item.Vehicle());
        }

        if (onStatusChange != null) onStatusChange.run();
    }

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

        // addItem จะไป set ที่ index 1 อัตโนมัติเพราะเป็น Category.EDUCATION
        p.getItemManager().addItem(newBook);

        if (onStatusChange != null) onStatusChange.run();
    }

    private void updatePlayerPosition() {
        double offsetX = (TILE_SIZE - player.getFitWidth()) / 2.0;
        double offsetY = (TILE_SIZE - player.getFitHeight()) / 2.0;

        player.setTranslateX(playerCol * TILE_SIZE + offsetX);
        player.setTranslateY(playerRow * TILE_SIZE + offsetY);
    }
    


}
