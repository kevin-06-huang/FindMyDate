package com.example.findmydate;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.util.Log;

import com.google.firebase.database.ValueEventListener;

import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements DatabaseHelper.DatabaseChangeListener{

    private User currentUser;
    private User[] visibleUsers;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        MainActivity activity = (MainActivity)getActivity();
        //access database and instantiate user
        currentUser = activity.getUser();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        TextView mDetailTextView =  (TextView) view.findViewById(R.id.detail);
        Log.d("profile_fragment2", Boolean.toString(mDetailTextView == null));
        mDetailTextView.setText("shitasdffffffffffffffffff");
        return view;
    }

    @Override
    public void onDatabaseChange(String value) {

    }
}
