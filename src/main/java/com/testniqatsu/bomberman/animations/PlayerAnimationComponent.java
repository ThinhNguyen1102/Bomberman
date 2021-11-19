package com.testniqatsu.bomberman.animations;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.util.Duration;

public class PlayerAnimationComponent extends Component {

    final String spriteSheetName = "wizard.png";
    final int spriteSheetWidth = 96;
    final int spriteSheetHeight = 128;
    final int numberOfColumn = 3;
    final int numberOfRow = 4;
    final int spriteWidth = spriteSheetWidth / numberOfColumn;
    final int spriteHeight = spriteSheetHeight / numberOfRow;


    final Image spriteSheetImage = FXGL.image(spriteSheetName);

    final AnimationChannel idleDown = loadAnimation(1, 1);
    final AnimationChannel idleLeft = loadAnimation(4, 4);
    final AnimationChannel idleRight = loadAnimation(7, 7);
    final AnimationChannel idleUp = loadAnimation(10, 10);

    final AnimationChannel walkDown = loadAnimation(0, 2);
    final AnimationChannel walkLeft = loadAnimation(3, 5);
    final AnimationChannel walkRight = loadAnimation(6, 8);
    final AnimationChannel walkUp = loadAnimation(9, 11);

    final AnimatedTexture texture = new AnimatedTexture(idleDown);

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

    @Override
    public void onAdded() {
        entity.setScaleOrigin(new Point2D(spriteWidth / 2f, spriteHeight / 2f));
        entity.getViewComponent().addChild(texture);
    }

    @Override
    public void onUpdate(double tpf) {
        // accelerate
    }

    // movement


}
