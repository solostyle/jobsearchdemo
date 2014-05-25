package com.example.androidcourse_assignment3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MyArrayAdapter extends ArrayAdapter<Job> {

	public MyArrayAdapter(Context context, int textViewResourceId,
			Job[] arr) {
		super(context, textViewResourceId, arr);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View v = convertView;
		
		if (v == null) {
			LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.row_layout, parent, false);
		}

		TextView jobTitleView = (TextView) v.findViewById(R.id.textView1);
		jobTitleView.setText(this.getItem(position).getTitle());
		
		TextView companyView = (TextView) v.findViewById(R.id.textView2);
		companyView.setText(this.getItem(position).getCompany());
		
		return v;
	}

}
