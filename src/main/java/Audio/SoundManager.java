package Audio;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * Class that Create Sound and control sound
 */

public class SoundManager {

    private static MediaPlayer backgroundPlayer;
    private static boolean muted = false;

    /**
     *  control sound and initialize sound by using string to convert to music in game
     */



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
            backgroundPlayer.setVolume(muted ? 0 : 0.10);
            backgroundPlayer.play();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * stop the music in game
     */


    public static void stopBackground() {
        if (backgroundPlayer != null) {
            backgroundPlayer.stop();
        }
    }

    /**
     * set music volume in game by using toggle mute in game
     */

    public static void toggleMute() {
        muted = !muted;
        if (backgroundPlayer != null) {
            backgroundPlayer.setVolume(muted ? 0 : 0.10);
        }
    }

    /**
     * check that sound is Muted yet
     */

    public static boolean isMuted() {
        return muted;
    }
}
