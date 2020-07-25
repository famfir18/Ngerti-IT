package com.app.ngertiit.Util;

import android.app.Application;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.app.ngertiit.KamusAct;
import com.app.ngertiit.LifeHackAct;
import com.app.ngertiit.MainActivity;
import com.app.ngertiit.SolutionAct;
import com.google.gson.Gson;
import com.onesignal.OSNotificationOpenResult;
import com.onesignal.OneSignal;

import org.json.JSONException;
import org.json.JSONObject;

public class NotificationHandler implements OneSignal.NotificationOpenedHandler {

    private MainActivity application;

    private String titleNotif;

    private String idTask;

    public NotificationHandler(MainActivity application) {
        this.application = application;
    }


    @Override
    public void notificationOpened(OSNotificationOpenResult result) {

        // Get data from notification
        Gson gson = new Gson();
        //System.out.println("DEVELOPER : result : " + gson.toJson(result));
        System.out.println("DEVELOPER : result : " + gson.toJson(result.notification.payload.body));

        // RegEx for notification
        String lifehack = "Life";
        String kamus = "bahasa";
        String solusi = "Solusi";
        String notif = result.notification.payload.body;

        System.out.println("Notif Develop : " + notif);

        titleNotif = result.notification.payload.title;

            if (notif.contains(lifehack)){
                System.out.println("DEVELOPER : Lifehack");
                startLifehack();
            }

            if (notif.contains(solusi)){
                System.out.println("DEVELOPER : Solusi");
                // Launch Tasklist Detail (Tasklist)
                startSolusi();
            }

            if (notif.contains(kamus)){
                System.out.println("DEVELOPER : Kamus");
                startKamus();
            }
    }

    private void startLifehack() {
        Intent intent = new Intent(application, LifeHackAct.class)
                .setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
        application.startActivity(intent);
    }

    private void startSolusi() {
        Intent intent = new Intent(application, SolutionAct.class)
                .setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
        application.startActivity(intent);
    }

    private void startKamus() {
        Intent intent = new Intent(application, KamusAct.class)
                .setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
        application.startActivity(intent);
    }

    private void startMain() {
        Intent intent = new Intent(application, MainActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
        application.startActivity(intent);
    }
}