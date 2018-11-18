package com.aleksa.syndroid.activities.connect;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.aleksa.syndroid.R;

import com.aleksa.syndroid.activities.connect.favourites.FavouritesFragment;
import com.aleksa.syndroid.managers.ThemeManager;
import com.aleksa.syndroid.objects.server.models.Server;


public class ConnectActivity extends AppCompatActivity implements FavouritesFragment.FavouritesFragmentListener
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
        Log.d("ConnectActivity", server.toString());
    }
}
