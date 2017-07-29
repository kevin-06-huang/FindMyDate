package com.example.findmydate;

import android.renderscript.Sampler;
import android.util.Log;

import java.util.List;
import java.util.ArrayList;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * Created by kevincrazykid on 27/07/17.
 */

public class DatabaseHelper {

    private static FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private static DatabaseReference databaseReference = firebaseDatabase.getReference();

    public static void register(User user){
        DatabaseReference mDatabaseReference = databaseReference.child("users").child(user.getUid());

        mDatabaseReference.child("email").setValue(user.getEmail());
        mDatabaseReference.child("name").setValue(user.getName());
        mDatabaseReference.child("profileURL").setValue(user.getProfileURL());
        mDatabaseReference.child("uid").setValue(user.getUid());

    }

}
