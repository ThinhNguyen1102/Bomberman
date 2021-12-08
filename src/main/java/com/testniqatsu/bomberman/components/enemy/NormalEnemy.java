package com.testniqatsu.bomberman.components.enemy;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGL.inc;
import static com.testniqatsu.bomberman.constants.GameConst.*;

public abstract class NormalEnemy extends Component {
    protected double dx = ENEMY_SPEED_BASE;
    protected double dy = 0;
    private double lastX = 0;
    private double lastY = 0;

    protected enum TurnDirection {
        BLOCK_LEFT, BLOCK_RIGHT, BLOCK_UP, BLOCK_DOWN
    }


    protected final AnimatedTexture texture;
    protected static final double ANIM_TIME = 0.5;
    protected static final int SIZE_FLAME = 48;

    protected AnimationChannel animWalkRight;
    protected AnimationChannel animWalkLeft;
    protected AnimationChannel animDie;
    protected AnimationChannel animStop;

    public NormalEnemy() {
        setAnimationMove();

        texture = new AnimatedTexture(animWalkRight);
        texture.loop();
    }

    protected abstract void setAnimationMove();

    @Override
    public void onAdded() {
        entity.getViewComponent().addChild(texture);
    }

    @Override
    public void onUpdate(double tpf) {
        entity.setScaleUniform(0.9);
        entity.translateX(dx * tpf);
        entity.translateY(dy * tpf);

        setAnimationStage();
    }

    protected void setAnimationStage() {
        double dx = entity.getX() - lastX;
        double dy = entity.getY() - lastY;

        lastX = entity.getX();
        lastY = entity.getY();

        if (dx == 0 && dy == 0) {
            return;
        }

        if (Math.abs(dx) > Math.abs(dy)) {
            if (dx > 0) {
                texture.loopNoOverride(animWalkRight);
            } else {
                texture.loopNoOverride(animWalkLeft);
            }
        } else {
            if (dy > 0) {
                texture.loopNoOverride(animWalkLeft);
            } else {
                texture.loopNoOverride(animWalkRight);
            }
        }
    }

    protected void setTurnEnemy(TurnDirection direct) {
        switch (direct) {
            case BLOCK_LEFT:
                entity.translateX(3);
                dx = 0.0;
                dy = getRandom();
                break;
            case BLOCK_RIGHT:
                entity.translateX(-3);
                dx = 0.0;
                dy = getRandom();
                break;
            case BLOCK_UP:
                entity.translateY(3);
                dy = 0.0;
                dx = getRandom();
                break;
            case BLOCK_DOWN:
                entity.translateY(-3);
                dy = 0.0;
                dx = getRandom();
                break;
        }
    }

    protected double getRandom() {
        return Math.random() > 0.5 ? ENEMY_SPEED_BASE : -ENEMY_SPEED_BASE;
    }

    protected void turn() {
        if (dx < 0.0) {
            setTurnEnemy(TurnDirection.BLOCK_LEFT);
        } else if (dx > 0.0) {
            setTurnEnemy(TurnDirection.BLOCK_RIGHT);
        } else if (dy < 0.0) {
            setTurnEnemy(TurnDirection.BLOCK_UP);
        } else if (dy > 0.0) {
            setTurnEnemy(TurnDirection.BLOCK_DOWN);
        }
    }

    public void enemyDie() {
        FXGL.inc("numOfEnemy", -1);
        int NORMAL_SCORE_E = 100;
        showScore(NORMAL_SCORE_E);
        inc("score", NORMAL_SCORE_E);
        dx = 0;
        dy = 0;
        texture.loopNoOverride(animDie);
    }

    protected void showScore(int score) {
        Label labelScore = new Label();
        labelScore.setText(score + "!");
        labelScore.setFont(Font.font("Comic Sans MS", FontWeight.EXTRA_BOLD, 15));
        labelScore.setTextFill(Color.WHITE);
        FXGL.addUINode(labelScore, entity.getX() + 24, entity.getY() + 24);
        FXGL.getGameTimer().runOnceAfter(() -> {
            FXGL.removeUINode(labelScore);
        }, Duration.seconds(2));
    }
}

