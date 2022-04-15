package com.example.ainshamsuniversity.ui;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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

import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;

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




        try {
            SharedPreferences settings = getSharedPreferences("prefs", 0);
            boolean firstRun = settings.getBoolean("firstRun2", false);
            if (firstRun==false )//if running for first time firstRun==false
            {
                Log.d(TAG, "run: intro");
                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean("firstRun2", true);
                editor.commit();
                MaterialTapTargetPrompt.Builder builder=new MaterialTapTargetPrompt.Builder(this);
                builder.setTarget(R.id.nav_drawer)
                        .setPrimaryText("Click to navigate throw app")
                        .setPrimaryTextColour(102-255-102)
                        .setFocalRadius(300.0F)
                        .setFocalRadius(500.0f)
                        .setPrimaryTextGravity(1)
                        .setBackgroundColour(51-51-0)
                        .setPromptStateChangeListener(new MaterialTapTargetPrompt.PromptStateChangeListener() {
                            @Override
                            public void onPromptStateChanged(@NonNull MaterialTapTargetPrompt prompt, int state) {
                                if(state==MaterialTapTargetPrompt.STATE_FOCAL_PRESSED) {
                                    drawerLayout.openDrawer(GravityCompat.START);
                                }
                            }
                        })
                        .show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }




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
                //
            case R.id.ads:
                Toast.makeText(this, "Loading Announcements page...", Toast.LENGTH_LONG).show();
                mWebView.loadUrl("https://science.asu.edu.eg/ar/announcements");
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.privateAds:
                Toast.makeText(this, "Loading private Announcements page...", Toast.LENGTH_LONG).show();
                mWebView.loadUrl("https://science.asu.edu.eg/ar/page/47/private-ads");
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
                Toast.makeText(this, "Loading Gpa page...", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), Gpa.class);
                startActivity(intent);
                drawerLayout.closeDrawer(GravityCompat.START);
                break;


        }


        return true;


    }
}
