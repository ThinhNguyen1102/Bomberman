package com.testniqatsu.bomberman;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.EntityBuilder;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.testniqatsu.bomberman.animations.PlayerAnimationComponent;
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
        settings.setTitle("Bomberman");
        settings.setVersion("0.7.2");
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
        FXGL.setLevelFromMap("demomap02/map2.tmx");

        player = new EntityBuilder()
                .type(BombermanType.PLAYER)
                .at(16, 16) // position (1, 1)
                .with(new PlayerAnimationComponent())
                .collidable()
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
        FXGL.onKey(KeyCode.W, () -> player.getComponent(PlayerAnimationComponent.class).moveUp());
        FXGL.onKey(KeyCode.A, () -> player.getComponent(PlayerAnimationComponent.class).moveLeft());
        FXGL.onKey(KeyCode.S, () -> player.getComponent(PlayerAnimationComponent.class).moveDown());
        FXGL.onKey(KeyCode.D, () -> player.getComponent(PlayerAnimationComponent.class).moveRight());
    }

    @Override
    protected void initPhysics() {
        FXGL.onCollision(
                BombermanType.PLAYER,
                BombermanType.WALL,
                (player, wall) -> {

                });
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
