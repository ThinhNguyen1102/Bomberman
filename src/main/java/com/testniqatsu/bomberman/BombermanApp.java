package com.testniqatsu.bomberman;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.SceneFactory;
import com.almasb.fxgl.app.scene.Viewport;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.PhysicsWorld;
import com.testniqatsu.bomberman.components.PlayerComponent;
import com.testniqatsu.bomberman.menus.BombermanGameMenu;
import com.testniqatsu.bomberman.menus.BombermanMenu;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getPhysicsWorld;
import static com.testniqatsu.bomberman.constants.GameConst.*;

public class BombermanApp extends GameApplication {
    private static final int VIEW_WIDTH = 720;
    private static final int VIEW_HEIGHT = 720;

    private static final String TITLE = "BOMBERMAN";
    private static final String VERSION = "1.0";

    private static final String FONT = "Quinquefive-Ea6d4.ttf";

    public static boolean sound_enabled = true;
    private boolean requestNewGame = false;

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setHeight(VIEW_HEIGHT);
        settings.setWidth(VIEW_WIDTH);
        settings.setTitle(TITLE);
        settings.setVersion(VERSION);
        settings.setIntroEnabled(false);
        settings.setMainMenuEnabled(true);
        settings.setGameMenuEnabled(true);
        settings.setPreserveResizeRatio(true);
        settings.setManualResizeEnabled(true);
        settings.setFontUI(FONT);
        settings.setSceneFactory(new SceneFactory() {
            @NotNull
            @Override
            public FXGLMenu newMainMenu() {
                return new BombermanMenu();
            }

            @NotNull
            @Override
            public FXGLMenu newGameMenu() {
                return new BombermanGameMenu();
            }
        });
    }


    @Override
    protected void initGame() {
        FXGL.getGameWorld().addEntityFactory(new BombermanFactory());
        nextLevel();
        FXGL.spawn("background");

        run(() -> inc("time", -1), Duration.seconds(1));
        getWorldProperties().<Integer>addListener("time", (old, now) -> {
            if (now == 0) {
                onPlayerKilled();
            }
        });
    }

    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("bomb", 1);
        vars.put("flame", 1);
        vars.put("score", 0);
        vars.put("speed", SPEED);
        vars.put("time", TIME_PER_LEVEL);
        vars.put("level", START_LEVEL);
    }

    @Override
    protected void onPreInit() {
        getSettings().setGlobalSoundVolume(sound_enabled ? 0.1 : 0.0);
        getSettings().setGlobalMusicVolume(sound_enabled ? 0.1 : 0.0);
        loopBGM("title_screen.mp3");
    }

    private double timeChangeWallE = 0;

    @Override
    protected void onUpdate(double tpf) {

        if (geti("time") == 0) {
            getDialogService().showMessageBox("Game Over!!!", getGameController()::startNewGame);
        }

        if (requestNewGame) {
            requestNewGame = false;
            getPlayer().getComponent(PlayerComponent.class).die();
            getPlayer().getComponent(PlayerComponent.class).setExploreCancel(true);
            getGameTimer().runOnceAfter(() -> getGameScene().getViewport().fade(() -> {
                set("bomb", 1);
                setLevel();
            }), Duration.seconds(0.5));
        }

        timeChangeWallE = timeChangeWallE + tpf;
        if (timeChangeWallE > 10) {
            setWallE();
            timeChangeWallE = 0.0;
        }
    }

    private void setWallE() {
        List<Entity> listWall = getGameWorld().getEntitiesByType(BombermanType.WALL_E);
        listWall.forEach(w -> w.translateY(SIZE_BLOCK));
    }

    @Override
    protected void initUI() {
        FXGL.addUINode(setTextUI("score", "Score: %d"), 20, 30);

        FXGL.addUINode(setTextUI("speed", "Speed: %d"), 140, 30);

        FXGL.addUINode(setTextUI("flame", "Flame: %d"), 280, 30);

        FXGL.addUINode(setTextUI("bomb", "Bombs: %d"), 390, 30);

        FXGL.addUINode(setTextUI("time", "Time: %d"), 500, 30);
    }

    private Label setTextUI(String valGame, String content) {
        Label label = new Label();
        label.setTextFill(Color.BLACK);
        label.setFont(Font.font("Comic Sans MS", FontWeight.EXTRA_BOLD, 20));
        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.LIGHTGREEN);
        label.setEffect(shadow);
        // valGame's datatype is int
        label.textProperty().bind(FXGL.getip(valGame).asString(content));
        return label;
    }

    private static Entity getPlayer() {
        return FXGL.getGameWorld().getSingleton(BombermanType.PLAYER);
    }

    @Override
    protected void initInput() {
        FXGL.getInput().addAction(new UserAction("Move Up") {
            @Override
            protected void onAction() {
                getPlayer().getComponent(PlayerComponent.class).up();
            }

            @Override
            protected void onActionEnd() {
                getPlayer().getComponent(PlayerComponent.class).stop();
            }
        }, KeyCode.W);

        FXGL.getInput().addAction(new UserAction("Move Down") {
            @Override
            protected void onAction() {
                getPlayer().getComponent(PlayerComponent.class).down();
            }

            @Override
            protected void onActionEnd() {
                getPlayer().getComponent(PlayerComponent.class).stop();
            }
        }, KeyCode.S);

        FXGL.getInput().addAction(new UserAction("Move Left") {
            @Override
            protected void onAction() {
                getPlayer().getComponent(PlayerComponent.class).left();
            }

            @Override
            protected void onActionEnd() {
                getPlayer().getComponent(PlayerComponent.class).stop();
            }
        }, KeyCode.A);

        FXGL.getInput().addAction(new UserAction("Move Right") {
            @Override
            protected void onAction() {
                getPlayer().getComponent(PlayerComponent.class).right();
            }

            @Override
            protected void onActionEnd() {
                getPlayer().getComponent(PlayerComponent.class).stop();
            }
        }, KeyCode.D);

        FXGL.getInput().addAction(new UserAction("Place Bomb") {
            @Override
            protected void onActionBegin() {
                getPlayer().getComponent(PlayerComponent.class).placeBomb();
            }
        }, KeyCode.SPACE);
    }

    @Override
    protected void initPhysics() {
        PhysicsWorld physics = getPhysicsWorld();
        physics.setGravity(0, 0);

        onCollisionBegin(BombermanType.PLAYER
                , BombermanType.PORTAL, (p, po) -> getGameTimer().runOnceAfter(()
                                -> getGameScene().getViewport().fade(this::nextLevel)
                        , Duration.seconds(1)));

        onCollisionBegin(BombermanType.PLAYER, BombermanType.FIRE, (p, f) -> onPlayerKilled());

        onCollisionBegin(BombermanType.PLAYER, BombermanType.BALLOOM_E, (p, b) -> onPlayerKilled());
    }

    private void onPlayerKilled() {
        requestNewGame = true;
    }

    private void nextLevel() {
        if (FXGL.geti("level") == MAX_LEVEL) {
            showMessage("You win !!!");
            return;
        }

        inc("level", +1);
        setLevel();
    }

    private void setLevel() {
        FXGL.setLevelFromMap("bbm_level" + FXGL.geti("level") + ".tmx");
        Viewport viewport = getGameScene().getViewport();
        viewport.setBounds(0, 0, GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT);
        viewport.bindToEntity(getPlayer(), (double) getAppWidth() / 2
                ,  (double) getAppHeight() / 2);
        viewport.setLazy(true);
        set("time", TIME_PER_LEVEL);
    }

    public static void main(String[] args) {
        launch(args);
    }
}

