package com.m2dl.helloandroid.wastelocator;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Toast;

import com.m2dl.helloandroid.wastelocator.backend.wasteApi.WasteApi;
import com.m2dl.helloandroid.wastelocator.backend.wasteApi.model.UserAccount;

import java.io.IOException;

class ReconnectAsyncTask extends AsyncTask<Long, Void, UserAccount> {
    private static WasteApi WasteApiService = null;
    private Context context;
    private WasteApi wasteApi;

    public ReconnectAsyncTask(Context context) {
        super();
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        wasteApi = CloudEndpointBuilderHelper.getEndpoints();
    }

    @Override
    protected UserAccount doInBackground(Long... params) {
        Long id = params[0];

        try {
            return wasteApi.users().detail(id).execute();
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    protected void onPostExecute(UserAccount result) {
        String msg;
        if (result == null) {
            msg = "Connection failed";

            SharedPreferences settings = context.getSharedPreferences("wastelocator", 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.remove("userId");
            editor.commit();
        } else {
            Intent intent = new Intent(context, MapsActivity.class);
            context.startActivity(intent);
            msg = String.format("You're now connected as %s", result.getUsername());
        }
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }
}