package com.example.helloandroid;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;

public class HistorySharedPreferences {
    public static SharedPreferences share(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("history", Context.MODE_PRIVATE);
        return sharedPreferences;
    }

    public static Map<String, ?> getData(Context context){
        return share(context).getAll();
    }

    public static boolean setNumber(String date, String number, Context context){
        SharedPreferences.Editor e = share(context).edit();
        e.putString(number, date);
        Boolean status = e.commit();
        return status;
    }
}
