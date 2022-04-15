package com.example.ainshamsuniversity.ui;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

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


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    SharedPreferences settings = getSharedPreferences("prefs", 0);
                    boolean firstRun = settings.getBoolean("firstRun1", false);
                    if (firstRun==false )//if running for first time firstRun==false
                    {
                        Log.d(TAG, "run: intro");
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putBoolean("firstRun1", true);
                        editor.commit();
                        Intent i = new Intent(getApplicationContext(), IntroActivity.class);//Activity to be     launched For the First time
                        startActivity(i);
                        finish();
                    } else {
                        Intent intent = new Intent(Splash.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


        },SPLASH_MAX_TIME);
    }
}