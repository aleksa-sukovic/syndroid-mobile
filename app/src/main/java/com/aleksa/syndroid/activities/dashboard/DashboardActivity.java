package com.aleksa.syndroid.activities.dashboard;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.aleksa.syndroid.R;
import com.aleksa.syndroid.activities.connect.ConnectActivity;
import com.aleksa.syndroid.library.application.Application;
import com.aleksa.syndroid.managers.ThemeManager;
import com.aleksa.syndroid.objects.server.models.Server;
import com.aleksa.syndroid.objects.server.repositories.ServerRepository;

public class DashboardActivity extends AppCompatActivity
{

    private Application application;
    private ServerRepository serverRepository;
    private AppDrawer appDrawer;
    private Server server;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        if (ThemeManager.isNightModeOn(this)) {
            setTheme(R.style.ConnectActivityDark);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        server = getIntent().getParcelableExtra("server");
        serverRepository = new ServerRepository(getApplication());
        appDrawer = new AppDrawer(this, server, R.id.drawer_layout, R.id.navigation_view);

        initialize();
    }

    private void initialize()
    {
        initializeToolbar();

        application = Application.getInstance(server.getIp(), Application.getDefaultPort());
        application.start();
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
        if (application != null) {
            application.start();
        }

        super.onStart();
    }

    @Override
    protected void onStop()
    {
        if (application != null) {
            application.stop();
        }

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
}
