package com.example.qrscanner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button button;
    public static final int CAMERA_REQUEST_PERMISSION = 1;
    public static final int SCANNING_REQUEST_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.btnScan);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Start Scanning
                startScanning();
            }
        });
    }

    private void startScanning() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                //ask for permission
                requestPermissions(new String[] {Manifest.permission.CAMERA}, CAMERA_REQUEST_PERMISSION);
            } else {
                //start scanning
                startActivityForResult(new Intent(MainActivity.this, ScanningActivity.class),SCANNING_REQUEST_CODE);
            }
        } else { // For devices having SDK version less than marshmello.. No need for taking permission.
            startActivityForResult(new Intent(MainActivity.this, ScanningActivity.class),SCANNING_REQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == SCANNING_REQUEST_CODE) {
            if(resultCode == RESULT_OK) {
                button.setText(data.getStringExtra("scanning_result"));
            }
        }
    }
}