package com.example.findmydate;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.util.Log;

import com.google.firebase.auth.FirebaseUser;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

/*
    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }
    */

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        MainActivity activity = (MainActivity) getActivity();
        Log.d("test_fragment", Boolean.toString(activity == null));

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }


}
