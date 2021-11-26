package com.testniqatsu.bomberman;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.EntityBuilder;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyDef;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.almasb.fxgl.physics.box2d.dynamics.FixtureDef;
import com.testniqatsu.bomberman.animations.PlayerAnimationComponent;
import com.testniqatsu.bomberman.components.PlayerComponents;
import javafx.scene.input.KeyCode;

public class BombermanApp extends GameApplication {

    private Entity player;

    /**
     * Set up some properties for game like windows size and title.
     *
     * @param settings object to store initial settings for the game
     * @author Khoi Nguyen Truong
     * @since 0.2.0
     */
    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(16 * 27); // width 27 tiles
        settings.setHeight(16 * 17); // height 17 tiles
        settings.setTitle("爆弾男");
        settings.setVersion("0.9.2");
        settings.setPreserveResizeRatio(true);
        settings.setManualResizeEnabled(true);
    }

    /**
     * Load level for the game
     *
     * @author Khoi Nguyen Truong
     * @since 0.3.0
     */
    @Override
    protected void initGame() {
        FXGL.getGameWorld().addEntityFactory(new BombermanFactory());
        // FXGL.setLevelFromMap("demomap02/map2.tmx")

        var fixture = new FixtureDef();
        fixture.setFriction(0);
        fixture.setDensity(0.1f);

        var body = new BodyDef();
        body.setFixedRotation(true);
        body.setType(BodyType.KINEMATIC);

        var physicsComponent = new PhysicsComponent();
        physicsComponent.setFixtureDef(fixture);
        physicsComponent.setBodyDef(body);

        var animationComponent = new PlayerAnimationComponent();

        player = new EntityBuilder()
                .type(BombermanType.PLAYER)
                .at(16, 16) // position (1, 1)
                .with(new PlayerComponents(animationComponent, physicsComponent))
                .buildAndAttach();
    }

    /**
     * Bind input to the game
     *
     * @author Khoi Nguyen Truong
     * @since 0.6.2
     */
    @Override
    protected void initInput() {
        FXGL.onKey(KeyCode.D, () -> player.getComponent(PlayerComponents.class).moveRight());
        FXGL.onKey(KeyCode.W, () -> player.getComponent(PlayerComponents.class).moveUp());
        FXGL.onKey(KeyCode.A, () -> player.getComponent(PlayerComponents.class).moveLeft());
        FXGL.onKey(KeyCode.S, () -> player.getComponent(PlayerComponents.class).moveDown());
    }

    @Override
    protected void initPhysics() {
        var physicsWorld = FXGL.getPhysicsWorld();
        physicsWorld.setGravity(0, 0); // remove gravity
    }

    /**
     * Main function.
     *
     * Launch is inherited from javafx.
     *
     * @param args not usually used
     * @author Khoi Nguyen Truong
     * @since 0.2.0
     */
    public static void main(String[] args) {
        launch(args);
    }
}
