package com.testniqatsu.bomberman.animations;

import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.util.Duration;

public class PlayerAnimationComponent extends Component {

    final String spriteSheetName = "wizard.png";
    final int spriteSheetWidth = 96 / 2; // scale down 2 times
    final int spriteSheetHeight = 128 / 2; // scale down 2 times
    final int numberOfColumn = 3;
    final int numberOfRow = 4;
    final int spriteWidth = spriteSheetWidth / numberOfColumn;
    final int spriteHeight = spriteSheetHeight / numberOfRow;


    final Image spriteSheetImage = FXGL.image(spriteSheetName, spriteSheetWidth, spriteSheetHeight);

    final AnimationChannel idleDown = loadAnimation(1, 1);
    final AnimationChannel idleLeft = loadAnimation(4, 4);
    final AnimationChannel idleRight = loadAnimation(7, 7);
    final AnimationChannel idleUp = loadAnimation(10, 10);

    final AnimationChannel walkDown = loadAnimation(0, 2);
    final AnimationChannel walkLeft = loadAnimation(3, 5);
    final AnimationChannel walkRight = loadAnimation(6, 8);
    final AnimationChannel walkUp = loadAnimation(9, 11);

    final AnimatedTexture texture = new AnimatedTexture(idleDown);

    final int maxSpeed = 250;
    final int acceleration = (int) (250 * 0.4f); // acceleration is 40%

    int speedX;
    int speedY;

    // 0: down
    // 1: right
    // 2: up
    // 3: left
    int direction;

    AnimationChannel loadAnimation(int start, int end) {
        return new AnimationChannel(
                spriteSheetImage,
                numberOfColumn,
                spriteWidth,
                spriteHeight,
                Duration.seconds(1),
                start,
                end
        );
    }

    /**
     * called when being added.
     *
     * @author Khoi Nguyen Truong
     * @since 0.7.2
     */
    @Override
    public void onAdded() {
        entity.setScaleOrigin(new Point2D(spriteWidth / 2f, spriteHeight / 2f));
        entity.getViewComponent().addChild(texture);
    }

    /**
     * Called each frame.
     *
     * @param tpf time of each frame
     *
     * @author Khoi Nguyen Truong
     * @since 0.7.2
     */
    @Override
    public void onUpdate(double tpf) {
        // move character
        entity.translate(speedX * tpf, speedY * tpf);

        // set animation for character
        if (speedX != 0 || speedY != 0) {

            if (speedX > 0) {
                direction = 1;
            } else if (speedX < 0) {
                direction = 3;
            } else if (speedY > 0) {
                direction = 0;
            } else {
                direction = 2;
            }

            switch (direction) {
                case 0 -> texture.loopNoOverride(walkDown);
                case 1 -> texture.loopNoOverride(walkRight);
                case 2 -> texture.loopNoOverride(walkUp);
                case 3 -> texture.loopNoOverride(walkLeft);
            }

            speedX = (int) (speedX * 0.1f);
            speedY = (int) (speedY * 0.1f);

            if (FXGLMath.abs(speedX) < 0 && FXGLMath.abs(speedY) < 0) {
                speedX = 0;
                speedY = 0;
            }

        } else {
            switch (direction) {
                case 0 -> texture.loopNoOverride(idleDown);
                case 1 -> texture.loopNoOverride(idleRight);
                case 2 -> texture.loopNoOverride(idleUp);
                case 3 -> texture.loopNoOverride(idleLeft);
            }
        }
    }

    public void moveDown() {
        if (speedY < maxSpeed) {
            speedY += acceleration;
        }
    }

    public void moveRight() {
        if (speedX < maxSpeed) {
            speedX += acceleration;
        }
    }

    public void moveUp() {
        if (speedY > -maxSpeed) {
            speedY -= acceleration;
        }
    }

    public void moveLeft() {
        if (speedX > -maxSpeed) {
            speedX -= acceleration;
        }
    }

}
