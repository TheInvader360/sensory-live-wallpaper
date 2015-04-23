package com.theinvader360.sensory.live.wallpaper.free.android;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class AndroidLauncher extends Activity {
    private static int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent;
        try {
            ComponentName component = new ComponentName(getPackageName(), AndroidWallpaper.class.getName());
            intent = new Intent(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER); //API Level 16 (4.1)
            intent.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT, component);
            startActivityForResult(intent, REQUEST_CODE);
        }
        catch (android.content.ActivityNotFoundException wallpaperChangeFail) {
            try {
                intent = new Intent(WallpaperManager.ACTION_LIVE_WALLPAPER_CHOOSER); //Introduced in API Level 7 (2.1)
                startActivityForResult(intent, REQUEST_CODE);
                Toast.makeText(this, "Please select " + getString(R.string.app_name), Toast.LENGTH_LONG).show();
            }
            catch (Exception e) {
                Toast.makeText(this, "Live Wallpaper Failed:\n" + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == REQUEST_CODE) {
            finish();
        }
    }

}
