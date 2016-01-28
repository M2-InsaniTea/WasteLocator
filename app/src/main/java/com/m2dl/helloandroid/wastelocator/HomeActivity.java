package com.m2dl.helloandroid.wastelocator;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.m2dl.helloandroid.wastelocator.backend.wasteApi.WasteApi;

public class HomeActivity extends AppCompatActivity {
    private WasteApi wasteApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        wasteApi = CloudEndpointBuilderHelper.getEndpoints();
        setContentView(R.layout.activity_home);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Please allow location access for this app", Toast.LENGTH_LONG);
        }

        SharedPreferences settings = getSharedPreferences("wastelocator", 0);

        if (settings.contains("userId")) {
            new ReconnectAsyncTask(this).execute(settings.getLong("userId", 0));
        }
    }

    public void openMapsActivity(View view) {
        String name = ((TextView) findViewById(R.id.editText)).getText().toString();
        new ConnectAsyncTask(this).execute(name);
    }
}
