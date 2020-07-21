package com.app.ngertiit.Util;

import android.app.Application;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.app.ngertiit.LifeHackAct;
import com.app.ngertiit.MainActivity;
import com.google.gson.Gson;
import com.onesignal.OSNotificationOpenResult;
import com.onesignal.OneSignal;

import org.json.JSONException;
import org.json.JSONObject;

public class NotificationHandler implements OneSignal.NotificationOpenedHandler {

    private Application application;

    private String titleNotif;

    private String idTask;

    public NotificationHandler(Application application) {
        this.application = application;
    }


    @Override
    public void notificationOpened(OSNotificationOpenResult result) {

        // Get data from notification
        Gson gson = new Gson();
        //System.out.println("DEVELOPER : result : " + gson.toJson(result));
        System.out.println("DEVELOPER : result : " + gson.toJson(result.notification.payload.additionalData));

        // RegEx for notification
        String lifehack = "Life";
        String kamus = "Kamus";
        String solusi = "Solusi";
        String notif = result.notification.payload.title;

        titleNotif = result.notification.payload.title;

            if (notif.contains(lifehack)){
                System.out.println("DEVELOPER : Sukses Membuka Data Observasi");
                startLifehack();
            } /*else {
                System.out.println("DEVELOPER : Sukses Membuka Data HAZARD/INSPEKSI");
                // Launch Tasklist Detail (Tasklist)
                startApp();
            }*/
    }

    private void startLifehack() {
        Intent intent = new Intent(application, LifeHackAct.class)
                .setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
//                .putExtra("idTask", idTask);
        application.startActivity(intent);
    }
}