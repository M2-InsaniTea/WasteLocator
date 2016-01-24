package com.m2dl.helloandroid.wastelocator;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.TextView;

import com.m2dl.helloandroid.wastelocator.backend.wasteApi.WasteApi;

public class HomeActivity extends AppCompatActivity {
    private WasteApi wasteApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        wasteApi = CloudEndpointBuilderHelper.getEndpoints();
        setContentView(R.layout.activity_home);
//        final Button button = (Button) findViewById(R.id.button_open_map);
    }

    public void openMapsActivity(View view) {
        String name = ((TextView) findViewById(R.id.editText)).getText().toString();
        new ConnectAsyncTask(this).execute(name);
    }
}
