package com.example.ainshamsuniversity.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ainshamsuniversity.IntroActivity;
import com.example.ainshamsuniversity.ui.MainActivity;
import com.example.ainshamsuniversity.R;

public class Splash extends AppCompatActivity {
    private static int SPLASH_MAX_TIME =3000;

    //init animations
    Animation image,text;

    //init views
    ImageView imageView;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slpash);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //find the drawable animations
        image= AnimationUtils.loadAnimation(this,R.anim.image);
        image.setDuration(2500);

        text= AnimationUtils.loadAnimation(this,R.anim.text);
        text.setDuration(3000);


        //find the views
        imageView=findViewById(R.id.image);
        textView=findViewById(R.id.text);

        //init animations
        imageView.setAnimation(image);
        textView.setAnimation(text);

        Boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                .getBoolean("isFirstRun", true);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {



                if (true) {
                    //show start activity

                    startActivity(new Intent(Splash.this, IntroActivity.class));
                    Toast.makeText(Splash.this, "First Run", Toast.LENGTH_LONG)
                            .show();
                    getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                            .putBoolean("isFirstRun", false).commit();
                }

                else {
                    startActivity(new Intent(Splash.this, MainActivity.class));
                    Toast.makeText(Splash.this, "First Run", Toast.LENGTH_LONG)
                            .show();
                    finish();
                }



            }
        },SPLASH_MAX_TIME);
    }
}