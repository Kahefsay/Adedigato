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
import android.widget.TextView;

public class AboutActivity extends Activity {
    private TextView titre;
    SoundPool soundPool;
    int clickSoundId;
    SharedPreferences sp;
    float volume_sound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_about);

        sp = this.getSharedPreferences("adedigato" , Context.MODE_PRIVATE);
        volume_sound = sp.getFloat("volume_sound", 1f);

        titre = findViewById(R.id.titreAbout);
        titre.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left  ));

        soundPool = new SoundPool.Builder().setMaxStreams(1).build();
        clickSoundId = soundPool.load(this, R.raw.clicksound, 1);

        ImageButton buttonClose = findViewById(R.id.iconclose);

        buttonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soundPool.play(clickSoundId, volume_sound, volume_sound, 1, 0, 1);
                Intent intent = new Intent(AboutActivity.this, HomeActivity.class);
                startActivity(intent);
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
