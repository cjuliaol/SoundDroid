package com.example.thewizard.cjuliaol.sounddroid;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thewizard.cjuliaol.sounddroid.com.example.thewizard.cjuliaol.sounddroid.soundcloud.SoundCloud;
import com.example.thewizard.cjuliaol.sounddroid.com.example.thewizard.cjuliaol.sounddroid.soundcloud.SoundCloudService;
import com.example.thewizard.cjuliaol.sounddroid.com.example.thewizard.cjuliaol.sounddroid.soundcloud.Track;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private TracksAdapter mAdapter;
    private List<Track> mTracks;
    private Toolbar mToolbar;
    private TextView mSelectedTitle;
    private ImageView mSelectedThumbnail;
    private MediaPlayer mMediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
            }
        });

        mToolbar = (Toolbar) findViewById(R.id.player_toolbar);
        mSelectedTitle = (TextView) findViewById(R.id.selected_title);
        mSelectedThumbnail = (ImageView) findViewById(R.id.selected_thumbnail);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.songs_list);

        // You have to set up the LayoutManager
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mTracks = new ArrayList<Track>();
        mAdapter = new TracksAdapter(this, mTracks);
        mAdapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Track selectedTrack = mTracks.get(position);
                mSelectedTitle.setText(selectedTrack.getTitle());
                Picasso.with(MainActivity.this).load(selectedTrack.getAvatarURL()).into(mSelectedThumbnail);
                try {
                    mMediaPlayer.setDataSource(selectedTrack.getStreamURL()+"?client_id="+SoundCloudService.CLIENT_ID);
                    mMediaPlayer.prepareAsync();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        recyclerView.setAdapter(mAdapter);

        SoundCloudService service = SoundCloud.getService();
        service.searchSongs("aerosmith", new Callback<List<Track>>() {
            @Override
            public void success(List<Track> tracks, Response response) {

                for (int i = 0; i < tracks.size(); i++) {
                    mTracks.clear();
                    mTracks.addAll(tracks);
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });


        //Log.d(TAG,"Title is "+ track.getTitle());
        //Log.d(TAG,"Stream URL is "+ track.getStreamURL());
        //Log.d(TAG,"ID is "+ track.getID());
    }

    private String trackJSON() {
        return "{\"kind\":\"track\",\"id\":13158665,\"created_at\":\"2011/04/06 15:37:43 +0000\",\"user_id\":3699101,\"duration\":18109,\"commentable\":true,\"state\":\"finished\",\"original_content_size\":201483,\"last_modified\":\"2013/02/18 19:18:11 +0000\",\"sharing\":\"public\",\"tag_list\":\"soundcloud:source=iphone-record\",\"permalink\":\"munching-at-tiannas-house\",\"streamable\":true,\"embeddable_by\":\"all\",\"downloadable\":false,\"purchase_url\":null,\"label_id\":null,\"purchase_title\":null,\"genre\":null,\"title\":\"Munching at Tiannas house\",\"description\":null,\"label_name\":null,\"release\":null,\"track_type\":\"recording\",\"key_signature\":null,\"isrc\":null,\"video_url\":null,\"bpm\":null,\"release_year\":null,\"release_month\":null,\"release_day\":null,\"original_format\":\"m4a\",\"license\":\"all-rights-reserved\",\"uri\":\"https://api.soundcloud.com/tracks/13158665\",\"user\":{\"id\":3699101,\"kind\":\"user\",\"permalink\":\"alex-stevenson\",\"username\":\"Alex Stevenson\",\"last_modified\":\"2011/06/13 23:58:44 +0000\",\"uri\":\"https://api.soundcloud.com/users/3699101\",\"permalink_url\":\"http://soundcloud.com/alex-stevenson\",\"avatar_url\":\"https://i1.sndcdn.com/avatars-000004193858-jnf2pd-large.jpg\"},\"created_with\":{\"id\":124,\"kind\":\"app\",\"name\":\"SoundCloud iOS\",\"uri\":\"https://api.soundcloud.com/apps/124\",\"permalink_url\":\"http://soundcloud.com/apps/iphone\",\"external_url\":\"http://itunes.com/app/soundcloud\"},\"permalink_url\":\"http://soundcloud.com/alex-stevenson/munching-at-tiannas-house\",\"artwork_url\":null,\"waveform_url\":\"https://w1.sndcdn.com/fxguEjG4ax6B_m.png\",\"stream_url\":\"https://api.soundcloud.com/tracks/13158665/stream\",\"playback_count\":6085,\"download_count\":134,\"favoritings_count\":2,\"comment_count\":3,\"attachments_uri\":\"https://api.soundcloud.com/tracks/13158665/attachments\",\"policy\":\"ALLOW\",\"monetization_model\":\"NOT_APPLICABLE\"}";
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
