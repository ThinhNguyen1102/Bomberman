package com.testniqatsu.bomberman.ui;

import com.almasb.fxgl.dsl.FXGL;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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

        var HUDRow1 = new GridPane();

        HUDRow1.add(setTextUI("score", "SCORE: %d"), 0, 0);
        HUDRow1.add(setTextUI("speed", "SPEED: %d"), 1, 0);
        HUDRow1.add(setTextUI("flame", "FLAME: %d"), 2, 0);
        HUDRow1.add(setTextUI("bomb", "BOMB: %d"), 3, 0);
        HUDRow1.add(setTextUI("life", "LIFE: %d"), 4, 0);
        HUDRow1.add(setTextUI("numOfEnemy", "E: %d"), 5, 0);

        for (var i = 0; i < 6; ++i) {
            var cc = new ColumnConstraints();
            cc.setPercentWidth(100.0 / 6.0);
            cc.setHgrow(Priority.ALWAYS);
            HUDRow1.getColumnConstraints().add(cc);
        }

        HUDRow1.setPadding(new Insets(0, padding, 0, padding));
        HUDRow1.prefWidthProperty().bind(FXGL.getSettings().actualWidthProperty());

        for (var child : HUDRow1.getChildren()) {
            GridPane.setHalignment(child, HPos.CENTER);
        }

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

    @Override
    public Pane getHUD() {
        return hud;
    }
}
