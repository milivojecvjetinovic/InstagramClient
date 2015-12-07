package com.example.mcvjetinovic.instagramclient.model;

import android.provider.Settings;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by mcvjetinovic on 12/1/15.
 */
public class InstagramModel {

    private String username;
    private String imageUrl;
    private long timestamp;
    private String caption;
    private int imageWidth;
    private int imageHeight;
    private int likesCount;
    private int commentCount;
    private String dateSince;
    private String profilePicture;
    private ArrayList<String> commentList;


    public InstagramModel(JSONObject post) {
        try {
            JSONObject imageResponse = post.getJSONObject("images").getJSONObject("standard_resolution");

            this.imageUrl = imageResponse.getString("url");

            this.username = post.getJSONObject("user").getString("username");
            this.profilePicture = post.getJSONObject("user").getString("profile_picture");


            this.imageHeight = imageResponse.getInt("height");
            this.imageWidth = imageResponse.getInt("width");


            if( post.getJSONObject("likes") != null) {
                JSONObject likes = post.getJSONObject("likes");
                this.likesCount = likes.getInt("count");
            }

            if(post.getJSONObject("comments") != null){
                JSONObject comments = post.getJSONObject("comments");
                this.commentCount = comments.getInt("count");
                JSONArray dataList = comments.getJSONArray("data");

                this.commentList = new ArrayList<>();
                for (int i = 0; i < dataList.length(); i++) {
                    JSONObject commentData = (JSONObject) dataList.get(i);
                    String commentText = commentData.getString("text");
                    this.commentList.add(commentText);
                    Log.i("Comments:", commentText );
                }
            }

            if(post.getJSONObject("caption") != null) {
                JSONObject caption = post.getJSONObject("caption");
                this.caption = caption.getString("text");
                this.timestamp = caption.getLong("created_time") * 1000;
                CharSequence relativeTimeSpanString = DateUtils.getRelativeTimeSpanString(this.timestamp, System.currentTimeMillis(),DateUtils.HOUR_IN_MILLIS,DateUtils.FORMAT_ABBREV_ALL);
                this.dateSince = relativeTimeSpanString.toString();
                Log.d("INSTAGRAM_MODEL", relativeTimeSpanString.toString());
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getDateSince() {
        return dateSince;
    }

    public void setDateSince(String dateSince) {
        this.dateSince = dateSince;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(int imageWidth) {
        this.imageWidth = imageWidth;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(int imageHeight) {
        this.imageHeight = imageHeight;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public ArrayList<String> getCommentList() {
        return commentList;
    }

    public void setCommentList(ArrayList<String> commentList) {
        this.commentList = commentList;
    }

    @Override
    public String toString() {
        return "InstagramModel{" +
                "username='" + username + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", timestamp=" + timestamp +
                ", caption='" + caption + '\'' +
                ", imageWidth=" + imageWidth +
                ", imageHeight=" + imageHeight +
                ", likesCount=" + likesCount +
                ", commentCount=" + commentCount +
                ", dateSince='" + dateSince + '\'' +
                ", profilePicture='" + profilePicture + '\'' +
                '}';
    }


}
