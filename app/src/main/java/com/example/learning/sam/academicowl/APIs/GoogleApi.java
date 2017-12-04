package com.example.learning.sam.academicowl.APIs;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.ActivityRecognition;
import com.google.android.gms.location.LocationServices;

/**
 * Created by Sam on 12/3/2017.
 */


public class GoogleApi implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener
{
    public GoogleApiClient mApiClient;

    public GoogleApi(Context context){
        mApiClient=new GoogleApiClient.Builder(context)
                .addApi(ActivityRecognition.API)
                .addApi(LocationServices.API)
                .addConnectionCallbacks((GoogleApiClient.ConnectionCallbacks) context)
                .addOnConnectionFailedListener((GoogleApiClient.OnConnectionFailedListener) context)
                .build();

    }

    public GoogleApiClient getmApiClient()
    {
        return mApiClient;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
