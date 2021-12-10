package com.testniqatsu.bomberman.ui;

import com.almasb.fxgl.dsl.FXGL;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class BombermanHUD implements HUD {
    VBox hud;

    public BombermanHUD(int width) {
        var spacer = new Pane();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        var padding = 20;

        var HUDRow0 = new HBox(
                setTextUI("level", "LEVEL %d"),
                spacer,
                setTextUI("time", "TIME: %d")
        );
        HUDRow0.setAlignment(Pos.CENTER_LEFT);
        HUDRow0.setPadding(new Insets(0, padding, 0, padding));

        HBox.setHgrow(HUDRow0, Priority.ALWAYS);

        var HUDRow1 = new HBox(
                setTextUI("score", "SCORE: %d"),
                setTextUI("speed", "SPEED: %d"),
                setTextUI("flame", "FLAME: %d"),
                setTextUI("bomb", "BOMB: %d"),
                setTextUI("life", "LIFE: %d"),
                setTextUI("numOfEnemy", "E: %d")
        );
        HUDRow1.setAlignment(Pos.CENTER_LEFT);
        HUDRow1.setPadding(new Insets(0, padding, 0, padding));

        var spacing = width;
        for (var child : HUDRow1.getChildren()) {
            spacing -= child.getBoundsInParent().getWidth();
        }
        spacing = spacing - padding * 2;
        spacing /= 5;
        HUDRow1.setSpacing(spacing);

        hud = new VBox(
                HUDRow0,
                HUDRow1
        );
        hud.setPadding(new Insets(padding, 0, padding, 0));
        hud.setSpacing(10);
    }

    private Text setTextUI(String valGame, String content) {
        var text = FXGL.getUIFactoryService().newText("", 20);
        text.setFill(Color.BLACK);
        text.textProperty().bind(FXGL.getip(valGame).asString(content));
        return text;
    }

    public Pane getHUD() {
        return hud;
    }
}
