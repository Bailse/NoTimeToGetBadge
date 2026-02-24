package ui;

import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class AnimatedAvatar extends ImageView {

    private Image frame1;
    private Image frame2;
    private boolean toggle = false;
    private long lastSwitch = 0;

    public AnimatedAvatar(String imagePath) {

        frame1 = new Image(getClass().getResourceAsStream(imagePath));
        frame2 = new Image(getClass().getResourceAsStream(imagePath));

        setImage(frame1);
        setFitWidth(64);
        setFitHeight(64);

        startAnimation();
    }

    private void startAnimation() {

        AnimationTimer timer = new AnimationTimer() {

            @Override
            public void handle(long now) {

                if (now - lastSwitch > 300_000_000) { // 0.3 sec
                    toggle = !toggle;
                    setImage(toggle ? frame1 : frame2);
                    lastSwitch = now;
                }
            }
        };

        timer.start();
    }
}