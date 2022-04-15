package com.example.ainshamsuniversity.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import com.example.ainshamsuniversity.R;
import com.example.ainshamsuniversity.checking.CheckingAnnouncementsWorker;
import com.example.ainshamsuniversity.checking.CheckingEventsWorker;
import com.example.ainshamsuniversity.checking.CheckingNewsWorker;
import com.google.android.material.navigation.NavigationView;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Constraints constraints = new Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
//            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresCharging(true)
            .build();

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    WebView mWebView;
    TextView textView;

    WorkManager workManagerEvents,workManagerNews,workManagerAnnounce;
    PeriodicWorkRequest periodicWorkRequestNews, periodicWorkRequestEvents, periodicWorkRequestAnnounce;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.text);

        // navigation dawer and toolbar

        drawerLayout = findViewById(R.id.drawer_layout);

        navigationView = findViewById(R.id.nav_drawer);

        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.home);


        //web view
        mWebView = findViewById(R.id.web);
        mWebView.loadUrl("https://science.asu.edu.eg/ar");
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowContentAccess(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setAppCacheEnabled(true);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
            }
        });

//        //Regester event checker
        periodicWorkRequestEvents = new PeriodicWorkRequest.Builder(CheckingEventsWorker.class, 3, TimeUnit.SECONDS
        )
               // .addTag("chicking")          we can ignore this sice we used enqueueUniquePeriodicWork XD
                //.setConstraints(constraints)
                .build();
        workManagerEvents = WorkManager.getInstance(this);
        workManagerEvents.enqueue(periodicWorkRequestEvents);   //enqueueUniquePeriodicWork is better


//        workManagerEvents.enqueueUniquePeriodicWork(
//                "checkevents",
//                ExistingPeriodicWorkPolicy.KEEP
//                ,
//                periodicWorkRequestEvents);

        //Regester news checker
        periodicWorkRequestNews = new PeriodicWorkRequest.Builder(CheckingNewsWorker.class, 3, TimeUnit.SECONDS
        )
               // .addTag("chicking")          we can ignore this sice we used enqueueUniquePeriodicWork XD
                //.setConstraints(constraints)
                .build();
        workManagerNews = WorkManager.getInstance(this);
        workManagerNews.enqueue(periodicWorkRequestNews);  // enqueueUniquePeriodicWork is better


//        workManagerNews.enqueueUniquePeriodicWork(
//                "checknews",
//                ExistingPeriodicWorkPolicy.KEEP
//                ,
//                periodicWorkRequestNews);


//        //Regester news checker
        periodicWorkRequestAnnounce = new PeriodicWorkRequest.Builder(CheckingAnnouncementsWorker.class, 3, TimeUnit.SECONDS
        )
               // .addTag("chicking")          we can ignore this sice we used enqueueUniquePeriodicWork XD
               // .setConstraints(constraints)
                .build();
        workManagerAnnounce = WorkManager.getInstance(this);
        workManagerAnnounce.enqueue(periodicWorkRequestAnnounce);   //enqueueUniquePeriodicWork is better


//        workManagerAnnounce.enqueueUniquePeriodicWork(
//                "checkannounce",
//                ExistingPeriodicWorkPolicy.KEEP
//                ,
//                periodicWorkRequestAnnounce);




        try {
            String intent = getIntent().getStringExtra("open");
            switch (intent) {
                case "event":
                    mWebView.loadUrl("https://science.asu.edu.eg/ar/events");
                    break;
                case "news":
                    mWebView.loadUrl("https://science.asu.edu.eg/ar/news");
                    break;
                case "announce":
                    mWebView.loadUrl("https://science.asu.edu.eg/ar/announcements");
                    break;
                default:
                    mWebView.loadUrl("https://science.asu.edu.eg/ar");
                    break;


            }
        } catch (Exception e) {
            e.printStackTrace();
        }



//        new TapTargetSequence(this)
//                .targets(
//                        TapTarget.forView(b1,"this is my button p:","don't u dare to click on it -_-")
//                                .outerCircleColor(R.color.teal_200)
//                                .outerCircleAlpha(0.2f)
//                                .targetCircleColor(android.R.color.holo_blue_bright)
//                                .titleTextSize(10)
//                                .textColor(R.color.design_default_color_on_primary)
//                                .descriptionTextColor(R.color.white)
//                                .descriptionTextSize(8)
//                                .textColor(R.color.black)
//                                .textTypeface(Typeface.SANS_SERIF)
//                                .dimColor(R.color.purple_200)
//                                .drawShadow(true)
//                                .cancelable(true)
//                                .tintTarget(false)
//                                .transparentTarget(true)
//                                .targetRadius(100),
//                        TapTarget.forView(b2,"yeah am button two what do u need",":)")
//                                .outerCircleColor(R.color.teal_200)
//                                .outerCircleAlpha(0.2f)
//                                .targetCircleColor(android.R.color.holo_blue_bright)
//                                .titleTextSize(10)
//                                .textColor(R.color.design_default_color_on_primary)
//                                .descriptionTextColor(R.color.white)
//                                .descriptionTextSize(8)
//                                .textColor(R.color.black)
//                                .textTypeface(Typeface.SANS_SERIF)
//                                .dimColor(R.color.purple_200)
//                                .drawShadow(true)
//                                .cancelable(true)
//                                .tintTarget(false)
//                                .transparentTarget(true)
//                                .targetRadius(100)).listener(new TapTargetSequence.Listener() {
//            @Override
//            public void onSequenceFinish() {
//                //  Toast.makeText(this,"Horay",20).show();
//            }
//
//            @Override
//            public void onSequenceStep(TapTarget lastTarget, boolean targetClicked) {
//                //Toast.makeText(Target.class,"Horaaay",Toast.LENGTH_SHORT).show();
//
//            }
//
//            @Override
//            public void onSequenceCanceled(TapTarget lastTarget) {
//
//            }
//        }).start();






    }


    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.home:
                Toast.makeText(this, "Loading Home page...", Toast.LENGTH_LONG).show();
                mWebView.loadUrl("https://science.asu.edu.eg/ar/page/47/private-ads");
                drawerLayout.closeDrawer(GravityCompat.START);
                break;

            case R.id.news:
                Toast.makeText(this, "Loading News page...", Toast.LENGTH_LONG).show();
                mWebView.loadUrl("https://science.asu.edu.eg/ar/news");
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.events:
                Toast.makeText(this, "Loading Events page...", Toast.LENGTH_LONG).show();
                mWebView.loadUrl("https://science.asu.edu.eg/ar/events");
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.ads:
                Toast.makeText(this, "Loading Announcements page...", Toast.LENGTH_LONG).show();
                mWebView.loadUrl("https://science.asu.edu.eg/ar/announcements");
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.facebook:
                Toast.makeText(this, "Loading Facebook page...", Toast.LENGTH_LONG).show();
                mWebView.loadUrl("http://www.facebook.com/672109416181270");
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.Complaints:
                Toast.makeText(this, "Loading Complaints page...", Toast.LENGTH_LONG).show();
//                mWebView.setWebViewClient(new WebViewClient());
                mWebView.loadUrl("https://forms.office.com/r/HfejjtfynN");
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.gpa:
                Toast.makeText(this, "Loading Facebook page...", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), Gpa.class);
                startActivity(intent);
                drawerLayout.closeDrawer(GravityCompat.START);
                break;


        }


        return true;


    }
}
