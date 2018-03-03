package com.genius.iconified.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ADMIN on 11/26/2017.
 */

public class Database {

    private static Context ourContext;
    public final String KEY_ID = "KEY_ID";
    public final String LOGO_BRAND_NAME = "LOGO_BRAND_NAME";
    public final String SEARCH_DATE = "SEARCH_DATE";
    private final String DATABASE_NAME = "Iconified";
    private final String TABLE_NAME = "LastSearchedIcons";
    private final int DATABASE_VERSION = 1;
    private DbHelper ourHelper;
    private SQLiteDatabase ourDatabase;

    public Database(Context c) {
        ourContext = c;
    }

    public Database write() throws SQLiteException {
        ourHelper = new DbHelper(ourContext);
        ourDatabase = ourHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        ourDatabase.close();
    }

    public boolean addSearchRecordInDB(SearchRecordHolder holder) {
        ContentValues cv = new ContentValues();
        cv.put(LOGO_BRAND_NAME, holder.getIconName());
        cv.put(SEARCH_DATE, holder.getDate());
        ourDatabase.insert(TABLE_NAME, null, cv);
        return true;
    }

    public boolean deleteAllRecordsFromTableIfExist() {
        ourDatabase.execSQL("delete from " + TABLE_NAME);
        return true;
    }

    public List<SearchRecordHolder> getAllSearchesFromDB() {
        List<SearchRecordHolder> searchRecordHolders = new ArrayList<>();

        String[] column = {LOGO_BRAND_NAME, SEARCH_DATE};
        Cursor c = ourDatabase.query(TABLE_NAME, column, null, null, null, null, null);

        int idKeyLogoBrandName = c.getColumnIndex(LOGO_BRAND_NAME);
        int idKeySearchDate = c.getColumnIndex(SEARCH_DATE);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            SearchRecordHolder holder = new SearchRecordHolder();

            holder.setIconName(c.getString(idKeyLogoBrandName));
            holder.setDate(c.getString(idKeySearchDate));

            searchRecordHolders.add(holder);
        }
        return searchRecordHolders;
    }

    private class DbHelper extends SQLiteOpenHelper {
        public DbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase p1) {
            String sql =
                    "CREATE TABLE " +
                            TABLE_NAME +
                            " ( " +
                            KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            LOGO_BRAND_NAME + " TEXT NOT NULL, " +
                            SEARCH_DATE + " TEXT NOT NULL " +
                            " ); ";

            p1.execSQL(sql);
        }

        @Override
        public void onUpgrade(SQLiteDatabase p1, int oldVersion, int newVersion) {
            if (oldVersion < newVersion) {
                p1.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME + ";");
                onCreate(p1);
            }
        }
    }
}