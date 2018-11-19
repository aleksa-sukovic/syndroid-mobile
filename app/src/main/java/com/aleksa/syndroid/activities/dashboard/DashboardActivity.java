package com.aleksa.syndroid.activities.dashboard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.aleksa.syndroid.R;
import com.aleksa.syndroid.managers.ThemeManager;

public class DashboardActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        if (ThemeManager.isNightModeOn(this)) {
            setTheme(R.style.ConnectActivityDark);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
    }

    public void switchTheme(View view)
    {
        ThemeManager.toggleNightMode(this);
    }
}
