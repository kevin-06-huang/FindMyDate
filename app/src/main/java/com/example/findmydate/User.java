package com.example.findmydate;

import android.util.Log;

import com.facebook.AccessTokenTracker;
import com.google.firebase.database.*;

import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DataSnapshot;


//   mDatabase = FirebaseDatabase.getInstance().getReference();

@IgnoreExtraProperties
public class User {
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    // mDatabase = FirebaseDatabase.getInstance().getReference();
    // mDatabase = FirebaseDatabase.getInstance().getReference();


    private String email;
    private String name;
    private String profileURL;
    private String uid;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;


    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String email, String name, String profileURL, String uid) {
        this.email = email;
        this.name = name;
        this.profileURL = profileURL;
        this.uid = uid;
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
    public void writeNewUser(String email, String name, String profileURL, String uid) {
        User user = new User(email, name, profileURL, uid);

        mDatabase.child("users").child(uid).setValue(user);
    }
    public boolean register(){
        Log.d("ahagoeirgjworjowagaoiwh", "awieujhfiuegwbaui");
        //  databaseReference.child("users").child(user.getUid()).child("username").setValue(user.getDisplayName());
        //  Log.d("hey", databaseReference.child("users").child(user.getUid()).toString());
        databaseReference = firebaseDatabase.getReference("users");
        Log.d("hey", databaseReference.toString());
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // User Exists
                }
            };

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return false;
    }
    // check if user already exist in database
    public static boolean checkUser(String uid){
        return false;
    }
}
