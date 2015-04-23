package com.theinvader360.sensory.live.wallpaper.free.android;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class AndroidWallpaperSettings extends PreferenceActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPreferenceManager().setSharedPreferencesName("preferences");
        getPreferenceManager().setSharedPreferencesMode(MODE_PRIVATE);
        addPreferencesFromResource(R.xml.preferences);
    }

}
