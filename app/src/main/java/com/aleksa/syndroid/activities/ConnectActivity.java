package com.aleksa.syndroid.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.aleksa.syndroid.R;
import com.aleksa.syndroid.managers.ThemeManager;

public class ConnectActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        if (ThemeManager.isNightModeOn(this)) {
            setTheme(R.style.ConnectActivityDark);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);
    }
}
