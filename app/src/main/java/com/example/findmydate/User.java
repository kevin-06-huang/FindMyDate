package com.example.findmydate;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.auth.FirebaseUser;

public class User implements Parcelable{


    private String email;
    private String name;
    private String profileURL;
    private String uid;



    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String email, String name, String profileURL, String uid) {
        this.email = email;
        this.name = name;
        this.profileURL = profileURL;
        this.uid = uid;
    }
    public User(FirebaseUser firebaseUser) {
        this.email = firebaseUser.getEmail();
        this.name = firebaseUser.getDisplayName();
        this.profileURL = firebaseUser.getPhotoUrl().toString();
        this.uid = firebaseUser.getUid();
    }

    public String getEmail(){
        return email;
    }
    public String getName(){
        return name;
    }
    public String getProfileURL(){
        return profileURL;
    }
    public String getUid(){
        return uid;
    }
    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(email);
        out.writeString(name);
        out.writeString(profileURL);
        out.writeString(uid);
    }
    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };
    private void readFromParcel(Parcel in) {

        email = in.readString();
        name = in.readString();
        profileURL = in.readString();
        uid = in.readString();

    }
    private User(Parcel in) {
        readFromParcel(in);
    }

}
