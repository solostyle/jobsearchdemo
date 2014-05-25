package com.example.androidcourse_assignment3;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
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
		
		TextView descView = (TextView) findViewById(R.id.textView4);
		descView.setText(job.getDesc());
		
		TextView postedTimeView = (TextView) findViewById(R.id.textView5);
		postedTimeView.setText(job.getPostedTime());
		
		TextView jobLinkView = (TextView) findViewById(R.id.textView6);
		jobLinkView.setText("<a href=\"" + job.getJobLink() + "\">View & Apply to Job</a>");
		jobLinkView.setMovementMethod(LinkMovementMethod.getInstance());
		
		TextView simJobsLinkView = (TextView) findViewById(R.id.textView7);
		simJobsLinkView.setText("<a href=\"" + job.getSimJobsLink() + "\">Similar Jobs</a>");
		simJobsLinkView.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
