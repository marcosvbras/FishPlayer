package com.marcosvbras.fishplayer.app.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.marcosvbras.fishplayer.R;
import com.marcosvbras.fishplayer.app.domain.Music;
import com.marcosvbras.fishplayer.app.domain.SimpleMusic;
import com.marcosvbras.fishplayer.app.util.Animations;
import com.marcosvbras.fishplayer.app.util.ImageHelper;
import com.marcosvbras.fishplayer.app.util.MusicHelper;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by marcosvbras on 07/02/17.
 */

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.MyViewHolder> implements Filterable {

    private Activity activity;
    private List<SimpleMusic> listSimpleMusic;
    private HashMap<Integer, Bitmap> hashMapImage;
    private int lastLoadedPosition;

    public MusicAdapter(Activity activity, List<SimpleMusic> listSimpleMusic) {
        this.activity = activity;
        this.listSimpleMusic = listSimpleMusic;
        hashMapImage = new HashMap<>();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_music, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder myViewHolder, final int position) {
        final SimpleMusic simpleMusic = listSimpleMusic.get(position);

        if(hashMapImage.containsKey(position))
            myViewHolder.imageViewCover.setImageBitmap(hashMapImage.get(position));
        else
            loadImage(myViewHolder.imageViewCover, simpleMusic.getMusicPath(), position);

        // Title
        if(simpleMusic.getTitle() != null && !simpleMusic.getTitle().equals("") && !simpleMusic.getTitle().equals("0"))
            myViewHolder.textViewTitle.setText(simpleMusic.getTitle());
        else
            myViewHolder.textViewTitle.setText(activity.getString(R.string.unknown_title));

        // Artist - Album
        String text = null;

        if(simpleMusic.getArtist() != null && !simpleMusic.getArtist().equals("") && !simpleMusic.getArtist().equals("0"))
            text = simpleMusic.getArtist();
        else
            text = activity.getString(R.string.unknown_artist);

        if(simpleMusic.getAlbum() != null && !simpleMusic.getArtist().equals("") && !simpleMusic.getAlbum().equals("0"))
            text += " - " + simpleMusic.getAlbum();
        else
            text += " - " + activity.getString(R.string.unknown_album);

        myViewHolder.textViewArtist.setText(text);

        // Music duration
        myViewHolder.textViewDuration.setText(
                String.format("%d:%02d", TimeUnit.MILLISECONDS.toMinutes(simpleMusic.getDuration()),
                        TimeUnit.MILLISECONDS.toSeconds(simpleMusic.getDuration()) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(simpleMusic.getDuration()))
                ));

        if(position > lastLoadedPosition) {
            Animations.setFadeInAnimation(myViewHolder.container, activity, 1200);
            lastLoadedPosition = position;
        }
    }

    private void loadImage(final ImageView imageView, final String musicPath, final int position) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final byte[] data = MusicHelper.getSpecificFilePicture(musicPath);

                if(data != null && data.length > 0) {
                    final Bitmap bitmap = ImageHelper.resizeBitmap(
                            BitmapFactory.decodeByteArray(data, 0, data.length),
                            140);

                    if(bitmap != null) {
                        hashMapImage.put(position, bitmap);

                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                imageView.setImageBitmap(bitmap);
                            }
                        });
                    }
                }
            }
        }).start();
    }

    @Override
    public void onViewDetachedFromWindow(MyViewHolder myViewHolder) {
        super.onViewDetachedFromWindow(myViewHolder);
        myViewHolder.container.clearAnimation();
    }

    @Override
    public int getItemCount() {
        return listSimpleMusic.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public SimpleMusic getMusicAt(int index) {
        return listSimpleMusic.get(index);
    }

    @Override
    public Filter getFilter() {


        return null;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle;
        TextView textViewArtist;
        TextView textViewDuration;
        ImageView imageViewCover;
        RelativeLayout container;

        public MyViewHolder(View itemView) {
            super(itemView);
            container = (RelativeLayout)itemView.findViewById(R.id.container);
            imageViewCover = (ImageView)itemView.findViewById(R.id.image_view_cover);
            textViewTitle = (TextView)itemView.findViewById(R.id.text_view_title);
            textViewArtist = (TextView)itemView.findViewById(R.id.text_view_artist);
            textViewDuration = (TextView)itemView.findViewById(R.id.text_view_duration);
        }
    }
}
