package com.testniqatsu.bomberman;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.physics.PhysicsComponent;

public class BombermanFactory implements EntityFactory {

    @Spawns("aroundWall")
    public Entity newAroundWall(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(BombermanType.WALL)
                .with(new PhysicsComponent())
                .build();
    }

    @Spawns("wall")
    public Entity newWall(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(BombermanType.WALL)
                .with(new PhysicsComponent())
                .build();
    }

}
