package com.testniqatsu.bomberman.components.enemy;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.BoundingBoxComponent;
import com.almasb.fxgl.pathfinding.astar.AStarGrid;
import com.almasb.fxgl.texture.AnimationChannel;
import com.testniqatsu.bomberman.BombermanType;
import javafx.util.Duration;

import java.util.List;

import static com.almasb.fxgl.dsl.FXGL.inc;
import static com.almasb.fxgl.dsl.FXGL.onCollisionBegin;

public class DoriaComponent extends OnealComponent {
    private double timeChangeMove = 0.0;

    public DoriaComponent() {
        super();
        onCollisionBegin(BombermanType.DORIA_E, BombermanType.AROUND_WALL, (d, w) -> {
            d.getComponent(DoriaComponent.class).turn();
        });

        onCollisionBegin(BombermanType.DORIA_E, BombermanType.BOMB, (d, w) -> {
            d.getComponent(DoriaComponent.class).turn();
        });
    }

    @Override
    public void onUpdate(double tpf) {
        super.onUpdate(tpf);
        timeChangeMove += tpf;
        if (!moveWithAi && timeChangeMove > 3.0) {
            timeChangeMove = 0;
            if (Math.random() > 0.5) {
                turn();
            }
        }
    }


    @Override
    protected void setAnimationMove() {
        animDie = new AnimationChannel(FXGL.image("doria.png"), 7, SIZE_FLAME, SIZE_FLAME,
                Duration.seconds(1), 6, 6);
        animWalkRight = new AnimationChannel(FXGL.image("doria.png"), 7, SIZE_FLAME, SIZE_FLAME,
                Duration.seconds(ANIM_TIME), 3, 5);
        animWalkLeft = new AnimationChannel(FXGL.image("doria.png"), 7, SIZE_FLAME, SIZE_FLAME,
                Duration.seconds(ANIM_TIME), 0, 2);
        animStop = new AnimationChannel(FXGL.image("doria.png"), 7, SIZE_FLAME, SIZE_FLAME,
                Duration.seconds(1), 1, 6);
    }

    @Override
    public void enemyDie() {
        FXGL.inc("numOfEnemy", -1);
        int DORIA_SCORE = 500;
        showScore(DORIA_SCORE);
        inc("score", DORIA_SCORE);
        die = true;
        dx = 0;
        dy = 0;
        astar.pause();
        texture.loopNoOverride(animDie);
    }
}
