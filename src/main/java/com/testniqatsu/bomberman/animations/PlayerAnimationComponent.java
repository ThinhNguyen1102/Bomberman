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

import java.util.HashMap;
import java.util.Map;

public class PlayerAnimationComponent extends Component {
    final String spriteSheetName = "wizard.png";
    final int spriteSheetWidth = 96;
    final int spriteSheetHeight = 128;

    final int columns = 3;
    final int rows = 4;

    final Image spriteSheetImage = FXGL.image(spriteSheetName, spriteSheetWidth, spriteSheetHeight);
    final int framePerRows = columns;
    final int spriteWidth = spriteSheetWidth / columns;
    final int spriteHeight = spriteSheetHeight / rows;
    final Duration duration = Duration.seconds(1);

    enum State {
        STAY_DOWN,
        STAY_UP,
        STAY_RIGHT,
        STAY_LEFT,
        WALK_DOWN,
        WALK_UP,
        WALK_RIGHT,
        WALK_LEFT
    }

    Map<State, AnimationChannel> animationChannels;
    AnimatedTexture texture;
    Point2D centerPoint;

    public PlayerAnimationComponent() {}

    AnimationChannel loadAnimation(int startFrame, int endFrame) {
        return new AnimationChannel(
                spriteSheetImage,
                framePerRows,
                spriteWidth,
                spriteHeight,
                duration,
                startFrame,
                endFrame
        );
    }
}
