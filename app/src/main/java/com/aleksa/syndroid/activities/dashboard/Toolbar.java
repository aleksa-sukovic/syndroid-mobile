package com.aleksa.syndroid.activities.dashboard;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.aleksa.syndroid.R;

public class Toolbar
{
    public Toolbar(AppCompatActivity activity, int toolbarId)
    {
        android.support.v7.widget.Toolbar toolbar = activity.findViewById(toolbarId);
        toolbar.setTitle("");
        activity.setSupportActionBar(toolbar);

        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
    }
}
