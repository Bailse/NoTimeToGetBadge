package Audio;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class SoundManager {

    private static MediaPlayer backgroundPlayer;
    private static boolean muted = false;

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
            backgroundPlayer.setVolume(muted ? 0 : 0.5);
            backgroundPlayer.play();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void stopBackground() {
        if (backgroundPlayer != null) {
            backgroundPlayer.stop();
        }
    }

    public static void toggleMute() {
        muted = !muted;
        if (backgroundPlayer != null) {
            backgroundPlayer.setVolume(muted ? 0 : 0.5);
        }
    }

    public static boolean isMuted() {
        return muted;
    }
}
