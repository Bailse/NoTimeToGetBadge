package Screen.BuildingScreen;

import Logic.GamePaneImage;

/**
 * Represents an item or action that can be shown as a button in a shop-style popup.
 */
public interface ShopItem {

    /**
     * @return the display name shown on the button
     */
    String getName();

    /**
     * @return the price cost
     */
    int getPrice();

    /**
     * @return the button color (hex string)
     */
    String getColor(); // สีประจำปุ่ม

    /**
     * Runs the item action
     */
    void execute(GamePaneImage gamePane); // Action เมื่อกดซื้อ
}