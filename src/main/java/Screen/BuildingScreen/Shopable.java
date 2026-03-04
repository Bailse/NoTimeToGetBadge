package Screen.BuildingScreen;

import javafx.scene.control.Button;
import Character.BasePlayer;

/**
 * Helper interface for popups that show a list of purchasable/selectable {@link ShopItem}s.
 */
public interface Shopable extends Normal {

    /**
     * Creates a button for a {@link ShopItem}. When clicked, it checks the player's money and
     * check if the player can afford it.
     */
    default Button createShopButton(ShopItem item, Logic.GamePane gamePane, Runnable refreshUI) {
        BasePlayer p = gamePane.getPlayer();
        String buttonText = (item.getPrice() > 0)
                ? item.getName() + "\n$" + item.getPrice()
                : item.getName();

        Button btn = new Button(buttonText);


        applyPixelStyle(btn, item.getColor());

        btn.setOnAction(e -> {

            if (p.getMoney() >= item.getPrice()) {


                item.execute(gamePane);

                gamePane.notifyUpdate();
                refreshUI.run();

                System.out.println("Purchased: " + item.getName());
            } else {
                System.out.println("Not enough money!");
            }
        });

        return btn;
    }
}