package com.sudeep.flappy_bird;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.sudeep.flappy_bird.game_view.screen_ratioX;
import static com.sudeep.flappy_bird.game_view.screen_ratioY;

public class bird
{
    public int speed = 20;
    public boolean was_shot = true;
    int x = 0, y, width, height, bird_counter = 1;
  Bitmap bird1, bird2, bird3, bird4;

  bird(Resources res)
  {
      bird1 = BitmapFactory.decodeResource(res, R.drawable.bird1);
      bird2 = BitmapFactory.decodeResource(res, R.drawable.bird2);
      bird3 = BitmapFactory.decodeResource(res, R.drawable.bird3);
      bird4 = BitmapFactory.decodeResource(res, R.drawable.bird4);

      width = bird1.getWidth();
      height = bird1.getHeight();

      width /= 6;
      height /= 6;

      width = (int) (width * screen_ratioX);
      height = (int) (height * screen_ratioY);

      bird1 = Bitmap.createScaledBitmap(bird1, width, height, false);
      bird2 = Bitmap.createScaledBitmap(bird2, width, height, false);
      bird3 = Bitmap.createScaledBitmap(bird3, width, height, false);
      bird4 = Bitmap.createScaledBitmap(bird4, width, height, false);

      y = -height;
  }
  Bitmap get_bird()
  {
      if(bird_counter == 1)
      {
          bird_counter++;
          return bird1;
      }
      if(bird_counter == 2)
      {
          bird_counter++;
          return bird2;
      }
      if(bird_counter == 3)
      {
          bird_counter++;
          return bird3;
      }
      bird_counter = 1;
      return bird4;
  }

  Rect get_collision_shape()
  {
      return new Rect(x, y, x+width, y+height);
  }
}
