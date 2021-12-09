package com.testniqatsu.bomberman.components;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.util.Duration;

import static com.testniqatsu.bomberman.constants.GameConst.SIZE_BLOCK;
import static com.almasb.fxgl.dsl.FXGLForKtKt.image;

public class BlockComponent extends Component {
    private AnimatedTexture texture;
    private AnimationChannel animationChannel;

    public BlockComponent(int startF, int endF, double duration) {
        animationChannel = new AnimationChannel(image("sprites.png"), 16, SIZE_BLOCK, SIZE_BLOCK,
                Duration.seconds(duration), startF, endF);

        texture = new AnimatedTexture(animationChannel);
        texture.loop();
    }

    @Override
    public void onAdded() {
        entity.getViewComponent().addChild(texture);
    }
}