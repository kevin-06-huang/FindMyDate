package com.example.findmydate;

import android.app.Fragment;
import android.os.Bundle;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import android.util.Log;

/**
 * Created by kevincrazykid on 11/10/17.
 */

public class DateFragment extends Fragment {
    private static User currentUser;
    private static FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private static DatabaseReference databaseReference = firebaseDatabase.getReference();
    private MainActivity activity;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        activity = (MainActivity)getActivity();
        currentUser = activity.getUser();
        Log.d("datefragment", currentUser.getUid());

        databaseReference.child("dates")
              //  .child(currentUser.getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot){
                        for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                         //   Date date = snapshot.getValue(Date.class);
                            Log.d("date", snapshot.toString());
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

    }

}
