package com.aleksa.syndroid.activities.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.aleksa.syndroid.R;
import com.aleksa.syndroid.activities.connect.ConnectActivity;
import com.aleksa.syndroid.activities.dashboard.navigation.app_drawer.AppDrawer;
import com.aleksa.syndroid.activities.dashboard.navigation.menu.ToolbarMenu;
import com.aleksa.syndroid.library.application.Application;
import com.aleksa.syndroid.managers.ThemeManager;
import com.aleksa.syndroid.objects.server.models.Server;

import org.greenrobot.eventbus.EventBus;

import androidx.annotation.Nullable;

public abstract class BaseDashboard extends AppCompatActivity implements ToolbarMenu.ToolbarMenuListener
{
    protected ToolbarMenu toolbarMenu;
    protected AppDrawer appDrawer;
    protected BottomNavigation bottomNavigation;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        if (ThemeManager.isNightModeOn(this)) {
            setTheme(R.style.DashboardActivityDark);
        }

        beforeInitialization();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        initialize();
    }

    protected void beforeInitialization() {
        //
    }

    protected void initialize() {
        toolbarMenu = new ToolbarMenu(this, R.menu.dashboard_toolbar_menu);
        appDrawer = new AppDrawer(this, getServer());
        bottomNavigation = new BottomNavigation(this);
    }

    protected abstract Application getSynDroidApplication();

    protected abstract Server getServer();

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        toolbarMenu.inflateMenu(menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        return toolbarMenu.handleOptionsItemSelected(item);
    }

    @Override
    public void onAppDrawerToggle()
    {
        appDrawer.toggleDrawer();
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

    @Override
    protected void onPause()
    {
        stopSynDroidApplication();

        super.onPause();
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
