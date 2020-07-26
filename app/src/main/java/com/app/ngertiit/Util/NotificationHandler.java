package com.app.ngertiit.Util;

import android.app.Application;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.app.ngertiit.KamusAct;
import com.app.ngertiit.KontenLifehackAct;
import com.app.ngertiit.KontenSolusiAct;
import com.app.ngertiit.LifeHackAct;
import com.app.ngertiit.MainActivity;
import com.app.ngertiit.SolutionAct;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.onesignal.OSNotificationOpenResult;
import com.onesignal.OneSignal;

import org.json.JSONException;
import org.json.JSONObject;

public class NotificationHandler implements OneSignal.NotificationOpenedHandler {

    private MainActivity application;

    private String titleNotif;

    private String idTask;

    private String idArtikelString;

    private String tag;

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
        String lifehack = "lifeh";
        String kamus = "kamus";
        String solusi = "sols";
        String notif = result.notification.payload.title;

        System.out.println("Notif Develop : " + notif);

        try {
            idTask = gson.toJson(result.notification.payload.additionalData);
            JSONObject addDataJson = new JSONObject(idTask);
            JSONObject addDataObj = addDataJson.getJSONObject("nameValuePairs");
            idArtikelString = addDataObj.getString("id");
            tag = addDataObj.getString("tag");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        titleNotif = result.notification.payload.title;
        System.out.println("Ini additional data " + tag);

            if (tag.contains(lifehack)){
                System.out.println("DEVELOPER : Lifehack");
                startLifehack();
            } else if (tag.contains(solusi)){
                System.out.println("DEVELOPER : Solusi");
                // Launch Tasklist Detail (Tasklist)
                startSolusi();
            } else if (tag.contains(kamus)){
                System.out.println("DEVELOPER : Kamus");
                startKamus();
            } else {
                startMain();
            }
    }

    private void startLifehack() {
        Intent intent = new Intent(application, KontenLifehackAct.class)
                .setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("idArtikel", idArtikelString);
        application.startActivity(intent);
    }

    private void startSolusi() {
        Intent intent = new Intent(application, KontenSolusiAct.class)
                .setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("idArtikel", idArtikelString);
        application.startActivity(intent);
    }

    private void startKamus() {
        Intent intent = new Intent(application, KamusAct.class)
                .setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent.putExtra("idArtikel", idArtikelString);
        application.startActivity(intent);
    }

    private void startMain() {
        Intent intent = new Intent(application, MainActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
        application.startActivity(intent);
    }
}