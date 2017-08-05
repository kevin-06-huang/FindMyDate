package com.example.findmydate;

import android.net.Uri;
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

    public static void register(User user){
        DatabaseReference mDatabaseReference = databaseReference.child("users").child(user.getUid());

        mDatabaseReference.child("email").setValue(user.getEmail());
        mDatabaseReference.child("name").setValue(user.getName());
        mDatabaseReference.child("profileURL").setValue(user.getProfileURL());
        mDatabaseReference.child("uid").setValue(user.getUid());
        new AsynchronousTask().execute(user);

    }
    private static void uploadFile(User user) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference mStorageReference = storage.getReferenceFromUrl("gs://findmydate-1c6f4.appspot.com/");
        StorageReference mStorageReferenceImages = mStorageReference.child(user.getUid() + "/profile.jpg");

      //  InputStream stream = new FileInputStream(new File("path/to/images/rivers.jpg"));
        InputStream input = null;
        try{
            input = new URL(user.getProfileURL()).openStream();

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
        /*
     StorageReference mStorageReferenceImages = mStorageReference.child("Images/profile.jpg");
        //showHorizontalProgressDialog("Uploading", "Please wait...");
        StorageReference uploadStorageReference = mStorageReference.child(uri.getLastPathSegment());
        //final UploadTask uploadTask = uploadStorageReference.putFile(pUri);
        final UploadTask uploadTask = mStorageReferenceImages.putFile(uri);
        Log.d("upload", "FacebookProfile:pUri: " + uri);

        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot pTaskSnapshot) {
                Log.d(TAG, "uploadFileonSuccess:" + pTaskSnapshot);
                Uri downloadUrl = pTaskSnapshot.getDownloadUrl();
                //hideProgressDialog();
                if (downloadUrl != null) {
                    imageUploaded = true;
                    //btnDownload.setEnabled(true);
                    //Glide.with(context).load(downloadUrl).into(imageView);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception pE) {
                Log.d(TAG, "uploadFileonFailure:" + pE);
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot pTaskSnapshot) {
                Log.d(TAG, "uploadFileonProgress:" + pTaskSnapshot);

                //int progress = (int) (100 * (float) pTaskSnapshot.getBytesTransferred() / pTaskSnapshot.getTotalByteCount());
                //updateProgress(progress);
            }
        });*/
    }

}
