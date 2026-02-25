package Logic;

import Screen.Game.Gamebar;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


//import javax.swing.text.html.ImageView;
import java.util.*;
import Logic.GameSession;
import Character.Player;

public class GamePane extends Pane {

    private final int TILE_SIZE = 40;
    private boolean[][] clickable;
    private Image wallImage = new Image(getClass().getResourceAsStream("/block.png"));
    private Image floorImage = new Image(getClass().getResourceAsStream("/grass.png"));
    private Image gym = new Image(getClass().getResourceAsStream("/store.png"));
    private Image imgDown = new Image(getClass().getResourceAsStream("/Lily.jpg"));
    private Image imgLeft = new Image(getClass().getResourceAsStream("/Huh.jpg"));
    private Image imgRight = new Image(getClass().getResourceAsStream("/deku_nerd.jpg"));
    private Image imgUp = new Image(getClass().getResourceAsStream("/Annie_Zheng.jpg"));

    private Runnable onReachBuilding;
    private Runnable onLeaveBuilding;
    private Runnable onStatusChange;





    private int[][] map = {

                    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                    {1,0,0,0,0,0,0,0,2,0,0,0,0,0,0,1},
                    {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                    {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                    {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                    {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                    {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                    {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                    {1,2,0,0,0,0,0,0,0,0,0,0,0,0,2,1},
                    {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                    {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                    {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                    {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                    {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                    {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                    {1,0,0,0,0,0,0,2,0,0,0,0,0,0,0,1},
                    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}


//            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
//            {1,0,0,0,0,0,1,0,0,0,0,1,0,0,0,1},
//            {1,0,1,1,1,0,1,0,1,1,0,1,0,1,0,1},
//            {1,0,1,0,0,0,0,0,1,0,0,0,0,1,0,1},
//            {1,0,1,0,1,1,1,0,1,0,1,1,0,1,0,1},
//            {1,0,0,0,1,0,0,0,0,0,1,0,0,0,0,1},
//            {1,1,1,0,1,0,1,1,1,0,1,0,1,1,0,1},
//            {1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,1},
//            {1,0,1,1,1,0,1,0,1,1,1,0,1,0,1,1},
//            {1,0,0,0,1,0,0,0,1,0,0,0,1,0,0,1},
//            {1,0,1,0,1,1,1,0,1,0,1,1,1,1,0,1},
//            {1,0,1,0,0,0,1,0,0,0,1,0,0,0,0,1},
//            {1,0,1,1,1,0,1,1,1,0,1,0,1,1,0,1},
//            {1,0,0,0,1,0,0,0,1,0,0,0,1,0,0,1},
//            {1,1,1,0,1,1,1,0,1,1,1,0,1,1,0,1},
//            {1,0,0,0,0,0,0,0,0,0,1,0,0,0,0,1},
//            {1,0,1,1,1,1,1,1,1,0,1,1,1,1,0,1},
//            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}


    };

    private int playerRow = 1;
    private int playerCol = 1;

    private ImageView player;
    private boolean isMoving = false;



    public GamePane() {



        this.setPrefSize(
                map[0].length * TILE_SIZE,
                map.length * TILE_SIZE
        );

        drawMap();

        Player sessionPlayer = GameSession.getPlayer();
        if(sessionPlayer != null && sessionPlayer.getImagePath() != null){
            player = new ImageView(new Image(getClass().getResourceAsStream(sessionPlayer.getImagePath())));
        }else{
            player = new ImageView(imgDown);
        }
        player.setFitWidth(TILE_SIZE - 10);
        player.setFitHeight(TILE_SIZE - 10);

        player.setTranslateX(playerCol * TILE_SIZE + 5);
        player.setTranslateY(playerRow * TILE_SIZE + 5);

        this.getChildren().add(player);

        clickablePath();
    }

    public GamePane(String avatarPath){
        this();
        if(avatarPath != null){
            player.setImage(new Image(getClass().getResourceAsStream(avatarPath)));
        }
    }


    private void drawMap() {

        for (int r = 0; r < map.length; r++) {
            for (int c = 0; c < map[0].length; c++) {

                ImageView tile;

                if (map[r][c] == 1) {
                    tile = new ImageView(wallImage);
                } else if (map[r][c] == 2) {
                    tile = new ImageView(gym);
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

                    Player sessionPlayer = GameSession.getPlayer();

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

                if (nr >= 0 && nr < rows && nc >= 0 && nc < cols &&
                        !visited[nr][nc] && map[nr][nc] != 1) {

                    visited[nr][nc] = true;
                    queue.add(new int[]{nr, nc});
                    parent.put(nr + "," + nc, current[0] + "," + current[1]);
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
        move.setToX(next[1] * TILE_SIZE + 5);
        move.setToY(next[0] * TILE_SIZE + 5);

        move.setOnFinished(e -> {
            playerRow = next[0];
            playerCol = next[1];

            Player sessionPlayer = GameSession.getPlayer();
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

        playerRow = 1;
        playerCol = 1;

        if (player != null) {
            player.setTranslateX(playerCol * TILE_SIZE + 5);
            player.setTranslateY(playerRow * TILE_SIZE + 5);
        }

        if (onLeaveBuilding != null) {
            onLeaveBuilding.run();
        }
    }

    /** Returns true if player is currently moving */
    public boolean isPlayerMoving() {
        return isMoving;
    }

}
