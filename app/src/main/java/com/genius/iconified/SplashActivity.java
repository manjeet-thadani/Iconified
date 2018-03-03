package com.genius.iconified;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.widget.TextView;

/**
 * Created by manjeet on 2/3/18.
 */

public class SplashActivity extends AppCompatActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        TextView appNameTextView = findViewById(R.id.activity_splash_app_name_textview);

        Typeface customFont = Typeface.createFromAsset(getAssets(), "fonts/Pacifico.ttf");
        appNameTextView.setTypeface(customFont);

        appNameTextView.setText(getString(R.string.app_name));

        Thread thread = new Thread() {
            public void run() {
                try {
                    sleep(2000);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    Intent jumpToMenu = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(jumpToMenu);
                }
            }
        };
        thread.start();
    }

    @Override
    protected void onPause() {
        super.onPause();

        finish();
    }
}
