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
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getPhysicsWorld;
import static com.testniqatsu.bomberman.constants.GameConst.*;

public class BombermanApp extends GameApplication {
    private static final int VIEW_WIDTH = 1080;
    private static final int VIEW_HEIGHT = 720;

    private static final String TITLE = "BOMBERMAN";
    private static final String VERSION = "1.0";

    private static final String FONT = "Retro Gaming.ttf";

    private static final int TIME_PER_LEVEL = 100;
    private static final int START_LEVEL = 0;

    public static boolean isSoundEnabled = true;
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
        settings.setDeveloperMenuEnabled(true);
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
        loadNextLevel();
        FXGL.spawn("background");
        startCountDownTimer();
        getWorldProperties().<Integer>addListener("time", this::killPlayerWhenTimeUp);
    }

    private void startCountDownTimer() {
        run(() -> inc("time", -1), Duration.seconds(1));
    }

    private void killPlayerWhenTimeUp(int old, int now) {
        if (now == 0) {
            onPlayerKilled();
        }
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
        getSettings().setGlobalMusicVolume(isSoundEnabled ? 0.1 : 0.0);
        getSettings().setGlobalSoundVolume(isSoundEnabled ? 0.1 : 0.0);
        loopBGM("title_screen.mp3");
    }

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
    }

    @Override
    protected void initUI() {
        var HUDRow0 = new HBox(setTextUI("level", "LEVEL %d"));
        HUDRow0.setAlignment(Pos.CENTER_LEFT);

        var HUDRow1 = new HBox(
                setTextUI("score", "SCORE: %d"),
                setTextUI("speed", "SPEED: %d"),
                setTextUI("flame", "FLAME: %d"),
                setTextUI("bomb", "BOMB: %d"),
                setTextUI("time", "TIME: %d")
        );
        HUDRow1.setAlignment(Pos.CENTER);
        HUDRow1.setSpacing(20);

        var HUD = new VBox(
                HUDRow0,
                HUDRow1
        );
        HUD.setSpacing(10);

        FXGL.addUINode(HUD);
    }

    private Label setTextUI(String valGame, String content) {
        Label label = new Label();
        label.setTextFill(Color.BLACK);
        label.setFont(Font.font(FONT, FontWeight.EXTRA_BOLD, 30));
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

        onCollisionBegin(BombermanType.PLAYER, BombermanType.PORTAL, this::endLevel);
        onCollisionBegin(BombermanType.PLAYER, BombermanType.FLAME, (p, f) -> onPlayerKilled());
        onCollisionBegin(BombermanType.PLAYER, BombermanType.BALLOOM_E, (p, b) -> onPlayerKilled());
    }

    private void endLevel(Entity player, Entity portal) {
        var timer = FXGL.getGameTimer();
        timer.runOnceAfter(this::fadeToNextLevel, Duration.seconds(1));
    }

    private void fadeToNextLevel() {
        var gameScene = FXGL.getGameScene();
        var viewPort = gameScene.getViewport();
        viewPort.fade(this::loadNextLevel);
    }

    private void onPlayerKilled() {
        requestNewGame = true;
    }

    private void loadNextLevel() {
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
        viewport.bindToEntity(
                getPlayer(),
                FXGL.getAppWidth() / 2.0f,
                FXGL.getAppHeight() / 2.0f);
        viewport.setLazy(true);
        set("time", TIME_PER_LEVEL);
    }

    public static void main(String[] args) {
        launch(args);
    }
}

