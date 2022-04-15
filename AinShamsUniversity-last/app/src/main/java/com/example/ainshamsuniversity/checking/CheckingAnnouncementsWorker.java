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
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.ainshamsuniversity.R;
import com.example.ainshamsuniversity.ui.MainActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class CheckingAnnouncementsWorker extends Worker {
    static String s1 = "hi";
    WorkerParameters workerParameters;
    static int checker = 0;
    private static final String TAG = "Checking";

    public CheckingAnnouncementsWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.workerParameters = workerParams;
    }


    @NonNull
    @Override
    public Result doWork() {
        Log.d(TAG, "doWork: Annonce working...first");



        try {
            if (checker == 0) {
                Document doc = Jsoup.connect("https://science.asu.edu.eg/ar/announcements").get();
                //class name =event-date
                //Elements date_elements = doc.getElementsByClass("event-date");
                Elements body_elements = doc.getElementsByClass("line-clamp-3 dir-rtl");
                Element element = body_elements.first();
//                Log.d(TAG, "doWork: working...first" +element.text());
                s1 = element.text();

                checker += 0;

            }

            Document doc = Jsoup.connect("https://science.asu.edu.eg/ar/announcements").get();
            //class name =event-date
            Elements body_elements = doc.getElementsByClass("line-clamp-3 dir-rtl");
            Element element = body_elements.first();
            String temp = element.text();

            if (!s1.equals(temp)) {
                Log.d(TAG, "doWork: announce notifi..." );
                triggerNotification(temp);
                s1 = temp;

            }


        } catch (IOException e) {
            e.printStackTrace();
        }


        return Result.success();
    }

    @Override
    public void onStopped() {
        super.onStopped();
        Log.i(TAG, "onStopped: work stopped");
    }


    private void triggerNotification(String body) {
        //String GROUP_KEY_WORK_EVENTS = "com.android.example.WORK_EVENTS";

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        //create an intent to open the event details activity
        Intent intent = new Intent(getApplicationContext(), MainActivity.class)
                .putExtra("open", "news");

//put together the PendingIntent
        PendingIntent pendingIntent =
                PendingIntent.getActivity(getApplicationContext(), 1, intent, FLAG_UPDATE_CURRENT);


        NotificationChannel channel1 = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            channel1 = new NotificationChannel("annonce_channal_1", "annonce_channal", NotificationManager.IMPORTANCE_DEFAULT);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channel1.setLightColor(Color.argb(80, 17, 17, 17));
            channel1.setVibrationPattern(new long[]{2000});
            // Register the channel with the system
            NotificationManager notificationManager1 = (NotificationManager) getApplicationContext().
                    getSystemService(Context.NOTIFICATION_SERVICE);
            if (notificationManager1 != null) {
                notificationManager1.createNotificationChannel(channel1);
            }

        }
        //build the notification

        long[] vibrate = {0, 100, 200, 300};

        NotificationCompat.Builder notificationBuilder1 =
                new NotificationCompat.Builder(getApplicationContext(), "annonce_channal_1")
                        .setSmallIcon(R.drawable.logo_splash)
                        .setContentTitle("Let's Check the Announcments 😊")
                        .setContentText(body)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setCategory(NotificationCompat.CATEGORY_ALARM)
                        .setSound(alarmSound)
                        .setVibrate(vibrate);


        //trigger the notification
        NotificationManagerCompat notificationManager1 =
                NotificationManagerCompat.from(getApplicationContext());

        //we give each notification the ID of the event it's describing,
        //to ensure they all show up and there are no duplicates
        notificationManager1.notify(33, notificationBuilder1.build());

    }

}