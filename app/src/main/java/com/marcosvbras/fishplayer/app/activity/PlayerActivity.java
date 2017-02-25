package com.marcosvbras.fishplayer.app.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.marcosvbras.fishplayer.R;
import com.marcosvbras.fishplayer.app.FishApplication;
import com.marcosvbras.fishplayer.app.domain.SimpleMusic;
import com.marcosvbras.fishplayer.app.interfaces.OnMusicProgressChangeListener;
import com.marcosvbras.fishplayer.app.util.Constants;
import com.marcosvbras.fishplayer.app.util.FishPlayer;
import com.marcosvbras.fishplayer.app.util.ImageResizeUtils;
import com.marcosvbras.fishplayer.app.util.MusicHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

public class PlayerActivity extends AppCompatActivity implements MediaPlayer.OnCompletionListener, OnMusicProgressChangeListener {

    // Views
    private ImageButton imageButtonPlay;
    private ImageButton imageButtonNext;
    private ImageButton imageButtonPrevious;
    private ImageButton imageButtonShuffle;
    private ImageButton imageButtonRepeat;
    private ImageView imageViewArt;
    private TextView textViewTitle;
    private TextView textViewArtist;
    private TextView textViewDurationProgress;
    private TextView textViewDurationLength;
    private SeekBar seekBar;

    // Another objects
    private FishPlayer fishPlayer;
    private SimpleMusic simpleMusic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        setContentView(R.layout.activity_player);
        loadComponents();

