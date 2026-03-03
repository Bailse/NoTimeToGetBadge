package Screen.UI;

import javafx.scene.text.Font;

/**
 * UItil for create pixelFont
 */
public class UIUtil {

    private static Font pixelFont;

    /**
     * for get Pixel font by using this class
     * @param size
     * @return
     */
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