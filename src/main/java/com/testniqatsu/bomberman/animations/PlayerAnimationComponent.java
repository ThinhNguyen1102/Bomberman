package com.testniqatsu.bomberman.animations;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.util.Duration;

public class PlayerAnimationComponent extends Component {
    String spriteSheetName = "wizard.png";
    int spriteSheetWidth = 96 / 2;
    int spriteSheetHeight = 128 / 2;

    Image spriteSheet = FXGL.image(
            spriteSheetName,
            spriteSheetWidth,
            spriteSheetHeight);

    int rows = 4;
    int columns = 3;

    int spriteWidth = spriteSheetWidth / columns;
    int spriteHeight = spriteSheetHeight / rows;

    int framesPerRow = columns;

    Duration animationDuration = Duration.seconds(1);

    AnimationChannel animationIdleDown = loadAnimation(1, 1);
    AnimationChannel animationIdleLeft = loadAnimation(4, 4);
    AnimationChannel animationIdleRight = loadAnimation(7, 7);
    AnimationChannel animationIdleUp = loadAnimation(10, 10);

    AnimationChannel animationWalkDown = loadAnimation(0, 2);
    AnimationChannel animationWalkLeft = loadAnimation(3, 5);
    AnimationChannel animationWalkRight = loadAnimation(6, 8);
    AnimationChannel animationWalkUp = loadAnimation(9, 11);

    AnimatedTexture animationTexture = new AnimatedTexture(animationIdleDown);

    public enum State {
        IDLE_DOWN,
        IDLE_LEFT,
        IDLE_RIGHT,
        IDLE_UP,
        WALK_DOWN,
        WALK_LEFT,
        WALK_RIGHT,
        WALK_UP
    }

    AnimationChannel loadAnimation(int startFrame, int endFrame) {
        return new AnimationChannel(
                spriteSheet,
                framesPerRow,
                spriteWidth,
                spriteHeight,
                animationDuration,
                startFrame,
                endFrame
        );
    }

    @Override
    public void onAdded() {
        entity.getTransformComponent()
                .setScaleOrigin(
                        new Point2D(
                                spriteWidth / 2.0f,
                                spriteHeight / 2.0f));

        entity.getViewComponent().addChild(animationTexture);
    }

    public void playAnimation(State state) {
        switch (state) {
            case IDLE_DOWN -> animationTexture.loopNoOverride(animationIdleDown);
            case IDLE_LEFT -> animationTexture.loopNoOverride(animationIdleLeft);
            case IDLE_RIGHT -> animationTexture.loopNoOverride(animationIdleRight);
            case IDLE_UP -> animationTexture.loopNoOverride(animationIdleUp);

            case WALK_DOWN -> animationTexture.loopNoOverride(animationWalkDown);
            case WALK_LEFT -> animationTexture.loopNoOverride(animationWalkLeft);
            case WALK_RIGHT -> animationTexture.loopNoOverride(animationWalkRight);
            case WALK_UP -> animationTexture.loopNoOverride(animationWalkUp);
        }
    }
}
