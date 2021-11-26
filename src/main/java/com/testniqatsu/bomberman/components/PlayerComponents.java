package com.testniqatsu.bomberman.components;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.testniqatsu.bomberman.animations.PlayerAnimationComponent;

public class PlayerComponents extends Component {
    PlayerAnimationComponent animationComponent;
    PhysicsComponent physicsComponent;

    public PlayerComponents(PlayerAnimationComponent animationComponent, PhysicsComponent physicsComponent)  {
        this.animationComponent = animationComponent;
        this.physicsComponent = physicsComponent;
    }

    @Override
    public void onAdded() {
        animationComponent.onAdded();
    }

    @Override
    public void onUpdate(double tpf) {
        // update animation
        // update position
    }

    public void moveDown() {}

    public void moveUp() {}

    public void moveRight() {}

    public void moveLeft() {}
}
