package com.riquart.damian.adedigato;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

public class OptionsActivity extends Activity {
    TextView titre;
    SoundPool soundPool;
    SeekBar songbar, soundbar;
    RadioGroup rGroup;
    RadioButton easy, normal, hard;
    boolean easyb, normalb, hardb;
    int clickSoundId;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    float volume_sound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_options);

        sp = this.getSharedPreferences("adedigato", Context.MODE_PRIVATE);
        editor = sp.edit();
        volume_sound = sp.getFloat("volume_sound", 1f);

        titre = findViewById(R.id.titreOptions);
        titre.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left  ));

        soundPool = new SoundPool.Builder().setMaxStreams(1).build();
        clickSoundId = soundPool.load(this, R.raw.clicksound, 1);

        ImageButton buttonClose = findViewById(R.id.iconclose);

        buttonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soundPool.play(clickSoundId, volume_sound, volume_sound, 1, 0, 1);
                Intent intent = new Intent(OptionsActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        songbar = findViewById(R.id.seekbarmusic);
        soundbar = findViewById(R.id.seekbarsound);

        songbar.setProgress(Math.round(sp.getFloat("volume_song", 1f) * 10));
        soundbar.setProgress(Math.round(sp.getFloat("volume_sound", 1f) * 10));

        songbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                editor.putFloat("volume_song", (i / 10f));
                editor.apply();
                editor.commit();
                HomeActivity.menuSong.setVolume(i/10f, i/10f);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                soundPool.play(clickSoundId, volume_sound, volume_sound, 1, 0, 1);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                soundPool.play(clickSoundId, volume_sound, volume_sound, 1, 0, 1);
            }
        });

        soundbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                editor.putFloat("volume_sound", (i / 10f));
                editor.apply();
                editor.commit();
                HomeActivity.soundPool.setVolume(R.raw.clicksound, i/10f, i/10f);
                volume_sound = (i / 10f);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                soundPool.play(clickSoundId, volume_sound, volume_sound, 1, 0, 1);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                soundPool.play(clickSoundId, volume_sound, volume_sound, 1, 0, 1);
            }
        });

        rGroup = findViewById(R.id.radiogroup);

        easy = findViewById(R.id.easy); normal = findViewById(R.id.normal); hard = findViewById(R.id.hard);
        easyb = sp.getBoolean("easy", false); normalb = sp.getBoolean("normal", true); hardb = sp.getBoolean("hard", false);
        easy.setChecked(easyb); normal.setChecked(normalb); hard.setChecked(hardb);

        rGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                switch(i) {
                    case (R.id.easy): editor.putBoolean("easy", true); editor.putBoolean("normal", false); editor.putBoolean("hard", false); break;
                    case (R.id.normal): editor.putBoolean("easy", false); editor.putBoolean("normal", true); editor.putBoolean("hard", false); break;
                    case (R.id.hard): editor.putBoolean("easy", false); editor.putBoolean("normal", false); editor.putBoolean("hard", true); break;
                }
                editor.apply();
                editor.commit();
            }
        });

        HomeActivity.menuSong.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        titre.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left  ));
        HomeActivity.menuSong.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        HomeActivity.menuSong.pause();
    }
}
