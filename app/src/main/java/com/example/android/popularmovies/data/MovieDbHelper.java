package com.example.android.popularmovies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class MovieDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "movieDb.db";

    // If you change the database schema, you must increment the database version
    private static final int VERSION = 1;


    MovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Create tasks table (careful to follow SQL formatting rules)
        final String CREATE_TABLE = "CREATE TABLE "  + MovieContract.MovieEntry.TABLE_NAME + " (" +
                MovieContract.MovieEntry._ID + " INTEGER PRIMARY KEY, " +
                MovieContract.MovieEntry.COLUMN_ID + " TEXT, " +
                MovieContract.MovieEntry.COLUMN_BACKGROUND + " TEXT, " +
                MovieContract.MovieEntry.COLUMN_IMAGE + " TEXT, " +
                MovieContract.MovieEntry.COLUMN_NAME + " TEXT, " +
                MovieContract.MovieEntry.COLUMN_PLOT + " TEXT, " +
                MovieContract.MovieEntry.COLUMN_RELEASE + " TEXT, " +
                MovieContract.MovieEntry.COLUMN_RATING + " TEXT);";

        sqLiteDatabase.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MovieContract.MovieEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
