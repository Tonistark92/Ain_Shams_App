package com.example.ainshamsuniversity.service;

import static android.content.ContentValues.TAG;

import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.ExistingWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;
import androidx.work.impl.WorkManagerImpl;

import com.example.ainshamsuniversity.checking.CheckingWorker;

import java.util.concurrent.TimeUnit;

@RequiresApi(api = Build.VERSION_CODES.M)
public class MService extends Service {
    WorkManager workManager;
    PeriodicWorkRequest periodicWorkRequest;

    Constraints constraints = new Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED)
            // .setRequiredNetworkType(NetworkType.UNMETERED)
            .setRequiresBatteryNotLow(true)
            .setRequiresDeviceIdle(true)
            .build();

    public MService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "service Started", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "doWork:sevice destroid");
        String data = intent.getStringExtra("uri");

//    ,15, TimeUnit.MILLISECONDS
        periodicWorkRequest = new PeriodicWorkRequest.Builder(CheckingWorker.class, 5, TimeUnit.SECONDS,15, TimeUnit.MILLISECONDS
        )
                .addTag("chicking")
                //.setConstraints(constraints)
                .setInputData(new Data.Builder()
                        .putString("URI", data)
                        .build())
                .build();

        workManager= WorkManager.getInstance(this);
//        workManager
//        .enqueueUniquePeriodicWork(
//                "checknews",
//                ExistingPeriodicWorkPolicy.KEEP
//                ,
//                periodicWorkRequest);
//        workManager.enqueue(periodicWorkRequest);


        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "service done", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "doWork:sevice destroid");

    }
}


//                WorkRequest Request =
//                new OneTimeWorkRequest.Builder(CheckingWorker.class)
//                        .addTag("checking")
//                        .build();
//
//        WorkManager
//                .getInstance(this)
//                .enqueue(Request);

//        WorkManager
//                .getInstance(this)
//                .enqueue(periodicWorkRequest);

//    Data data= new Data().getString("url","https://science.asu.edu.eg/ar/news");


