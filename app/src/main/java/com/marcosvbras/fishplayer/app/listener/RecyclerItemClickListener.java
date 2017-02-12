package com.marcosvbras.fishplayer.app.listener;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.marcosvbras.fishplayer.app.interfaces.RecyclerViewTouchListener;

/**
 * Created by marcos on 25/12/2016.
 */

public class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {

    private RecyclerView recyclerView;
    private RecyclerViewTouchListener recyclerViewTouchListener;
    private GestureDetector gestureDetector;

    public RecyclerItemClickListener(Context context, RecyclerView recyclerView, RecyclerViewTouchListener recyclerViewTouchListener) {
        this.recyclerView = recyclerView;
        this.recyclerViewTouchListener = recyclerViewTouchListener;
        gestureDetector = new GestureDetector(context, onSimpleGestureListener());
    }

    private GestureDetector.SimpleOnGestureListener onSimpleGestureListener() {
        return new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent motionEvent) {
                View clickedView = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());

                if(clickedView != null && recyclerViewTouchListener != null)
                    recyclerViewTouchListener.onItemClickListener(clickedView, recyclerView.getChildAdapterPosition(clickedView));

                return true;
            }

            @Override
            public void onLongPress(MotionEvent motionEvent) {
                View clickedView = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());

                if(clickedView != null && recyclerViewTouchListener != null)
                    recyclerViewTouchListener.onLongItemClickListener(clickedView, recyclerView.getChildAdapterPosition(clickedView));
            }
        };
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView recycleView, MotionEvent motionEvent) {
        gestureDetector.onTouchEvent(motionEvent);
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView view, MotionEvent motionEvent) { }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) { }
}
