package com.aleksa.syndroid.activities.dashboard;


import android.os.Handler;
import android.os.Looper;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.aleksa.syndroid.R;
import com.aleksa.syndroid.activities.dashboard.navigation.listeners.UnitSelectListener;
import com.aleksa.syndroid.fragments.FragmentOrchestrator;
import com.aleksa.syndroid.library.application.Application;
import com.aleksa.syndroid.library.events.ApplicationEvent;
import com.aleksa.syndroid.library.router.request.OutgoingRequest;
import com.aleksa.syndroid.managers.ThemeManager;
import com.aleksa.syndroid.objects.server.models.Server;
import com.aleksa.syndroid.objects.unit_item.models.Unit;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class DashboardActivity extends BaseDashboard implements UnitSelectListener
{

    private FragmentOrchestrator fragmentOrchestrator;
    private Application          application;
    private Server               server;

    @Override
    protected void beforeInitialization()
    {
        super.beforeInitialization();

        server = getIntent().getParcelableExtra("server");
    }

    @Override
    protected void initialize()
    {
        super.initialize();

        fragmentOrchestrator = new FragmentOrchestrator(this, R.id.fragment_container);

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

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onOutgoingRequest(OutgoingRequest request)
    {
        application.send(request);
    }

    @Override
    public void onUnitSelect(Unit unit)
    {
        fragmentOrchestrator.navigateToFragment(unit);
    }

    @Override
    public void onUnitOrderChange(Unit one, Unit two)
    {
        new Handler(Looper.getMainLooper()).post(() -> bottomNavigation.refreshItems());
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event)
    {
        int code = event.getUnicodeChar();

        OutgoingRequest.Builder requestBuilder = new OutgoingRequest.Builder();
        requestBuilder.setRoutePath("/keyboard/type");
        requestBuilder.addParam("key", Integer.toString(code));

        if (keyCode == KeyEvent.KEYCODE_SHIFT_LEFT || keyCode == KeyEvent.KEYCODE_SHIFT_RIGHT) {
            requestBuilder.addParam("shift", "true");
        }

        if (keyCode == KeyEvent.KEYCODE_DEL) {
            requestBuilder.addParam("backspace", "true");
        }

        EventBus.getDefault().post(requestBuilder.build());

        return super.onKeyUp(keyCode, event);
    }

    public void switchTheme(View view)
    {
        ThemeManager.toggleNightMode(this);
    }
}
