package com.example.namra.fyp;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.annotation.NonNull;
import android.support.wearable.view.WatchViewStub;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiActivity;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataItemBuffer;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

import javax.net.ssl.SSLEngineResult;


public class MainActivity extends Activity {

    private TextView mTextView;
    private Button start;
    private TextView lat;
    private TextView lon;
    private TextView setlat;
    private TextView setlon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final GoogleApiClient apiClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .build();
        apiClient.connect();

        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mTextView = (TextView) stub.findViewById(R.id.text);
                lat = (TextView) stub.findViewById(R.id.textView2);
                lon = (TextView) stub.findViewById(R.id.textView3);
                setlat = (TextView) stub.findViewById(R.id.textView4);
                setlon = (TextView) stub.findViewById(R.id.textView5);
                start = (Button) findViewById(R.id.button);

                lat.setVisibility(View.GONE);
                lon.setVisibility(View.GONE);
                setlat.setVisibility(View.GONE);
                setlon.setVisibility(View.GONE);

                start.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        mTextView.setVisibility(View.GONE);
                        start.setVisibility(View.GONE);
                        lat.setVisibility(View.VISIBLE);
                        lon.setVisibility(View.VISIBLE);
                        setlat.setVisibility(View.VISIBLE);
                        setlon.setVisibility(View.VISIBLE);

                        GetLocation tracker = new GetLocation(MainActivity.this);
                       //if (!tracker.canGetLocation()) {
                          //  tracker.showSettingsAlert();
                        //}
                            double latitude = tracker.getLatitude();
                            double longitude = tracker.getLongitude();
                            String dblat = Double.toString(latitude);
                            String dblon = Double.toString(longitude);
                        //System.out.println("lat = "+latitude);
                        //System.out.println("lon = "+longitude);
                        Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                        startActivity(intent);
                       // setlat.setText(dblat);
                        //setlon.setText(dblon);

                        if(apiClient.isConnected()){
                            //create the dataMapRequest with a path from constants.Path must start with a /
                            PutDataMapRequest putDataMapRequest = PutDataMapRequest.create("Coordinates");
                            putDataMapRequest.getDataMap().putString("Latitude", dblat);
                            putDataMapRequest.getDataMap().putString("Longitude", dblon);
                            PutDataRequest request = putDataMapRequest.asPutDataRequest();
                            Wearable.DataApi.putDataItem(apiClient, request).setResultCallback(new ResultCallback() {
                                @Override
                                public void onResult(@NonNull Result result) {

                                    if (!result.getStatus().isSuccess()) {
                                        //data sync failed
                                    }else{
                                        //data sync was  success
                                    }

                                }

                            });
                        }
                    }
                });


            }

        });

    }
}

