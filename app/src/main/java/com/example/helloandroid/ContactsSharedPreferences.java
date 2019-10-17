package com.example.helloandroid;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;

public class ContactsSharedPreferences {
    public static SharedPreferences share(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("contacts", Context.MODE_PRIVATE);
        return sharedPreferences;
    }

    public static Map<String, ?> getData(Context context){
        return share(context).getAll();
    }

    public static boolean setContacts(String contacts, String number, Context context){
        SharedPreferences.Editor e = share(context).edit();
        e.putString(contacts, number);
        Boolean status = e.commit();
        return status;
    }
}

