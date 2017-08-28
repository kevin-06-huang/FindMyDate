package com.example.findmydate;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by kevincrazykid on 25/08/17.
 */

public class Date implements Parcelable {
    protected String UidFrom;
    protected String UidTo;
    protected String location;

    public Date(){

    }

    public Date(User From, User To){
        this.UidFrom = From.getUid();
        this.UidTo = To.getUid();
    }

    private void setLocation(String location){
        this.location = location;
    }

    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeValue(this);
    }
    public static final Parcelable.Creator<Date> CREATOR = new Parcelable.Creator<Date>() {
        public Date createFromParcel(Parcel in) {
            return new Date(in);
        }

        public Date[] newArray(int size) {
            return new Date[size];
        }
    };
    private String getUidFrom(){
        return UidFrom;
    }
    private String getUidTo(){
        return UidTo;
    }
    private String getLocation(){
        return location;
    }
    private Date(Parcel in) {
        Date date = Date.class.cast(in.readValue(Date.class.getClassLoader()));
        this.UidFrom = date.getUidFrom();
        this.UidTo = date.getUidTo();
        this.location = date.getLocation();
    }
    // simple class that just has one member property as an example
}
