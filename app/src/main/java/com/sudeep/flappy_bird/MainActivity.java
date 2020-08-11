package com.sudeep.flappy_bird;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private boolean is_mute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        findViewById(R.id.play).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, game_acitvity.class));
            }
        });

        TextView high_score_text = findViewById(R.id.high_score);

        final SharedPreferences prefs = getSharedPreferences("game", MODE_PRIVATE);
        high_score_text.setText("HighScore:" + prefs.getInt("highscore", 0));

        is_mute = prefs.getBoolean("isMute", false);

        final ImageView volume_ctrl = findViewById(R.id.volume_on);

        if(is_mute)
        {
            volume_ctrl.setImageResource(R.drawable.ic_baseline_volume_off_24);
        }
        else
        {
            volume_ctrl.setImageResource(R.drawable.ic_baseline_volume_up_24);
        }

        volume_ctrl.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                is_mute = !is_mute;if(is_mute)
                {
                    volume_ctrl.setImageResource(R.drawable.ic_baseline_volume_off_24);
                }
                else
                {
                    volume_ctrl.setImageResource(R.drawable.ic_baseline_volume_up_24);
                }

                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean("isMute", is_mute);
                editor.apply();
            }
        });
    }
}