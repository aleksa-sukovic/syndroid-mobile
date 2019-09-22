package com.aleksa.syndroid.activities.scanner;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.aleksa.syndroid.R;
import com.aleksa.syndroid.library.permissions.PermissionManager;
import com.aleksa.syndroid.managers.ThemeManager;
import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;

import androidx.annotation.NonNull;

public class ScannerActivity extends AppCompatActivity implements DecodeCallback
{

    private CodeScanner codeScanner;

    private PermissionManager permissionManager;
    private int cameraPermissionRequestCode;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        if (ThemeManager.isNightModeOn(this)) {
            setTheme(R.style.ConnectActivityDark);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

        permissionManager = new PermissionManager();

        if (permissionManager.check(this, Manifest.permission.CAMERA)) {
            initScanner();
            return;
        }

        cameraPermissionRequestCode = permissionManager.request(this, Manifest.permission.CAMERA);
    }

    private void initScanner()
    {
        CodeScannerView scannerView = findViewById(R.id.scanner_view);
        scannerView.setOnClickListener(e -> codeScanner.startPreview());

        codeScanner = new CodeScanner(this, scannerView);
        codeScanner.setDecodeCallback(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @android.support.annotation.NonNull String[] permissions, @android.support.annotation.NonNull int[] grantResults)
    {
        if (permissionManager.permissionGranted(cameraPermissionRequestCode, requestCode, permissions, grantResults)) {
            initScanner();
        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        if (codeScanner != null) {
            codeScanner.startPreview();
        }
    }

    @Override
    protected void onPause()
    {
        if (codeScanner != null) {
            codeScanner.releaseResources();
        }

        super.onPause();
    }

    @Override
    public void onDecoded(@NonNull Result result)
    {
        Intent data = new Intent();
        data.setData(Uri.parse(result.toString()));

        setResult(RESULT_OK, data);
        finish();
    }
}
