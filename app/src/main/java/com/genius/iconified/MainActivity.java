package com.genius.iconified;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.genius.iconified.classifier.CaptureImageActivity;
import com.genius.iconified.utils.Database;
import com.genius.iconified.utils.EmptyRecyclerView;
import com.genius.iconified.utils.SearchRecordHolder;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView titleToolbarTextView;

    private View view;

    private List<SearchRecordHolder> lastReadRecordHolderList;
    private EmptyRecyclerView recyclerView;
    private View emptyView;
    private RecyclerViewMainActivityRecentSearches recyclerViewAdapter;
    private LinearLayoutManager layoutManager;

    private FloatingActionButton cameraFAB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
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

        view = findViewById(R.id.coordinator_layout);

        cameraFAB = findViewById(R.id.main_activity_fab);

        cameraFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), CaptureImageActivity.class));
            }
        });

        initRecyclerView();
    }

    private void initRecyclerView() {
        recyclerView = (EmptyRecyclerView) findViewById(R.id.main_activity_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        emptyView = findViewById(R.id.main_activity_hidden_layout);
        recyclerView.setEmptyView(emptyView);

        Database db = new Database(getApplicationContext());
        db.write();
        lastReadRecordHolderList = db.getAllSearchesFromDB();
        db.close();

        recyclerViewAdapter = new RecyclerViewMainActivityRecentSearches(lastReadRecordHolderList, MainActivity.this);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    public void confirmClearLogs() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to clear all recent searches?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Database db = new Database(getApplicationContext());
                        db.write();
                        db.deleteAllRecordsFromTableIfExist();
                        db.close();

                        Toast.makeText(MainActivity.this, "All recent searches has been cleared", Toast.LENGTH_SHORT).show();

                        initRecyclerView();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void exitConfirm() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MainActivity.this.finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_about:
                startActivity(new Intent(getApplicationContext(), AboutActivity.class));
                break;

            case R.id.action_clear:
                confirmClearLogs();
                break;

            case R.id.action_exit:
                exitConfirm();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
