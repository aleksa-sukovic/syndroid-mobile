package com.aleksa.syndroid.activities.dashboard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.aleksa.syndroid.R;
import com.aleksa.syndroid.library.application.Application;
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

        application = Application.getInstance("192.168.1.103", 3000);
        application.start();

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
        application.sendMessage(editText.getText().toString());
    }
}
