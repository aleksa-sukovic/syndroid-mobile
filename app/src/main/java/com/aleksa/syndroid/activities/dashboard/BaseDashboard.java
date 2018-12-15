package com.aleksa.syndroid.activities.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.aleksa.syndroid.R;
import com.aleksa.syndroid.activities.connect.ConnectActivity;
import com.aleksa.syndroid.activities.dashboard.navigation.app_drawer.AppDrawer;
import com.aleksa.syndroid.library.application.Application;
import com.aleksa.syndroid.managers.ThemeManager;
import com.aleksa.syndroid.objects.server.models.Server;

import org.greenrobot.eventbus.EventBus;

import androidx.annotation.Nullable;

public abstract class BaseDashboard extends AppCompatActivity
{
    private AppDrawer appDrawer;
    private BottomNavigation bottomNavigation;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        if (ThemeManager.isNightModeOn(this)) {
            setTheme(R.style.DashboardActivityDark);
        }

        beforeInitialization();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        appDrawer = new AppDrawer(this, getServer());
        bottomNavigation = new BottomNavigation(this);

        initialize();
        afterInitialization();
    }

    protected void beforeInitialization() {
        //
    }

    protected abstract void initialize();

    protected void afterInitialization() {
        appDrawer.setServer(getServer());
    }

    protected abstract Application getSynDroidApplication();

    protected abstract Server getServer();

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
                appDrawer.toggleDrawer();
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

    @Override
    public void onBackPressed()
    {
        stopSynDroidApplication();

        Intent intent = new Intent(this, ConnectActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(intent);
    }

    @Override
    protected void onStart()
    {
        EventBus.getDefault().register(this);
        startSynDroidApplication();

        super.onStart();
    }

    @Override
    protected void onStop()
    {
        stopSynDroidApplication();
        EventBus.getDefault().unregister(this);

        super.onStop();
    }

    protected void startSynDroidApplication()
    {
        Application application = getSynDroidApplication();

        if (application != null) {
            application.start();
        }
    }

    protected void stopSynDroidApplication()
    {
        Application application = getSynDroidApplication();

        if (application != null) {
            application.stop();
        }
    }
}
