package com.aleksa.syndroid.activities.dashboard.app_drawer;

import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.aleksa.syndroid.R;
import com.aleksa.syndroid.objects.server.models.Server;

public class AppDrawer
{
    private AppCompatActivity activity;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private int drawerLayoutId, navigationViewId;
    private Server server;

    private TextView serverName;
    private TextView serverIp;

    public AppDrawer(AppCompatActivity activity, Server server, int drawerLayoutId, int navigationViewId)
    {
        this.activity = activity;
        this.server = server;
        this.drawerLayoutId = drawerLayoutId;
        this.navigationViewId = navigationViewId;

        initializeAppDrawerViews();
        initializeNavigationView();
        initializeAppDrawerData();
    }

    private void initializeAppDrawerViews()
    {
        drawerLayout = activity.findViewById(drawerLayoutId);
        navigationView = activity.findViewById(navigationViewId);

        serverName = navigationView.getHeaderView(0).findViewById(R.id.server_name);
        serverIp = navigationView.getHeaderView(0).findViewById(R.id.server_ip);
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
        this.serverName.setText(server.getName());
        this.serverIp.setText(server.getIp());
    }

    public void openDrawer()
    {
        drawerLayout.openDrawer(GravityCompat.START);
    }
}
