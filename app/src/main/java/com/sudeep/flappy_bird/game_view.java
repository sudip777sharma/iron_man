package com.sudeep.flappy_bird;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.view.MotionEvent;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class game_view extends SurfaceView implements Runnable{
    private Thread thread;
    private boolean is_game_over = false;
    private boolean is_playing;
    private int screenX, screenY, score = 0;
    public static float screen_ratioX, screen_ratioY;
    private Paint paint;
    private Random random;
    private SoundPool soundPool;
    private bird[] birds;
    private SharedPreferences prefs;
    private List<bullet> bullets;
    private int sound;
    private flight flight;
    private  background background1, background2;
    private game_acitvity activity;
    public game_view(game_acitvity activity, int screenX, int screenY)
    {
        super(activity);
        this.activity = activity;
        prefs = activity.getSharedPreferences("game", Context.MODE_PRIVATE);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .build();

            soundPool = new SoundPool.Builder()
                    .setAudioAttributes(audioAttributes)
                    .build();
        }
        else
        {
            soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        }
        sound = soundPool.load(activity, R.raw.shoot_sound, 1);
        this.screenX = screenX;
        this.screenY = screenY;

        screen_ratioX = 1920f / screenX;
        screen_ratioY = 1080f / screenY;

        background1 = new background(screenX, screenY, getResources());
        background2 = new background(screenX, screenY, getResources());

        flight = new flight(this , screenY, getResources());

        bullets = new ArrayList<>();

        background2.x = screenX;
        paint = new Paint();

        paint.setTextSize(128);
        paint.setColor(Color.WHITE);

        birds = new bird[4];
        for(int i = 0;i < 4;i++)
        {
            bird bird = new bird(getResources());
            birds[i] = bird;
        }

        random = new Random();
    }

    @Override
    public void run()
    {
        while(is_playing)
        {
            update();
            draw();
            sleep();
        }
    }

    private void update()
    {
        background1.x -= 10 * screen_ratioX;
        background2.x -= 10 * screen_ratioX;
        if(background1.x + background1.background.getWidth() < 0)
        {
            background1.x = screenX;
        }
        if(background2.x + background2.background.getWidth() < 0)
        {
            background2.x = screenX;
        }
        if(flight.is_going_up)
        {
            flight.y -= 30 * screen_ratioY;
        }
        else
        {
            flight.y += 30 * screen_ratioY;
        }
        if(flight.y < 0)
        {
            flight.y = 0;
        }
        if(flight.y >= screenY - flight.height)
        {
            flight.y = screenY - flight.height;
        }

        List<bullet> trash = new ArrayList<>();
        for(bullet bullet : bullets)
        {
            if(bullet.x > screenX)
            {
                trash.add(bullet);
            }
            bullet.x += 50 * screen_ratioX;
            for(bird bird : birds)
            {
                if(Rect.intersects(bird.get_collision_shape(), bullet.get_collision_shape()))
                {
                    score++;
                    bird.x = -500;
                    bullet.x = screenX + 500;
                    bird.was_shot = true;
                }
            }
        }

        for(bullet bullet : trash)
        {
            bullets.remove(bullet);
        }

        for(bird bird : birds)
        {
            bird.x -= bird.speed;
            if(bird.x + bird.width < 0)
            {
                if(!bird.was_shot)
                {
                    is_game_over = true;
                    return;
                }
               int bound = (int) (30 * screen_ratioX);
               bird.speed = random.nextInt(bound);

               if(bird.speed < 10 * screen_ratioX)
               {
                   bird.speed = (int) (10 * screen_ratioX);
               }
               bird.x = screenX;
               bird.y = random.nextInt(screenY -bird.height);

               bird.was_shot = false;
            }

            if(Rect.intersects(bird.get_collision_shape(), flight.get_collision_shape()))
            {
                is_game_over = true;
                return;
            }
        }
    }

    private void draw()
    {
        if(getHolder().getSurface().isValid())
        {
            Canvas canvas = getHolder().lockCanvas();
            canvas.drawBitmap(background1.background, background1.x, background1.y, paint);
            canvas.drawBitmap(background2.background, background2.x, background2.y, paint);

            for(bird bird : birds)
            {
                canvas.drawBitmap(bird.get_bird(), bird.x, bird.y, paint);
            }

            canvas.drawText(score + "", screenX / 2f, 164, paint);

            if(is_game_over)
            {
                is_playing = false;
                canvas.drawBitmap(flight.getdead(), flight.x, flight.y, paint);
                getHolder().unlockCanvasAndPost(canvas);
                save_if_high_score();
                wait_before_exiting();
                return;
            }

            canvas.drawBitmap(flight.getflight(), flight.x, flight.y, paint);

            for(bullet bullet : bullets)
            {
                canvas.drawBitmap(bullet.bullet, bullet.x , bullet.y, paint);
            }

            getHolder().unlockCanvasAndPost(canvas);
        }
    }

    private void wait_before_exiting()
    {
        try {
            Thread.sleep(2000);
            activity.startActivity(new Intent(activity, MainActivity.class));
            activity.finish();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void save_if_high_score()
    {
        if(prefs.getInt("highscore", 0) < score)
        {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("highscore", score);
            editor.apply();
        }
    }

    private void sleep()
    {
        try {
            Thread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void resume()
    {
        is_playing = true;
        thread = new Thread(this);
        thread.start();
    }
    public void pause()
    {
        try {
            is_playing = false;
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                if(event.getX() < screenX / 2)
                {
                    flight.is_going_up = true;
                }
                break;
            case MotionEvent.ACTION_UP:
                flight.is_going_up = false;
                if(event.getX() > screenX / 2)
                {
                    flight.to_shoot++;
                }
                break;
        }
        return true;
    }

    public void new_bullet()
    {
        if(!prefs.getBoolean("isMute", false))
        {
            soundPool.play(sound, 1, 1, 0,0, 0);
        }
        bullet bullet = new bullet(getResources());
        bullet.x = flight.x ;
        bullet.y = flight.y ;
        bullets.add(bullet);
    }
}
