package com.testniqatsu.bomberman.animations;

import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
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

    final int maxSpeed = 2000;

    int speedX;
    int speedY;

    int direction;

    final int down = 0;
    final int right = 1;
    final int up = 2;
    final int left = 3;

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
        if (speedX != 0 || speedY != 0) {
            speedX = (int) (speedX * 0.9f);
            speedY = (int) (speedY * 0.9f);

            if (FXGLMath.abs(speedX) < 1 && FXGLMath.abs(speedY) < 1) {
                speedX = 0;
                speedY = 0;
            }

        } else {
            switch (direction) {
                case down -> texture.loopNoOverride(idleDown);
                case right -> texture.loopNoOverride(idleRight);
                case up -> texture.loopNoOverride(idleUp);
                case left -> texture.loopNoOverride(idleLeft);
            }
        }

        entity.getComponent(PhysicsComponent.class).setLinearVelocity(speedX * tpf, speedY * tpf);
    }

    public void moveDown() {
        speedY = maxSpeed;
        direction = down;
        texture.loopNoOverride(walkDown);
    }

    public void moveRight() {
        speedX = maxSpeed;
        direction = right;
        texture.loopNoOverride(walkRight);
    }

    public void moveUp() {
        speedY = -maxSpeed;
        direction = up;
        texture.loopNoOverride(walkUp);
    }

    public void moveLeft() {
        speedX = -maxSpeed;
        direction = left;
        texture.loopNoOverride(walkLeft);
    }

}
