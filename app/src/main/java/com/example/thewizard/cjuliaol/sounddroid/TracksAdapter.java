package com.example.thewizard.cjuliaol.sounddroid;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thewizard.cjuliaol.sounddroid.com.example.thewizard.cjuliaol.sounddroid.soundcloud.Track;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by cjuliaol on 07/07/2015.
 */
public class TracksAdapter extends RecyclerView.Adapter<TracksAdapter.ViewHolder> {

    // To implement OnItemClickListener implement this
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView titleTextView;
        private ImageView thumbImageView;

        ViewHolder(View v) {
            super(v);
            titleTextView = (TextView) v.findViewById(R.id.track_title);
            thumbImageView = (ImageView) v.findViewById(R.id.track_thumbnail);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(null,v,getAdapterPosition(),0);
            }
        }
    }

    private List<Track> mTracks;
    private Context mContext;

    //  While RecyclerView is more flexible and performant than ListView, we do lose some convenience.
    // An example of this is the OnItemClickListener which does not come with RecyclerView. In this lesson we implemented our own.
    private AdapterView.OnItemClickListener mOnItemClickListener;


    TracksAdapter(Context context,List<Track> tracks) {
        mContext = context;
        mTracks = tracks;
    }
    // Setter for the OnItemClickListener
    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

        Track track = mTracks.get(i);
        viewHolder.titleTextView.setText(track.getTitle());
        Picasso.with(mContext).load(mTracks.get(i).getAvatarURL() ).into(viewHolder.thumbImageView);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        // To inflate the layout for tracks
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.track_row, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public int getItemCount() {
        return mTracks.size();
    }
}
