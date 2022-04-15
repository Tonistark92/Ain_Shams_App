package com.example.ainshamsuniversity.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ainshamsuniversity.R;
import com.example.ainshamsuniversity.adapter.SliderAdabter;

public class IntroActivity extends AppCompatActivity  {
    private ViewPager vp;
    private LinearLayout dotsLay;
    SliderAdabter sliderAdabter;
    TextView Dots [];
    Button nextbtn;
    Button backbtn;
    int curpage;
    private static final String islam ="sd";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        vp=findViewById(R.id.vp);
        dotsLay=findViewById(R.id.dots);
        sliderAdabter = new SliderAdabter(this);
        vp.setAdapter(sliderAdabter);
        addDotsIndicator(0);
        vp.addOnPageChangeListener(vl);
        nextbtn = findViewById(R.id.nextbtn);
        backbtn = findViewById(R.id.backbtn);
        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(curpage== Dots.length-1) {

                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    Toast.makeText(getApplicationContext(), "Going to main", Toast.LENGTH_LONG)
                            .show();
                }
                else {vp.setCurrentItem(curpage + 1);}

            }
        });
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vp.setCurrentItem(curpage -1);
            }
        });




    }
    @SuppressLint("ResourceAsColor")
    public void addDotsIndicator(int position) {
        Dots = new TextView[5];
        dotsLay.removeAllViews();
        for (int i = 0; i < Dots.length; i++) {
            Dots[i] = new TextView(this);
            Dots[i].setText(Html.fromHtml("&#8226;"));
            Dots[i].setTextSize(35);
            Dots[i].setTextColor(getResources().getColor(R.color.black_red));
            dotsLay.addView(Dots[i]);
        }
        if(Dots.length > 0) {
            Dots[position].setTextColor(getResources().getColor(R.color.red));
        }
    }
    ViewPager.OnPageChangeListener vl = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if(position-1==Dots.length-1){
                startActivity(new Intent(getApplicationContext(), MainActivity.class));


            }

        }

        @Override
        public void onPageSelected(int position) {
            addDotsIndicator(position);
            curpage=position;

            if(position == 0)
            {
                nextbtn.setEnabled(true); backbtn.setEnabled(false); backbtn. setVisibility(View.INVISIBLE);
                nextbtn.setText("Next"); backbtn.setText("");
            } else if (position ==Dots.length -1) {
                Log.d(islam,"islam");
                nextbtn.setEnabled(true);
                backbtn.setEnabled(true); backbtn. setVisibility (View.VISIBLE);
                nextbtn.setText("Finish"); backbtn.setText("Back");

            } else {
                nextbtn.setEnabled(true);
                backbtn.setEnabled(true);
                backbtn.setVisibility(View.VISIBLE);
                nextbtn.setText("Next");
                backbtn.setText("Back");
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };


}