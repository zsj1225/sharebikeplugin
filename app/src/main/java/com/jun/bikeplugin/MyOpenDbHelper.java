package com.jun.bikeplugin;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyOpenDbHelper extends SQLiteOpenHelper {

    private static final String DBNAME="bikePlugin.db";
    private static final int DBVERSION = 1;

    public MyOpenDbHelper(Context context) {
        super(context, DBNAME, null, DBVERSION);
    }

    public interface tbBike{
        String NAME = "bikepwd";
    }

    public interface tbBikeColumn{
        String ID="id";
        String CARNO="carno";
        String CARPWD="carpwd";
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE " + tbBike.NAME + "(" +
                        tbBikeColumn.ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        tbBikeColumn.CARNO + " TEXT NOT NULL," +
                        tbBikeColumn.CARPWD + " TEXT NOT NULL" +
                        ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
