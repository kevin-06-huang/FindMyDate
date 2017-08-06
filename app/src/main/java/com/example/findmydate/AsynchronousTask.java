package com.example.findmydate;

import android.os.AsyncTask;
import javax.xml.transform.Result;

import android.net.*;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by kevincrazykid on 31/07/17.
 */

public class AsynchronousTask extends AsyncTask<User, Void, Boolean>{


    @Override
    protected Boolean doInBackground(User... params) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
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

  /*  protected void onProgressUpdate(Integer... progress) {
        setProgressPercent(progress[0]);
    }

    protected void onPostExecute(Long result) {
        showDialog("Downloaded " + result + " bytes");
    }

*/
}
