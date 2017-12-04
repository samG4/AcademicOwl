package com.example.learning.sam.academicowl;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.support.annotation.BinderThread;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.learning.sam.academicowl.Services.MyLocationService;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;

import static android.R.layout.simple_spinner_dropdown_item;

public class PreMainActivity extends AppCompatActivity {


    @Nullable
    @BindView(R.id.etHomeAddress)
    EditText etHomeAddress;

    @Nullable
    @BindView(R.id.etSchoolAddress)
    EditText etSchoolAddress;

    @Nullable
    @BindView(R.id.btnSaveLocation)
    Button btnSaveAddress;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    MyLocationService myLocationService;

    @Nullable
    @BindView(R.id.spinTransport)
    Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_main);
        ButterKnife.bind(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.transport_mode,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        sharedPreferences = getSharedPreferences("PATTERN", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        myLocationService = MyLocationService.getInstance();
    }


    @Optional
    @OnClick(R.id.btnSaveLocation)
    public void saveAddress() {

        float longitude;
        float latitude;
//        public GeoPoint getLocationFromAddress(String strAddress){

        Geocoder coder = new Geocoder(this);
        List<Address> address;
//            GeoPoint p1 = null;

        try {

            //home address
            address = coder.getFromLocationName(etHomeAddress.getText().toString(),5);
            if (address.isEmpty()) {
                Toast.makeText(this,"Address cannot be empty",Toast.LENGTH_SHORT);
            }
            Address location=address.get(0);
            latitude = (float) location.getLatitude();
            longitude = (float) location.getLongitude();
            Log.i("Home Location",latitude+"");
            Log.i("Home Location",latitude+"");
            editor.putFloat("homeLong", longitude);
            editor.putFloat("homeLat", latitude);
            MyLocationService.setHomeLatitude(latitude);
            MyLocationService.setHomeLongitude(longitude);
            //school address
            address = coder.getFromLocationName(etSchoolAddress.getText().toString(),5);
            if (address.isEmpty()) {
                Toast.makeText(this,"Address cannot be empty",Toast.LENGTH_SHORT);
            }
            Address location2=address.get(0);
            latitude = (float) location2.getLatitude();
            longitude = (float) location2.getLongitude();
            Log.i("Office Location",latitude+"");
            Log.i("Office Location",latitude+"");
            editor.putFloat("schoolLong", longitude);
            editor.putFloat("schoolLat", latitude);
            MyLocationService.setHomeLatitude(latitude);
            MyLocationService.setHomeLongitude(longitude);


            String modeOfTransport = spinner.getSelectedItem().toString();
            editor.putString("transport mode",modeOfTransport);
            editor.putBoolean("addressSaved",true);
            editor.apply();
            startActivity(new Intent(this,MainActivity.class));
        }catch (IOException e)
        {
            editor.putBoolean("addressSaved",false);
            Log.e("QAActivity",e.getMessage());
        }
    }
}
