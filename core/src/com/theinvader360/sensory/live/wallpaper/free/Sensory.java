package com.theinvader360.sensory.live.wallpaper.free;

import com.badlogic.gdx.Game;

public class Sensory extends Game {
    public static final float WIDTH = 480f;
    public static final float HEIGHT = 800f;
    public GameScreen gameScreen;

    @Override
    public void create () {
        Assets.instance.init();
        gameScreen = new GameScreen();
        setScreen(gameScreen);
    }

    @Override
    public void resume() {
        Assets.instance.init();
    }

    @Override
    public void dispose() {
        Assets.instance.dispose();
        gameScreen.dispose();
    }
}
