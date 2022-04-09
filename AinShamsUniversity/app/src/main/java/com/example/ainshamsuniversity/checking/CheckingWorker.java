package com.example.ainshamsuniversity.checking;

import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.ainshamsuniversity.R;
import com.example.ainshamsuniversity.ui.University_News;
import com.google.android.material.snackbar.Snackbar;

public class CheckingWorker extends Worker {
    WorkerParameters workerParameters;
    private static final String TAG = "Checking";

    public CheckingWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.workerParameters=workerParams;
    }


    @NonNull
    @Override
    public Result doWork() {

        Log.d(TAG, "doWork: working...");


        for (int i = 0; i < 5; ++i) {
            Log.d(TAG, "doWork: woking " + i);

//            try {
//                //Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//                return Result.failure();
//            }

        }
        Log.d(TAG, "doWork:###########################################################################################");
        triggerNotification();



        return Result.success();
    }

    @Override
    public void onStopped() {
        super.onStopped();
        Log.i(TAG, "onStopped: work stopped");
    }


    private void triggerNotification() {
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        //create an intent to open the event details activity
        Intent intent = new Intent(getApplicationContext(), University_News.class);

//put together the PendingIntent
        PendingIntent pendingIntent =
                PendingIntent.getActivity(getApplicationContext(), 1, intent, FLAG_UPDATE_CURRENT);


        NotificationChannel channel = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            channel = new NotificationChannel("channal_id", "channal_name", NotificationManager.IMPORTANCE_DEFAULT);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channel.setLightColor(Color.MAGENTA);
            channel.setVibrationPattern(new long[]{2000});
            // Register the channel with the system
            NotificationManager notificationManager = (NotificationManager) getApplicationContext().
                    getSystemService(Context.NOTIFICATION_SERVICE);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }

        }
        //build the notification

        long[] vibrate = {0, 100, 200, 300};

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(getApplicationContext(), "channal_id")
                        .setSmallIcon(R.drawable.logo_splash)
                        .setContentTitle("notificationTitle")
                        .setContentText("notificationText")
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setCategory(NotificationCompat.CATEGORY_ALARM)
                        .setSound(alarmSound)
                        .setVibrate(vibrate)
                ;


        //trigger the notification
        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(getApplicationContext());

        //we give each notification the ID of the event it's describing,
        //to ensure they all show up and there are no duplicates
        notificationManager.notify(55, notificationBuilder.build());

    }
}



//   WorkManager.getWorkInfoByIdLiveData(syncWorker.id)
//           .observe(getViewLifecycleOwner(), workInfo -> {
//           if (workInfo.getState() != null &&
//           workInfo.getState() == WorkInfo.State.SUCCEEDED) {
//           Snackbar.make(requireView(),
//           R.string.work_completed, Snackbar.LENGTH_SHORT)
//           .show();
//           }
//           });
