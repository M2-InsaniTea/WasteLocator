package com.m2dl.helloandroid.wastelocator;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.m2dl.helloandroid.wastelocator.backend.wasteApi.model.Tag;
import com.m2dl.helloandroid.wastelocator.backend.wasteApi.model.UserAccount;

import org.codepond.wizardroid.WizardStep;
import org.codepond.wizardroid.persistence.ContextVariable;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.List;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.entity.ContentType;
import cz.msebera.android.httpclient.entity.mime.HttpMultipartMode;
import cz.msebera.android.httpclient.entity.mime.MultipartEntityBuilder;
import cz.msebera.android.httpclient.entity.mime.content.ByteArrayBody;
import cz.msebera.android.httpclient.entity.mime.content.StringBody;
import cz.msebera.android.httpclient.impl.client.HttpClientBuilder;


public class PictureStep extends WizardStep {
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private Uri imageUri;
    private ImageView imageView;

    @ContextVariable
    private List<Tag> tags;

    private Double longitude;
    private Double latitude;
    Bitmap bitmap;
    LocationManager mLocationManager;
    Long userId;

    public PictureStep() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) throws SecurityException {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_picture_step, container, false);
        imageView = (ImageView) v.findViewById(R.id.imageView);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
        }
        Button button = (Button) v.findViewById(R.id.picButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePhoto(view);
            }
        });

        SharedPreferences settings = getContext().getSharedPreferences("wastelocator", 0);
        userId = settings.getLong("userId", 0);
        mLocationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 20, mLocationListener);
        mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 100, mLocationListener);

        return v;
    }


    public void takePhoto(View view) {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photo = new File(Environment.getExternalStorageDirectory(), "Pic.jpg");
        imageUri = Uri.fromFile(photo);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }

    @Override
    public void onExit(int exitCode) {
        switch (exitCode) {
            case WizardStep.EXIT_NEXT:
                System.out.println("this is lon" + longitude);
                System.out.println("this is lat" + latitude);

                if (bitmap == null) {
                } else if (latitude == null || longitude == null) {
                } else {
                    new SendDataAsyncTask().execute();
                }
                break;
            case WizardStep.EXIT_PREVIOUS:
                //Do nothing...
                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                Uri selectedImage = imageUri;
                getActivity().getContentResolver().notifyChange(selectedImage, null);
                ContentResolver cr = getActivity().getContentResolver();

                try {
                    bitmap = android.provider.MediaStore.Images.Media
                            .getBitmap(cr, selectedImage);

                    imageView.setImageBitmap(bitmap);
                } catch (Exception e) {
                    Toast.makeText(getActivity(), "Failed to load", Toast.LENGTH_SHORT)
                            .show();
                    Log.e("Camera", e.toString());
                }
            }
        }
    }


    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {
            longitude = location.getLongitude();
            latitude = location.getLatitude();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    private class SendDataAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                HttpClient httpClient = HttpClientBuilder.create().build();
                HttpPost postRequest = new HttpPost(BuildConfig.UPLOAD_URL);
                MultipartEntityBuilder reqEntity = MultipartEntityBuilder.create();
                reqEntity.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);


                reqEntity.addPart("userId", new StringBody(Long.toString(userId), ContentType.TEXT_PLAIN));

                reqEntity.addPart("latitude", new StringBody(Double.toString(latitude), ContentType.TEXT_PLAIN));
                reqEntity.addPart("longitude", new StringBody(Double.toString(longitude), ContentType.TEXT_PLAIN));

                for (Tag tag : tags) {
                    reqEntity.addPart("tagIds[]", new StringBody(tag.getId().toString(), ContentType.TEXT_PLAIN));
                }

                try {
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 75, bos);
                    byte[] dataBitmap = bos.toByteArray();
                    ByteArrayBody bab = new ByteArrayBody(dataBitmap, "image.jpg");
                    reqEntity.addPart("photo", bab);
                } catch (Exception e) {
                    //Log.v("Exception in Image", ""+e);
                    reqEntity.addPart("photo", new StringBody("", ContentType.TEXT_PLAIN));
                }

                postRequest.setEntity(reqEntity.build());
                HttpResponse response = httpClient.execute(postRequest);
                BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
                String sResponse;
                StringBuilder s = new StringBuilder();
                while ((sResponse = reader.readLine()) != null) {
                    s = s.append(sResponse);
                }

                System.out.println("test");
            } catch (Exception e) {
                System.out.println("test");
            }

            getActivity().finish();
            return null;
        }
    }


}
