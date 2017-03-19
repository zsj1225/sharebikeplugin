package com.jun.bikeplugin;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import java.text.DecimalFormat;

public class Utils {
    private static Toast toast;
    private static final String SPPLUGIN = "bikeplugin";

    private Utils() {
    }

    public static void showToast(Context context, String content) {
        if (toast == null) {
            toast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
        }

        toast.setText(content);

        toast.show();
    }


    public static void SaveData(Context context, String key,String value) {
        SharedPreferences share = context.getSharedPreferences(SPPLUGIN, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = share.edit();
        edit.putString(key, value);
        edit.apply();  //保存数据信息
    }

    public static void SaveData(Context context, String key,boolean value) {
        SharedPreferences share = context.getSharedPreferences(SPPLUGIN, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = share.edit();
        edit.putBoolean(key, value);
        edit.apply();  //保存数据信息
    }

    public static String getStringData(Context context, String key) {
        //指定操作的文件名称
        SharedPreferences share =  context.getSharedPreferences(SPPLUGIN, Context.MODE_PRIVATE);
        return share.getString(key,null);
    }

    public static boolean getBooleabData(Context context, String key) {
        //指定操作的文件名称
        SharedPreferences share =  context.getSharedPreferences(SPPLUGIN, Context.MODE_PRIVATE);
        return share.getBoolean(key,false);
    }
}
