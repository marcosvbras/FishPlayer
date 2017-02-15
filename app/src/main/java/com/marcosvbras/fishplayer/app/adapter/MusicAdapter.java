package com.marcosvbras.fishplayer.app.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.marcosvbras.fishplayer.R;
import com.marcosvbras.fishplayer.app.domain.Music;
import com.marcosvbras.fishplayer.app.util.ImageHelper;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by marcosvbras on 07/02/17.
 */

public class MusicAdapter extends RecyclerView.Adapter {

    private Activity activity;
    private List<Music> listMusic;
    private HashMap<Integer, Bitmap> hashMapImage;

    public MusicAdapter(Activity activity, List<Music> listMusic) {
        this.activity = activity;
        this.listMusic = listMusic;
        hashMapImage = new HashMap<>();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_music, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        final Music music = listMusic.get(position);
        final MyViewHolder myViewHolder = (MyViewHolder)viewHolder;

        if(music.getAlbumArtPath() != null) {
            if(hashMapImage.containsKey(position)) {
                myViewHolder.imageViewCover.setImageBitmap(hashMapImage.get(position));
            } else {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final Bitmap bitmap = ImageHelper.resizeBitmap(
                                BitmapFactory.decodeByteArray(music.getFilePicture(), 0, music.getFilePicture().length),
                                140);
                        hashMapImage.put(position, bitmap);
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                myViewHolder.imageViewCover.setImageBitmap(bitmap);
                            }
                        });
                    }
                }).start();
            }
        }

        // Title
        if(music.getTitle() != null && !music.getTitle().equals("") && !music.getTitle().equals("0"))
            myViewHolder.textViewTitle.setText(music.getTitle());
        else
            myViewHolder.textViewTitle.setText(activity.getString(R.string.unknown_title));

        // Artist - Album
        String text = null;

        if(music.getArtist() != null && !music.getArtist().equals("") && !music.getArtist().equals("0"))
            text = music.getArtist();
        else
            text = activity.getString(R.string.unknown_artist);

        if(music.getAlbum() != null && !music.getArtist().equals("") && !music.getAlbum().equals("0"))
            text += " - " + music.getAlbum();
        else
            text += " - " + activity.getString(R.string.unknown_album);

        myViewHolder.textViewArtist.setText(text);

        // Music duration
        myViewHolder.textViewDuration.setText(
                String.format("%d:%02d", TimeUnit.MILLISECONDS.toMinutes(music.getDuration()),
                        TimeUnit.MILLISECONDS.toSeconds(music.getDuration()) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(music.getDuration()))
                ));
    }

    @Override
    public int getItemCount() {
        return listMusic.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public Music getMusicAt(int index) {
        return listMusic.get(index);
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle;
        TextView textViewArtist;
        TextView textViewDuration;
        ImageView imageViewCover;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageViewCover = (ImageView)itemView.findViewById(R.id.image_view_cover);
            textViewTitle = (TextView)itemView.findViewById(R.id.text_view_title);
            textViewArtist = (TextView)itemView.findViewById(R.id.text_view_artist);
            textViewDuration = (TextView)itemView.findViewById(R.id.text_view_duration);
        }
    }
}
