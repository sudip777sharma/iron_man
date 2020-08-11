package com.sudeep.flappy_bird;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class background {
    int x = 0, y = 0;
    Bitmap background;
    background(int screenX, int screenY, Resources res)
    {
        background = BitmapFactory.decodeResource(res, R.drawable.flappy_bird_background);
        background = Bitmap.createScaledBitmap(background, screenX, screenY, false);
    }
}
