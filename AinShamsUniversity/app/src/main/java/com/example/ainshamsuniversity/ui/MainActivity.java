package com.example.ainshamsuniversity.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.work.Data;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import com.example.ainshamsuniversity.R;
import com.example.ainshamsuniversity.checking.CheckingWorker;
import com.example.ainshamsuniversity.service.MService;
import com.google.android.material.navigation.NavigationView;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    WebView mWebView;

    WorkManager workManager;
    PeriodicWorkRequest periodicWorkRequest;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // navigation bar

        drawerLayout = findViewById(R.id.drawer_layout);

        navigationView = findViewById(R.id.nav_drawer);

        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        //web view
        mWebView = findViewById(R.id.webview);
        //mWebView.loadUrl("http://newportal.asu.edu.eg/science/ar/page/47/private-ads");






        periodicWorkRequest = new PeriodicWorkRequest.Builder(CheckingWorker.class, 30, TimeUnit.SECONDS
        )
                .addTag("chicking")
                //.setConstraints(constraints)
//                .setInputData(new Data.Builder()
//                        .putString("URI", data)
//                        .build())
                .build();
        workManager= WorkManager.getInstance(this);
        //workManager.enqueue(periodicWorkRequest);


//        workManager.enqueueUniquePeriodicWork(
//                        "checknews",
//                        ExistingPeriodicWorkPolicy.KEEP
//                        ,
//                        periodicWorkRequest);




//        sevice
//        String dataUrl="http://newportal.asu.edu.eg/science/ar/page/47/private-ads";
//        Intent serviceIntent = new Intent(this, MService.class);
//        serviceIntent.putExtra("uri",dataUrl);
//        startService(serviceIntent);



    }











    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.home:
                Toast.makeText(this, "Loading Home page...", Toast.LENGTH_LONG).show();
                mWebView.loadUrl("http://newportal.asu.edu.eg/science/ar/page/47/private-ads");
                drawerLayout.closeDrawer(GravityCompat.START);
                break;

            case R.id.news:
                Toast.makeText(this, "Loading News page...", Toast.LENGTH_LONG).show();
                Intent intent =new Intent(this,University_News.class);
                startActivity(intent);
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.events:
                Toast.makeText(this, "Loading Events page...", Toast.LENGTH_LONG).show();
                Intent intent_1 =new Intent(this,University_Events.class);
                startActivity(intent_1);
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.ads:
                Toast.makeText(this, "Loading Announcements page...", Toast.LENGTH_LONG).show();
                Intent intent_2 =new Intent(this,University_News.class);
                startActivity(intent_2);
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.facebook:
                Toast.makeText(this, "Loading Facebook page...", Toast.LENGTH_LONG).show();
                mWebView.loadUrl("https://www.facebook.com/Elgam3a.m");
                drawerLayout.closeDrawer(GravityCompat.START);
                break;

        }


        return true;


    }
}


//try {
//        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/Elgam3a.m"));
//        startActivity(intent);
//        } catch(Exception e) {
//        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.facebook.com/appetizerandroid")));
//        }