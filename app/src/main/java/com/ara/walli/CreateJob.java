package com.ara.walli;

import android.*;
import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by r4kia on 4/20/2017.
 */

public class CreateJob extends AppCompatActivity {
    EditText editName,editJobname,editJobdescription,editPay,editLocation;
    Button btnadd,getlocation;
    LocationManager locationManager;
    String provider;
    final int MY_PERMISSION_REQUEST_CODE = 7171;
    double lat, lng;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_job);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        editName = (EditText) findViewById(R.id.editname);
        editJobname = (EditText) findViewById(R.id.editjname);
        editJobdescription = (EditText) findViewById(R.id.editdescription);
        editPay = (EditText) findViewById(R.id.editpay);
        editLocation = (EditText) findViewById(R.id.editaddress);
        getlocation = (Button) findViewById(R.id.getlocation);
        getlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    return;
                }

                Location myLocation = locationManager.getLastKnownLocation(provider);
                lat = myLocation.getLatitude();
                lng = myLocation.getLongitude();
                new GetAddress().execute(String.format("%.4f,%.4f", lat, lng));

            }

        });
        btnadd =(Button) findViewById(R.id.btnadd);
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnAdd(v);
                startActivity(new Intent(CreateJob.this,Dashboard.class));
            }
        });



    }
    public void OnAdd(View view){
        String ename = editName.getText().toString();
        String jname = editJobname.getText().toString();
        String jdes = editJobdescription.getText().toString();
        String jpay = editPay.getText().toString();
        String jlocation = editLocation.getText().toString();
        String type = "Post";
        DatabaseInsert databaseInsert = new DatabaseInsert(this);
        databaseInsert.execute(type,ename,jname,jdes,jpay,jlocation);




    }


    private class GetAddress extends AsyncTask<String, Void, String> {

        ProgressDialog dialog = new ProgressDialog(CreateJob.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("Please wait...");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                double lat = Double.parseDouble(strings[0].split(",")[0]);
                double lng = Double.parseDouble(strings[0].split(",")[1]);
                String response;
                LocationDataHelper http = new LocationDataHelper();
                String url = String.format("https://maps.googleapis.com/maps/api/geocode/json?latlng=%.4f,%.4f&sensor=false", lat, lng);
                response = http.GetHTTPData(url);
                return response;
            } catch (Exception ex) {

            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject jsonObject = new JSONObject(s);

                String address = ((JSONArray) jsonObject.get("results")).getJSONObject(0).get("formatted_address").toString();
                editLocation.setText("hello");


            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (dialog.isShowing())
                dialog.dismiss();
        }
    }

}
