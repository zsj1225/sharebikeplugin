package com.jun.bikeplugin;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbManager {

    private  static DbManager mManager;
    private final SQLiteOpenHelper mHelper;

    private DbManager(SQLiteOpenHelper helper){
        this.mHelper = helper;
    }

    public static synchronized DbManager getInstance(Context context){
        if(context == null){
            throw new IllegalStateException("context must no be NULL.");
        }

        if(mManager == null){
            mManager = new DbManager(new MyOpenDbHelper(context));
        }
        return mManager;
    }

    public SQLiteDatabase getReadableDb(){
        return mHelper.getReadableDatabase();
    }

    public SQLiteDatabase getWritableDb(){
        return mHelper.getWritableDatabase();
    }
}
