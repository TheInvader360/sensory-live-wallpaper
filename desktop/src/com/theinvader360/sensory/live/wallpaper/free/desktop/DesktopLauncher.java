package com.theinvader360.sensory.live.wallpaper.free.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.theinvader360.sensory.live.wallpaper.free.Sensory;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = (int) Sensory.WIDTH;
        config.height = (int) Sensory.HEIGHT;
		new LwjglApplication(new Sensory(), config);
	}
}
