package Logic;

import javafx.scene.image.Image;

public class TileMap {

    // for create map

    private Image wallImage = new Image(getClass().getResourceAsStream("/map/block.png"));
    // street
    private Image streetImage90 = new Image(getClass().getResourceAsStream("/map/street90.png"));
    private Image streetImage180 = new Image(getClass().getResourceAsStream("/map/street180.png"));
    // wall
    private Image floorImage = new Image(getClass().getResourceAsStream("/map/grass2.png"));

    // dome //

    private Image domeleft = new Image(getClass().getResourceAsStream("/map/DomeLeft.png"));
    private Image domeright = new Image(getClass().getResourceAsStream("/map/DomeRight.png"));
    // 5,6

    private Image gym = new Image(getClass().getResourceAsStream("/map/store.png"));




}
