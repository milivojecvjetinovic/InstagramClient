package com.example.mcvjetinovic.instagramclient.model;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mcvjetinovic.instagramclient.R;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;

/**
 * Created by mcvjetinovic on 12/2/15.
 */
public class InstagramViewAdapter extends ArrayAdapter<InstagramModel> {

    private static class ViewHolder {
        TextView txtUser;
        TextView title;
        ImageView image;
        TextView likesCount;
        TextView dateCreated;
        ImageView profilePicture;
        TextView comments;
        TextView commentCount;
    }

    public InstagramViewAdapter(Context context, ArrayList<InstagramModel> instagramList) {
        super(context, 0, instagramList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        InstagramModel  model = getItem(position);

        ViewHolder viewHolder;
        //check if view exists
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.instagram, parent, false);
            viewHolder = new ViewHolder();
            TextView txtUser = (TextView) convertView.findViewById(R.id.username);
            TextView title = (TextView) convertView.findViewById(R.id.title);
            ImageView image = (ImageView) convertView.findViewById(R.id.postImage);
            TextView likesCount = (TextView) convertView.findViewById(R.id.likesCount);
            TextView dateCreated = (TextView) convertView.findViewById(R.id.dateCreated);
            ImageView profilePicture = (ImageView) convertView.findViewById(R.id.profilePicture);
            TextView comments = (TextView) convertView.findViewById(R.id.comment);
            TextView commentCount = (TextView) convertView.findViewById(R.id.commentCount);

            viewHolder.txtUser = txtUser;
            viewHolder.title = title;
            viewHolder.image = image;
            viewHolder.likesCount = likesCount;
            viewHolder.dateCreated = dateCreated;
            viewHolder.profilePicture = profilePicture;
            viewHolder.comments = comments;
            viewHolder.commentCount = commentCount;
            convertView.setTag(viewHolder);


        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.txtUser.setText(model.getUsername());
        viewHolder.title.setText(model.getCaption());
        viewHolder.likesCount.setText(String.valueOf(model.getLikesCount()));
        viewHolder.dateCreated.setText(model.getDateSince());
        viewHolder.commentCount.setText(String.valueOf(model.getCommentCount()));
        Picasso.with(convertView.getContext()).load(model.getImageUrl()).into(viewHolder.image);

        Transformation transformation = new RoundedTransformationBuilder()
                .borderColor(Color.BLACK)
                .borderWidthDp(3)
                .cornerRadiusDp(30)
                .oval(true)
                .build();

        Picasso.with(convertView.getContext())
                .load(model.getProfilePicture())
                .fit()
                .transform(transformation)
                .into(viewHolder.profilePicture);
        if(model.getCommentCount()> 0) {
            viewHolder.comments.setText(model.getCommentList().get(0));
        }

        return convertView;
    }
}
