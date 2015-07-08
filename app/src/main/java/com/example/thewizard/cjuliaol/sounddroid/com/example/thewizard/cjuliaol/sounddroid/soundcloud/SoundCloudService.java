package com.example.thewizard.cjuliaol.sounddroid.com.example.thewizard.cjuliaol.sounddroid.soundcloud;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by cjuliaol on 06/07/2015.
 */
public interface SoundCloudService {
    static final String CLIENT_ID="74d3382316c394c1ab108b9ee27f4091";

   @GET("/tracks?client_id="+CLIENT_ID)
   public void searchSongs(@Query("q") String query, Callback<List<Track>> callback);
}
