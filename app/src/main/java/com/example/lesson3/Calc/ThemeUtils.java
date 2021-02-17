package com.example.lesson3.Calc;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class ThemeUtils {
    public static final String THEME_KEY = "THEME_KEY";
    private static int themeCode;

    public static void onActivityCreateSetTheme(Activity activity) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        themeCode = preferences.getInt(THEME_KEY, 0);
        Constants.currentTheme = themeCode;
        activity.setTheme(Constants.themeIDs[themeCode]);

        Log.d("MMM", "Theme = " + Constants.themeNames[themeCode]);
    }

    public static void setCalcTheme(Activity activity, int tCode) {
        themeCode = tCode;
        activity.finish();
        activity.startActivity(new Intent(activity, activity.getClass()));

        Log.d("MMM", "New Theme set = " + Constants.themeNames[themeCode]);
    }
}
