package com.example.androidcourse_assignment3;

import android.os.Parcel;
import android.os.Parcelable;

public class JobsArray implements Parcelable {
	
	private Job[] jobs;
	
	public Job[] getJobsArray() {
		return jobs;
	}
	
    // 99.9% of the time you can just ignore this
    public int describeContents() {
        return 0;
    }

    // write your object's data to the passed-in Parcel
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeArray(jobs);
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<JobsArray> CREATOR = new Parcelable.Creator<JobsArray>() {
        public JobsArray createFromParcel(Parcel in) {
            return new JobsArray(in);
        }

        public JobsArray[] newArray(int size) {
            return new JobsArray[size];
        }
    };

    // example constructor that takes a Parcel and gives you an object populated with its values
    private JobsArray(Parcel source) {
        source.readArray(getClass().getClassLoader());
    }
}