package com.testniqatsu.bomberman.animations;

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
        final int framesPerRow = 4;
        final int charWidth = 176;
        final int charHeight = 248;

        animIdleDown = new AnimationChannel(FXGL.image("touhou_char.png"),
                framesPerRow, charWidth, charHeight, Duration.seconds(1), 0, 0);

        animIdleLeft = new AnimationChannel(FXGL.image("touhou_char.png"),
                framesPerRow, charWidth, charHeight, Duration.seconds(1), 4, 4);

        animIdleRight = new AnimationChannel(FXGL.image("touhou_char.png"),
                framesPerRow, charWidth, charHeight, Duration.seconds(1), 8, 8);

        animIdleUp = new AnimationChannel(FXGL.image("touhou_char.png"),
                framesPerRow, charWidth, charHeight, Duration.seconds(1), 12, 12);

        animWalkDown = new AnimationChannel(FXGL.image("touhou_char.png"),
                framesPerRow, charWidth, charHeight, Duration.seconds(1), 0, 3);

        animWalkLeft = new AnimationChannel(FXGL.image("touhou_char.png"),
                framesPerRow, charWidth, charHeight, Duration.seconds(1), 4, 7);

        animWalkRight = new AnimationChannel(FXGL.image("touhou_char.png"),
                framesPerRow, charWidth, charHeight, Duration.seconds(1), 8, 11);

        animWalkUp = new AnimationChannel(FXGL.image("touhou_char.png"),
                framesPerRow, charWidth, charHeight, Duration.seconds(1), 12, 15);

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
        entity.getTransformComponent().setScaleOrigin(new Point2D(44, 62));
        entity.getViewComponent().addChild(texture);
    }

    @Override
    public void onUpdate(double tpf) {
        // what is this tpf? I think this is sort of like delta in other game engines, perhaps.
        entity.translate(speedX * tpf, speedY * tpf);

        if (speedX != 0 || speedY != 0) {
            state = 1;
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

            if (speedX < 1 && speedY < 1) {
                speedX = 0;
                speedY = 0;
                state = 0; // return to idle
            }
        }

        if (state == 0) {
            switch (direction) {
                case 0: texture.loopAnimationChannel(animIdleDown);
                case 1: texture.loopAnimationChannel(animIdleRight);
                case 2: texture.loopAnimationChannel(animIdleUp);
                case 3: texture.loopAnimationChannel(animIdleLeft);
            }
        } else {
            switch (direction) {
                case 0: texture.loopAnimationChannel(animWalkDown);
                case 1: texture.loopAnimationChannel(animWalkRight);
                case 2: texture.loopAnimationChannel(animWalkUp);
                case 3: texture.loopAnimationChannel(animWalkLeft);
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
