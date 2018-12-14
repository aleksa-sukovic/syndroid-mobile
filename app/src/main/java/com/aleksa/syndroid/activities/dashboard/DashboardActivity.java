package com.aleksa.syndroid.activities.dashboard;


import com.aleksa.syndroid.R;
import com.aleksa.syndroid.library.application.Application;
import com.aleksa.syndroid.objects.server.models.Server;

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
}
