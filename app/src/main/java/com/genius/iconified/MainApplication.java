package com.genius.iconified;

import com.genius.iconified.utils.FontsOverride;

/**
 * Created by manjeet on 2/3/18.
 */

public class MainApplication extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();

        FontsOverride.setDefaultFont(this, "DEFAULT", "fonts/RalewayRegular.ttf");
        FontsOverride.setDefaultFont(this, "MONOSPACE", "fonts/RalewayRegular.ttf");
        FontsOverride.setDefaultFont(this, "SERIF", "fonts/RalewayRegular.ttf");
        FontsOverride.setDefaultFont(this, "SANS_SERIF", "fonts/RalewayRegular.ttf");
    }
}