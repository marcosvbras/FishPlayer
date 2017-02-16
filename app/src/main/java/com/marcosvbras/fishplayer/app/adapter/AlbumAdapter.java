package com.marcosvbras.fishplayer.app.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.marcosvbras.fishplayer.R;
import com.marcosvbras.fishplayer.app.domain.Album;
import com.marcosvbras.fishplayer.app.util.Animations;
import com.marcosvbras.fishplayer.app.util.ImageHelper;

import java.util.HashMap;
import java.util.List;

/**
 * Created by marcosvbras on 13/02/17.
 */

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.MyViewHolder> {

    private HashMap<Integer, Bitmap> hashMapImage;
    private List<Album> listAlbums;
    private Activity context;
    private int lastLoadedPosition;

    public AlbumAdapter(List<Album> listAlbums, Activity context) {
        this.listAlbums = listAlbums;
        this.context = context;
        hashMapImage = new HashMap<>();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_album, parent, false);
        view.setMinimumWidth(parent.getMeasuredWidth() / 2);
        view.setMinimumHeight(parent.getMeasuredWidth() / 2);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int position) {
        Album album = listAlbums.get(position);

        if(album.getAlbumArtPath() != null && !album.getAlbumArtPath().equals("")) {
            if(hashMapImage.containsKey(position))
                myViewHolder.imageViewArt.setImageBitmap(hashMapImage.get(position));
            else
                loadImage(myViewHolder.imageViewArt, position, album.getAlbumArtPath());
        }

        if(album.getName() != null && !album.getName().equals("") && !album.getName().equals("0"))
            myViewHolder.textViewAlbum.setText(album.getName());
        else
            myViewHolder.textViewAlbum.setText(context.getString(R.string.unknown_album));

        if(position > lastLoadedPosition) {
            Animations.setFadeInAnimation(myViewHolder.container, context, 1200);
            lastLoadedPosition = position;
        }
    }

    private void loadImage(final ImageView imageView, final int position, final String albumArtPath) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final Bitmap bitmap = ImageHelper.resizeBitmap(BitmapFactory.decodeFile(albumArtPath), 140);

                if(bitmap != null) {
                    hashMapImage.put(position, bitmap);
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            imageView.setImageBitmap(bitmap);
                        }
                    });
                }
            }
        }).start();
    }

    public Album getItemAt(int position) { return listAlbums.get(position); }

    @Override
    public void onViewDetachedFromWindow(MyViewHolder myViewHolder) {
        super.onViewDetachedFromWindow(myViewHolder);
        myViewHolder.container.clearAnimation();
    }

    @Override
    public int getItemCount() {
        return listAlbums.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        FrameLayout container;
        ImageView imageViewArt;
        TextView textViewAlbum;

        public MyViewHolder(View itemView) {
            super(itemView);
            container = (FrameLayout)itemView.findViewById(R.id.container);
            imageViewArt = (ImageView)itemView.findViewById(R.id.image_view_art);
            textViewAlbum = (TextView)itemView.findViewById(R.id.text_view_album);
        }
    }
}
