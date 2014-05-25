package com.example.androidcourse_assignment3;

import android.app.Activity;
import android.os.Bundle;

public class JobDetailsActivity extends Activity {
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Retrieve values from previous activity
        
        Bundle data = getIntent().getExtras();
        Job job = (Job) data.getParcelable("JOB_DETAILS");

    }
}
