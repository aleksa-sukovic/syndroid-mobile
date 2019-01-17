package com.aleksa.syndroid.activities.connect;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.aleksa.syndroid.R;

import com.aleksa.syndroid.activities.connect.favourites.FavouritesFragment;
import com.aleksa.syndroid.activities.dashboard.DashboardActivity;
import com.aleksa.syndroid.activities.scanner.ScannerActivity;
import com.aleksa.syndroid.managers.ThemeManager;
import com.aleksa.syndroid.objects.server.models.Server;
import com.aleksa.syndroid.objects.server.repositories.ServerRepository;


public class ConnectActivity extends AppCompatActivity implements FavouritesFragment.OnFavouritesSelect
{

    private static final int SCAN_QR_REQUEST_CODE = 321;

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
        Intent intent = new Intent(this, ScannerActivity.class);

        startActivityForResult(intent, SCAN_QR_REQUEST_CODE);
    }

    @Override
    public void onFavouritesSelect(Server server)
    {
        Intent intent = new Intent(this, DashboardActivity.class);

        intent.putExtra("server", server);

        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        if (requestCode != SCAN_QR_REQUEST_CODE || resultCode != RESULT_OK) {
            return;
        }

        if (data == null || data.getData() == null) {
            return;
        }

        ServerRepository repository = new ServerRepository(getApplication());

        repository.insert(new Server(data.getData().toString(), "New Server"))
            .then(result -> onFavouritesSelect((Server) result));
    }
}
