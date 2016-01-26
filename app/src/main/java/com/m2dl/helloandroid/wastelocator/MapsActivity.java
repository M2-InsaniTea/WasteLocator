/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.m2dl.helloandroid.wastelocator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.common.base.Joiner;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.m2dl.helloandroid.wastelocator.backend.wasteApi.WasteApi;
import com.m2dl.helloandroid.wastelocator.backend.wasteApi.model.GeoPt;
import com.m2dl.helloandroid.wastelocator.backend.wasteApi.model.Interest;
import com.m2dl.helloandroid.wastelocator.backend.wasteApi.model.ListInterestBean;
import com.m2dl.helloandroid.wastelocator.backend.wasteApi.model.Tag;
import com.m2dl.helloandroid.wastelocator.backend.wasteApi.model.UserAccount;

import java.util.ArrayList;
import java.util.List;

/**
 * This shows how to create a simple activity with a map and a marker on the map.
 */
public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private WasteApi wasteApi;

    GoogleMap mMap;

    static final CameraPosition SYDNEY =
            new CameraPosition.Builder().target(new LatLng(-33.87365, 151.20689))
                    .zoom(15.5f)
                    .bearing(0)
                    .tilt(25)
                    .build();
    static final CameraPosition BMIG =
            new CameraPosition.Builder().target(new LatLng(43.560802, 1.468649))
                    .zoom(16.5f)
                    .bearing(0)
                    .tilt(25)
                    .build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        wasteApi = CloudEndpointBuilderHelper.getEndpoints();
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we
     * just add a marker near Africa.
     */
    @Override
    public void onMapReady(GoogleMap map) {

        mMap = map;

        new GetInterestsAsyncTask(this).execute();

        //map.addMarker(new MarkerOptions().position(BMIG.target).title("Marker"));

        map.moveCamera(CameraUpdateFactory.newCameraPosition(BMIG));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.disconnect:
            case R.id.post_interest:
                System.out.println("You have chosen "+item.getTitle());
                return true;
        }
        return false;
    }

    public void callbackFromGetInterestsAsyncTask(ListInterestBean result) {

        List<Interest> interests = result.getInterests();
        List<Tag> tags = result.getTags();

        for (Interest interest : interests) {
            drawInterest(interest, tags);
        }

    }

    public void drawInterest(Interest interest, List<Tag> tags) {

        // System.out.println(interest.getId());

        List<GeoPt> locations = interest.getLocations();

        if (locations.isEmpty())
            return;

        if (locations.size() == 1) {

            // draw a marker
            String markerText = "";

            // coordinates
            double latitude = locations.get(0).getLatitude();
            double longitude = locations.get(0).getLongitude();

            // tags name
            ArrayList<String> listTagsName = new ArrayList<>();
            for (Long tagID : interest.getTagIds()) {
                for (Tag tag : tags) {
                    if (tag.getId().equals(tagID)) {
                        listTagsName.add(tag.getName());
                    }
                }
            }

            if (!listTagsName.isEmpty()) {
                markerText += Joiner.on(", ").join(listTagsName);
            }

            // submitter
            UserAccount submitter = interest.getSubmitter();
            if (submitter != null) {
                markerText += "\n"+submitter.getUsername();
            }

            mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title(markerText));

        } else {



        }

    }

    private String getColorOfInterest(Interest interest, List<Tag> tags) {

        List<Long> tagIds = interest.getTagIds();
        if (tagIds != null && !tagIds.isEmpty()) {
            final Long firstTagId = tagIds.get(0);

            Tag myTag = Iterables.tryFind(tags,
                    new Predicate<Tag>() {
                        @Override
                        public boolean apply(Tag input) {
                            return input.getId() == firstTagId;
                        }
                    }).orNull();

            if (myTag != null) {
                return myTag.getColor();
            }
        }
        return "#000000"; // Couleur par défaut si on a rien trouvé. Là c'est du noir
    }

}
