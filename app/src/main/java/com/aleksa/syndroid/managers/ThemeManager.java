package com.aleksa.syndroid.managers;

import android.content.Context;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

import com.aleksa.syndroid.library.events.ApplicationEvent;

import org.greenrobot.eventbus.EventBus;

public class ThemeManager
{
    private static String NIGHT_MODE = "dark_mode_shared_preferences";

    public static void toggleNightMode(Context context)
    {
        setNightMode(context, !isNightModeOn(context));
    }

    public static void toggleNightMode(AppCompatActivity activity)
    {
        setNightMode(activity, !isNightModeOn(activity));

        activity.recreate();
    }

    public static boolean isNightModeOn(Context context)
    {
        return PreferenceManager.getDefaultSharedPreferences(context)
            .getBoolean(NIGHT_MODE, false);
    }

    public static void setNightMode(Context context, boolean enabled)
    {
        PreferenceManager.getDefaultSharedPreferences(context)
            .edit()
            .putBoolean(NIGHT_MODE, enabled)
            .apply();
    }

}
