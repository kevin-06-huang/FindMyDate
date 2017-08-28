package com.example.findmydate;

import android.util.Log;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.os.Bundle;

public class DateActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date);
        TextView uIDFrom = (TextView) findViewById(R.id.uid_from);
        TextView uIDTo = (TextView) findViewById(R.id.uid_to);
        Date date = getIntent().getParcelableExtra("date");
        uIDFrom.setText("From: " + date.getUidFrom());
        uIDTo.setText("To: " + date.getUidTo());
       // Log.d("date_activity", Boolean.toString(date==null));
     //   Log.d("date_activity", date.getUidFrom());
       // Log.d("date_activity", date.getUidTo());
     /*   Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }
}