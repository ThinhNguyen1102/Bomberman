package com.testniqatsu.bomberman.animations;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.geometry.Point2D;
import javafx.util.Duration;

public class PlayerAnimationComponent extends Component {

    private int speed;

    private AnimatedTexture texture;
    private AnimationChannel animIdleDown, animIdleRight, animIdleLeft, animIdleUp;
    private AnimationChannel animWalkDown, animWalkRight, animWalkLeft, animWalkUp;

    /**
     * Define animation for character movement. Each state can be represented by a "video" .aka
     * animation channel.
     *
     * @author Khoi Nguyen Truong
     * @since 0.5.0
     */
    public PlayerAnimationComponent() {
        final int framesPerRows = 4;
        final int charWidth = 176;
        final int charHeight = 248;

        animIdleDown = new AnimationChannel(FXGL.image("touhou.png"),
                framesPerRows, charWidth, charHeight, Duration.seconds(1), 0, 0);

        animIdleLeft = new AnimationChannel(FXGL.image("touhou.png"),
                framesPerRows, charWidth, charHeight, Duration.seconds(1), 4, 4);

        animIdleRight = new AnimationChannel(FXGL.image("touhou.png"),
                framesPerRows, charWidth, charHeight, Duration.seconds(1), 8, 8);

        animIdleUp = new AnimationChannel(FXGL.image("touhou.png"),
                framesPerRows, charWidth, charHeight, Duration.seconds(1), 12, 12);

        animWalkDown = new AnimationChannel(FXGL.image("touhou.png"),
                framesPerRows, charWidth, charHeight, Duration.seconds(1), 0, 3);

        animWalkLeft = new AnimationChannel(FXGL.image("touhou.png"),
                framesPerRows, charWidth, charHeight, Duration.seconds(1), 4, 7);

        animWalkRight = new AnimationChannel(FXGL.image("touhou.png"),
                framesPerRows, charWidth, charHeight, Duration.seconds(1), 8, 11);

        animWalkUp = new AnimationChannel(FXGL.image("touhou.png"),
                framesPerRows, charWidth, charHeight, Duration.seconds(1), 12, 15);

        // still don't know what the heck is this thing called animated texture, but the tutorial has
        // it here, so I will do the same.
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
        // still don't understand set scale origin but this maybe a way to resize the character
        // sprite, I don't know, better keep it here.
        entity.getTransformComponent().setScaleOrigin(new Point2D(88, 124));
        // seems like the entity is whatever entity that can add this component
        // maybe in factory, entity is being generated after texture, hence we can not
        // add child in the constructor
        entity.getViewComponent().addChild(texture);
    }

    @Override
    public void onUpdate(double tpf) {

    }
}
