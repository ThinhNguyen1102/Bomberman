package com.testniqatsu.bomberman.components;

import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.testniqatsu.bomberman.animations.PlayerAnimationComponent;

public class PlayerComponents extends Component {
    final int MAX_VELOCITY = 1000;
    final double FRICTION = 0.2f;
    final int ACCELERATION = (int) (MAX_VELOCITY * 0.2f);

    enum Direction {
        DOWN, LEFT, RIGHT, UP
    }

    int velocityX;
    int velocityY;

    Direction direction = Direction.DOWN;

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
            velocityX = (int) (velocityX * FRICTION);
            velocityY = (int) (velocityY * FRICTION);

            if (FXGLMath.abs(velocityX) < 1 && FXGLMath.abs(velocityY) < 1) {
                velocityX = 0;
                velocityY = 0;
            }
        } else {
            switch (direction) {
                case DOWN -> animationComponent.playAnimation(PlayerAnimationComponent.State.IDLE_DOWN);
                case LEFT -> animationComponent.playAnimation(PlayerAnimationComponent.State.IDLE_LEFT);
                case RIGHT -> animationComponent.playAnimation(PlayerAnimationComponent.State.IDLE_RIGHT);
                case UP -> animationComponent.playAnimation(PlayerAnimationComponent.State.IDLE_UP);
            }
        }

        physicsComponent.setLinearVelocity(velocityX * tpf, velocityY * tpf);
    }

    public void moveDown() {
        if (velocityY < MAX_VELOCITY) {
            velocityY += ACCELERATION;
        }
        direction = Direction.DOWN;
        animationComponent.playAnimation(PlayerAnimationComponent.State.WALK_DOWN);
    }

    public void moveUp() {
        if (velocityY > -MAX_VELOCITY) {
            velocityY -= ACCELERATION;
        }
        direction = Direction.UP;
        animationComponent.playAnimation(PlayerAnimationComponent.State.WALK_UP);
    }

    public void moveRight() {
        if (velocityX < MAX_VELOCITY) {
            velocityX += ACCELERATION;
        }
        direction = Direction.RIGHT;
        animationComponent.playAnimation(PlayerAnimationComponent.State.WALK_RIGHT);
    }

    public void moveLeft() {
        if (velocityX > -MAX_VELOCITY) {
            velocityX -= ACCELERATION;
        }
        direction = Direction.LEFT;
        animationComponent.playAnimation(PlayerAnimationComponent.State.WALK_LEFT);
    }
}
