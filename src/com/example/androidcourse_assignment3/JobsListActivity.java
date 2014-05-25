package com.example.androidcourse_assignment3;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class JobsListActivity extends ListActivity {
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Retrieve values from previous activity
        
        Intent i = getIntent();
        Parcelable[] p = i.getParcelableArrayExtra("JOBS_ARRAY");
        Job[] jobs = new Job[p.length];
        for (int j=0; j<jobs.length; j++){
             jobs[j] = (Job) p[j];
        }
        MyArrayAdapter jobsAdapter = new MyArrayAdapter(this, android.R.layout.simple_list_item_1, jobs);
		this.setListAdapter(jobsAdapter);
    }
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
        Job job = (Job) getListAdapter().getItem(position);
        String title = (String) job.getTitle();
        Toast.makeText(this, "Clicked on: " + " " + title, Toast.LENGTH_LONG).show();
    }
	
	
}
