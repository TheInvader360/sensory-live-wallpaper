package com.theinvader360.sensory.live.wallpaper.free.android;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.backends.android.AndroidLiveWallpaperService;
import com.theinvader360.sensory.live.wallpaper.free.Sensory;

public class AndroidWallpaper extends AndroidLiveWallpaperService {

    @Override
    public void onCreateApplication () {
        super.onCreateApplication();

        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        config.useAccelerometer = false;
        config.useCompass = false;
        config.getTouchEventsForLiveWallpaper = true;

        ApplicationListener listener = new Sensory();
        initialize(listener, config);
    }

}
