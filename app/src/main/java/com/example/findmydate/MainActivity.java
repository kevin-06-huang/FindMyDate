package com.example.findmydate;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.TextView;
import android.widget.ImageView;
import android.content.Intent;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
public class MainActivity extends Activity {

    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private final String TAG = "MyActivity";
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authListener;
    private TextView mStatusTextView;
    private TextView mDetailTextView;
    private AccessTokenTracker accessTokenTracker;
   // private FirebaseDatabase firebaseDatabase;
   // private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(getApplicationContext());
        auth = FirebaseAuth.getInstance();
        final ImageView imgView = (ImageView)findViewById(R.id.launch_image);

        final FragmentManager fragmentManager = getFragmentManager();


        //  firebaseDatabase = FirebaseDatabase.getInstance();
      //  databaseReference = firebaseDatabase.getReference();
        authListener = new FirebaseAuth.AuthStateListener() {


            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user = firebaseAuth.getCurrentUser();
              //  Log.d(TAG, "onAuthStateChanged:event:" + user.getUid());
                if (user != null) {
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    imgView .setVisibility(View.GONE);

                    Fragment fragment = fragmentManager.findFragmentByTag("profile_fragment");
                    if (fragment == null) {
                        // if none were found, create it
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragment = new ProfileFragment();
                        fragmentTransaction.add(R.id.profile_fragment, fragment);
                        fragmentTransaction.commit();
                    }

                } else {
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                    imgView .setVisibility(View.VISIBLE);
                }


            }
        };

     //   mStatusTextView = (TextView) findViewById(R.id.status);
     //   mDetailTextView = (TextView) findViewById(R.id.detail);

        //  FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("email", "public_profile");


        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                FirebaseUser currentUser = auth.getCurrentUser();
                updateUI(currentUser);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException e) {
            }
        });

        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken,
                                                       AccessToken currentAccessToken) {
                if (currentAccessToken == null) {
                    Log.d("logout", "logged out");
                    FirebaseAuth.getInstance().signOut();
                }
            }
        };
    }



    @Override
    protected void onStart() {
        super.onStart();
      ///  FirebaseUser currentUser = auth.getCurrentUser();
       // if(currentUser != null)
        //   updateUI(currentUser);
        auth.addAuthStateListener(authListener);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = auth.getCurrentUser();
                            updateUI(user);
                        } else {
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                        }

                    }
                });
    }
    @Override
    public void onDestroy() {
      //  auth.getInstance().signOut();
        super.onDestroy();
    }
    public FirebaseUser getUser(){
        return auth.getCurrentUser();
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
          //  mStatusTextView.setText(user.getDisplayName());
          //  mDetailTextView.setText(user.getEmail());

        } else {
           // mStatusTextView.setText("Nobody");
           // mDetailTextView.setText("No Email");

        }
    }
}
