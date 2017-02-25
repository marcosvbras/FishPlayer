package com.marcosvbras.fishplayer.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.marcosvbras.fishplayer.R;
import com.marcosvbras.fishplayer.app.domain.Artist;
import com.marcosvbras.fishplayer.app.util.Animations;

import java.util.List;

/**
 * Created by marcosvbras on 12/02/17.
 */

public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.MyViewHolder> implements Filterable {

    private List<Artist> listArtist;
    private Context context;
    private int lastLoadedPosition;

    public ArtistAdapter(List<Artist> listArtist, Context context) {
        this.listArtist = listArtist;
        this.context = context;
        setHasStableIds(true);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_artist, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int position) {
        Artist artist = listArtist.get(position);
        myViewHolder.textViewArtist.setText(artist.getName());
        myViewHolder.textViewNumberMusics.setText(artist.getNumberOfTracks() + " " +
                (artist.getNumberOfTracks() > 1 ? context.getString(R.string.musics_item) :
                        context.getString(R.string.music)));
        myViewHolder.textViewNumberAlbums.setText(artist.getNumberOfAlbums() + " " +
                (artist.getNumberOfAlbums() > 1 ? context.getString(R.string.albums_item) :
                        context.getString(R.string.album)));

        if(position > lastLoadedPosition) {
            Animations.setFadeInAnimation(myViewHolder.container, context, 1200);
            lastLoadedPosition = position;
        }
    }

    @Override
    public void onViewDetachedFromWindow(MyViewHolder myViewHolder) {
        super.onViewDetachedFromWindow(myViewHolder);
        myViewHolder.container.clearAnimation();
    }

    @Override
    public int getItemCount() {
        return listArtist.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public Filter getFilter() {
        final Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();

                if(constraint != null && !constraint.equals("")) {

                }

                return null;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

            }
        };

        return filter;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout container;
        TextView textViewArtist;
        TextView textViewNumberMusics;
        TextView textViewNumberAlbums;

        public MyViewHolder(View itemView) {
            super(itemView);
            container = (LinearLayout)itemView.findViewById(R.id.container);
            textViewArtist = (TextView)itemView.findViewById(R.id.text_view_artist);
            textViewNumberMusics = (TextView)itemView.findViewById(R.id.text_view_number_musics);
            textViewNumberAlbums = (TextView)itemView.findViewById(R.id.text_view_number_albums);
        }
    }
}
