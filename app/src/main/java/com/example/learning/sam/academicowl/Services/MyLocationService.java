package com.example.learning.sam.academicowl.Services;

/**
 * Created by Sam on 12/3/2017.
 */
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.icu.math.BigDecimal;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

//import com.example.a586333.locationprototype.UserPattern.UserPatternWrtTime;

public class MyLocationService extends Service {
    private static MyLocationService loc_instance = null;
    private boolean flag;
    private static final String TAG = "LocationUpdate";
    private LocationManager mLocationManager = null;
    private static final int LOCATION_INTERVAL = 100;
    private static final float LOCATION_DISTANCE = 10f;

    public static double getHomeLatitude() {
        return homeLatitude;
    }

    public static void setHomeLatitude(float homeLatitude) {
        MyLocationService.homeLatitude = homeLatitude;
    }

    public static double getHomeLongitude() {
        return homeLongitude;
    }

    public static void setHomeLongitude(float homeLongitude) {
        MyLocationService.homeLongitude = homeLongitude;
    }

    public static double getOfficeLatitude() {
        return schoolLatitude;
    }

    public static void setOfficeLatitude(float schoolLatitude) {
        MyLocationService.schoolLatitude = schoolLatitude;
    }

    public static double getOfficeLongitude() {
        return schoolLongitude;
    }

    public static void setOfficeLongitude(float schoolLongitude) {
        MyLocationService.schoolLongitude = schoolLongitude;
    }

    private static float homeLatitude;
    private static float homeLongitude;
    private static float schoolLatitude;
    private static float schoolLongitude;
    //    private final static double homeLatitude = 37.2904;
//    private final static double homeLongitude = -121.9036;
//    private final static double schoolLatitude = 37.3131;
//    private final static double schoolLongitude = -121.9342;
    private static final double constant = .0050;
    public boolean getFlag() {
        return flag;
    }


    public static MyLocationService getInstance() {
        if (loc_instance == null)
            loc_instance = new MyLocationService();
        return loc_instance;
    }


    private class LocationListener implements android.location.LocationListener {
        Location mLastLocation;

        public LocationListener(String provider) {
            Log.e(TAG, "LocationListener " + provider);
            mLastLocation = new Location(provider);
        }
        @Override
        public void onLocationChanged(Location location) {
            Log.e(TAG, "onLocationChanged: " + location);
            if (location != null) {
                location.getLatitude();
                location.getLongitude();
                location.getTime();
                // Returns accuracy of location lock in meters
                location.getAccuracy();
                String newLat = String.valueOf(location.getLatitude());
                String newLong = String.valueOf(location.getLongitude());
                String time = String.valueOf(location.getTime());
                String accuracy = String.valueOf(location.getAccuracy());
                new MyAsyncTask().execute(newLat, newLong);
            }
            mLastLocation.set(location);
//            if (String.format("%.2f", location.getLatitude()).equals(String.valueOf(homeLatitude)) &&
//                        String.format("%.2f", location.getLongitude()).equals(String.valueOf(homeLongitude))) {
//                    Log.i("User is at Home", "" + location);
//                }
//                if (String.format("%.2f", location.getLatitude()).equals(String.valueOf(schoolLatitude)) &&
//                        String.format("%.2f", location.getLongitude()).equals(String.valueOf(schoolLongitude))) {
//                    Log.i("User is at school", "" + location);
//            }
        }


        private class MyAsyncTask extends AsyncTask<String, String, Double> {

            @Override
            protected Double doInBackground(String... params) {
                Log.i("Location: ", params[0] + "   other paramerter  " + params[1]);
                Double newLat = Double.parseDouble(params[0]);
                Double newLong = Double.parseDouble(params[1]);
                Double dist2Office = Math.sqrt((newLat - schoolLatitude) * (newLat - schoolLatitude) + (newLong - schoolLongitude) * (newLong - schoolLongitude));
                Double dist2Home = Math.sqrt((newLat - homeLatitude) * (newLat - homeLatitude) + (newLong - homeLongitude) * (newLong - homeLongitude));

                if (dist2Office <= constant)
                    Log.i("User status:----->", "Towards Office");
                if (dist2Home <= constant) {
                    Log.i("User status:----->", "Towards Home");
//                    UserPatternWrtTime.location = "home";
                    MyLocationService.getInstance().flag=true;
                }
                else
                {
                    MyLocationService.getInstance().flag=false;
                }
                return null;
            }

            protected void onPostExecute(Double result) {
            }


        }

        @Override
        public void onProviderDisabled(String provider) {
            Log.e(TAG, "onProviderDisabled: " + provider);
        }

        @Override
        public void onProviderEnabled(String provider) {
            Log.e(TAG, "onProviderEnabled: " + provider);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Log.e(TAG, "onStatusChanged: " + provider);
        }
    }

    LocationListener[] mLocationListeners = new LocationListener[]{
            new LocationListener(LocationManager.GPS_PROVIDER),
            new LocationListener(LocationManager.NETWORK_PROVIDER)
    };

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand");
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        Log.e(TAG, "onCreate");
        initializeLocationManager();
        try {
            mLocationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
                    mLocationListeners[1]);
        } catch (java.lang.SecurityException ex) {
            Log.i(TAG, "fail to request location update, ignore", ex);
        } catch (IllegalArgumentException ex) {
            Log.d(TAG, "network provider does not exist, " + ex.getMessage());
        }
        try {
            mLocationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
                    mLocationListeners[0]);
        } catch (java.lang.SecurityException ex) {
            Log.i(TAG, "fail to request location update, ignore", ex);
        } catch (IllegalArgumentException ex) {
            Log.d(TAG, "gps provider does not exist " + ex.getMessage());
        }
    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy");
        super.onDestroy();
        if (mLocationManager != null) {
            for (int i = 0; i < mLocationListeners.length; i++) {
                try {
                    mLocationManager.removeUpdates(mLocationListeners[i]);
                } catch (Exception ex) {
                    Log.i(TAG, "fail to remove location listeners, ignore", ex);
                }
            }
        }
    }

    private void initializeLocationManager() {
        Log.e(TAG, "initializeLocationManager");
        if (mLocationManager == null) {
            mLocationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        }
    }
}
