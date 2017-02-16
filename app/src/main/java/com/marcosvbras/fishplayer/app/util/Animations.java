package com.marcosvbras.fishplayer.app.util;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

/**
 * Created by marcosvbras on 15/02/2017.
 */

public class Animations {

    public static void setFadeInAnimation(View viewToAnimate, Context context, long duration) {
        Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.fade_in);
        animation.setDuration(duration);
        viewToAnimate.startAnimation(animation);
    }

    public static void setFadeOutAnimation(View viewToAnimate, Context context, long duration) {
        Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.fade_out);
        animation.setDuration(duration);
        viewToAnimate.startAnimation(animation);
    }

}
