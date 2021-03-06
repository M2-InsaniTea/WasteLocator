package com.m2dl.helloandroid.wastelocator;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.m2dl.helloandroid.wastelocator.backend.wasteApi.WasteApi;
import com.m2dl.helloandroid.wastelocator.backend.wasteApi.model.UserAccount;

import java.io.IOException;

class ConnectAsyncTask extends AsyncTask<String, Void, UserAccount> {
    private static WasteApi WasteApiService = null;
    private Context context;
    private WasteApi wasteApi;

    public ConnectAsyncTask(Context context) {
        super();
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        wasteApi = CloudEndpointBuilderHelper.getEndpoints();
    }

    @Override
    protected UserAccount doInBackground(String... params) {
        String name = params[0];

        try {
            return wasteApi.users().connect(name).execute();
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    protected void onPostExecute(UserAccount result) {
        String msg;
        if (result == null) {
            msg = "Connection failed";
        } else {
            Intent intent = new Intent(context, MapsActivity.class);
            context.startActivity(intent);
            msg = String.format("You're now connected as %s", result.getUsername());

            SharedPreferences settings = context.getSharedPreferences("wastelocator", 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putLong("userId", result.getId());
            editor.commit();
        }
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }
}