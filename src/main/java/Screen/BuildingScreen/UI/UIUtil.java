package Screen.BuildingScreen.UI;

import javafx.scene.text.Font;

public class UIUtil {

    private static Font pixelFont;

    public static Font getPixelFont(double size) {
        if (pixelFont == null) {
            pixelFont = Font.loadFont(
                    UIUtil.class.getResourceAsStream("/fonts/PressStart2P-Regular.ttf"),
                    size
            );
        }
        return Font.font(pixelFont.getFamily(), size);
    }
}