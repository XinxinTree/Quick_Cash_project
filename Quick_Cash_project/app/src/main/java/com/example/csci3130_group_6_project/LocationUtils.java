package com.example.csci3130_group_6_project;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LocationUtils {

    private volatile static LocationUtils uniqueInstance;

    private LocationManager locationManager;

    private String locationProvider;

    private Location location;

    private Context mContext;

    private LocationUtils(Context context) {
        mContext = context;
    }


    // Double CheckLock(DCL) is used to implement a single instance
    public static LocationUtils getInstance(Context context) {
        if (uniqueInstance == null) {
            synchronized (LocationUtils.class) {
                if (uniqueInstance == null) {
                    uniqueInstance = new LocationUtils( context );
                }
            }
        }
        return uniqueInstance;
    }

    void getLocation() {
        //1.Get Location Manager
        locationManager = (LocationManager) mContext.getSystemService( Context.LOCATION_SERVICE );
        //2.Get a location provider, GPS or NetWork
        List<String> providers = locationManager.getProviders( true );
        if (providers.contains( LocationManager.NETWORK_PROVIDER )) {
            //In case of network positioning
            Log.d( TAG, "In case of network positioning" );
            locationProvider = LocationManager.NETWORK_PROVIDER;
        } else if (providers.contains( LocationManager.GPS_PROVIDER )) {
            //If it is GPS positioning
            Log.d( TAG, "If it is GPS positioning" );
            locationProvider = LocationManager.GPS_PROVIDER;
        } else {
            Log.d( TAG, "No location provider available" );
            return;
        }
        locationProvider = LocationManager.GPS_PROVIDER;
        // Need to check permissions, otherwise compile error
        if (Build.VERSION.SDK_INT >= 23 &&
                ActivityCompat.checkSelfPermission( mContext, "android.permission.ACCESS_FINE_LOCATION" ) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission( mContext, "android.permission.ACCESS_COARSE_LOCATION" ) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        if (ActivityCompat.checkSelfPermission( mContext, "android.permission.ACCESS_FINE_LOCATION" ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission( mContext, "android.permission.ACCESS_COARSE_LOCATION" ) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        //3.Get the last position, usually the first run, this value is null
        Location location = locationManager.getLastKnownLocation( locationProvider );
        if (location != null) {
            setLocation( location );
        }
        // The second and third parameters are the updated minimum time minTime and minimum distance minDistace, respectively.
        locationManager.requestLocationUpdates( locationProvider, 0, 0, locationListener );
    }

    private void setLocation(Location location) {
        this.location = location;
        String address = "Latitude：" + location.getLatitude() + "Longitude：" + location.getLongitude();
        Log.d( TAG, address );
        new Thread(new Runnable() {
            @Override
            public void run() {
                Geocoder geoCoder = new Geocoder(mContext, Locale.getDefault()); //it is Geocoder
                try {
                    List<Address> address = geoCoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                    if(null == address || address.isEmpty()) {
                        return;
                    }

                    String str = address.get(0).getAddressLine(0);


                    if(mContext instanceof EmployerDashboardActivity) {
                        ((EmployerDashboardActivity) mContext).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ((EmployerDashboardActivity) mContext).setAddress(location, str);
                            }
                        });
                    } else if(mContext instanceof EmployeeDashboardActivity){
                        ((EmployeeDashboardActivity) mContext).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ((EmployeeDashboardActivity) mContext).setAddress(location, str);
                            }
                        });
                    }
                } catch (Exception e) {
                }
            }
        }).start();
    }

    //Get latitude and longitude
    public Location showLocation() {
        return location;
    }

    // Remove Location Listening
    public void removeLocationUpdatesListener() {
        // Need to check permissions, otherwise it won't compile
        if (Build.VERSION.SDK_INT >= 23 &&
                ActivityCompat.checkSelfPermission( mContext, "android.permission.ACCESS_FINE_LOCATION" ) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission( mContext, "android.permission.ACCESS_COARSE_LOCATION" ) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        if (locationManager != null) {
            uniqueInstance = null;
            locationManager.removeUpdates( locationListener );
        }
    }

    /**
     * LocationListern
     * Parameters: Geolocation provider, time interval for listening to location change, distance interval for location change, LocationListener listener
     */

    LocationListener locationListener = new LocationListener() {

        /**
         * When the status of a location provider changes
         */
        @Override
        public void onStatusChanged(String provider, int status, Bundle arg2) {

        }

        /**
         * When a device is turned on
         */
        @Override
        public void onProviderEnabled(String provider) {

        }

        /**
         * When a device is turned off
         */
        @Override
        public void onProviderDisabled(String provider) {

        }

        /**
         * Cell phone location change
         */
        @Override
        public void onLocationChanged(Location location) {
            location.getAccuracy();//Accuracy
            setLocation( location );
        }
    };

}
