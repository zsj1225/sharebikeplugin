package com.jun.bikeplugin;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DbUtils {

    public static String queryPwd(Context context,String carNo){
        String pwd=null;

        SQLiteDatabase db = DbManager.getInstance(context).getWritableDb();
        String sql = "select " + MyOpenDbHelper.tbBikeColumn.CARPWD
                + " from " + MyOpenDbHelper.tbBike.NAME + " where " + MyOpenDbHelper.tbBikeColumn.CARNO + "=? limit 1";
        Log.d("DbUtils",sql);
        Cursor cursor = db.rawQuery(sql, new String[]{carNo});
        if(cursor.moveToNext()){
            pwd =  cursor.getString(0);
        }

        cursor.close();
        return pwd;
    }


    public static boolean savePwd(Context context,String carNo,String pwd){
        SQLiteDatabase db = DbManager.getInstance(context).getWritableDb();

        ContentValues values = new ContentValues();
        values.put(MyOpenDbHelper.tbBikeColumn.CARNO,carNo);
        values.put(MyOpenDbHelper.tbBikeColumn.CARPWD,pwd);

        db.beginTransaction();
        long result = db.insert(MyOpenDbHelper.tbBike.NAME, null, values);
        if(result != -1){
            db.setTransactionSuccessful();
        }
        db.endTransaction();

        if(result==1){
            return true;
        }else {
            return false;
        }
    }
}
