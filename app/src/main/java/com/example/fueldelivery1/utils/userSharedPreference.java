package com.example.fueldelivery1.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import static android.content.ContentValues.TAG;

public class userSharedPreference {

    static String PREF_USER_NAME= "username";

    static SharedPreferences getSharedPreferences(Context ctx) {
        //Log.i("User : ", "getSharedPreferences : " + PREF_USER_NAME);
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setUserName(Context ctx, String userName)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_NAME, userName);
        //Log.i("User : ", "Logged In : " + userName);
        editor.apply();
        editor.commit();
    }

    public static String getUserName(Context ctx)
    {
        //Log.i("User : ", "getSharedPreferences : " + PREF_USER_NAME);
        return getSharedPreferences(ctx).getString(PREF_USER_NAME, "");
    }
}
