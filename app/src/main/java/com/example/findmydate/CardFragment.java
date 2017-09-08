package com.example.findmydate;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.mindorks.placeholderview.SwipePlaceHolderView;
import com.mindorks.placeholderview.SwipeDecor;
import android.content.Context;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;

/**
 * Created by kevincrazykid on 30/08/17.
 */

public class CardFragment extends Fragment {

    private SwipePlaceHolderView mSwipeView;
    private Context mContext;
    private MainActivity activity;
    private static FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private static DatabaseReference databaseReference = firebaseDatabase.getReference();
    private static User currentUser;
    private static ArrayList<User> visibleUsers;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (MainActivity) getActivity();
        currentUser = activity.getUser();
        // eventually visibleUsers will incorporate matching
        visibleUsers = new ArrayList<User>();
        databaseReference.child("users")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            User user = snapshot.getValue(User.class);
                            Log.d("dersh", user.getUid());
                            if(!user.getUid().equals(currentUser.getUid())){
                                visibleUsers.add(user);
                            }
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
        mContext = activity.getApplicationContext();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
    //  setContentView(R.layout.activity_main);
        View view = inflater.inflate(R.layout.fragment_card, container, false);
        mSwipeView = (SwipePlaceHolderView)view.findViewById(R.id.swipe_view);
     //   mContext = getApplicationContext();

        mSwipeView.getBuilder()
                .setDisplayViewCount(3)
                .setSwipeDecor(new SwipeDecor()
                        .setPaddingTop(20)
                        .setRelativeScale(0.01f)
                        .setSwipeInMsgLayoutId(R.layout.swipe_in_msg_view)
                        .setSwipeOutMsgLayoutId(R.layout.swipe_out_msg_view));
    for (User user : visibleUsers){

    }
/*
        for(Profile profile : Utils.loadProfiles(this.getApplicationContext())){
            mSwipeView.addView(new TinderCard(mContext, profile, mSwipeView));
        }

        findViewById(R.id.rejectBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSwipeView.doSwipe(false);
            }
        });

        findViewById(R.id.acceptBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSwipeView.doSwipe(true);
            }
        });*/
        return view;
    }
}
