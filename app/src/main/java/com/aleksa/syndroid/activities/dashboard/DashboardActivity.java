package com.aleksa.syndroid.activities.dashboard;


import android.widget.Toast;

import com.aleksa.syndroid.R;
import com.aleksa.syndroid.library.application.Application;
import com.aleksa.syndroid.library.events.ApplicationEvent;
import com.aleksa.syndroid.objects.server.models.Server;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class DashboardActivity extends BaseDashboard
{

    private Application application;
    private Server server;

    public DashboardActivity()
    {
        super(R.layout.activity_dashboard, R.style.DashboardActivityDark, R.id.drawer_layout, R.id.navigation_view, R.menu.dashboard_toolbar_menu);
    }

    @Override
    protected void beforeInitialization()
    {
        server = getIntent().getParcelableExtra("server");
    }

    @Override
    protected void initialize()
    {
        application = Application.getInstance(getServer().getIp(), Application.getDefaultPort());

        application.start();
    }

    @Override
    protected Application getSynDroidApplication()
    {
        return application;
    }

    @Override
    protected Server getServer()
    {
        return server;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onServerStateChange(ApplicationEvent event)
    {
        if (event.getEventType() == ApplicationEvent.EventCode.SERVER_CONNECT) {
            Toast.makeText(this, "Connected to server!", Toast.LENGTH_SHORT).show();
        }

        if (event.getEventType() == ApplicationEvent.EventCode.SERVER_DISCONNECT) {
            Toast.makeText(this, "Disconnected from server! " + event.getData(), Toast.LENGTH_SHORT).show();
        }
    }
}
