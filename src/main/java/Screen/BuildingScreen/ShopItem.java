package Screen.BuildingScreen;

import Logic.GamePane;

public interface ShopItem {
    String getName();
    int getPrice();
    String getColor(); // สีประจำปุ่ม
    void execute(GamePane gamePane); // Action เมื่อกดซื้อ
}