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


public class CheckingWorker extends Worker {
    WorkerParameters workerParameters;
    private static final String TAG = "Checking";

    public CheckingWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.workerParameters = workerParams;
    }


    @NonNull
    @Override
    public Result doWork() {


        //Elements links =doc.select("a[href]");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        //Calendar calendar = new GregorianCalendar();


        try {
            Document doc = Jsoup.connect("https://science.asu.edu.eg/ar/events").get();
            String s;
            //class name =event-date
            Elements elements = doc.getElementsByClass("event-date");
            Element element = elements.first();
//            Elements links = doc.select("a[href]");
            s = element.text();
            String b = month(s);
            Date date = sdf.parse(b);
            if (new Date().before(date)) {
                Log.d(TAG, "doWork: working..." + b);
                triggerNotification();
            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }


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
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);

//put together the PendingIntent
        PendingIntent pendingIntent =
                PendingIntent.getActivity(getApplicationContext(), 1, intent, FLAG_UPDATE_CURRENT);


        NotificationChannel channel = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            channel = new NotificationChannel("channal_id", "channal_name", NotificationManager.IMPORTANCE_DEFAULT);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channel.setLightColor(Color.MAGENTA);
//            channel.setVibrationPattern(new long[]{2000});
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
                        .setVibrate(vibrate);


        //trigger the notification
        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(getApplicationContext());

        //we give each notification the ID of the event it's describing,
        //to ensure they all show up and there are no duplicates
        notificationManager.notify(55, notificationBuilder.build());

    }

    //          December 18, 2021
    //          0123456789012345678
    //          January 18, 2021
    //          0123456789012345678
    //          October 18, 2021
    //          0123456789012345678
//    18-11-2021
    private String month(String s) {
        s += " ";
        String e = s.substring(0, 3);
        String result = "";

        if (e.equals("Jan")) {
            result += s.substring(8, 10);
            result += "/" + "1";
            result += "/" + s.substring(12, 16);
        } else if (e.equals("Feb")) {
            result += s.substring(9, 11);
            result += "/" + "2";
            result += "/" + s.substring(13, 17);
        } else if (e.equals("Mar")) {
            result += s.substring(6, 8);
            result += "/" + "3";
            result += "/" + s.substring(10, 14);
        } else if (e.equals("Apr")) {
            result += s.substring(6, 8);
            result += "/" + "4";
            result += "/" + s.substring(10, 14);
        } else if (e.equals("May")) {
            result += s.substring(4, 6);
            result += "/" + "5";
            result += "/" + s.substring(8, 12);
        } else if (e.equals("Jun")) {
            result += s.substring(5, 7);
            result += "/" + "6";
            result += "/" + s.substring(9, 13);
        } else if (e.equals("Jul")) {
            result += s.substring(5, 7);
            result += "/" + "7";
            result += "/" + s.substring(9, 13);
        } else if (e.equals("Aug")) {
            result += s.substring(7, 9);
            result += "/" + "8";
            result += "/" + s.substring(11, 15);
        } else if (e.equals("Sep")) {
            result += s.substring(10, 12);
            result += "/" + "9";
            result += "/" + s.substring(14, 18);
        } else if (e.equals("Oct")) {
            result += s.substring(8, 10);
            result += "/" + "10";
            result += "/" + s.substring(12, 16);
        } else if (e.equals("Nov")) {
            result += s.substring(9, 11);
            result += "/" + "11";
            result += "/" + s.substring(13, 17);
        } else {
            result += s.substring(9, 11);
            result += "/" + "1";
            result += "/" + s.substring(13, 17);
        }

        return result;

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
