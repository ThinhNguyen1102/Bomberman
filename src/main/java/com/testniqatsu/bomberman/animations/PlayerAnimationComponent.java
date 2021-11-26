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

    Image spriteSheetImage;
    int framePerRows;
    int spriteWidth;
    int spriteHeight;
    Duration duration;

    String spriteSheetName;
    int spriteSheetWidth;
    int SpriteSheetHeight;

    public PlayerAnimationComponent() {}
}
