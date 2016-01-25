package com.m2dl.helloandroid.wastelocator;

import android.content.Context;
import android.os.AsyncTask;

import com.m2dl.helloandroid.wastelocator.backend.wasteApi.WasteApi;
import com.m2dl.helloandroid.wastelocator.backend.wasteApi.model.ListInterestBean;

import java.io.IOException;

class GetInterestsAsyncTask extends AsyncTask<Void, Void, ListInterestBean> {
    private static WasteApi WasteApiService = null;
    MapsActivity mapsActivity;
    private WasteApi wasteApi;

    public GetInterestsAsyncTask(MapsActivity mapsActivity) {
        super();
        this.mapsActivity = mapsActivity;
    }

    @Override
    protected ListInterestBean doInBackground(Void... params) {

        try {
            return wasteApi.interests().list().execute();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    protected void onPreExecute() {
        wasteApi = CloudEndpointBuilderHelper.getEndpoints();
    }

    @Override
    protected void onPostExecute(ListInterestBean result) {
        mapsActivity.callbackFromGetInterestsAsyncTask(result);
    }
}