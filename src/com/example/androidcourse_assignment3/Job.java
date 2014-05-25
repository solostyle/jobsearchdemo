package com.example.androidcourse_assignment3;

import android.os.Parcel;
import android.os.Parcelable;

public class Job implements Parcelable {
	
	public String company;
	public String title;
	public String location;
	public String desc;
	public String postedTime;

	public Job(String company, String title, String location, String desc, String postedTime) {
		this.company = company;
		this.title = title;
		this.location = location;
		this.desc = desc;
		this.postedTime = postedTime;
	}
    // example constructor that takes a Parcel and gives you an object populated with its values
    public Job(Parcel source) {
        company = source.readString();
        title = source.readString();
        location = source.readString();
        desc = source.readString();
        postedTime = source.readString();
    }
    
	public String getCompany() {
		return this.company;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public String getLocation() {
		return this.location;
	}
	
	public String getDesc() {
		return this.desc;
	}
	
	public String getPostedTime() {
		return this.postedTime;
	}
    // 99.9% of the time you can just ignore this
    public int describeContents() {
        return 0;
    }

    // write your object's data to the passed-in Parcel
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(company);
        dest.writeString(title);
        dest.writeString(location);
        dest.writeString(desc);
        dest.writeString(postedTime);
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<Job> CREATOR = new Parcelable.Creator<Job>() {
    	
        public Job createFromParcel(Parcel in) {
            return new Job(in);
        }

        public Job[] newArray(int size) {
            return new Job[size];
        }
    };
}