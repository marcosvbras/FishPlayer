package com.marcosvbras.fishplayer.app.interfaces;

import android.view.View;

/**
 * Created by marcosvbras on 25/12/2016.
 */

public interface OnRecyclerViewTouchListener {
    public void onItemClick(View view, int position);
    public void onLongItemClick(View view, int position);
}
