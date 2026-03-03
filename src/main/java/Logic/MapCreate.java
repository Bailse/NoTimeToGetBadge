package Logic;

import Screen.BuildingScreen.Park.Park;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Class for initialize map image in GamePane
 */


public class MapCreate {

    // for create map
    private ImageView choosing;

    private final Image wallImage = new Image(getClass().getResourceAsStream("/map/block.png"));
    // street
    private final Image streetImage90 = new Image(getClass().getResourceAsStream("/map/street90.png"));
    private final Image streetImage180 = new Image(getClass().getResourceAsStream("/map/street180.png"));
    // wall
    private final Image floorImage = new Image(getClass().getResourceAsStream("/map/grass2.png"));

    // dome  //

    private final Image domeleft = new Image(getClass().getResourceAsStream("/map/DomeLeft.png"));
    private final Image domeright = new Image(getClass().getResourceAsStream("/map/DomeRight.png"));

    // gym

    private final Image gymTop = new Image(getClass().getResourceAsStream("/map/gym_t.png"));
    private final Image gymMid = new Image(getClass().getResourceAsStream("/map/gym_m_2.png"));
    private final Image gymBot = new Image(getClass().getResourceAsStream("/map/gym_b.png"));

    // Uni

    private final Image Uni1 = new Image(getClass().getResourceAsStream("/map/Uni1.png"));
    private final Image Uni2 = new Image(getClass().getResourceAsStream("/map/Uni2.png"));

    // res
    private final Image resLeft = new Image(getClass().getResourceAsStream("/map/res1.png"));
    private final Image resRight = new Image(getClass().getResourceAsStream("/map/res2.png"));
    // travel

    private final Image traveLeft = new Image(getClass().getResourceAsStream("/map/travel1.png"));
    private final Image traveRight = new Image(getClass().getResourceAsStream("/map/travel2.png"));
    // park

    private final Image parkLeft = new Image(getClass().getResourceAsStream("/map/Park_left.png"));
    private final Image parkRight = new Image(getClass().getResourceAsStream("/map/Park_right.png"));

    // mall

    private final Image mallTop = new Image(getClass().getResourceAsStream("/map/mall_t.png"));
    private final Image mallMid = new Image(getClass().getResourceAsStream("/map/mall_m.png"));
    private final Image mallBot = new Image(getClass().getResourceAsStream("/map/mall_b.png"));

    // water

    private final Image water = new Image(getClass().getResourceAsStream("/map/water3.png"));
    private final Image watertop = new Image(getClass().getResourceAsStream("/map/water4.png"));

    // flower

    private final Image flower = new Image(getClass().getResourceAsStream("/map/flower2.png"));

    /**
     * create map Tile by using number image to set Image of each TILE
     *
     */
    public MapCreate(int number){

        switch (number){
            case 0:
                choosing = new ImageView(floorImage);
                break;
            case 1:
                choosing = new ImageView(wallImage);
                break;
            case 3:
                choosing = new ImageView(streetImage90);
                break;
            case 4:
                choosing = new ImageView(streetImage180);
                break;

            case 5:
                choosing = new ImageView(water);
                break;
            case 6:
                choosing = new ImageView(water);
                choosing.setRotate(180);
                break;
            case 7:
                choosing = new ImageView(watertop);
                break;
            case 8:
                choosing = new ImageView(flower);
                break;
            case 10:
                choosing = new ImageView(domeleft);
                break;
            case 11:
                choosing =  new ImageView(domeright);
                break;
            case 12:
                choosing = new ImageView(gymTop);
                break;
            case 13:
                choosing = new ImageView(gymMid);
                break;
            case 14:
                choosing = new ImageView(gymBot);
                break;
            case 15:
                choosing = new ImageView(Uni1);
                break;
            case 16:
                choosing = new ImageView(Uni2);
                break;
            case 17:
                choosing = new ImageView(resLeft);
                break;
            case 18:
                choosing = new ImageView(resRight);
                break;
            case 19:
                choosing = new ImageView(traveLeft);
                break;
            case 20:
                choosing = new ImageView(traveRight);
                break;
            case 21:
                choosing = new ImageView(parkLeft);
                break;
            case 22:
                choosing = new ImageView(parkRight);
                break;
            case 23:
                choosing = new ImageView(mallTop);
                break;
            case 24:
                choosing = new ImageView(mallMid);
                break;
            case 25:
                choosing = new ImageView(mallBot);
                break;
        }



    }

    public ImageView getChoosing() {
        return choosing;
    }

}
