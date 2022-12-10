package com.example.sparespark.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesHelper {

    private static final String PREFS_FILE = "spare_space";
    private static final String ROLE = "user_role";
    private static final String UID = "user_id";


    private static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE);
    }


    //For setting user role
    public static void setUserRole(Context context, String role) {
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.putString(ROLE, role);
        editor.apply();
    }

    //Gettting user role
    public static String getUserRole(Context context ) {
        SharedPreferences prefs = getPrefs(context);
        return prefs.getString(ROLE, "");
    }

    //For setting current uid
    public static void setCurrentUid(Context context, String uid) {
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.putString(UID, uid);
        editor.apply();
    }

    //Gettting current uid
    public static String getCurrentUid(Context context ) {
        SharedPreferences prefs = getPrefs(context);
        return prefs.getString(UID, "");
    }




}
