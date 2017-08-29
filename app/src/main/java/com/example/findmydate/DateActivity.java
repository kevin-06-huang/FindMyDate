package com.example.findmydate;

import android.util.Log;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.EditText;
import android.view.inputmethod.InputMethodManager;

import android.os.Bundle;

public class DateActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date);
        // instantiate all view
        TextView uIDFrom = (TextView) findViewById(R.id.uid_from);
        TextView uIDTo = (TextView) findViewById(R.id.uid_to);
        final EditText locationEdit  = (EditText)findViewById(R.id.location);
        FloatingActionButton send = (FloatingActionButton) findViewById(R.id.send_date);
        FloatingActionButton cancel = (FloatingActionButton) findViewById(R.id.cancel_date);
        // set the view value according to parcelled date
        Date date = getIntent().getExtras().getParcelable("date");
        uIDFrom.setText("From: " + date.getUserFrom().getName());
        uIDTo.setText("To: " + date.getUserTo().getName());
        //set listener on send button
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                InputMethodManager inputManager = (InputMethodManager)
                        getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);


             //   Log.d("EditText", locationEdit.getText().toString());
                String location = locationEdit.getText().toString();
                // check if location is vcalid
                if(location.length() != 0){
                    MainActivity.dateSent();
                    finish();
                }
                else{
                    Snackbar.make(view, "Input valid data!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });
        //set listener on cancel button
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                InputMethodManager inputManager = (InputMethodManager)
                        getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
                MainActivity.dateCancelled();
                finish();
            }
        });
       // Log.d("date_activity", Boolean.toString(date==null));
     //   Log.d("date_activity", date.getUidFrom());
       // Log.d("date_activity", date.getUidTo());
     /*   Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }
}