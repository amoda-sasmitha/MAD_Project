package com.example.myapplication;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 1300;
    private RelativeLayout background, image , back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        background = findViewById(R.id.background);
        image = findViewById(R.id.image);
        back = findViewById(R.id.back);
        image.setAlpha(0);
        image.setTranslationY(-40);
        image.animate().alpha(1).setDuration(200);
        image.animate().translationY(0).setDuration(1000).setStartDelay(100);

        back.animate().alpha(0).setDuration(800).setStartDelay(500);


        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {


                Intent mainIntent = new Intent(SplashScreen.this,  MainActivity.class);
                SplashScreen.this.startActivity(mainIntent );
                overridePendingTransition(0, 0);
                SplashScreen.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);


    }


}
