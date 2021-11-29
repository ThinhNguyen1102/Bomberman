package com.testniqatsu.bomberman.components;

import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.testniqatsu.bomberman.animations.PlayerAnimationComponent;

public class PlayerComponents extends Component {
    final int MAX_VELOCITY = 50 * 100;
    final double FRICTION = 0.5f;

    enum Direction {
        DOWN, LEFT, RIGHT, UP
    }

    int velocityX;
    int velocityY;

    double frictionX;
    double frictionY;

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
        updateVelocity();
        playAnimation();
        physicsComponent.setLinearVelocity(velocityX * tpf, velocityY * tpf);
    }

    public void moveDown() {
        velocityY = MAX_VELOCITY;
        direction = Direction.DOWN;
        frictionY = 1;
    }

    public void moveUp() {
        velocityY = -MAX_VELOCITY;
        direction = Direction.UP;
        frictionY = 1;
    }

    public void moveRight() {
        velocityX = MAX_VELOCITY;
        direction = Direction.RIGHT;
        frictionX = 1;
    }

    public void moveLeft() {
        velocityX = -MAX_VELOCITY;
        direction = Direction.LEFT;
        frictionX = 1;
    }

    void playAnimation() {
        if (velocityX != 0 || velocityY != 0) {
            switch (direction) {
                case DOWN -> animationComponent.playAnimation(PlayerAnimationComponent.State.WALK_DOWN);
                case LEFT -> animationComponent.playAnimation(PlayerAnimationComponent.State.WALK_LEFT);
                case RIGHT -> animationComponent.playAnimation(PlayerAnimationComponent.State.WALK_RIGHT);
                case UP -> animationComponent.playAnimation(PlayerAnimationComponent.State.WALK_UP);
            }
        } else {
            switch (direction) {
                case DOWN -> animationComponent.playAnimation(PlayerAnimationComponent.State.IDLE_DOWN);
                case LEFT -> animationComponent.playAnimation(PlayerAnimationComponent.State.IDLE_LEFT);
                case RIGHT -> animationComponent.playAnimation(PlayerAnimationComponent.State.IDLE_RIGHT);
                case UP -> animationComponent.playAnimation(PlayerAnimationComponent.State.IDLE_UP);
            }
        }
    }

    void updateVelocity() {
        velocityX = (int) (velocityX * frictionX);
        velocityY = (int) (velocityY * frictionY);

        frictionX = FRICTION;
        frictionY = FRICTION;

        if (FXGLMath.abs(velocityX) < 1 && FXGLMath.abs(velocityY) < 1) {
            velocityX = 0;
            velocityY = 0;
        }
    }
}
