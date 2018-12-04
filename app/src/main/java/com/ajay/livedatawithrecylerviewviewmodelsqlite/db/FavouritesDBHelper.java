package com.ajay.livedatawithrecylerviewviewmodelsqlite.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FavouritesDBHelper extends SQLiteOpenHelper {

    public FavouritesDBHelper(Context context) {
        super(context, DBSettings.DB_NAME, null, DBSettings.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + DBSettings.DBEntry.TABLE + " ( " +
                DBSettings.DBEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DBSettings.DBEntry.COL_FAV_URL + " TEXT NOT NULL, " +
                DBSettings.DBEntry.COL_FAV_DATE + " INTEGER NOT NULL);";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DBSettings.DBEntry.TABLE);
        onCreate(db);
    }

}