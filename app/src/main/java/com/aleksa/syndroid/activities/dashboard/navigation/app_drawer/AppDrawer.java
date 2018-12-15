package com.aleksa.syndroid.activities.dashboard.navigation.app_drawer;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aleksa.syndroid.R;
import com.aleksa.syndroid.activities.dashboard.navigation.toolbar.Toolbar;
import com.aleksa.syndroid.managers.ThemeManager;
import com.aleksa.syndroid.objects.server.models.Server;

public class AppDrawer implements DrawerLayout.DrawerListener
{
    private AppCompatActivity activity;
    private DrawerLayout      drawerLayout;
    private NavigationView    navigationView;
    private Toolbar           toolbar;
    private Server            server;

    private boolean isDrawerOpen;

    private TextView serverName;
    private TextView serverIp;
    private ImageView nightModeToggle;

    public AppDrawer(AppCompatActivity activity, Server server)
    {
        this.activity = activity;
        this.toolbar = new Toolbar(activity);

        initializeAppDrawerViews();
        initializeNavigationView();
        initializeAppDrawerData();
        initializeNightModeSwitch();
    }

    private void initializeAppDrawerViews()
    {
        drawerLayout = activity.findViewById(R.id.drawer_layout);
        drawerLayout.addDrawerListener(this);

        navigationView = activity.findViewById(R.id.navigation_view);

        serverName = navigationView.getHeaderView(0).findViewById(R.id.server_name);
        serverIp = navigationView.getHeaderView(0).findViewById(R.id.server_ip);
        nightModeToggle = navigationView.getHeaderView(0).findViewById(R.id.night_mode_toggle);
    }

    private void initializeNavigationView()
    {
        navigationView.setNavigationItemSelectedListener( menuItem -> {
            menuItem.setChecked(true);
            drawerLayout.closeDrawers();

            return true;
        } );
    }

    private void initializeAppDrawerData()
    {
        if (server != null) {
            this.serverName.setText(server.getName());
            this.serverIp.setText(server.getIp());
        }
    }

    public void toggleDrawer()
    {
        if (isDrawerOpen) {
            drawerLayout.closeDrawers();
        } else {
            drawerLayout.openDrawer(GravityCompat.START);
        }
    }

    public void setServer(Server server)
    {
        this.server = server;

        initializeAppDrawerData();
    }

    private void initializeNightModeSwitch()
    {
        nightModeToggle.setOnClickListener(v -> {
            toggleNightModeButtonResource();

            ThemeManager.toggleNightMode(activity);
        });

       toggleNightModeButtonResource();
    }

    private void toggleNightModeButtonResource()
    {
        if (ThemeManager.isNightModeOn(activity)) {
            nightModeToggle.setImageResource(R.drawable.ic_dark_mode_off);
        } else {
            nightModeToggle.setImageResource(R.drawable.ic_dark_mode_on);
        }
    }

    @Override
    public void onDrawerSlide(@NonNull View view, float slideOffset)
    {
        toolbar.setTransformationOffset(isDrawerOpen, slideOffset);
    }

    @Override
    public void onDrawerOpened(@NonNull View view)
    {
        isDrawerOpen = true;
    }

    @Override
    public void onDrawerClosed(@NonNull View view)
    {
        isDrawerOpen = false;
    }

    @Override
    public void onDrawerStateChanged(int newState)
    {
        if (newState == DrawerLayout.STATE_IDLE) {
            if (isDrawerOpen) {
                toolbar.setArrowIcon();
            } else {
                toolbar.setBurgerIcon();
            }
        }
    }
}
