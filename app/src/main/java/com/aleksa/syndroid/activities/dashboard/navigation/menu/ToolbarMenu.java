package com.aleksa.syndroid.activities.dashboard.navigation.menu;

import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.aleksa.syndroid.R;
import com.aleksa.syndroid.library.router.request.Request;
import com.aleksa.syndroid.managers.KeyboardManager;

import org.greenrobot.eventbus.EventBus;

public class ToolbarMenu
{
    private int menuResource;
    private AppCompatActivity activity;
    private ToolbarMenuListener listener;

    public ToolbarMenu(AppCompatActivity activity, int menuResource)
    {
        this.activity = activity;
        this.listener = (ToolbarMenuListener) activity;
        this.menuResource = menuResource;
    }

    public void inflateMenu(Menu menu)
    {
        activity.getMenuInflater().inflate(menuResource, menu);
    }

    public boolean handleOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId()) {
            case android.R.id.home : {
                listener.onAppDrawerToggle();
                return true;
            }
            case R.id.menu_keyboard : {
                KeyboardManager.showKeyboard((AppCompatActivity) listener);
                return true;
            }
            case R.id.menu_left_click : {
                EventBus.getDefault().post(new Request.Builder()
                    .setRouteByPath("/mouse/left-click").build());
                return true;
            }
            case R.id.menu_right_click : {
                EventBus.getDefault().post(new Request.Builder()
                    .setRouteByPath("/mouse/right-click").build());
                return true;
            }
        }

        return false;
    }

    public interface ToolbarMenuListener {
        void onAppDrawerToggle();
    }
}
