package com.testniqatsu.bomberman.components;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.pathfinding.CellState;
import com.almasb.fxgl.pathfinding.astar.AStarGrid;
import com.almasb.fxgl.physics.PhysicsWorld;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.testniqatsu.bomberman.BombermanType;
import com.testniqatsu.bomberman.components.enemy.BalloomComponent;
import com.testniqatsu.bomberman.components.enemy.DoriaComponent;
import com.testniqatsu.bomberman.components.enemy.OnealComponent;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.testniqatsu.bomberman.constants.GameConst.SCORE_BRICK;
import static com.testniqatsu.bomberman.constants.GameConst.SIZE_BLOCK;

public class FlameComponent extends Component {
    private AnimatedTexture texture;
    private AnimationChannel animationFlame;


    public FlameComponent() {
        PhysicsWorld physics = getPhysicsWorld();

        onCollisionBegin(BombermanType.FIRE, BombermanType.WALL, (f, w) -> {
            f.removeFromWorld();
        });

        setCollisionBreak(BombermanType.BRICK, "brick_break");

        setCollisionBreak(BombermanType.GRASS, "grass_break");

        setCollisionBreak(BombermanType.CORAL, "coral_break");

        onCollisionBegin(BombermanType.FIRE, BombermanType.BALLOOM_E, (f, b) -> {
            b.getComponent(BalloomComponent.class).enemyDie();
            getGameTimer().runOnceAfter(b::removeFromWorld, Duration.seconds(1));
        });

        onCollisionBegin(BombermanType.FIRE, BombermanType.ONEAL_E, (f, o) -> {
            o.getComponent(OnealComponent.class).enemyDie();
            getGameTimer().runOnceAfter(o::removeFromWorld, Duration.seconds(1));
        });

        onCollisionBegin(BombermanType.FIRE, BombermanType.DORIA_E, (f, d) -> {
            d.getComponent(DoriaComponent.class).enemyDie();
            getGameTimer().runOnceAfter(d::removeFromWorld, Duration.seconds(1));
        });

        animationFlame = new AnimationChannel(image("bomb_exploded_1.png"), 3, SIZE_BLOCK, SIZE_BLOCK,
                Duration.seconds(0.4), 0, 2);

        texture = new AnimatedTexture(animationFlame);
        texture.loop();
    }

    private void setCollisionBreak(BombermanType type, String nameTypeBreakAnim) {
        onCollisionBegin(BombermanType.FIRE, type, (f, t) -> {
            int cellX = (int)((t.getX() + 24) / SIZE_BLOCK);
            int cellY = (int)((t.getY() + 24) / SIZE_BLOCK);

            AStarGrid grid = geto("grid");
            grid.get(cellX, cellY).setState(CellState.WALKABLE);
            set("grid", grid);

            Entity bBreak = spawn(nameTypeBreakAnim, new SpawnData(t.getX(), t.getY()));
            t.removeFromWorld();
            getGameTimer().runOnceAfter(bBreak::removeFromWorld, Duration.seconds(1));
            inc("score", SCORE_BRICK);
        });
    }

    @Override
    public void onAdded() {
        entity.getViewComponent().addChild(texture);
    }
}
