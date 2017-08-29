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



    public Date(User from, User to){
        this.from = from;
        this.to = to;
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
