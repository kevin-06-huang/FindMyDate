package com.example.findmydate;

import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Context;
import com.bumptech.glide.Glide;
import android.util.Log;

import com.mindorks.placeholderview.SwipePlaceHolderView;
import com.mindorks.placeholderview.annotations.swipe.SwipeCancelState;
import com.mindorks.placeholderview.annotations.swipe.SwipeIn;
import com.mindorks.placeholderview.annotations.swipe.SwipeInState;
import com.mindorks.placeholderview.annotations.swipe.SwipeOut;
import com.mindorks.placeholderview.annotations.swipe.SwipeOutState;

/**
 * Created by kevincrazykid on 07/09/17.
 */
@Layout(R.layout.card_view)
public class Card {

    @View(R.id.userImageView)
    private ImageView userImageView;

    @View(R.id.nameTxt)
    private TextView nameTxt;
/*
    @View(R.id.locationNameTxt)
    private TextView locationNameTxt;
*/
    private User mUser;
    private Context mContext;
    private SwipePlaceHolderView mSwipeView;

    public Card(Context context, User user, SwipePlaceHolderView swipeView) {
        mContext = context;
        mUser = user;
        mSwipeView = swipeView;
    }

    @Resolve
    private void onResolved(){
        Glide.with(mContext).load(mUser.getProfileURL()).into(userImageView);
        nameTxt.setText(mUser.getName());
     //   locationNameTxt.setText(mUser.getLocation());
    }

    @SwipeOut
    private void onSwipedOut(){
        Log.d("EVENT", "onSwipedOut");
        mSwipeView.addView(this);
    }


    @SwipeCancelState
    private void onSwipeCancelState(){
        Log.d("EVENT", "onSwipeCancelState");
    }

    @SwipeIn
    private void onSwipeIn(){
        Log.d("EVENT", "onSwipedIn");
    }

    @SwipeInState
    private void onSwipeInState(){
        Log.d("EVENT", "onSwipeInState");
    }

    @SwipeOutState
    private void onSwipeOutState(){
        Log.d("EVENT", "onSwipeOutState");
    }
}