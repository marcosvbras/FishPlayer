package com.marcosvbras.fishplayer.app.activity;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.marcosvbras.fishplayer.R;
import com.marcosvbras.fishplayer.app.view.RoundedImageView;

public class TesteActivity extends AppCompatActivity {

    RoundedImageView roundedImageView;
    ImageView imageView;
    ImageView teste;
    private RotateAnimation rotateAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teste);

//        roundedImageView = (RoundedImageView)findViewById(R.id.rounded_image_view);
//        imageView = (ImageView)findViewById(R.id.imageViewRodar);
        teste = (ImageView)findViewById(R.id.teste);
        rotateAnimation = (RotateAnimation) AnimationUtils.loadAnimation(this, R.anim.rotate_relative_to_self);
    }

    @Override
    protected void onResume() {
        super.onResume();
        teste.startAnimation(rotateAnimation);
    }
}
