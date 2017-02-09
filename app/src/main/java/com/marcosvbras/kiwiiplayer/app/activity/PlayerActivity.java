package com.marcosvbras.kiwiiplayer.app.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.marcosvbras.kiwiiplayer.R;
import com.marcosvbras.kiwiiplayer.app.domain.Music;
import com.marcosvbras.kiwiiplayer.app.util.Constants;
import com.marcosvbras.kiwiiplayer.app.util.KiwiiPlayer;

public class PlayerActivity extends AppCompatActivity {

    // Views
    private ImageButton imageButtonPlay;
    private ImageButton imageButtonNext;
    private ImageButton imageButtonPrevious;
    private TextView textViewTitle;
    private TextView textViewArtist;
    private ImageView imageViewCover;

    // Another objects
    private KiwiiPlayer kiwiiPlayer;
    private Music music;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        loadComponents();
        kiwiiPlayer = new KiwiiPlayer(this);
    }

    private void loadComponents() {
        imageButtonPlay = (ImageButton)findViewById(R.id.imagebutton_play);
        imageButtonPlay.setOnClickListener(onPlayButtonClick());
        imageButtonNext = (ImageButton)findViewById(R.id.imagebutton_next);
        imageButtonNext.setOnClickListener(onNextButtonClick());
        imageButtonPrevious = (ImageButton)findViewById(R.id.imagebutton_previous);
        imageButtonPrevious.setOnClickListener(onPreviousButtonClick());
        textViewTitle = (TextView)findViewById(R.id.text_view_title);
        textViewArtist = (TextView)findViewById(R.id.text_view_artist);
        imageViewCover = (ImageView)findViewById(R.id.image_view_cover);

        Bundle bundle = getIntent().getExtras();

        if(bundle != null)
            music = bundle.getParcelable(Constants.KEY_MUSIC);
    }

    @Override
    protected void onResume() {
        super.onResume();
        playMusic();
        showMusicInfo();
    }

    private void showMusicInfo() {
        textViewTitle.setText(music.getTitle());
        textViewArtist.setText(music.getArtist());

        new Thread(new Runnable() {
            @Override
            public void run() {
                final Bitmap bitmap = BitmapFactory.decodeFile(music.getCoverPath());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        imageViewCover.setImageBitmap(bitmap);
                    }
                });
            }
        }).start();
    }

    private View.OnClickListener onPreviousButtonClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        };
    }

    private View.OnClickListener onNextButtonClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        };
    }

    private View.OnClickListener onPlayButtonClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playMusic();
            }
        };
    }

    private void playMusic() {
        if(kiwiiPlayer.isPlaying()) {
            imageButtonPlay.setImageResource(R.drawable.white_play_128);
            kiwiiPlayer.pause();
        } else {
            imageButtonPlay.setImageResource(R.drawable.white_pause_128);
            kiwiiPlayer.play(music.getMusicPath());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        kiwiiPlayer.close();
    }
}
