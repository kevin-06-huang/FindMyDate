package com.example.findmydate;

import java.io.IOException;
import java.util.ArrayList;
import java.net.*;

import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.squareup.picasso.Picasso;

public class UsersAdapter extends ArrayAdapter<User> {
    public UsersAdapter(Context context, ArrayList<User> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        User user = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        // Lookup view for data population
        TextView name = (TextView) convertView.findViewById(R.id.name);
        // Populate the data into the template view using the data object

        ImageView picture = (ImageView) convertView.findViewById(R.id.profile_picture);
        name.setText(user.getName());
        picture.setTag(user.getProfileURL());
        Picasso.with(getContext()).load(user.getProfileURL()).into(picture);

        // Return the completed view to render on screen
        return convertView;
    }

    public class AsynchronousDownloadTask extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... params) {
            URL url = null;
            Bitmap bmp = null;
            try {
                url = new URL(params[0]);
            } catch (MalformedURLException e) {
            }
            try {
                bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            } catch (IOException e) {

            }
            return bmp;
        }
    }
}