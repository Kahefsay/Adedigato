package com.riquart.damian.adedigato;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.media.SoundPool;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class GameView extends SurfaceView implements Runnable {
    public static final long UPDATE_INTERVAL = 25; // = 40 FPS

    private SurfaceHolder holder;

    SharedPreferences sp;
    SharedPreferences.Editor editor;

    private boolean paused = true;

    private Timer timer = new Timer();
    private TimerTask timerTask;

    private Player player;
    private List<Obstacle> obstacles = new ArrayList<>();
    private Background background;
    private Typeface tf;

    private int height;
    private int width;

    private SoundPool soundPool;
    private float volume_sound;
    private int scoreSoundID;
    private int flapSoundID;
    private int deadSoundID;

    private boolean easy, hard;
    private int score;
    public static boolean isDead;

    public GameView(Context context) {
        super(context);

        sp = this.getContext().getSharedPreferences("adedigato" , Context.MODE_PRIVATE);
        editor = sp.edit();

        easy = sp.getBoolean("easy", false);
        hard = sp.getBoolean("hard", false);

        holder = getHolder();
        player = new Player(context, this);
        background = new Background(context);
        /* TYPO CUSTOM POUR LE SCORE*/
        tf = Typeface.createFromAsset(context.getAssets(), "fonts/prstart.ttf");

        /* ON CONSTRUIT LES OBSTACLES */
        Random r = new Random();
        double randomValue = 0.35 + (0.65 - 0.35) * r.nextDouble();

        height = context.getResources().getDisplayMetrics().heightPixels;
        width = context.getResources().getDisplayMetrics().widthPixels;

        obstacles.add(new Obstacle(context,(int) (width + (width * 0.5f )), (int)-(height * 0.5)));
        obstacles.add(new Obstacle(context,(int) (width * 2 + (width * 0.5f )), (int)-(height * randomValue)));
        obstacles.add(new Obstacle(context,(int) (width * 3 + (width * 0.5f )), (int)-(height * randomValue) ));

        /* EFFET SONORE */
        soundPool = new SoundPool.Builder().setMaxStreams(3).build();
        volume_sound = sp.getFloat("volume_sound", 1f);
        scoreSoundID = soundPool.load(this.getContext(), R.raw.scoresound, 1);
        flapSoundID = soundPool.load(this.getContext(), R.raw.flapsound, 1);
        deadSoundID = soundPool.load(this.getContext(), R.raw.deadsound, 1);

        isDead = false;

        new Thread(new Runnable() {
            @Override
            public void run() {
                GameView.this.run();
            }
        }).start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        performClick();
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            if(paused) {
                resume();
            } else {
                if(!isDead) {
                    playSound(R.raw.flapsound);
                    this.player.onTap();
                }
            }
        }
        return true;
    }

    @Override
    public boolean performClick() {
        super.performClick();

        return true;
    }

    private void resume() {
        paused = false;
        startTimer();
    }

    private void startTimer() {
        setUpTimerTask();
        timer = new Timer();
        timer.schedule(timerTask, UPDATE_INTERVAL, UPDATE_INTERVAL);
    }

    private void stopTimer() {
        if (timer != null) {
            timer.cancel();
            timer.purge();
        }
        if (timerTask != null) {
            timerTask.cancel();
        }
    }

    private void setUpTimerTask() {
        stopTimer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                GameView.this.run();
            }
        };
    }

    @Override
    public void run() {
        if (isDead) {
            player.deadmove();
            draw();
            endGame();
        } else {
            player.move();
            obstacle();
            draw();
        }

    }

    private void draw() {
        while(!holder.getSurface().isValid()){
            try { Thread.sleep(10); } catch (InterruptedException e) { e.printStackTrace(); }
        }
        Canvas canvas = holder.lockCanvas();
        if (canvas != null) {


            drawCanvas(canvas);
        }
        try {
            holder.unlockCanvasAndPost(canvas);
        } catch (IllegalStateException e) {
            // Already unlocked
        }
    }

    private void drawCanvas(Canvas canvas) {
        background.draw(canvas);

        /* DESSIN DES OBSTACLES */
        for (Obstacle obstacle : obstacles) {
            obstacle.draw(canvas);
        }

        player.draw(canvas);

        /* ON AFFICHE LE SCORE */
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(60);
        paint.setTypeface(tf);
        if (!easy)
            canvas.drawText(Integer.toString(score), canvas.getWidth() / 2 - 30, 200, paint);
        else
            canvas.drawText("EASY MODE", (canvas.getWidth() / 2) - 250, 200, paint);

        if (paused) {
            Paint paintPause = new Paint();
            paintPause.setColor(Color.BLACK);
            paintPause.setStyle(Paint.Style.FILL);
            paintPause.setTextSize(20);
            paintPause.setTypeface(tf);
            canvas.drawText("TAP TO BEGIN", (canvas.getWidth() / 2) - 100, canvas.getHeight() / 2, paintPause);
        }
    }

    private void obstacle() {
        for(Obstacle obstacle : obstacles) {

            obstacle.move();

            /* COLLISION AVEC L'OBSTACLE DU HAUT */
            if ( (player.getY() < obstacle.getY() + height - obstacle.getGapHeight() / 2) &&
                    player.getX() + player.getWidth() > obstacle.getX() &&
                    player.getX() < obstacle.getX() + obstacle.getWidth() ) {
                playSound(R.raw.deadsound);
                isDead = true;
            }
            /* COLLISION AVEC L'OBSTACLE DU BAS */
            else if (player.getY() + player.getHeight() > height + (obstacle.getGapHeight() / 2) + obstacle.getY() &&
                    player.getX() + player.getWidth() > obstacle.getX() &&
                    player.getX() < obstacle.getX() + obstacle.getWidth() ) {
                playSound(R.raw.deadsound);
                isDead = true;
            }

            /* SI L'OBSTACLE DISPARAIT DE L'ECRAN, ON LE REPLACE PLUS LOIN*/
            if(obstacle.getX() + width / 5 < 0 ) {
                playSound(R.raw.scoresound);
                if (hard)
                    score += 2;
                else
                    score++;
                Random r = new Random();
                double randomValue = 0.35 + (0.65 - 0.35) * r.nextDouble();

                obstacle.setX(width * 3 - width / 5);
                obstacle.setY((int)-(height * randomValue));
            }
        }
    }

    private void endGame() {
        if(player.getY() > height ) {
            if (!easy) {
                if (sp.getInt("personnal_best", 0) < score) {
                    editor.putInt("personnal_best", score);
                }
                editor.putInt("previous_score", score);
                editor.apply();
                editor.commit();
            }
            timer.cancel();
            timer.purge();
            Intent intent = new Intent(this.getContext(), HomeActivity.class);
            this.getContext().startActivity(intent);
        }
    }

    private void playSound(int id) {
        if(id == R.raw.scoresound)
            soundPool.play(scoreSoundID, volume_sound, volume_sound, 1, 0, 1);
        if(id == R.raw.flapsound) {
            soundPool.play(flapSoundID, volume_sound, volume_sound, 1, 0, 1);
        }
        if (id == R.raw.deadsound)
            soundPool.play(deadSoundID, volume_sound,volume_sound,1,0,1);
    }

}
