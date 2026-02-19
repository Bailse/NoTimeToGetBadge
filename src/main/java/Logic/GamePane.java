package Logic;

import javafx.animation.TranslateTransition;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.*;

public class GamePane extends Pane {

    private final int TILE_SIZE = 40;

    private int[][] map = {

                    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                    {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                    {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                    {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                    {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                    {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                    {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                    {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                    {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                    {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                    {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                    {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                    {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                    {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                    {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                    {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                    {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}


    };

    private int playerRow = 1;
    private int playerCol = 1;

    private Rectangle player;
    private boolean isMoving = false;

    public GamePane() {

        this.setPrefSize(
                map[0].length * TILE_SIZE,
                map.length * TILE_SIZE
        );

        drawMap();

        player = new Rectangle(TILE_SIZE - 10, TILE_SIZE - 10, Color.BLUE);
        player.setTranslateX(playerCol * TILE_SIZE + 5);
        player.setTranslateY(playerRow * TILE_SIZE + 5);

        this.getChildren().add(player);
    }

    private void drawMap() {

        for (int r = 0; r < map.length; r++) {
            for (int c = 0; c < map[0].length; c++) {

                Rectangle tile = new Rectangle(TILE_SIZE, TILE_SIZE);
                tile.setTranslateX(c * TILE_SIZE);
                tile.setTranslateY(r * TILE_SIZE);

                tile.setFill(map[r][c] == 1 ? Color.DARKGRAY : Color.LIGHTGRAY);

                int finalR = r;
                int finalC = c;

                tile.setOnMouseClicked(e -> {
                    if (!isMoving && map[finalR][finalC] != 1) {
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

    private void walkPath(List<int[]> path) {

        if (path.isEmpty()) return;

        isMoving = true;

        int[] next = path.remove(0);

        TranslateTransition move = new TranslateTransition();
        move.setNode(player);
        move.setDuration(Duration.seconds(0.15));
        move.setToX(next[1] * TILE_SIZE + 5);
        move.setToY(next[0] * TILE_SIZE + 5);

        move.setOnFinished(e -> {
            playerRow = next[0];
            playerCol = next[1];

            if (path.isEmpty()) {
                isMoving = false;
            } else {
                walkPath(path);
            }
        });

        move.play();
    }
}
