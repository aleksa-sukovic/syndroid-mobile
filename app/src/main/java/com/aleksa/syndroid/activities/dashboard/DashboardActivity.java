package com.aleksa.syndroid.activities.dashboard;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.aleksa.syndroid.R;
import com.aleksa.syndroid.activities.connect.ConnectActivity;
import com.aleksa.syndroid.activities.dashboard.navigation.app_drawer.AppDrawer;
import com.aleksa.syndroid.activities.dashboard.navigation.listeners.UnitSelectListener;
import com.aleksa.syndroid.activities.dashboard.navigation.menu.ToolbarMenu;
import com.aleksa.syndroid.fragments.FragmentOrchestrator;
import com.aleksa.syndroid.fragments.keyboard.KeyboardFragment;
import com.aleksa.syndroid.fragments.keyboard.buttons.KeyboardButton;
import com.aleksa.syndroid.library.application.Application;
import com.aleksa.syndroid.library.events.ApplicationEvent;
import com.aleksa.syndroid.library.managers.KeyPressManager;
import com.aleksa.syndroid.library.router.request.Request;
import com.aleksa.syndroid.managers.ThemeManager;
import com.aleksa.syndroid.objects.server.models.Server;
import com.aleksa.syndroid.objects.unit_item.models.Unit;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.util.HashMap;
import java.util.Map;

public class DashboardActivity extends AppCompatActivity implements UnitSelectListener, ToolbarMenu.ToolbarMenuListener
{
    private Server server;
    protected AppDrawer appDrawer;
    private Application application;
    protected ToolbarMenu toolbarMenu;
    private KeyPressManager keyPressManager;
    protected BottomNavigation bottomNavigation;
    private FragmentOrchestrator fragmentOrchestrator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        if (ThemeManager.isNightModeOn(this)) {
            setTheme(R.style.DashboardActivityDark);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        keyPressManager = new KeyPressManager();
        server = getIntent().getParcelableExtra("server");
        appDrawer = new AppDrawer(this, server);
        bottomNavigation = new BottomNavigation(this);
        toolbarMenu = new ToolbarMenu(this, R.menu.dashboard_toolbar_menu);
        fragmentOrchestrator = new FragmentOrchestrator(this, R.id.fragment_container);
    }

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
    public void onBackPressed()
    {
        stopApp();

        Intent intent = new Intent(this, ConnectActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(intent);
    }

    @Override
    protected void onStart()
    {
        EventBus.getDefault().register(this);
        startApp();
        super.onStart();
    }

    @Override
    protected void onStop()
    {
        stopApp();
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    protected void onPause()
    {
        stopApp();
        super.onPause();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleServerStateChange(ApplicationEvent event)
    {
        if (event.getEventType() == ApplicationEvent.EventCode.SERVER_CONNECT) {
            Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();
        }

        if (event.getEventType() == ApplicationEvent.EventCode.SERVER_DISCONNECT) {
            Toast.makeText(this, event.getData(), Toast.LENGTH_SHORT).show();
        }
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void sendRequest(Request request)
    {
        application.send(request, request.getResponseCallback());
    }

    @Override
    public void handleUnitSelect(Unit unit)
    {
        fragmentOrchestrator.navigateToFragment(unit);
    }

    @Override
    public void handleUnitOrderChange(Unit one, Unit two)
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

        Request request = keyPressManager.constructRequest(this, keyCode, event, modifiers);
        if (request != null) {
            sendRequest(request);
        }

        return super.onKeyUp(keyCode, event);
    }

    @Override
    public void onAppDrawerToggle()
    {
        appDrawer.toggleDrawer();
    }

    public void switchTheme(View view)
    {
        ThemeManager.toggleNightMode(this);
    }

    protected void startApp()
    {
        Application application = getApp();

        if (application != null) {
            application.start();
        }
    }

    protected void stopApp()
    {
        Application application = getApp();

        if (application != null) {
            application.stop();
        }
    }

    protected Application getApp()
    {
        if (application != null) {
            application.stop();
        }

        return (application = Application.getInstance(this, server.getIp(), Application.PORT));
    }
}
