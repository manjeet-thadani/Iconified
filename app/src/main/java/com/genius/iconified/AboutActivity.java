package com.genius.iconified;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by manjeet on 3/3/18.
 */

public class AboutActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView titleToolbarTextView;

    private LinearLayout openLicencesLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        
        init();
    }

    private void init() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        titleToolbarTextView = findViewById(R.id.toolbar_title_textview);

        Typeface customFont = Typeface.createFromAsset(getAssets(), "fonts/Pacifico.ttf");
        titleToolbarTextView.setTypeface(customFont);
        titleToolbarTextView.setText(getString(R.string.app_name));

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        openLicencesLayout = findViewById(R.id.activity_about_open_source_licences_layout);

        openLicencesLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SoftwareLicenceActivity.class));
            }
        });
    }
}
