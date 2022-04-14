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


public class CheckingEventsWorker extends Worker {
    static String s = "";
    WorkerParameters workerParameters;
    static int checker = 0;

    private static final String TAG = "Checking";

    public CheckingEventsWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.workerParameters = workerParams;
    }


    @NonNull
    @Override
    public Result doWork() {


        try {
            if (checker == 0) {
                Document doc = Jsoup.connect("https://science.asu.edu.eg/ar/events").get();
                //class name =event-date
                //Elements date_elements = doc.getElementsByClass("event-date");
                Elements body_elements = doc.getElementsByClass("max-h-12 overflow-ellipsis overflow-hidden");
                Element element = body_elements.first();

                s = element.text();
                checker += 0;

            }

            Document doc = Jsoup.connect("https://science.asu.edu.eg/ar/events").get();
            //class name =event-date
            Elements body_elements = doc.getElementsByClass("max-h-12 overflow-ellipsis overflow-hidden");
            Element element = body_elements.first();

            String temp=element.text();
            if(!s.equals(temp)){
                triggerNotification(temp);
                s=temp;

            }




        } catch (IOException e) {
            e.printStackTrace();
        }



        return Result.success();
    }
    private void triggerNotification(String body) {
        //String GROUP_KEY_WORK_EVENTS = "com.android.example.WORK_EVENTS";

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        //create an intent to open the event details activity
        Intent intent = new Intent(getApplicationContext(), MainActivity.class)
                .putExtra("open","event");

//put together the PendingIntent
        PendingIntent pendingIntent =
                PendingIntent.getActivity(getApplicationContext(), 1, intent, FLAG_UPDATE_CURRENT);


        NotificationChannel channel = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            channel = new NotificationChannel("event_channal_1", "event_channal", NotificationManager.IMPORTANCE_DEFAULT);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channel.setLightColor(Color.argb(80, 17, 17, 17));
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
                new NotificationCompat.Builder(getApplicationContext(), "event_channal_1")
                        .setSmallIcon(R.drawable.logo_splash)
                        .setContentTitle("Let's Check the Events 😊")
                        .setContentText(body)
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

    @Override
    public void onStopped() {
        super.onStopped();
        Log.i(TAG, "onStopped: work stopped");
    }

    public static int getMonth(String month) {
        if (month.equalsIgnoreCase("January"))
            return 1;
        else if (month.equalsIgnoreCase("February"))
            return 2;
        else if (month.equalsIgnoreCase("March"))
            return 3;
        else if (month.equalsIgnoreCase("April"))
            return 4;
        else if (month.equalsIgnoreCase("May"))
            return 5;
        else if (month.equalsIgnoreCase("June"))
            return 6;
        else if (month.equalsIgnoreCase("July"))
            return 7;
        else if (month.equalsIgnoreCase("August"))
            return 8;
        else if (month.equalsIgnoreCase("September"))
            return 9;
        else if (month.equalsIgnoreCase("October"))
            return 10;
        else if (month.equalsIgnoreCase("November"))
            return 11;
        else if (month.equalsIgnoreCase("December"))
            return 12;
        return -1;

    }

    public static int getMonthASNumber(String Date) {
        String monthAsString = Date.substring(0, Date.indexOf(' '));
        int monthASNumber = getMonth(monthAsString);
        return monthASNumber;
    }

    public static String getDay(String Date) {
        String day = Date.substring(Date.indexOf(' ') + 1, Date.indexOf(','));
        return day;
    }

    public static String getYear(String Date) {
        String year = Date.substring(Date.lastIndexOf(' ') + 1);
        return year;
    }

    public static String getLocalDate(String Date) {
        String localDate = getDay(Date) + "/";
        localDate += getMonthASNumber(Date) + "/";
        localDate += getYear(Date);
        return localDate;
    }





}


//          Log.d(TAG, "doWork: working..." + s);
//            if (date_elements.size() > checker) {
//                checker=date_elements.size();
//                triggerNotification();
//                for (int i = 1; i < date_elements.size(); i++) {
//                    Element element1 = date_elements.get(i);
//                    dataHolder.setDate(getLocalDate(element1.text()));
//                    String element2 = String.valueOf(body_elements.eq(i));
//                    Log.d(TAG, "doWork: working..."+element2);
//                    dataHolder.setBody(element2);
//                    dates[i]=dataHolder.getDate();

//                }
//                DateSorter dateSorter=new DateSorter();
////                Collections.sort(dates,dateSorter);
//                for (int i=0; i<dates.length ;i++){
//                    Log.d(TAG, "doWork: working..."+ dates[i]);
//                    //triggerNotification(body_elements.get(i),date_elements.get(i));
//                    Thread.sleep(2000);
//
//                }

// }


//            Elements links = doc.select("a[href]");

// s = element.text();
//         String b = getLocalDate(s);
//         Date date = sdf.parse(b);
//         if (new Date().before(date)) {
//         Log.d(TAG, "doWork: working..." + b);
//         triggerNotification();
//         }

//   WorkManager.getWorkInfoByIdLiveData(syncWorker.id)
//           .observe(getViewLifecycleOwner(), workInfo -> {
//           if (workInfo.getState() != null &&
//           workInfo.getState() == WorkInfo.State.SUCCEEDED) {
//           Snackbar.make(requireView(),
//           R.string.work_completed, Snackbar.LENGTH_SHORT)
//           .show();
//           }
//           });
