package com.theinvader360.sensory.live.wallpaper.free;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pools;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import java.util.HashMap;

public class GameScreen implements Screen {

    private Stage stage;
    private HashMap<Integer, Touchpoint> map = new HashMap<Integer, Touchpoint>();
    private Array<ParticleEffectPool.PooledEffect> effects = new Array<ParticleEffectPool.PooledEffect>();
    private BitmapFont font;
    private float timer = 0;
    private float delay = 1;
    private boolean soundEnabled = false;

    public GameScreen() {
        stage = new Stage();
        stage.setViewport(new StretchViewport(Sensory.WIDTH, Sensory.HEIGHT));
        font = new BitmapFont();
    }

    @Override
    public void render(float delta) {
        timer += delta;
        if (Gdx.app.getPreferences("preferences").contains("delay")) delay = Float.parseFloat(Gdx.app.getPreferences("preferences").getString("delay"));
        if (Gdx.app.getPreferences("preferences").contains("sound")) soundEnabled = Gdx.app.getPreferences("preferences").getBoolean("sound");
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.getBatch().begin();
        for (ParticleEffectPool.PooledEffect effect : effects) {
            effect.draw(stage.getBatch(), delta);
            if (effect.isComplete()) {
                effect.free();
                effects.removeValue(effect, true);
            }
        }
        stage.getBatch().end();
        stage.act(delta);
        stage.draw();
        for (int i = 0; i < 10; i++) {
            if (Gdx.input.isTouched(i)) {
                Vector3 pos = Pools.obtain(Vector3.class);
                pos.set(Gdx.input.getX(i), Gdx.input.getY(i), 0);
                stage.getCamera().unproject(pos);
                if (map.containsKey(i)) {
                    Touchpoint touchpoint = map.get(i);
                    touchpoint.setPosition(pos.x, pos.y);
                    map.put(i, touchpoint);
                } else {
                    map.put(i, new Touchpoint(pos.x, pos.y));
                }
                Pools.free(pos);
            } else {
                if (map.containsKey(i)) map.remove(i);
            }
        }
        if (timer > delay) {
            if (map.isEmpty()) {
                map.put(-1, new Touchpoint(MathUtils.random(Sensory.WIDTH), MathUtils.random(Sensory.HEIGHT)));
            }
            else {
                if (map.containsKey(-1)) map.remove(-1);
                timer = 0;
            }
        }
        for (Integer key : map.keySet()) {
            Touchpoint touchpoint = map.get(key);
            touchpoint.update(delta);
            if (touchpoint.getTimer() >= Touchpoint.TIMER_MAX) {
                touchpoint.resetTimer();
                ParticleEffectPool.PooledEffect burstEffect = Assets.instance.particles.burstPool.obtain();
                burstEffect.setPosition(touchpoint.getX(), touchpoint.getY());
                burstEffect.getEmitters().get(0).getTint().setColors(touchpoint.getColor());
                effects.add(burstEffect);
                if (soundEnabled) Assets.instance.sounds.sound.play();
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
        effects.clear();
    }

    @Override
    public void dispose() {
        stage.dispose();
        font.dispose();
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

}
