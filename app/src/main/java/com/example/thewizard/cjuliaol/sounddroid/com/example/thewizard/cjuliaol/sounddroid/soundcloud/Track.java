package com.example.thewizard.cjuliaol.sounddroid.com.example.thewizard.cjuliaol.sounddroid.soundcloud;

import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by cjuliaol on 05/07/2015.
 */
public class Track {

    @SerializedName("title")
    private String mTitle;

    @SerializedName("stream_url")
    private String mStreamURL;

    @SerializedName("id")
    private int mID;

    @SerializedName("artwork_url")
    private String mArtworkURL;

    public String getArtworkURL() {
        return mArtworkURL;
    }

    public String getAvatarURL() {
        String avatarURL = mArtworkURL;
        if (avatarURL != null) {
           avatarURL = mArtworkURL.replace("large","tiny");
        }
        return  avatarURL;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getStreamURL() {
        return mStreamURL;
    }

    public void setStreamURL(String streamURL) {
        mStreamURL = streamURL;
    }

    public int getID() {
        return mID;
    }

    public void setID(int ID) {
        mID = ID;
    }

    // Old way using JSONObject
    private static Track parse(JSONObject jsonObject) {
        Track track = new Track();

        try {
            track.setTitle(jsonObject.getString("title"));

        } catch (JSONException ex
                ) {
        }
        return track;
    }
}
