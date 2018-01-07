package com.example.paulp.hangouts;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by paulp on 12/30/2017.
 */

public class MessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String message = "";
        String title = "";
        if (remoteMessage.getData().size() > 0) {
            message = remoteMessage.getData().get("message");
            title = remoteMessage.getData().get("title");
            sendNotification(message, title);
        }
    }

    private void sendNotification(String message, String title){

        NotificationCompat.Builder notBuilder = new NotificationCompat.Builder(this)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.first)
                .setAutoCancel(true);

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, notBuilder.build());
    }
}
