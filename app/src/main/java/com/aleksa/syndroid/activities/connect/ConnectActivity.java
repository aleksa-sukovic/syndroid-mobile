package com.aleksa.syndroid.activities.connect;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.aleksa.syndroid.R;

import com.aleksa.syndroid.activities.connect.favourites.FavouritesFragment;
import com.aleksa.syndroid.managers.ThemeManager;
import com.aleksa.syndroid.objects.server.models.Server;


public class ConnectActivity extends AppCompatActivity implements FavouritesFragment.OnFavouritesSelect
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        if (ThemeManager.isNightModeOn(this)) {
            setTheme(R.style.ConnectActivityDark);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);
    }

    public void scanQrCode(View view)
    {
        ThemeManager.toggleNightMode(this);
    }

    @Override
    public void onFavouritesSelect(Server server)
    {
        ConnectActivity.this.runOnUiThread(() -> Toast.makeText(this, server.getName(), Toast.LENGTH_SHORT).show());
    }
}
