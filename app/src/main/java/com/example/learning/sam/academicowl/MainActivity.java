package com.example.learning.sam.academicowl;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.example.learning.sam.academicowl.APIs.ActivityRecognizedService;
import com.example.learning.sam.academicowl.APIs.GoogleApi;
import com.example.learning.sam.academicowl.Services.MyLocationService;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.ActivityRecognition;
import com.google.android.gms.location.LocationServices;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;
import android.Manifest;
//import com.google.android.gms.common.api.GoogleApiClient;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        ActivityCompat.OnRequestPermissionsResultCallback , PermissionResultCallback {
    @Nullable
    @BindView(R.id.btnLog)
    Button btnLog;

//    public GoogleApiClient mApiClient;

    ArrayList<String> permissions = new ArrayList<>();
    PermissionUtils permissionUtils;

    GoogleApi mLocApi;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        permissionUtils = new PermissionUtils(this);
        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        permissionUtils.check_permission(permissions, "Needs permission to serve you better", 1);
        mLocApi = new GoogleApi(this);
        mLocApi.getmApiClient().connect();
//        //getting the google API Client.
//        mApiClient=new GoogleApiClient.Builder(this)
//                .addApi(ActivityRecognition.API)
//                .addApi(LocationServices.API)
//                .addConnectionCallbacks(this)
//                .addOnConnectionFailedListener(this)
//                .build();
//        mApiClient.connect();

        sharedPreferences= getSharedPreferences("PATTERN", MODE_PRIVATE);
        if(sharedPreferences.getBoolean("addressSaved",false)==false){
            startActivity(new Intent(this,PreMainActivity.class));
        }
        else {

            Calendar cal = Calendar.getInstance();
            try{
            cal.setTime(new Date(
                    this.getPackageManager()
                    .getPackageInfo(
                            this.getPackageName(),0
                    ).firstInstallTime)
            );
            Log.i("Installed date is", cal.getTime().toString());
            }
            catch (Exception e)
            {

            }
            //check the current date is < installed date+7
            if(true){
                //make one job that will only run once and will log the user activities, with its time
            }
            else{
                //send notification to user.
            }

        }
        }


    @Optional
    @OnClick(R.id.btnLog)
    public void checkLog(){
        //get the log of the mobile using databases.

    }
//
//    private String getDateTime() {
//        // get date time in custom format
//        SimpleDateFormat sdf = new SimpleDateFormat("[yyyy/MM/dd - HH:mm:ss]");
//        return sdf.format(new Date(appInfo.firstInstallTime));
//    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        PendingIntent pendingIntent=PendingIntent.getService(this,0,(new Intent(getApplicationContext(), ActivityRecognizedService.class)),
                PendingIntent.FLAG_UPDATE_CURRENT);
        ActivityRecognition.ActivityRecognitionApi.requestActivityUpdates(mLocApi.getmApiClient(),100,pendingIntent);
        startService(new Intent(getApplicationContext(), MyLocationService.class));

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        permissionUtils.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    @Override
    public void PermissionGranted(int request_code) {
        Log.i("PERMISSION", "GRANTED");
    }

    @Override
    public void PartialPermissionGranted(int request_code, ArrayList<String> granted_permissions) {

        Log.i("PERMISSION PARTIALLY", "GRANTED");
    }

    @Override
    public void PermissionDenied(int request_code) {

        Log.i("PERMISSION", "DENIED");
    }

    @Override
    public void NeverAskAgain(int request_code) {
        Log.i("PERMISSION", "NEVER ASK AGAIN");
    }
}
