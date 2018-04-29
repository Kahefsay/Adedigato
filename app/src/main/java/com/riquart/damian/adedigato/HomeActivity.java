package com.riquart.damian.adedigato;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;


public class HomeActivity extends Activity {
    public static MediaPlayer menuSong;
    public static SoundPool soundPool;
    int clickSoundId, previousscorei, personnalbesti;
    TextView titre, previousscore, personnalbest;
    SharedPreferences sp;
    float volume_song, volume_sound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);

        sp = this.getSharedPreferences("adedigato" ,Context.MODE_PRIVATE);

        volume_song = sp.getFloat("volume_song", 1f);
        volume_sound = sp.getFloat("volume_sound", 1f);

        titre = findViewById(R.id.titre);
        titre.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left  ));

        personnalbest = findViewById(R.id.personnalbest);
        previousscore = findViewById(R.id.previousscore);

        previousscorei = sp.getInt("previous_score", 0);
        personnalbesti = sp.getInt("personnal_best", 0);

        personnalbest.setText("PERSONNAL BEST: " + Integer.toString(personnalbesti));
        previousscore.setText("PREVIOUS SCORE: " + Integer.toString(previousscorei));

        personnalbest.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left  ));
        previousscore.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left  ));


        ImageButton buttonPlay = findViewById(R.id.boutonplay);
        ImageButton buttonOptions = findViewById(R.id.boutonoptions);
        ImageButton buttonAbout = findViewById(R.id.boutonabout);

        soundPool = new SoundPool.Builder().setMaxStreams(1).build();
        clickSoundId = soundPool.load(this, R.raw.clicksound, 1);

        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soundPool.play(clickSoundId, volume_sound, volume_sound, 1, 0, 1);
                Intent intent = new Intent(HomeActivity.this, GameActivity.class);
                startActivity(intent);
            }
        });

        buttonOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soundPool.play(clickSoundId, volume_sound, volume_sound, 1, 0, 1);
                Intent intent = new Intent(HomeActivity.this, OptionsActivity.class);
                startActivity(intent);
            }
        });

        buttonAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soundPool.play(clickSoundId, volume_sound, volume_sound, 1, 0, 1);
                Intent intent = new Intent(HomeActivity.this, AboutActivity.class);
                startActivity(intent);
            }
        });

        if (menuSong == null) {
            menuSong = MediaPlayer.create(this, R.raw.menusong);
            menuSong.setVolume(volume_song, volume_song);
        }
        menuSong.setLooping(true);
        menuSong.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        titre.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left  ));
        menuSong.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        menuSong.pause();
    }

}

