package com.aleksa.syndroid.activities.dashboard;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.aleksa.syndroid.R;
import com.aleksa.syndroid.activities.connect.ConnectActivity;
import com.aleksa.syndroid.library.application.Application;
import com.aleksa.syndroid.managers.ThemeManager;

public class DashboardActivity extends AppCompatActivity
{

    private Application application;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        if (ThemeManager.isNightModeOn(this)) {
            setTheme(R.style.ConnectActivityDark);
        }

        application = Application.getInstance(getIntent().getStringExtra("ip"), Application.getDefaultPort());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        initialize();
    }

    private void initialize()
    {
        initializeToolbar();
    }

    private void initializeToolbar()
    {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
    }

    @Override
    protected void onStart()
    {
        application.start();
        super.onStart();
    }

    @Override
    protected void onStop()
    {
        application.stop();
        super.onStop();
    }

    @Override
    public void onBackPressed()
    {
        application.stop();

        Intent intent = new Intent(this, ConnectActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.dashboard_toolbar_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId()) {
            case android.R.id.home : {
                Toast.makeText(this, "App Drawer", Toast.LENGTH_SHORT).show();
                return true;
            }
            case R.id.menu_keyboard : {
                Toast.makeText(this, "Keyboard", Toast.LENGTH_SHORT).show();
                return true;
            }
            case R.id.menu_left_click : {
                Toast.makeText(this, "Left Click", Toast.LENGTH_SHORT).show();
                return true;
            }
            case R.id.menu_right_click : {
                Toast.makeText(this, "Right Click", Toast.LENGTH_SHORT).show();
                return true;
            }
        }

        return false;
    }
}
