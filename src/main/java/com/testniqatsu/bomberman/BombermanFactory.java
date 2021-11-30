package com.testniqatsu.bomberman;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;

public class BombermanFactory implements EntityFactory {

    @Spawns("aroundWall")
    public Entity newAroundWall(SpawnData data) {
        var physicsComponent = new PhysicsComponent();
        physicsComponent.setBodyType(BodyType.STATIC);

        var width = (int) data.get("width");
        var height = (int) data.get("height");

        return FXGL.entityBuilder(data)
                .type(BombermanType.WALL)
                .bbox(new HitBox(BoundingShape.box(width, height)))
                .with(physicsComponent)
                .build();
    }

    @Spawns("wall")
    public Entity newWall(SpawnData data) {
        var physicsComponent = new PhysicsComponent();
        physicsComponent.setBodyType(BodyType.STATIC);

        var width = (int) data.get("width");
        var height = (int) data.get("height");

        return FXGL.entityBuilder(data)
                .type(BombermanType.WALL)
                .bbox(new HitBox(BoundingShape.box(width, height)))
                .with(physicsComponent)
                .build();
    }

}
