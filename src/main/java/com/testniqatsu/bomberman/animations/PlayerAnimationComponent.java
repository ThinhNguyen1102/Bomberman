package com.testniqatsu.bomberman.animations;

import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.geometry.Point2D;
import javafx.util.Duration;

public class PlayerAnimationComponent extends Component {

    private final AnimatedTexture texture;
    private final AnimationChannel animIdleDown, animIdleRight, animIdleLeft, animIdleUp;
    private final AnimationChannel animWalkDown, animWalkRight, animWalkLeft, animWalkUp;

    private int speedX;
    private int speedY;

    // use number to represent character state
    // 0: idle
    // 1: walk
    private int state = 0;

    // also use number to represent direction
    // 0: down
    // 1: right
    // 2: up
    // 3: left
    private int direction = 0;

    /**
     * Define animation for character movement. Each state can be represented by a "video" .aka
     * animation channel.
     *
     * @author Khoi Nguyen Truong
     * @since 0.5.0
     */
    public PlayerAnimationComponent() {
        final var framesPerRow = 4;

        // scale down 8 times the original
        final var charWidth = 176 / 8;
        final var charHeight = 248 / 8;

        // sprite sheet is now 88 x 124
        var sprites = FXGL.image("touhou_char.png", 88, 124);
        
        // idle animation
        animIdleDown = new AnimationChannel(sprites,
                framesPerRow, charWidth, charHeight,
                Duration.seconds(0.5), 0, 0);

        animIdleLeft = new AnimationChannel(sprites,
                framesPerRow, charWidth, charHeight,
                Duration.seconds(0.5), 4, 4);

        animIdleRight = new AnimationChannel(sprites,
                framesPerRow, charWidth, charHeight,
                Duration.seconds(0.5), 8, 8);

        animIdleUp = new AnimationChannel(sprites,
                framesPerRow, charWidth, charHeight,
                Duration.seconds(0.5), 12, 12);

        // walking animation
        animWalkDown = new AnimationChannel(sprites,
                framesPerRow, charWidth, charHeight,
                Duration.seconds(1), 1, 3);

        animWalkLeft = new AnimationChannel(sprites,
                framesPerRow, charWidth, charHeight,
                Duration.seconds(1), 5, 7);

        animWalkRight = new AnimationChannel(sprites,
                framesPerRow, charWidth, charHeight,
                Duration.seconds(1), 9, 11);

        animWalkUp = new AnimationChannel(sprites,
                framesPerRow, charWidth, charHeight,
                Duration.seconds(1), 13, 15);

        texture = new AnimatedTexture(animIdleDown);
    }

    /**
     * Called by the entity when adding this component.
     *
     * @author Khoi Nguyen Truong
     * @since 0.5.0
     */
    @Override
    public void onAdded() {
        // because sprite is now 22 x 31, the origin will be 11, 15.5
        entity.getTransformComponent().setScaleOrigin(new Point2D(88, 124));
        entity.getViewComponent().addChild(texture);
    }

    @Override
    public void onUpdate(double tpf) {
        // what is this tpf? I think this is sort of like delta in other game engines, perhaps.
        entity.translate(speedX * tpf, speedY * tpf);

        if (speedX != 0 || speedY != 0) {
            state = 1;

            // changing direction
            if (speedX > 0) {
                direction = 1; // right
            } else if (speedX < 0) {
                direction = 3; // left
            }

            if (speedY > 0) {
                direction = 0; // down
            } else if (speedY < 0) {
                direction = 2; // up
            }

            speedX = (int) (speedX * 0.9);
            speedY = (int) (speedY * 0.9);

            if (FXGLMath.abs(speedX) < 1 && FXGLMath.abs(speedY) < 1) {
                speedX = 0;
                speedY = 0;
                state = 0; // return to idle
            }
        }

        if (state == 0) {
            switch (direction) {
                case 0 -> texture.loopNoOverride(animIdleDown);
                case 1 -> texture.loopNoOverride(animIdleRight);
                case 2 -> texture.loopNoOverride(animIdleUp);
                case 3 -> texture.loopNoOverride(animIdleLeft);
            }
        }

        if (state == 1) {
            switch (direction) {
                case 0 -> texture.loopNoOverride(animWalkDown);
                case 1 -> texture.loopNoOverride(animWalkRight);
                case 2 -> texture.loopNoOverride(animWalkUp);
                case 3 -> texture.loopNoOverride(animWalkLeft);
            }
        }
    }

    public void moveRight () {
        speedX = 250;
    }

    public void moveLeft() {
        speedX = -250;
    }

    public void moveUp() {
        speedY = -250;
    }

    public void moveDown() {
        speedY = 250;
    }
}
