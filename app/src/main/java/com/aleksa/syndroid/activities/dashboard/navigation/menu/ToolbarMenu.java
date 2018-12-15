package com.aleksa.syndroid.activities.dashboard.navigation.menu;

import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.aleksa.syndroid.R;

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
                Toast.makeText(activity, "Keyboard", Toast.LENGTH_SHORT).show();
                return true;
            }
            case R.id.menu_left_click : {
                Toast.makeText(activity, "Left Click", Toast.LENGTH_SHORT).show();
                return true;
            }
            case R.id.menu_right_click : {
                Toast.makeText(activity, "Right Click", Toast.LENGTH_SHORT).show();
                return true;
            }
        }

        return false;
    }

    public interface ToolbarMenuListener {
        void onAppDrawerToggle();
    }
}
