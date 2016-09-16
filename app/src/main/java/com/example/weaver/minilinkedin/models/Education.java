package com.example.weaver.minilinkedin.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Weaver on 2016/8/24.
 */
public class Education implements Parcelable {
    public String id;
    public String name;
    public String major;
    public Date startDate;
    public Date endDate;
    public List<String> details;
    public Education(){
        id = UUID.randomUUID().toString();
    }

    protected Education(Parcel in) {
        id = in.readString();
        name = in.readString();
        major = in.readString();
        startDate = new Date(in.readLong());
        endDate = new Date(in.readLong());
        details = in.createStringArrayList();
    }

    public static final Creator<Education> CREATOR = new Creator<Education>() {
        @Override
        public Education createFromParcel(Parcel in) {
            return new Education(in);
        }

        @Override
        public Education[] newArray(int size) {
            return new Education[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(major);
        dest.writeLong(startDate.getTime());
        dest.writeLong(endDate.getTime());
        dest.writeStringList(details);
    }
}
