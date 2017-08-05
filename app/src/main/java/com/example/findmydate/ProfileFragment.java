package com.example.findmydate;

import java.util.List;
import java.util.ArrayList;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DatabaseError;
import android.widget.Toast;
import android.os.StrictMode;


import android.widget.ListView;

import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;


public class ProfileFragment extends Fragment {

    private static User currentUser;
    private static ArrayList<User> visibleUsers = new ArrayList<User>();
    private static FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private static DatabaseReference databaseReference = firebaseDatabase.getReference();
    private static ArrayAdapter<User> adapter;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        MainActivity activity = (MainActivity)getActivity();
        //access database and instantiate user
        currentUser = activity.getUser();
        adapter = new UsersAdapter(getActivity().getApplicationContext(),
                visibleUsers);
        databaseReference.child("users")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            User user = snapshot.getValue(User.class);
                            Log.d("dersh", user.getUid());
                            if(!user.getUid().equals(currentUser.getUid())){

                                Log.d("dersh2", user.getUid());
                                visibleUsers.add(user);
                                adapter.notifyDataSetChanged();
                            }
                        }


                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ListView lv = (ListView)view.findViewById(R.id.profile_list);
        Log.d("listview", Integer.toString(visibleUsers.size()));
        lv.setAdapter(adapter);

        return view;
    }

}
