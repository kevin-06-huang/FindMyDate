package com.example.findmydate;

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
import android.widget.ListView;
import android.content.Intent;
import android.widget.AdapterView;

import android.widget.AdapterView.OnItemClickListener;


public class ProfileFragment extends Fragment {

    private static User currentUser;
    private static ArrayList<User> visibleUsers;
    private static FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private static DatabaseReference databaseReference = firebaseDatabase.getReference();
    private static ArrayAdapter<User> adapter;
    private MainActivity activity;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        activity = (MainActivity)getActivity();
        //access database and instantiate user
        currentUser = activity.getUser();
        visibleUsers = new ArrayList<User>();
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

     //   activity = (MainActivity)getActivity();
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ListView lv = (ListView)view.findViewById(R.id.profile_list);
        Log.d("listview", Integer.toString(visibleUsers.size()));
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int integer,
                                    long longNumber) {
               // Log.d("list", visibleUsers.get(integer).getName());
                Intent i= new Intent(getActivity(), DateActivity.class);
                Date date = new Date(currentUser, visibleUsers.get(integer));
                i.putExtra("date", date);
                startActivity(i);
              //  Log.d("list", visibleUsers.get(integer).getName());

            }

        });
        return view;
    }
    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
      //  activity = (MainActivity) getActivity();
        super.onActivityResult(requestCode, resultCode, data);
       // activity.getCallbackManager().onActivityResult(requestCode, resultCode, data);
    }

}
