package com.aleksa.syndroid.activities.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.aleksa.syndroid.R;
import com.aleksa.syndroid.activities.connect.ConnectActivity;
import com.aleksa.syndroid.activities.dashboard.app_drawer.AppDrawer;
import com.aleksa.syndroid.activities.dashboard.toolbar.Toolbar;
import com.aleksa.syndroid.library.application.Application;
import com.aleksa.syndroid.managers.ThemeManager;
import com.aleksa.syndroid.objects.server.models.Server;

import androidx.annotation.Nullable;

public abstract class BaseDashboard extends AppCompatActivity
{
    private int layoutId;
    private int darkStyleId;
    private int optionsMenuId;
    private int drawerLayoutId;
    private int navigationViewId;

    private Toolbar   toolbar;
    private AppDrawer appDrawer;

    public BaseDashboard(int layoutId, int darkStyleId, int drawerLayoutId, int navigationViewId, int optionsMenuId)
    {
        this.layoutId = layoutId;
        this.darkStyleId = darkStyleId;
        this.optionsMenuId = optionsMenuId;
        this.drawerLayoutId = drawerLayoutId;
        this.navigationViewId = navigationViewId;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        if (ThemeManager.isNightModeOn(this)) {
            setTheme(darkStyleId);
        }

        beforeInitialization();

        super.onCreate(savedInstanceState);
        setContentView(layoutId);

        toolbar = new Toolbar(this, R.id.toolbar);
        appDrawer = new AppDrawer(this, getServer(), drawerLayoutId, navigationViewId);

        initialize();

        afterInitialization();
    }

    protected void beforeInitialization() {
        //
    }

    protected abstract void initialize();

    protected void afterInitialization() {

    }

    protected abstract Application getSynDroidApplication();

    protected abstract Server getServer();

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(optionsMenuId, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId()) {
            case android.R.id.home : {
                appDrawer.openDrawer();
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
        startSynDroidApplication();

        super.onStart();
    }

    @Override
    protected void onStop()
    {
        stopSynDroidApplication();

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
