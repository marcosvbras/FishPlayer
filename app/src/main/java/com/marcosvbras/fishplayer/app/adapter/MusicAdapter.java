package com.marcosvbras.fishplayer.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.marcosvbras.fishplayer.R;
import com.marcosvbras.fishplayer.app.domain.Music;

import java.util.List;

/**
 * Created by marcosvbras on 07/02/17.
 */

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.MyViewHolder> {

    private Context context;
    private List<Music> listMusic;

    public MusicAdapter(Context context, List<Music> listMusic) {
        this.context = context;
        this.listMusic = listMusic;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_music, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int position) {
        Music music = listMusic.get(position);
        myViewHolder.textViewTitle.setText(music.getTitle());
        myViewHolder.textViewArtist.setText(music.getArtist());
    }

    @Override
    public int getItemCount() {
        return listMusic.size();
    }

    public Music getMusicAt(int index) {
        return listMusic.get(index);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle;
        TextView textViewArtist;

        public MyViewHolder(View itemView) {
            super(itemView);
            textViewTitle = (TextView)itemView.findViewById(R.id.text_view_title);
            textViewArtist = (TextView)itemView.findViewById(R.id.text_view_artist);
        }
    }
}
