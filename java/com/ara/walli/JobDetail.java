package com.ara.walli;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by r4kia on 4/20/2017.
 */

public class JobDetail extends AppCompatActivity {
    TextView d_name, d_jname, d_description, d_pay, d_location;
    double dpay = 0.0;
    String newString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.job_detail_layout);
        d_name = (TextView) findViewById(R.id.d_name);
        d_jname = (TextView) findViewById(R.id.d_jname);
        d_description = (TextView) findViewById(R.id.d_description);
        d_pay = (TextView) findViewById(R.id.d_jpay);
        d_location = (TextView) findViewById(R.id.d_jlocation);
        d_name.setText("Employer name : " + getIntent().getStringExtra("d_name"));
        d_jname.setText("Job name : " + getIntent().getStringExtra("d_jname"));
        d_description.setText("Job description : " + getIntent().getStringExtra("d_description"));
        d_pay.setText("Job payment(dollars) : " + getIntent().getDoubleExtra("d_jpay", dpay));
//        d_location.setText("Job location : "+getIntent().getStringExtra("d_jlocation"));

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                newString = null;
            } else {
                newString = extras.getString("Address");
            }
        } else {
            newString = (String) savedInstanceState.getSerializable("Address");
        }
        d_location.setText(newString);
    }
}
