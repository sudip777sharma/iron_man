package com.sudeep.flappy_bird;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.sudeep.flappy_bird.game_view.screen_ratioX;
import static com.sudeep.flappy_bird.game_view.screen_ratioY;

public class bullet
{
    int x , y, width, height;
    Bitmap bullet;

    bullet(Resources res)
    {
        bullet = BitmapFactory.decodeResource(res, R.drawable.bullet);
        width = bullet.getWidth();
        height = bullet.getHeight();

        width /= 4;
        height /= 4;

        width = (int) (width * screen_ratioX);
        height = (int) (height * screen_ratioY);

        bullet = Bitmap.createScaledBitmap(bullet, width, height, false);
    }

    Rect get_collision_shape()
    {
        return new Rect(x, y, x+width, y+height);
    }
}
