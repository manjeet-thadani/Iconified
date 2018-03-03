package com.genius.iconified.classifier;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.genius.iconified.R;
import com.genius.iconified.utils.Database;
import com.genius.iconified.utils.SearchRecordHolder;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by manjeet on 3/3/18.
 */

public class ClassifierResultsActivity extends AppCompatActivity {

    private ClassifierResultHolder results;

    private Toolbar toolbar;
    private TextView titleToolbarTextView;

    private RecyclerView recyclerView;
    private RecyclerViewClassifierResults recyclerViewAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_classifier_results);

        results = (ClassifierResultHolder) getIntent().getExtras().getSerializable("holder");

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

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String date = df.format(Calendar.getInstance().getTime());

        ClassifierResult classifierResult = results.getResults().get(0);
        SearchRecordHolder holder = new SearchRecordHolder();
        holder.setIconName(classifierResult.getIconName());
        holder.setDate(date);

        Database database = new Database(getApplicationContext());
        database.write();
        database.addSearchRecordInDB(holder);
        database.close();

        initRecyclerView();
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.activity_classifier_results_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerViewAdapter = new RecyclerViewClassifierResults(results.getResults(), this);
        recyclerView.setAdapter(recyclerViewAdapter);
    }
}
