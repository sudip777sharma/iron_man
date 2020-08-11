package com.sudeep.flappy_bird;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.sudeep.flappy_bird.game_view.screen_ratioX;
import static com.sudeep.flappy_bird.game_view.screen_ratioY;

public class flight
{
    public int to_shoot = 0;
    boolean is_going_up = false;
    int x, y, width, height, wing_counter = 0, shoot_counter = 0;
    Bitmap flight1, flight2, shoot1, shoot2, shoot3, shoot4, shoot5, dead;
    private game_view game_view;

    flight(game_view game_view, int screenY, Resources res)
    {
        this.game_view = game_view;
        flight1 = BitmapFactory.decodeResource(res, R.drawable.fly1);
        flight2 = BitmapFactory.decodeResource(res, R.drawable.fly2);

        width = flight1.getWidth();
        height = flight1.getHeight();

        width /= 4;
        height /= 4;

        width = (int) (width * screen_ratioX);
        height = (int) (height * screen_ratioY);

        flight1 = Bitmap.createScaledBitmap(flight1, width, height, false);
        flight2 = Bitmap.createScaledBitmap(flight2, width, height, false);

        shoot1 = BitmapFactory.decodeResource(res, R.drawable.shoot1);
        shoot2 = BitmapFactory.decodeResource(res, R.drawable.shoot2);
        shoot3 = BitmapFactory.decodeResource(res, R.drawable.shoot3);
        shoot4 = BitmapFactory.decodeResource(res, R.drawable.shoot4);
        shoot5 = BitmapFactory.decodeResource(res, R.drawable.shoot5);

        shoot1 = Bitmap.createScaledBitmap(shoot1, width, height, false);
        shoot2 = Bitmap.createScaledBitmap(shoot2, width, height, false);
        shoot3 = Bitmap.createScaledBitmap(shoot3, width, height, false);
        shoot4 = Bitmap.createScaledBitmap(shoot4, width, height, false);
        shoot5 = Bitmap.createScaledBitmap(shoot5, width, height, false);

        dead = BitmapFactory.decodeResource(res, R.drawable.dead);
        dead = Bitmap.createScaledBitmap(dead, width, height, false);
        y = screenY / 2;
        x = (int) (64 * screen_ratioX);
    }

    Bitmap getflight()
    {
        if(to_shoot != 0)
        {
            if(shoot_counter == 1)
            {
                shoot_counter++;
                return shoot1;
            }
            if(shoot_counter == 2)
            {
                shoot_counter++;
                return shoot2;
            }
            if(shoot_counter == 3)
            {
                shoot_counter++;
                return shoot3;
            }
            if(shoot_counter == 4)
            {
                shoot_counter++;
                return shoot4;
            }
            shoot_counter = 1;
            to_shoot--;
            game_view.new_bullet();
            return shoot5;
        }
        if(wing_counter == 0)
        {
            wing_counter++;
            return flight1;
        }
        wing_counter--;
        return flight2;

    }

    Rect get_collision_shape()
    {
        return new Rect(x, y, x+width, y+height);
    }

    Bitmap getdead()
    {
        return dead;
    }
}
