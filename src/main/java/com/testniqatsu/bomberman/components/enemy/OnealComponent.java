package com.testniqatsu.bomberman.components.enemy;


import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Required;
import com.almasb.fxgl.entity.components.BoundingBoxComponent;
import com.almasb.fxgl.pathfinding.astar.AStarMoveComponent;
import com.almasb.fxgl.texture.AnimationChannel;
import com.testniqatsu.bomberman.BombermanType;
import javafx.util.Duration;

import java.util.List;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.almasb.fxgl.dsl.FXGL.image;

@Required(AStarMoveComponent.class)
public class OnealComponent extends NormalEnemy {
    private double oldX = 0;
    private double oldY = 0;

    protected boolean die = false;
    protected AStarMoveComponent astar;
    protected boolean moveWithAi = true;
    protected int rangeDetectPlayer = 60;

    public void setRangeDetectPlayer(int rangeDetectPlayer) {
        this.rangeDetectPlayer = rangeDetectPlayer;
    }

    public OnealComponent() {
        super();
        onCollisionBegin(BombermanType.ONEAL_E, BombermanType.BOMB, (o, w) -> {
            o.getComponent(OnealComponent.class).turn();
        });

        onCollisionBegin(BombermanType.ONEAL_E, BombermanType.WALL, (o, w) -> {
            o.getComponent(OnealComponent.class).turn();
        });

        onCollisionBegin(BombermanType.ONEAL_E, BombermanType.AROUND_WALL, (o, w) -> {
            o.getComponent(OnealComponent.class).turn();
        });

        onCollisionBegin(BombermanType.ONEAL_E, BombermanType.BRICK, (o, br) -> {
            o.getComponent(OnealComponent.class).turn();
        });

        onCollisionBegin(BombermanType.ONEAL_E, BombermanType.GRASS, (o, gr) -> {
            o.getComponent(OnealComponent.class).turn();
        });

        onCollisionBegin(BombermanType.ONEAL_E, BombermanType.CORAL, (o, gr) -> {
            o.getComponent(OnealComponent.class).turn();
        });
    }

    @Override
    public void onUpdate(double tpf) {
        if (!die) {
            if (!moveWithAi) {
                astar.pause();
                // fix bug move
                double x = entity.getX() - oldX;
                double y = entity.getY() - oldY;

                oldX = entity.getX();
                oldY = entity.getY();

                if (x == 0 && y == 0) turn();
                //
                entity.setScaleUniform(0.9);
                entity.translateX(dx * tpf);
                entity.translateY(dy * tpf);
            } else {
                astar.resume();
                move();
            }
            setAnimationStage();
            detectPlayer();
        }
    }

    private void move() {
        var player = FXGL.getGameWorld().getSingleton(BombermanType.PLAYER);

        int x = player.call("getCellX");
        int y = player.call("getCellY");

        astar.moveToCell(x, y);
    }

    protected void detectPlayer() {
        BoundingBoxComponent bbox = entity.getBoundingBoxComponent();
        List<Entity> list = FXGL.getGameWorld().getEntitiesInRange(bbox.range(rangeDetectPlayer, rangeDetectPlayer));
        for (Entity entity : list) {
            if (entity.isType(BombermanType.BOMB)) {
                moveWithAi = false;
                return;
            }
        }
        for (int i = 0; i < list.size(); i++) {
            Entity entity = list.get(i);
            if (entity.isType(BombermanType.PLAYER)) {
                moveWithAi = true;
                break;
            }

            if (i == list.size() - 1) {
                moveWithAi = false;
            }
        }
    }

    @Override
    protected void setAnimationMove() {
        animDie = new AnimationChannel(image("sprites.png"), 16, SIZE_FLAME, SIZE_FLAME,
                Duration.seconds(ANIM_TIME), 38, 38);
        animWalkRight = new AnimationChannel(image("sprites.png"), 16, SIZE_FLAME, SIZE_FLAME,
                Duration.seconds(ANIM_TIME), 35, 37);
        animWalkLeft = new AnimationChannel(image("sprites.png"), 16, SIZE_FLAME, SIZE_FLAME,
                Duration.seconds(ANIM_TIME), 32, 34);
        animStop = new AnimationChannel(image("sprites.png"), 16, SIZE_FLAME, SIZE_FLAME,
                Duration.seconds(1), 32, 36);
    }

    @Override
    public void enemyDie() {
        FXGL.inc("numOfEnemy", -1);
        int ONEAL_SCORE = 200;
        showScore(ONEAL_SCORE);
        inc("score", ONEAL_SCORE);
        die = true;
        dx = 0;
        dy = 0;
        astar.pause();
        texture.loopNoOverride(animDie);
    }
}

