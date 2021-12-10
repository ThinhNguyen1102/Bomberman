package com.testniqatsu.bomberman.ui;

import com.almasb.fxgl.dsl.FXGL;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class BombermanHUD implements HUD {
    VBox hud;

    public BombermanHUD() {
        var spacer = new Pane();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        var padding = 20;

        var HUDRow0 = new HBox(
                setTextUI("level", "LEVEL %d"),
                spacer,
                setTextUI("time", "⏰ %d")
        );
        HUDRow0.setAlignment(Pos.CENTER_LEFT);
        HUDRow0.setPadding(new Insets(0, padding, 0, padding));

        var HUDRow1 = new HBox(
                setTextUI("score", "SCORE: %d"),
                createSpacer(),
                setTextUI("speed", "SPEED: %d"),
                createSpacer(),
                setTextUI("flame", "FLAME: %d"),
                createSpacer(),
                setTextUI("bomb", "BOMB: %d"),
                createSpacer(),
                setTextUI("life", "LIFE: %d"),
                createSpacer(),
                setTextUI("numOfEnemy", "E: %d")
        );
        HUDRow1.setAlignment(Pos.CENTER_LEFT);
        HUDRow1.prefWidthProperty().bind(FXGL.getSettings().actualWidthProperty());
        HUDRow1.setPadding(new Insets(0, padding, 0, padding));

        hud = new VBox(
                HUDRow0,
                HUDRow1
        );
        hud.setPadding(new Insets(padding, 0, padding, 0));
        hud.setSpacing(10);
    }

    private Node createSpacer() {
        var spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        return spacer;
    }

    private Text setTextUI(String valGame, String content) {
        var text = FXGL.getUIFactoryService().newText("", 20);
        text.setFill(Color.BLACK);
        text.textProperty().bind(FXGL.getip(valGame).asString(content));
        return text;
    }

    @Override
    public Pane getHUD() {
        return hud;
    }
}
