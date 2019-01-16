package com.aleksa.syndroid.activities.dashboard;


import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.aleksa.syndroid.R;
import com.aleksa.syndroid.activities.dashboard.navigation.listeners.UnitSelectListener;
import com.aleksa.syndroid.fragments.FragmentOrchestrator;
import com.aleksa.syndroid.fragments.keyboard.KeyboardFragment;
import com.aleksa.syndroid.fragments.keyboard.buttons.KeyboardButton;
import com.aleksa.syndroid.fragments.keyboard.buttons.ShiftButton;
import com.aleksa.syndroid.library.application.Application;
import com.aleksa.syndroid.library.events.ApplicationEvent;
import com.aleksa.syndroid.library.managers.KeyPressManager;
import com.aleksa.syndroid.library.router.request.OutgoingRequest;
import com.aleksa.syndroid.managers.ThemeManager;
import com.aleksa.syndroid.objects.server.models.Server;
import com.aleksa.syndroid.objects.unit_item.models.Unit;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;

public class DashboardActivity extends BaseDashboard implements UnitSelectListener
{

    private FragmentOrchestrator fragmentOrchestrator;
    private Application          application;
    private Server               server;
    private KeyPressManager      keyPressManager;

    @Override
    protected void beforeInitialization()
    {
        super.beforeInitialization();

        server = getIntent().getParcelableExtra("server");
        keyPressManager = new KeyPressManager();
    }

    @Override
    protected void initialize()
    {
        super.initialize();

        fragmentOrchestrator = new FragmentOrchestrator(this, R.id.fragment_container);
    }

    @Override
    protected Application getSynDroidApplication()
    {
        if (application != null) {
            application.stop();
        }

        application = Application.getInstance(this, getServer().getIp(), Application.getDefaultPort());

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
            Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();
        }

        if (event.getEventType() == ApplicationEvent.EventCode.SERVER_DISCONNECT) {
            Toast.makeText(this, event.getData(), Toast.LENGTH_SHORT).show();
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
        Map<KeyboardButton, Boolean> modifiers = new HashMap<>();

        if (fragmentOrchestrator.isActiveFragment("keyboard")) {
            KeyboardFragment keyboardFragment = (KeyboardFragment) fragmentOrchestrator.getActiveFragment();

            modifiers = keyboardFragment.getModifiers();
        }

        OutgoingRequest outgoingRequest = keyPressManager.constructRequest(this, keyCode, event, modifiers);
        if (outgoingRequest != null) {
            onOutgoingRequest(outgoingRequest);
        }

        return super.onKeyUp(keyCode, event);
    }

    public void switchTheme(View view)
    {
        ThemeManager.toggleNightMode(this);
    }
}
