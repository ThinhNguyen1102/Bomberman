package com.testniqatsu.bomberman.components;

import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.testniqatsu.bomberman.animations.PlayerAnimationComponent;

public class PlayerComponents extends Component {
    final int MAX_VELOCITY = 1000;

    enum Direction {
        DOWN, LEFT, RIGHT, UP
    }

    int velocityX;
    int velocityY;

    Direction direction;

    PlayerAnimationComponent animationComponent;
    PhysicsComponent physicsComponent;

    @Override
    public void onAdded() {
        animationComponent = entity.getComponent(PlayerAnimationComponent.class);
        physicsComponent = entity.getComponent(PhysicsComponent.class);
    }

    @Override
    public void onUpdate(double tpf) {
        if (velocityX != 0 && velocityY != 0) {
            velocityX = (int) (velocityX * 0.9f);
            velocityY = (int) (velocityY * 0.9f);

            if (FXGLMath.abs(velocityX) < 1 && FXGLMath.abs(velocityY) < 1) {
                velocityX = 0;
                velocityY = 0;

                switch (direction) {
                    case DOWN -> animationComponent.playAnimation(PlayerAnimationComponent.State.IDLE_DOWN);
                    case LEFT -> animationComponent.playAnimation(PlayerAnimationComponent.State.IDLE_LEFT);
                    case RIGHT -> animationComponent.playAnimation(PlayerAnimationComponent.State.IDLE_RIGHT);
                    case UP -> animationComponent.playAnimation(PlayerAnimationComponent.State.IDLE_UP);
                }
            }
        }

        physicsComponent.setLinearVelocity(velocityX * tpf, velocityY * tpf);
    }

    public void moveDown() {
        velocityY = MAX_VELOCITY;
        direction = Direction.DOWN;
        animationComponent.playAnimation(PlayerAnimationComponent.State.WALK_DOWN);
    }

    public void moveUp() {
        velocityY = -MAX_VELOCITY;
        direction = Direction.UP;
        animationComponent.playAnimation(PlayerAnimationComponent.State.WALK_UP);
    }

    public void moveRight() {
        velocityX = MAX_VELOCITY;
        direction = Direction.RIGHT;
        animationComponent.playAnimation(PlayerAnimationComponent.State.WALK_RIGHT);
    }

    public void moveLeft() {
        velocityX = MAX_VELOCITY;
        direction = Direction.LEFT;
        animationComponent.playAnimation(PlayerAnimationComponent.State.WALK_LEFT);
    }
}
