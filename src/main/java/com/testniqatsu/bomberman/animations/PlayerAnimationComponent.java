package com.testniqatsu.bomberman.animations;

import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.geometry.Point2D;
import javafx.util.Duration;

public class PlayerAnimationComponent extends Component {

    // original sprite sheet data
    private final int rows = 4;
    private final int columns = 4;
    private final int width = 704;
    private final int height = 992;
    
    // character data
    private final int scale = 4;
    
    private final int characterWidth = width / columns / scale;
    private final int characterHeight = height / rows / scale;

    private int speedX;
    private int speedY;

    // speed is inverse proportional with scale
    private final int maxSpeed = 800 / scale;

    // current entity state
    // 0: idle
    // 1: walk
    private int state; // default 0

    // 0: down
    // 1: right
    // 2: up
    // 3: left
    private int direction; // default 0

    
    // animation data
    private final AnimatedTexture texture;
    private final AnimationChannel animIdleDown, animIdleRight, animIdleLeft, animIdleUp;
    private final AnimationChannel animWalkDown, animWalkRight, animWalkLeft, animWalkUp;
    
    
    /**
     * Define animations for each movement of the character
     *
     * @author Khoi Nguyen Truong
     * @since 0.5.0
     */
    public PlayerAnimationComponent() {
        final var sprites = FXGL.image(
                "touhou_char.png",
                characterWidth * columns,
                characterHeight * columns);

        // in this case, frames per row is equal to number of columns

        // idle animation
        animIdleDown = new AnimationChannel(sprites, columns,
                characterWidth, characterHeight,
                Duration.seconds(0.5), 0, 0);
        animIdleLeft = new AnimationChannel(sprites, columns,
                characterWidth, characterHeight,
                Duration.seconds(0.5), 4, 4);
        animIdleRight = new AnimationChannel(sprites, columns,
                characterWidth, characterHeight,
                Duration.seconds(0.5), 8, 8);
        animIdleUp = new AnimationChannel(sprites, columns,
                characterWidth, characterHeight,
                Duration.seconds(0.5), 12, 12);

        // walking animation
        animWalkDown = new AnimationChannel(sprites, columns,
                characterWidth, characterHeight,
                Duration.seconds(1), 1, 3);
        animWalkLeft = new AnimationChannel(sprites, columns,
                characterWidth, characterHeight,
                Duration.seconds(1), 5, 7);
        animWalkRight = new AnimationChannel(sprites, columns,
                characterWidth, characterHeight,
                Duration.seconds(1), 9, 11);
        animWalkUp = new AnimationChannel(sprites, columns,
                characterWidth, characterHeight,
                Duration.seconds(1), 13, 15);

        texture = new AnimatedTexture(animIdleDown);
    }

    /**
     * Called by entity when adding.
     *
     * @author Khoi Nguyen Truong
     * @since 0.5.0
     */
    @Override
    public void onAdded() {
        final var centerX = characterWidth / 2;
        final var centerY = characterHeight / 2;
        entity.getTransformComponent().setScaleOrigin(new Point2D(centerX, centerY));
        entity.getViewComponent().addChild(texture);
    }

    /**
     * Update animation state.
     *
     * @param tpf time it takes to loop the game
     *
     * @author Khoi Nguyen Truong
     * @since 0.5.0
     */
    @Override
    public void onUpdate(double tpf) {
        // move entity
        entity.translate(speedX * tpf, speedY * tpf);


        if (speedX != 0 || speedY != 0) {
            state = 1;

            // slow down entity
            speedX = (int) (speedX * 0.9);
            speedY = (int) (speedY * 0.9);

            if (FXGLMath.abs(speedX) < 1 && FXGLMath.abs(speedY) < 1) {
                speedX = 0;
                speedY = 0;
                state = 0; // return to idle
            }
        }

        switch ((state * 2 - 1) * (direction + 1)) {
            case -1 -> texture.loopNoOverride(animIdleDown);
            case -2 -> texture.loopNoOverride(animIdleRight);
            case -3 -> texture.loopNoOverride(animIdleUp);
            case -4 -> texture.loopNoOverride(animIdleLeft);

            case 1 -> texture.loopNoOverride(animWalkDown);
            case 2 -> texture.loopNoOverride(animWalkRight);
            case 3 -> texture.loopNoOverride(animWalkUp);
            case 4 -> texture.loopNoOverride(animWalkLeft);
        }
    }

    public void moveDown() {
        speedY = maxSpeed;
        direction = 0;
    }

    public void moveRight () {
        speedX = maxSpeed;
        direction = 1;
    }

    public void moveUp() {
        speedY = -maxSpeed;
        direction = 2;
    }

    public void moveLeft() {
        speedX = -maxSpeed;
        direction = 3;
    }

}
