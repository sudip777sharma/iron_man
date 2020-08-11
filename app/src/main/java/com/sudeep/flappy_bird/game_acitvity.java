package com.sudeep.flappy_bird;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.os.Bundle;
import android.view.WindowManager;

public class game_acitvity extends AppCompatActivity
{
    private game_view game_view;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);

        game_view = new game_view(this, point.x, point.y);

        setContentView(game_view);
    }

    @Override
    protected void onPause() {
        super.onPause();
        game_view.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        game_view.resume();
    }
}