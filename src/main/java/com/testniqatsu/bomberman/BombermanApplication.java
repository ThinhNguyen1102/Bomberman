package com.testniqatsu.bomberman;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;

public class BombermanApplication extends GameApplication {

    /**
     * Set up some properties for games like windows size and title.
     *
     * @param settings object to store initial settings for the game
     * @author Khoi Nguyen Truong
     * @since 0.2.0
     */
    @Override
    protected void initSettings(GameSettings settings) {
        settings.setTitle("Bomberman");
        settings.setVersion("0.2.0");
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
