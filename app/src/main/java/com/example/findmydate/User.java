package com.example.findmydate;


import com.google.firebase.auth.FirebaseUser;

public class User {


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

}