        fishPlayer = new FishPlayer(this);
        fishPlayer.setOnCompletionListener(this);
        fishPlayer.setOnMusicProgressChangeListener(this);
    }

    private void loadComponents() {
        imageButtonPlay = (ImageButton)findViewById(R.id.image_button_play);
        imageButtonPlay.setOnClickListener(onPlayButtonClick());
        imageButtonNext = (ImageButton)findViewById(R.id.image_button_next);
        imageButtonNext.setOnClickListener(onNextButtonClick());
        imageButtonPrevious = (ImageButton)findViewById(R.id.image_button_previous);
        imageButtonPrevious.setOnClickListener(onPreviousButtonClick());
        imageButtonShuffle = (ImageButton)findViewById(R.id.image_button_shuffle);
        imageButtonShuffle.setOnClickListener(onShuffleButtonClick());
        imageButtonRepeat = (ImageButton)findViewById(R.id.image_button_repeat);
        imageButtonRepeat.setOnClickListener(onRepeatButtonClick());
        textViewTitle = (TextView)findViewById(R.id.text_view_title);
        textViewArtist = (TextView)findViewById(R.id.text_view_artist);
        textViewDurationProgress = (TextView)findViewById(R.id.text_view_duration_progress);
        textViewDurationLength = (TextView)findViewById(R.id.text_view_duration_length);
        imageViewArt = (ImageView)findViewById(R.id.image_view_art);
        seekBar = (SeekBar)findViewById(R.id.seek_bar);
        seekBar.setOnSeekBarChangeListener(onSeekBarChangeListener());

        Bundle bundle = getIntent().getExtras();

        if(bundle != null)
            simpleMusic = bundle.getParcelable(Constants.KEY_MUSIC);

        if(!hasPreviousMusic())
            controlButton(imageButtonPrevious, false);

        if(!hasNextMusic())
            controlButton(imageButtonNext, false);
    }

    private View.OnClickListener onRepeatButtonClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageButtonRepeat.setSelected(!imageButtonRepeat.isSelected());
            }
        };
    }

    private View.OnClickListener onShuffleButtonClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageButtonShuffle.setSelected(!imageButtonShuffle.isSelected());

                // Se a opção Shuffle tiver selecionada, a música atual deve ser a primeira da lista
                // e o resto deve ser embaralhado
                if(imageButtonShuffle.isSelected()) {
                    // Salvando a lista e o índice antigo
                    FishApplication.oldMusicList = new ArrayList<>(FishApplication.currentMusicList);

                    // Removendo a música atual e "embaralhando" o resto da lista
                    FishApplication.currentMusicList.remove(FishApplication.currentMusicIndex);
                    Collections.shuffle(FishApplication.currentMusicList);

                    // Adicionando a música atual de volta ao início da lista embaralhada
                    FishApplication.currentMusicList.add(0, simpleMusic);
                    FishApplication.currentMusicIndex = 0;
                } else { // Se não, o estado original da lista de músicas deve ser restaurado
                    for(int i = 0; i < FishApplication.oldMusicList.size(); i++) {
                        if(FishApplication.oldMusicList.get(i).getId() == simpleMusic.getId()) {
                            FishApplication.currentMusicIndex = i;
                            break;
                        }
                    }

                    FishApplication.currentMusicList = new ArrayList<>(FishApplication.oldMusicList);
                    FishApplication.oldMusicList = null;
                }
            }
        };
    }

    private SeekBar.OnSeekBarChangeListener onSeekBarChangeListener() {
        return new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fishPlayer != null && fromUser){
                    fishPlayer.seekTo(progress * 1000);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        playMusic();
        showMusicInfo();
    }

    /**
     * Update all simpleMusic informations on the Activity
     * */
    private void showMusicInfo() {
        if(simpleMusic.getTitle() != null && !simpleMusic.getTitle().equals("") && !simpleMusic.getTitle().equals("0"))
            textViewTitle.setText(simpleMusic.getTitle());
        else
            textViewTitle.setText(getString(R.string.unknown_title));

        String text = null;

        if(simpleMusic.getArtist() != null && !simpleMusic.getArtist().equals("") && !simpleMusic.getArtist().equals("0"))
            text = simpleMusic.getArtist();
        else
            text = getString(R.string.unknown_artist);

        if(simpleMusic.getAlbum() != null && !simpleMusic.getArtist().equals("") && !simpleMusic.getAlbum().equals("0"))
            text += " - " + simpleMusic.getAlbum();
        else
            text += " - " + getString(R.string.unknown_album);

        textViewArtist.setText(text);

        // Image cover
        new Thread(new Runnable() {
            @Override
            public void run() {
                final byte[] data = MusicHelper.getSpecificFilePicture(simpleMusic.getMusicPath());

                if(data != null && data.length > 0) {
                    final Bitmap bitmap = ImageResizeUtils.resizeBitmap(BitmapFactory.decodeByteArray(data, 0, data.length), 280);

                    if(bitmap != null) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                imageViewArt.setImageBitmap(bitmap);
                            }
                        });
                    }
                }
            }
        }).start();

        // Music duration
        textViewDurationLength.setText(
                String.format("%d:%02d", TimeUnit.MILLISECONDS.toMinutes(fishPlayer.getDuration()),
                TimeUnit.MILLISECONDS.toSeconds(fishPlayer.getDuration()) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(fishPlayer.getDuration()))
        ));

        seekBar.setMax(fishPlayer.getDuration() / 1000);
    }

    private View.OnClickListener onPreviousButtonClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpleMusic = FishApplication.currentMusicList.get(--FishApplication.currentMusicIndex);
                fishPlayer.prepareForNewMusic();
                playMusic();
                showMusicInfo();

                if(!hasPreviousMusic())
                    controlButton(imageButtonPrevious, false);

                controlButton(imageButtonNext, true);
            }
        };
    }

    private View.OnClickListener onNextButtonClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpleMusic = FishApplication.currentMusicList.get(++FishApplication.currentMusicIndex);
                fishPlayer.prepareForNewMusic();
                playMusic();
                showMusicInfo();

                if(!hasNextMusic())
                    controlButton(imageButtonNext, false);

                controlButton(imageButtonPrevious, true);
            }
        };
    }

    private boolean hasNextMusic() {
        return FishApplication.currentMusicList.size() - 1 > FishApplication.currentMusicIndex;
    }

    private boolean hasPreviousMusic() {
        return FishApplication.currentMusicIndex - 1 >= 0;
    }

    private void controlButton(ImageButton imageButton, boolean activate) {
        imageButton.setEnabled(activate);
        imageButton.setVisibility(activate ? View.VISIBLE : View.INVISIBLE);
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
        if(fishPlayer.isPlaying()) {
            imageButtonPlay.setImageResource(R.drawable.white_play_250);
            fishPlayer.pause();
        } else {
            imageButtonPlay.setImageResource(R.drawable.white_pause_250);
            fishPlayer.play(simpleMusic.getMusicPath());
        }
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        imageButtonPlay.setImageResource(R.drawable.white_play_250);

        if(imageButtonRepeat.isSelected()) {
            // Repete a música caso o botão esteja selecionado
            imageButtonPlay.setImageResource(R.drawable.white_pause_250);
            fishPlayer.reset();
            fishPlayer.play(simpleMusic.getMusicPath());
        } else if(hasNextMusic()) {
            // Reproduz a próxima música caso exista alguma música na fila
            FishApplication.currentMusicIndex++;
            simpleMusic = FishApplication.currentMusicList.get(FishApplication.currentMusicIndex);
            fishPlayer.prepareForNewMusic();
            playMusic();
            showMusicInfo();

            if (!hasNextMusic())
                controlButton(imageButtonNext, true);

            controlButton(imageButtonPrevious, true);
        }
    }

    @Override
    public void onProgressChange(int currentProgress) {
        seekBar.setProgress(currentProgress / 1000);
        textViewDurationProgress.setText(String.format("%d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(currentProgress),
                TimeUnit.MILLISECONDS.toSeconds(currentProgress) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(currentProgress))
        ));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        fishPlayer.close();
    }
}
