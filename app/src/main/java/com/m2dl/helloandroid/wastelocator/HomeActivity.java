package com.m2dl.helloandroid.wastelocator;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Pair;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        new EndpointsAsyncTask().execute(new Pair<Context, String>(this, "Manfred"));
    }
}
