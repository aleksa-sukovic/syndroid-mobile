package com.aleksa.syndroid.activities.dashboard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.aleksa.syndroid.R;
import com.aleksa.syndroid.library.application.Application;
import com.aleksa.syndroid.library.router.request.OutgoingRequest;
import com.aleksa.syndroid.managers.ThemeManager;

public class DashboardActivity extends AppCompatActivity
{

    private Application application;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        if (ThemeManager.isNightModeOn(this)) {
            setTheme(R.style.ConnectActivityDark);
        }

        String ip = getIntent().getStringExtra("ip");
        application = Application.getInstance(ip, Application.getDefaultPort());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        editText = findViewById(R.id.edit_text);
    }

    @Override
    protected void onStart()
    {
        application.start();
        super.onStart();
    }

    @Override
    protected void onStop()
    {
        application.stop();
        super.onStop();
    }

    public void sendMessage(View view)
    {
        OutgoingRequest request = new OutgoingRequest.Builder()
            .setExpectsResponse(true)
            .setRoutePath("/sound/volume/set")
            .addParam("amount", Integer.toString(58))
            .setOnResponseCallback(response -> Log.d("Dashboard", "sendMessage: response -> " + response.getData()))
            .build();

        Log.d("Dashboard", "sendMessage: request -> " + request.toString());

        application.send(request);
        application.sendMessage(editText.getText().toString());
    }
}
