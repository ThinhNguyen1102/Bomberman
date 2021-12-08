package com.testniqatsu.bomberman.components;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.testniqatsu.bomberman.BombermanType;
import javafx.util.Duration;

import java.util.ArrayList;

import static com.almasb.fxgl.dsl.FXGL.onCollisionEnd;
import static com.almasb.fxgl.dsl.FXGL.spawn;
import static com.almasb.fxgl.dsl.FXGLForKtKt.geti;
import static com.almasb.fxgl.dsl.FXGLForKtKt.image;
import static com.testniqatsu.bomberman.constants.GameConst.SIZE_BLOCK;

public class BombComponent extends Component {
    private ArrayList<Entity> listFire = new ArrayList<>();
    Entity wallBomb;

    private AnimatedTexture texture;
    private AnimationChannel animation;

    public BombComponent() {
        onCollisionEnd(BombermanType.BOMB, BombermanType.PLAYER, (b, p) -> {
            if (entity != null) {
                wallBomb = spawn("wall_bomb", new SpawnData(entity.getX(), entity.getY()));
            }
        });

        animation = new AnimationChannel(image("sprites.png"), 16, SIZE_BLOCK, SIZE_BLOCK,
                Duration.seconds(0.5), 72, 74);
        texture = new AnimatedTexture(animation);
        texture.loop();
    }

    @Override
    public void onAdded() {
        entity.getViewComponent().addChild(texture);
    }

    public void explode() {
        int flameLength = geti("flame");
        for (int i = 1; i <= flameLength - 1; i++) {
            listFire.add(spawn("horizontalFlame", new SpawnData(entity.getX() + SIZE_BLOCK * i, entity.getY())));
            listFire.add(spawn("horizontalFlame", new SpawnData(entity.getX() - SIZE_BLOCK * i, entity.getY())));
            listFire.add(spawn("verticalFlame", new SpawnData(entity.getX(), entity.getY() + SIZE_BLOCK * i)));
            listFire.add(spawn("verticalFlame", new SpawnData(entity.getX(), entity.getY() - SIZE_BLOCK * i)));
        }

        listFire.add(spawn("rightEFlame", new SpawnData(entity.getX() + SIZE_BLOCK * flameLength, entity.getY())));
        listFire.add(spawn("leftEFlame", new SpawnData(entity.getX() - SIZE_BLOCK * flameLength, entity.getY())));
        listFire.add(spawn("bottomEFlame", new SpawnData(entity.getX(), entity.getY() + SIZE_BLOCK * flameLength)));
        listFire.add(spawn("aboveEFlame", new SpawnData(entity.getX(), entity.getY() - SIZE_BLOCK * flameLength)));
        listFire.add(spawn("centerFlame", new SpawnData(entity.getX(), entity.getY())));

        //clear Flame
        clearFlame();
    }

    public void clearFlame() {
        FXGL.getGameTimer().runOnceAfter(() -> {
            for (Entity value : listFire) {
                value.removeFromWorld();
            }
        }, Duration.seconds(0.2));
        if (wallBomb != null) wallBomb.removeFromWorld();
        entity.removeFromWorld();
    }
}