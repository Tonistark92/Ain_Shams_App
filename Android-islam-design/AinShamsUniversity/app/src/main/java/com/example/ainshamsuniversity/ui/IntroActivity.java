package com.example.ainshamsuniversity.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.ainshamsuniversity.R;

public class IntroActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
//MaterialIntroActivityw
//        addSlide(new SlideFragmentBuilder()
//                        .backgroundColor(R.color.colorPrimary)
//                        .buttonsColor(R.color.colorAccent)
//                        .possiblePermissions(new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.READ_SMS})
//                        .neededPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
//                        .image(agency.tango.materialintroscreen.R.drawable.ic_next)
//                        .title("title 3")
//                        .description("Description 3")
//                        .build(),
//                new MessageButtonBehaviour(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        showMessage("We provide solutions to make you love your work");
//                    }
//                }, "Work with love"));
    }
}