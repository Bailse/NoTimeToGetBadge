package Logic;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class SoundManager {

    private static MediaPlayer backgroundPlayer;

    public static void playBackground(String filename) {

        try {

            if (backgroundPlayer != null) {
                backgroundPlayer.stop();
            }

            Media media = new Media(
                    SoundManager.class
                            .getResource("/music/" + filename)
                            .toExternalForm()
            );

            backgroundPlayer = new MediaPlayer(media);

            backgroundPlayer.setCycleCount(MediaPlayer.INDEFINITE);

            backgroundPlayer.setVolume(0.5);

            backgroundPlayer.play();

        } catch (Exception e) {
            System.out.println("Cannot play music: " + filename);
            e.printStackTrace();
        }

    }

    public static void stopBackground() {
        if (backgroundPlayer != null) {
            backgroundPlayer.stop();
        }
    }

}
