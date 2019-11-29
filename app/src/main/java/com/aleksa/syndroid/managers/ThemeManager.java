package com.aleksa.syndroid.managers;

import android.content.Context;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

public class ThemeManager
{
    private static String LIGHT_MODE = "light_mode_shared_preferences";

    public static void toggleTheme(AppCompatActivity activity)
    {
        setLightMode(activity, !isLightModeOn(activity));

        activity.recreate();
    }

    public static boolean isLightModeOn(Context context)
    {
        return PreferenceManager.getDefaultSharedPreferences(context)
            .getBoolean(LIGHT_MODE, false);
    }

    private static void setLightMode(Context context, boolean enabled)
    {
        PreferenceManager.getDefaultSharedPreferences(context)
            .edit()
            .putBoolean(LIGHT_MODE, enabled)
            .apply();
    }
}
