package com.riquart.damian.adedigato;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;

public class GameActivity extends Activity {
    MediaPlayer backgroundsong;
    SharedPreferences sp;
    float volume_song;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new GameView(this));

        sp = this.getSharedPreferences("adedigato" , Context.MODE_PRIVATE);

        volume_song = sp.getFloat("volume_song", 1f);

        if (backgroundsong == null ) {
            backgroundsong = MediaPlayer.create(this, R.raw.backgroundsong);
            backgroundsong.setVolume(volume_song, volume_song);
        }
        backgroundsong.setLooping(true);
        backgroundsong.start();

    }

    @Override
    protected void onResume() {
        super.onResume();
        backgroundsong.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        backgroundsong.stop();
    }
}
