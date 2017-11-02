package com.example.findmydate;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by kevincrazykid on 25/08/17.
 */

public class Date implements Parcelable {
    private User from;
    private User to;
    private String location;
    //private Boolean accepted;
   // private String suitor_name;


    public Date() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Date(User from, User to){
        this.from = from;
        this.to = to;
    }

    public void setLocation(String location){
        this.location = location;
    }

    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeParcelable(from, flags);
        out.writeParcelable(to, flags);
        out.writeString(location);
    }
    public static final Parcelable.Creator<Date> CREATOR = new Parcelable.Creator<Date>() {
        public Date createFromParcel(Parcel in) {
            return new Date(in);
        }

        public Date[] newArray(int size) {
            return new Date[size];
        }
    };
    public User getUserFrom(){
        return from;
    }
    public User getUserTo(){
        return to;
    }
    public String getLocation(){
        return location;
    }
    // to date from string; this method convert a string from datasnapshot into a date
    // class
    // DataSnapshot {
    //      key = 7f3tkfj14Jb9z11FZG0rBmuJCo12,
    //      value = {
    //          location=mt. pleasant,
    //          Suitor's Uid=7f3tkfj14Jb9z11FZG0rBmuJCo12,
    //          Accepted=false,
    //          Suitor's Name=Chris Dean
    //      }
    // }
    // parse above into a date
    public Date toDate(String date){
       // date.
        return new Date();
    }

    private void readFromParcel(Parcel in) {
        final ClassLoader cl = getClass().getClassLoader();

        from = in.readParcelable(cl);
        to = in.readParcelable(cl);
        location = in.readString();

    }
    private Date(Parcel in) {
        readFromParcel(in);
    //    Date date = Date.class.cast(in.readValue(Date.class.getClassLoader()));
    /*    this.From = in.readValue(User.getClassLoader());
        this.To = in.readValue();
        this.location = in.readString();*/
    }
}
