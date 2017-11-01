package com.example.findmydate;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ImageView;
import android.content.Intent;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;

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

import android.net.Uri;

import com.google.firebase.storage.FirebaseStorage;

import android.content.SharedPreferences;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.StorageReference;


public class MainActivity extends AppCompatActivity {

    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private final String TAG = "MyActivity";
    private static final String PREF = "MyPrefs";
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authListener;
    private AccessTokenTracker accessTokenTracker;
    private static View main;
    private FragmentManager fragmentManager;

    private static boolean PROFILE_FLAG = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final SharedPreferences pref = getSharedPreferences(PREF, AppCompatActivity.MODE_PRIVATE);
        setContentView(R.layout.activity_main);
        main = findViewById(android.R.id.content);

        FirebaseApp.initializeApp(getApplicationContext());
        auth = FirebaseAuth.getInstance();

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        final ImageView imgView = (ImageView) findViewById(R.id.launch_image);

        fragmentManager = getFragmentManager();

        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("email", "public_profile");
        //  loginButton.setFragment(this);

        authListener = new FirebaseAuth.AuthStateListener() {


            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

                if (firebaseUser != null) {
                    if (!pref.contains("FIRST_LAUNCH")) {
                        DatabaseHelper.register(new User(firebaseUser));
                        pref.edit().putBoolean("FIRST_LAUNCH", true).commit();
                    }

                    Log.d(TAG, "onAuthStateChanged:signed_in:" + firebaseUser.getUid());
                    imgView.setVisibility(View.GONE);
                    Fragment fragment = fragmentManager.findFragmentByTag("profile_fragment");
                    if (fragment == null && PROFILE_FLAG == false) {
                        // if none were found, create it
                        Log.d("fragment firing", "1");
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragment = new ProfileFragment();

                        fragmentTransaction.replace(R.id.fragment_container, fragment, "profile_fragment");
                        fragmentTransaction.commit();
                        //   loginButton.setFragment(fragment);
                        PROFILE_FLAG = true;
                    }
                } else {
                    Log.d(TAG, "onAuthStateChanged:signed_out");

                    Fragment fragment = fragmentManager.findFragmentByTag("profile_fragment");
                    if (fragment != null && PROFILE_FLAG == true) {
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.remove(fragment);
                        fragmentTransaction.commit();
                        PROFILE_FLAG = false;

                        imgView.setVisibility(View.VISIBLE);
                    }


                }
            }
        };
        auth.addAuthStateListener(authListener);


        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {

                Log.d(TAG, "facebook:onCancel:");
            }

            @Override
            public void onError(FacebookException e) {
            }
        });
        //   Button buttonOne = (Button) findViewById(R.id.button1);
        loginButton.setOnClickListener(new LoginButton.OnClickListener() {
            public void onClick(View v) {
                Log.d("button", "activity");
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Fragment fragment = getFragmentManager().findFragmentByTag("profile_fragment");
        //  fragment.onActivityResult(requestCode, resultCode, data);
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
                        } else {
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                        }

                    }
                });
    }
    // create an action bar button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // handle button activities
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.heart_button) {
            Log.d("heart", "adgsdg");
            Fragment fragment = new CardFragment();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.fragment_container, fragment, "card_fragment");
            transaction.commit();
        }
        else if(id == R.id.date_button){

            Log.d("date", "adsfsdafsd");
        }
        return super.onOptionsItemSelected(item);
    }

    public CallbackManager getCallbackManager() {
        return callbackManager;
    }

    public User getUser() {
        return new User(auth.getCurrentUser());
    }
    public static void dateSent(){
        Snackbar.make(main, "Date has been sent!", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }
    public static void dateCancelled(){
        Snackbar.make(main, "Date has been cancelled!", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }
}
