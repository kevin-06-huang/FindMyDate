package com.example.findmydate;

import android.net.Uri;
import android.os.AsyncTask;
import android.renderscript.Sampler;
import android.support.annotation.NonNull;
import android.support.v4.view.AsyncLayoutInflater;
import android.util.Log;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.ArrayList;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import java.io.InputStream;
import java.net.URL;
import com.google.firebase.storage.UploadTask;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnFailureListener;

/**
 * Created by kevincrazykid on 27/07/17.
 */

public class DatabaseHelper {

    private static FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private static DatabaseReference databaseReference = firebaseDatabase.getReference();

    private static FirebaseStorage storage = FirebaseStorage.getInstance();
    public static void register(User user){
        DatabaseReference mDatabaseReference = databaseReference.child("users").child(user.getUid());

        mDatabaseReference.child("email").setValue(user.getEmail());
        mDatabaseReference.child("name").setValue(user.getName());
        mDatabaseReference.child("profileURL").setValue(user.getProfileURL());
        mDatabaseReference.child("uid").setValue(user.getUid());
        new AsynchronousUserUploadTask().execute(user);

    }
    public static void sendDate(Date date){
        final Date myDate = date;
        final User userTo = date.getUserTo();
        final User userFrom = date.getUserFrom();
        final DatabaseReference mDatabaseReference =
                databaseReference.child("dates").child(userTo.getUid())
                                                .child(userFrom.getUid());

        mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mDatabaseReference.child("Accepted").setValue(false);
                mDatabaseReference.child("Suitor's Name").setValue(userFrom.getName());
                mDatabaseReference.child("Suitor's Uid").setValue(userFrom.getUid());
                mDatabaseReference.child("location").setValue(myDate.getLocation());

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
    private static class AsynchronousUserUploadTask extends AsyncTask<User, Void, Boolean> {


        @Override
        protected Boolean doInBackground(User... params) {
            StorageReference mStorageReference = storage.getReferenceFromUrl("gs://findmydate-1c6f4.appspot.com/");
            StorageReference mStorageReferenceImages = mStorageReference.child(params[0].getUid() + "/profile_pic.jpg");

            //  InputStream stream = new FileInputStream(new File("path/to/images/rivers.jpg"));
            InputStream input = null;

            try{
                input = new URL(params[0].getProfileURL()).openStream();

            }catch(MalformedURLException e){

            }catch(IOException e){

            }

            UploadTask uploadTask = mStorageReferenceImages.putStream(input);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                    //  Uri downloadUrl = taskSnapshot.getDownloadUrl();
                }
            });
            return false;
        }

    }
    /*
    private static class AsynchronousDateUploadTask extends AsyncTask<Date, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Date... params){
            StorageReference mStorageReference = storage.getReferenceFromUrl("gs://findmydate-1c6f4.appspot.com/");
            StorageReference mStorageReferenceImages = mStorageReference.child(params[0].getUserFrom() + "/profile_pic.jpg");

            //  InputStream stream = new FileInputStream(new File("path/to/images/rivers.jpg"));
            InputStream input = null;

            try{
                input = new URL(params[0].getProfileURL()).openStream();

            }catch(MalformedURLException e){

            }catch(IOException e){

            }

            UploadTask uploadTask = mStorageReferenceImages.putStream(input);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                    //  Uri downloadUrl = taskSnapshot.getDownloadUrl();
                }
            });
            return false;
        }
    }
*/
}
