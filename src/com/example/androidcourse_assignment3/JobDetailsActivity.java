package com.example.androidcourse_assignment3;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class JobDetailsActivity extends Activity {
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Retrieve values from previous activity
        
        Bundle data = getIntent().getExtras();
        Job job = (Job) data.getParcelable("JOB_DETAILS");
        setContentView(R.layout.activity_job_details);
        
        TextView jobTitleView = (TextView) findViewById(R.id.textView1);
		jobTitleView.setText(job.getTitle());
		
		TextView companyView = (TextView) findViewById(R.id.textView2);
		companyView.setText(job.getCompany());
		
		TextView locationView = (TextView) findViewById(R.id.textView3);
		locationView.setText(job.getLocation());
    }
}
