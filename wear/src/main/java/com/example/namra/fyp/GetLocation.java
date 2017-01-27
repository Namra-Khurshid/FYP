package com.example.namra.fyp;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by namra on 11/13/2016.
 */
public class GetLocation implements LocationListener {

    private Context context;
    private Location location;
    protected LocationManager locationManager;

    boolean isGPSEnabled = false;
    boolean canGetLocation = false;
    boolean isNetworkEnabled = false;

    double latitude;
    double longitude;

    private final static long MIN_DISTANCE_FOR_UPDATE = 10;
    private final static long MIN_TIME_TO_UPDATE = 0;

    Utility utility;

    public GetLocation(Context context) {
        this.context = context;

        utility = new Utility(context);

        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (!isGPSEnabled && !isNetworkEnabled) {
            utility.showSettingsDialog();
        } else {
            canGetLocation = true;
            if (isGPSEnabled) {
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_TO_UPDATE, MIN_DISTANCE_FOR_UPDATE, this);
                    location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                    if (location != null) {
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                    }
                }
                else utility.showSettingsDialog();
            } else if (isNetworkEnabled) {
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_TO_UPDATE, MIN_DISTANCE_FOR_UPDATE, this);
                    location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                    if (location != null) {
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                    }
                }
                else utility.showSettingsDialog();
            }
        }
    }

    private boolean checkPerm() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            utility.showSettingsDialog();
            return false;
        }
    }

    public void stopGPSUpdate() {
        if (locationManager != null) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                locationManager.removeUpdates(GetLocation.this);
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        this.location = location;
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

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void showSettingsAlert() {
    }



}
