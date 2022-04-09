package com.example.ainshamsuniversity.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

import com.example.ainshamsuniversity.R;

public class University_Events extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_university_events);

        //web view
        WebView mWebView=findViewById(R.id.webview_events);

        mWebView.loadUrl("https://science.asu.edu.eg/ar/events");
    }
}