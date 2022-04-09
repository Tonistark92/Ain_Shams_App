package com.example.ainshamsuniversity.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

import com.example.ainshamsuniversity.R;

public class University_Announce extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_university_announce);

        //web view
        WebView mWebView=findViewById(R.id.webview_announce);

        mWebView.loadUrl("https://science.asu.edu.eg/ar/announcements");
    }
}